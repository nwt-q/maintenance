package top.ptcc9.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.DataBase;
import top.ptcc9.controller.vo.KnownledgeRepVO;
import top.ptcc9.entity.Knowledgebase;
import top.ptcc9.mapper.KnowledgebaseMapper;
import top.ptcc9.service.KnowledgebaseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static top.ptcc9.common.State.ERROR_WITH_NOTIFY;
import static top.ptcc9.common.State.SUCCESS;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class KnowledgeBaseController {

    @Resource
    @Lazy
    private KnowledgebaseMapper knowledgebaseService;

    @Resource
    @Lazy
    private KnowledgebaseService knowledgeBaseService;

    // pdf储存目录
    private static final String HOMEWORK_DIR = "static/knowledge/";

    // 同步锁对象
    private final Object DataLock = new Object();

    // 文件扩展名
    private static final String EXTENSION = "pdf";

    // 允许上传的图片格式
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");


    // 图片存储目录
    private String UPLOAD_DIR = "static/images";

    // 日期时间格式化器
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 上传知识库
    @PostMapping(value = "/upload")
    public R<String> upload(@ModelAttribute DataBase dataBase) {
       String message =  knowledgeBaseService.upload(dataBase);
        return R.build(SUCCESS,message);
    }



    // 下载知识库
    @GetMapping("/downloadHomework")
    public void downloadHomework(HttpServletResponse response) throws IOException {
        File homeworkDir = new File(HOMEWORK_DIR);

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"homework.zip\"");

        System.out.println("[INOF:] 😘徒手打印日志信息: 压缩包👍.......");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            File[] files = homeworkDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(file.toPath(), zipOut);
                    zipOut.closeEntry();
                }
            }
        }
    }

    // 分页查询知识库
    @GetMapping("/knowledgebase")
    public R<List<Knowledgebase>> getKnowledgebase(KnownledgeRepVO knownledgeRepVO) {
        if(knownledgeRepVO.getPage() != null && knownledgeRepVO.getPage() != 0) knownledgeRepVO.setPage((knownledgeRepVO.getPage() - 1));
        return R.build(SUCCESS,knowledgebaseService.selectByPage(knownledgeRepVO.getName(),
                knownledgeRepVO.getPage(), knownledgeRepVO.getPageSize(),
                knownledgeRepVO.getStartTime(), knownledgeRepVO.getEndTime()));
    }

    // 根据文件地址本地文件地址下载
    @GetMapping("/downloadPdf")
    public void downloadPdf(HttpServletResponse response,@Param("path") String path) throws IOException {
        File file = new File(HOMEWORK_DIR + path);

        // 检查文件是否存在
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 根据名称查询数据,更新数据状态
        Knowledgebase knowledgebase = knowledgebaseService.selectByPath(path);
        knowledgebase.setDownload(knowledgebase.getDownload() + 1);
        knowledgebaseService.updateById(knowledgebase);

        // 设置响应头
        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + knowledgebase.getFilename() + "\"");

        // 复制文件内容到响应输出流
        try (InputStream in = Files.newInputStream(file.toPath());
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            // 记录异常并返回错误
            System.err.println(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 删除知识库
    @DeleteMapping("/dknowledgebase")
    public R<String> deleteKnowledgebase(@Param("id") String id) {
        try {
            knowledgebaseService.deleteByIdt(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return R.build(SUCCESS,"删除成功");
    }

    /**
     * 上传图片
     */
    @PostMapping("/uploadimage")
    public R<?> uploadImage(@RequestBody MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);

        // 获取原始文件名
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFilename == null || originalFilename.isEmpty()) {
            return R.build(ERROR_WITH_NOTIFY, "文件名格式不正确");
        }

        // 获取文件扩展名并验证
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            R.build(ERROR_WITH_NOTIFY, "不支持的图片格式，仅支持: " + String.join(", ", ALLOWED_EXTENSIONS));
        }

        System.out.println(formattedTime + "  [INFO:] 😎 上传图片: " + originalFilename);

        // 生成唯一文件名，避免重名
        String uniqueFilename = UUID.randomUUID().toString() + "." + extension;
        Path uploadPath = Paths.get(UPLOAD_DIR);

        try {
            // 确保上传目录存在
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path destPath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(formattedTime + "  [INFO:] 😍 图片保存成功: " + uniqueFilename);

            // 返回成功信息和图片访问路径
            return R.build(SUCCESS, new ImageResponse(
                    uniqueFilename,
                    "/api/images/download/" + uniqueFilename,
                    originalFilename
            ));

        } catch (IOException e) {
            e.printStackTrace();
            return R.build(ERROR_WITH_NOTIFY, "图片上传失败");
        }
    }

    /**
     * 根据文件名下载图片
     */
    @GetMapping("/downloadImage/{filename:.+}")
    public R<?> downloadImage(@PathVariable String filename) {
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);

        System.out.println(formattedTime + "  [INFO:] 📥 请求下载图片: " + filename);

        Path filePath = Paths.get(UPLOAD_DIR, filename);

        if (!Files.exists(filePath)) {
            R.build(ERROR_WITH_NOTIFY, "图片不存在");
        }

        try {
            // 确定图片的MIME类型
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // 读取图片内容并返回
            byte[] imageBytes = Files.readAllBytes(filePath);

            return R.build(SUCCESS,imageBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return R.build(ERROR_WITH_NOTIFY, "图片下载失败");
        }
    }

    // 图片响应类
    static class ImageResponse {
        private String filename;
        private String url;
        private String originalFilename;

        public ImageResponse(String filename, String url, String originalFilename) {
            this.filename = filename;
            this.url = url;
            this.originalFilename = originalFilename;
        }

        // Getters
        public String getFilename() { return filename; }
        public String getUrl() { return url; }
        public String getOriginalFilename() { return originalFilename; }
    }
}

package top.ptcc9.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
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
import java.time.LocalDateTime;
import java.util.List;
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

    private static final String HOMEWORK_DIR = "static/knowledge/";
    private final Object DataLock = new Object();

    // 上传知识库
    @PostMapping(value = "/upload")
    public R<String> upload(@ModelAttribute DataBase dataBase) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println( now + "  [INOF:] 😘 徒手打印日志信息: 文件上传👍.......");

        if(dataBase.getFile() != null) {
            // 解析学号和姓名
            String originalFilename = dataBase.getFile().getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty())
                return R.build(ERROR_WITH_NOTIFY, "文件名格式不正确");

            // 去除文件扩展名
            String baseName = originalFilename.replaceFirst("[.][^.]+$", "");

            System.out.println(now + "  [INOF:] 😘 徒手打印日志信息: " + originalFilename + " 上传一个文件");

            // 保存文件
            Path uploadPath = Paths.get(HOMEWORK_DIR);

            System.out.println(now + "  [INOF:] 😘 徒手打印日志信息: 开始保存文件信息😎.........");


            try {
                if (!Files.exists(uploadPath)) {
                    //将内存的文件移动到指定的地方
                    Files.createDirectories(uploadPath);
                }
                Path destPath = uploadPath.resolve(originalFilename);
                dataBase.getFile().transferTo(destPath);
                System.out.println(now + "  [INOF:] 😘 徒手打印日志信息: 文件保存完毕😍........");
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized (DataLock) {
                try {
                    // 构建数据
                    if (dataBase.getId() == null) {
                        Knowledgebase knowledgebase = new Knowledgebase();
                        knowledgebase.setFilename(originalFilename);
                        knowledgebase.setPath(uploadPath + originalFilename);
                        knowledgebase.setCreateTime(now);
                        knowledgebase.setUpdataTime(now);
                        // 将数据保存到数据库
                        knowledgebase.setCreater("admin");
                        knowledgebase.setUpdater("admin");
                        knowledgebase.setDescription(dataBase.getDescription());
                        knowledgebase.setDownload(0L);
                        knowledgebaseService.insert(knowledgebase);
                    } else {
                        Knowledgebase knowledgebase = knowledgebaseService.selectById(dataBase.getId());
                        knowledgebase.setFilename(originalFilename);
                        knowledgebase.setPath(uploadPath + "\\" + originalFilename);
                        knowledgebase.setUpdataTime(now);
                        knowledgebase.setDescription(dataBase.getDescription());
                        knowledgebaseService.updateById(knowledgebase);
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Knowledgebase knowledgebase = knowledgebaseService.selectByIdy(dataBase.getName());
            if(knowledgebase == null) {
                return R.build(ERROR_WITH_NOTIFY,"文件不存在");
            }
            knowledgebase.setUpdataTime(now);
            knowledgebase.setDescription(dataBase.getDescription());
            knowledgebaseService.updateById(knowledgebase);
        }
        System.out.println( now + "  [INOF:] 😘 徒手打印日志信息: 系统又处理了一个请求真棒🥰" );
        return R.build(SUCCESS,"文件上传成功");
    }

    // 下载知识库
    @GetMapping("/downloadHomework")
    public void downloadHomework(HttpServletResponse response) throws IOException {
        File homeworkDir = new File(HOMEWORK_DIR);

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"homework.zip\"");

        System.out.println("[INOF:] 😘徒手打印日志信息: 下载作业压缩包👍.......");

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
}

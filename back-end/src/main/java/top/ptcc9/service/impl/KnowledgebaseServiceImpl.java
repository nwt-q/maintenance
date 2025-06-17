package top.ptcc9.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.DataBase;
import top.ptcc9.entity.Knowledgebase;
import top.ptcc9.mapper.KnowledgebaseMapper;
import org.springframework.stereotype.Service;
import top.ptcc9.service.KnowledgebaseService;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static top.ptcc9.common.State.ERROR_WITH_NOTIFY;

/**
* @author NWT
* @description 针对表【KnowledgeBase】的数据库操作Service实现
* @createDate 2025-06-09 15:41:55
*/
@Service
public class KnowledgebaseServiceImpl extends ServiceImpl<KnowledgebaseMapper, Knowledgebase>
    implements KnowledgebaseService {

    @Resource
    @Lazy
    private KnowledgebaseMapper knowledgebaseMapper;

    // pdf储存目录
    private static final String HOMEWORK_DIR = "static/knowledge/";

    // 同步锁对象
    private final Object DataLock = new Object();

    // 文件扩展名
    private static final String EXTENSION = "pdf";


    @Override
    public String upload(DataBase dataBase) {
        LocalDateTime now = LocalDateTime.now();

        if(dataBase.getFile() != null) {
            String originalFilename = dataBase.getFile().getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty())
                return "文件名格式不正确";

            // 获取文件扩展名
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            if(!suffix.equals(EXTENSION)) {
                return "文件格式不正确";
            }

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
                        knowledgebaseMapper.insert(knowledgebase);
                    } else {
                        Knowledgebase knowledgebase = knowledgebaseMapper.selectById(dataBase.getId());
                        knowledgebase.setFilename(originalFilename);
                        knowledgebase.setPath(uploadPath + "\\" + originalFilename);
                        knowledgebase.setUpdataTime(now);
                        knowledgebase.setDescription(dataBase.getDescription());
                        knowledgebaseMapper.updateById(knowledgebase);
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Knowledgebase knowledgebase = knowledgebaseMapper.selectByIdy(dataBase.getName());
            if(knowledgebase == null) {
                return"文件不存在";
            }
            knowledgebase.setUpdataTime(now);
            knowledgebase.setDescription(dataBase.getDescription());
            knowledgebaseMapper.updateById(knowledgebase);
        }

        return "上传成功";
    }
}





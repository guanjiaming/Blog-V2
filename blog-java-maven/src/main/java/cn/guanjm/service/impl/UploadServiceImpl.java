package cn.guanjm.service.impl;

import cn.guanjm.common.config.UploadProperties;
import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {
    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private UploadProperties prop;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if(!prop.getAllowTypes().contains(contentType)) {
                throw new UmException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null) {
                throw new UmException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");

            Random random = new Random();

            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

            File dest = new File("/Users/guanjiaming/Documents/project/blog2.0/blog-java-maven/upload/",
                    format.format(new Date()) + "-" +random.nextInt(9) + "." +  suffix);
            file.transferTo(dest);
            return dest.getPath();
        } catch (IOException e) {
            log.error("图片上传失败：", e);
            throw new UmException(ExceptionEnum.INVALID_FILE_TYPE);
        }
    }
}

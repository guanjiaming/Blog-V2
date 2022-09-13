package cn.guanjm.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "um.upload")
public class UploadProperties {
    private String baseURL;
    private List<String> allowTypes;
}

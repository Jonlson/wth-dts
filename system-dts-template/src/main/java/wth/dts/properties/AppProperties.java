package wth.dts.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private AliyunDts aliyunDts;

    private DtsMysqlStore dtsMysqlStore;

    @Data
    public static class AliyunDts {
        private String brokerUrl;

        private String topic;

        private String groupId;

        private String username;

        private String password;

        private String checkPoint;
    }

    @Data
    public static class DtsMysqlStore {
        private String url;

        private String username;

        private String password;
    }
}

package wth.dts.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import wth.dts.properties.AppProperties;
import wth.dts.service.DTSConsumerSubscribeService;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class AppAutoConfiguration {
    @Resource
    private AppProperties appProperties;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public void init() {
        DTSConsumerSubscribeService consumerDemo = new DTSConsumerSubscribeService(
                appProperties.getAliyunDts().getBrokerUrl(),
                appProperties.getAliyunDts().getTopic(),
                appProperties.getAliyunDts().getGroupId(),
                appProperties.getAliyunDts().getUsername(),
                appProperties.getAliyunDts().getPassword(),
                appProperties.getAliyunDts().getCheckPoint(),
                appProperties.getDtsMysqlStore().getUrl(),
                appProperties.getDtsMysqlStore().getUsername(),
                appProperties.getDtsMysqlStore().getPassword(),
                kafkaTemplate
        );
        consumerDemo.start();
    }
}

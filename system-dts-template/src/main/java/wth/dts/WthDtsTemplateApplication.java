package wth.dts;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"wth.dts"})
public class WthDtsTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(WthDtsTemplateApplication.class, args);
    }
}

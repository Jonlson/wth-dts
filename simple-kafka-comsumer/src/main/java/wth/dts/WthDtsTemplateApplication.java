package wth.dts;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@ComponentScan(basePackages = "wth.dts")
@SpringBootApplication(scanBasePackages = {"wth.dts"})
public class WthDtsTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(WthDtsTemplateApplication.class, args);
    }
}

package wth.dts.listener;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.DestinationTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import wth.dts.business.BizEsDocUserService;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class EsDocUserListener {

    @Resource
    private BizEsDocUserService bizEsDocUserService;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "es-doc-user";

    @KafkaListener(topics = TOPIC)
    public void execute(ConsumerRecord<?, ?> record, Acknowledgment ack ) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("topic: ").append(record.topic()).append("\n");
        sb.append("key  : ").append(record.key()).append("\n");
        sb.append("value: ").append(record.value().toString()).append("\n");

        log.info(sb.toString());

        bizEsDocUserService.execute((String) record.key(), (String) record.value());

        ack.acknowledge();

    }

    // 异步消费方法:
    @Bean
    public void startKafkaConsumer() {
        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "your-group-id");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("enable.auto.commit", "false");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList("es-doc-user"));

            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<String, String> record : records) {
                        // 异步提交
                        consumer.commitAsync((offsets, exception) -> {
                            if (exception != null) {
                                System.err.println("异步提交失败：" + exception.getMessage());
                                kafkaTemplate.send("user-topic", record.key(), JSON.toJSONString(record));
                            } else {
//                            log.error("异步提交成功：" + offsets);
                            }
                        });
                        // 处理逻辑
                        System.out.printf("收到消息: %s => %s%n", record.key(), record.value());
                        try {
                            bizEsDocUserService.execute(record.key(), record.value());
                            kafkaTemplate.send("user-topic", record.key(), JSON.toJSONString(record));
                        } catch (Exception e) {
                            log.error("处理失败：{}", e.getMessage(), e);
                            kafkaTemplate.send("user-topic", record.key(), JSON.toJSONString(record));
                        }
                    }


                }
            } finally {
                consumer.commitSync(); // 最后提交一次
                consumer.close();
            }
        }).start();
    }

}

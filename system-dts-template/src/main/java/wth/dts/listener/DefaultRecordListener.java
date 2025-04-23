package wth.dts.listener;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dts.subscribe.clients.common.RecordListener;
import com.aliyun.dts.subscribe.clients.record.DefaultUserRecord;
import com.aliyun.dts.subscribe.clients.record.OperationType;
import com.aliyun.dts.subscribe.clients.record.RowImage;
import com.aliyun.dts.subscribe.clients.record.value.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DefaultRecordListener implements RecordListener {

    private final KafkaTemplate<String,String>  kafkaTemplate;

    public DefaultRecordListener(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void consume(DefaultUserRecord record) {
        OperationType operationType = record.getOperationType();
        if (operationType.equals(OperationType.INSERT) || operationType.equals(OperationType.UPDATE) || operationType.equals(OperationType.DELETE)) {
            //executorService.submit(() -> processData(record));
            try {
                processData(record);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private void processData(DefaultUserRecord record) {
        Map<String, String> newMap = null;
        RowImage afterRowImage = record.getAfterImage();

        // 获取更新后的数据
        if (afterRowImage != null) {
            Map<String, Value> afterRowImageMap = afterRowImage.toMap(key -> key, value -> value);
            newMap = afterRowImageMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
        } else {
            return;
        }

        log.info(JSON.toJSONString(newMap));

        String id = newMap.get("id");

        switch (record.getAvroRecord().objectName) {
            // 根据不同的表名发送到不同的topic
            case "user":
                kafkaTemplate.send("user-topic", id, JSON.toJSONString(newMap));
                break;
        }

    }
}

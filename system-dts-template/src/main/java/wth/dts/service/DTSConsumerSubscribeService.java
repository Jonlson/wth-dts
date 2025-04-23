package wth.dts.service;

import com.aliyun.dts.subscribe.clients.ConsumerContext;
import com.aliyun.dts.subscribe.clients.DTSConsumer;
import com.aliyun.dts.subscribe.clients.DefaultDTSConsumer;
import com.aliyun.dts.subscribe.clients.common.RecordListener;
import com.aliyun.dts.subscribe.clients.record.OperationType;
import org.springframework.kafka.core.KafkaTemplate;
import wth.dts.UserMetaStore;
import wth.dts.listener.DefaultRecordListener;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class DTSConsumerSubscribeService {
    private final DTSConsumer dtsConsumer;

    public DTSConsumerSubscribeService(String brokerUrl, String topic, String groupId, String userName, String password, String checkpoint, String storeUrl, String storeUsername, String storePassword, KafkaTemplate<String, String> kafkaTemplate) {

        this.dtsConsumer = initDTSClient(brokerUrl, topic, groupId, userName, password, checkpoint, storeUrl, storeUsername, storePassword, kafkaTemplate);
    }

    private DTSConsumer initDTSClient(String brokerUrl, String topic, String groupId, String userName, String password, String initCheckpoint, String storeUrl, String storeUsername, String storePassword, KafkaTemplate<String, String> kafkaTemplate) {

        ConsumerContext consumerContext = new ConsumerContext(brokerUrl, topic, groupId, userName, password, initCheckpoint, ConsumerContext.ConsumerSubscribeMode.SUBSCRIBE);

        //add user meta store to manage checkpoint by yourself
        consumerContext.setUserRegisteredStore(new UserMetaStore(storeUrl, storeUsername, storePassword));
        consumerContext.setForceUseCheckpoint(false);

        DTSConsumer dtsConsumer = new DefaultDTSConsumer(consumerContext);

        dtsConsumer.addRecordListeners(buildRecordListener(kafkaTemplate));

        return dtsConsumer;
    }

    public static Map<String, RecordListener> buildRecordListener(KafkaTemplate<String, String> kafkaTemplate) {
        // user can impl their own listener
        RecordListener mysqlRecordPrintListener = record -> {

            OperationType operationType = record.getOperationType();

            if (operationType.equals(OperationType.INSERT)
                    || operationType.equals(OperationType.UPDATE)
                    || operationType.equals(OperationType.DELETE)
                    || operationType.equals(OperationType.HEARTBEAT)) {

                // consume record
                RecordListener recordPrintListener = new DefaultRecordListener(kafkaTemplate);

                recordPrintListener.consume(record);

                record.commit("");
            }
        };

        return Collections.singletonMap("mysqlRecordPrinter", mysqlRecordPrintListener);
    }

    public void start() {
        System.out.println("Start DTS subscription client...");

        dtsConsumer.start();
    }
}

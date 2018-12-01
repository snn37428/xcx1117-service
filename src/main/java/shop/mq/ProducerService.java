package shop.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

import java.util.Properties;

public class ProducerService {

    private static Properties mqConfig;

    public void init() {

        System.out.println("000000000000000" + mqConfig);
    }

    public static void send(String code) {
        Producer producer = ONSFactory.createProducer(mqConfig);
        producer.start();
        Message msg = new Message("TY_20181201_CONTROLLER", "CC", code.getBytes());
        SendResult sendResult = producer.send(msg);
        System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());
        producer.shutdown();
    }

    public static void setMqConfig(Properties mqConfig) {
        ProducerService.mqConfig = mqConfig;
    }
}

package shop.mq;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import org.apache.log4j.Logger;

import java.util.Properties;

public class ProducerService {

    private static final Logger logger = Logger.getLogger(ProducerService.class);

    private static Properties mqConfig;

    public static void send(String code) {
        Producer producer = ONSFactory.createProducer(mqConfig);
        producer.start();
        Message msg = new Message("TY_20181201_CONTROLLER", "919", code.getBytes());
        SendResult sendResult = producer.send(msg);
        logger.info("product mq is success. Message ID is: " + JSONObject.toJSONString(sendResult));
        producer.shutdown();
    }

    public static void setMqConfig(Properties mqConfig) {
        ProducerService.mqConfig = mqConfig;
    }
}

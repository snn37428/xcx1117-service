package shop.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

public class ProducerService {

    private static Properties mqConfig;

    public void init() {

        System.out.println("000000000000000" + mqConfig);
    }


    public static void main(String[] args) {
        Properties properties = new Properties();
        // 您在 MQ 控制台创建的 Producer ID
        properties.put(PropertyKeyConst.ProducerId, "XXX");
        // 鉴权用 AccessKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, "XXX");
        // 鉴权用 SecretKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "XXX");
        // 设置 TCP 接入域名，进入 MQ 控制台的生产者管理页面，在右侧操作列单击获取接入点获取
        // 此处以公有云公网地域接入点为例
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        System.out.println("11111111111111111" + properties);
//        Producer producer = ONSFactory.createProducer(properties);
//        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
//        producer.start();
//        //循环发送消息
//        while (true) {
//            Message msg = new Message( //
//                    // 在控制台创建的 Topic，即该消息所属的 Topic 名称
//                    "TopicTestMQ",
//                    // Message Tag,
//                    // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//                    "TagA",
//                    // Message Body
//                    // 任何二进制形式的数据， MQ 不做任何干预，
//                    // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
//                    "Hello MQ".getBytes());
//            // 设置代表消息的业务关键属性，请尽可能全局唯一，以方便您在无法正常收到消息情况下，可通过 MQ 控制台查询消息并补发
//            // 注意：不设置也不会影响消息正常收发
//            msg.setKey("ORDERID_100");
//            // 发送消息，只要不抛异常就是成功
//            // 打印 Message ID，以便用于消息发送状态查询
//            SendResult sendResult = producer.send(msg);
//            System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());
//        }
//        // 在应用退出前，可以销毁 Producer 对象
//        // 注意：如果不销毁也没有问题
    }


    public static void setMqConfig(Properties mqConfig) {
        ProducerService.mqConfig = mqConfig;
    }
}

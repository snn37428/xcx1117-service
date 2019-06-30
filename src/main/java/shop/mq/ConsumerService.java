package shop.mq;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import shop.domain.Instruct;

import javax.annotation.PostConstruct;
import java.util.Properties;

public class ConsumerService {

    private static final Logger logger = Logger.getLogger(ConsumerService.class);

    private static Properties mqConfig;

    @PostConstruct
    public void read() {
//        Consumer consumer = ONSFactory.createConsumer(mqConfig);
//        consumer.subscribe("TY_20181201_CONTROLLER", "919", new MessageListener() {
//            public Action consume(Message message, ConsumeContext context) {
//                Instruct rs = JSONObject.parseObject(new String(message.getBody()), Instruct.class);
//                if (rs == null) {
//                    logger.error("read : rs null");
//                }
//                try {
//                   HttpClientGet("https://tianyuanfarm.com/cb/back", new String(message.getBody()));
////                    HttpClientGet("http://127.0.0.1:8081/cb/back", new String(message.getBody()));
//                } catch (Exception e) {
//                    logger.info("HttpClientGet is Exception" + e);
//                }
//
//                logger.info("Consumer mq is success: " + JSONObject.parseObject(new String(message.getBody())));
//                return Action.CommitMessage;
//            }
//        });
//        consumer.start();
        logger.info("read : onsumer ----------------- Started");
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param param
     * @throws Exception
     */
    public static void HttpClientGet(String url, String param) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("data", param);
        HttpGet httpGet = new HttpGet(builder.build());
        CloseableHttpResponse Response = client.execute(httpGet);
        HttpEntity entity = Response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");
        logger.info("post msg: -------------- " + httpGet.getURI().toString());
        Response.close();
    }

    public static void setMqConfig(Properties mqConfig) {
        ConsumerService.mqConfig = mqConfig;
    }

}

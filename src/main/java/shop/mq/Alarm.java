package shop.mq;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import shop.dao.ConfigMapper;
import shop.domain.AlarmDo;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Alarm {

    private static final Logger logger = Logger.getLogger(Alarm.class);

    private static List<String> listPhone = new ArrayList<String>();

    @Autowired
    private ConfigMapper configMapper;

    private static String man = "0";

    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static final String accessKeyId = "LTAIpy8aiA0Ngl1t";
    private static final String accessKeySecret = "EiW1cYfR8CrFnsSVLrFBSlQ1jeVl60";


    @PostConstruct
    public void init() {

        try {
            AlarmDo alarmInfo = configMapper.selectMan();
            if (alarmInfo == null) {
                logger.warn("----init: alarmInfo is null");
            }
            String manSwitch = alarmInfo.getManSwitch();
            if (StringUtils.isBlank(alarmInfo.getListMobies()) || StringUtils.isBlank(manSwitch)) {
                logger.warn("----init: phones is blank or manSwitch is blank");
            }
            setMan(manSwitch);
            String phones = alarmInfo.getListMobies();
            String[] p = phones.toString().split("\\;");
            if (p.length == 0) {
                logger.warn("----init: phones length = 0");
            }
            List<String> pl = new ArrayList<String>();
            for (int i = 0; i < p.length; i++) {
                pl.add(p[i]);
            }
            logger.info("----init: phones = " + JSONObject.toJSONString(pl));
            setListPhone(pl);
        } catch (Exception e) {
            logger.error("----init is Exception");
        }

    }

    /**
     * 定时更新配置
     */
    public void taskAlarmConfig() {
        try {
            init();
            logger.info("----更新云库Alarm配置，至内存完毕！");
        } catch (Exception e) {
            logger.warn("----taskAlarmConfig is Exception" + e);
        }
    }

    /**
     * 通知消息
     *
     * @param code
     */
    public void sendAlarmInfo(int code, String address, String status) {

        if ("0".equals(man)) {
            logger.info("====master-switch = 0 报警信息不触发");
            return;
        }

        switch (code) {
            case 1: {
                Map<String, String> mapMsg = new HashMap<String, String>(2);
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mapMsg.put("dot", address);
                mapMsg.put("var", status);
                String template = "SMS_152211436";
                String singName = "甜圆云控制";
                send(mapMsg, template, singName);
                sendDDingAlarmInfo("甜圆云控制：控制状态变更，控制点"+address +"状态置为"+status +"");
                break;
            }
            case 2: {
                String template = "SMS_152543131";
                String singName = "甜圆云通知";
                send(new HashMap<String, String>(2), template, singName);
                sendDDingAlarmInfo("甜圆云通知：无人值守，单位小时内云库没有数据更新。生产基地设备掉电或网络异常，请处理。");
                break;
            }
        }
    }

    /**
     * 短信循环电话号码报警
     */
    public void send(Map<String, String> mapMsg, String template, String singName) {
        try {
            for (String p : listPhone) {
                if (StringUtils.isNotBlank(p)) {
                    SendSmsResponse response = sendSms(p, mapMsg, template, singName);
                    logger.info("短信接口-------------------------");
                    logger.info("Code:" + response.getCode());
                    logger.info("Message:" + response.getMessage());
                    //logger.info("RequestId: is Exception" + response.getRequestId());
                    //logger.info("BizId: is Exception" + response.getBizId());
                } else {
                    logger.error("send  phone  is  null");
                }
            }
        } catch (Exception e) {
            logger.error("send is Exception" + e);
        }
    }

    /**
     * 发送钉钉消息
     *
     * @param msg
     */
    public void sendDDingAlarmInfo(String msg) {

        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(
                    "https://oapi.dingtalk.com/robot/send?access_token=cfd308e57ae0dac4df80d85cb13d2d8d9324a718db31e53369fcd75349ba2534");
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            //String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"测试消息类型？\"}}";
            // 内容
            Map<String, Object> contentMap = new HashMap<String, Object>();
            contentMap.put("content", msg);
            // at
            Map<String, Object> atMap = new HashMap<String, Object>();
            // atMap.put("atMobiles", listPhone);
            // 主体
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msgtype", "text");
            map.put("text", JSONObject.toJSONString(contentMap));
            map.put("at", atMap);
            StringEntity strEnt = new StringEntity(JSONObject.toJSONString(map), "utf-8");
            httppost.setEntity(strEnt);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                //System.out.println(result);
            }
        } catch (IOException e) {
            logger.error("sendQuantiyAlarmInfo  is  Exception" + e);
        }
    }

    /**
     * 发短信
     *
     * @param phone
     * @return
     * @throws ClientException
     */
    private SendSmsResponse sendSms(String phone, Map<String, String> mapMsg, String sms_template, String singName) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.error("sendSms DefaultProfile.addEndpoint is Exception" + e);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(singName);
        request.setTemplateCode(sms_template);
        request.setTemplateParam(JSONObject.toJSONString(mapMsg));
        request.setOutId("yourOutId");
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            logger.error("sendSms is Exception" + e);
        }
        return sendSmsResponse;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public static List<String> getListPhone() {
        return listPhone;
    }

    public static void setListPhone(List<String> listPhone) {
        Alarm.listPhone = listPhone;
    }

}

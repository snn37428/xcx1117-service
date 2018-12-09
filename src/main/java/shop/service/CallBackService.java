package shop.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import shop.domain.Instruct;
import shop.domain.SendTemplateMessage;
import shop.domain.TemplateData;
import shop.domain.WXLoginFinal;
import shop.uitl.Md5Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CallBackService {


    private static final Logger logger = Logger.getLogger(CallBackService.class);
    private static String WXTemplateURL;
    private static String WXTemplateCode;

    /**
     * 发送模板信息
     */
    public void sendTemplateMsg(String msg) {
        try {
            Instruct rs = JSONObject.parseObject(msg, Instruct.class);
            if (rs == null) {
                logger.error("sendTemplateMsg msg is null");
                return;
            }
            Map<String,TemplateData> map = new HashMap<String,TemplateData>();
            map.put("keyword1",new TemplateData(rs.getpDesc()));
            map.put("keyword2",new TemplateData(String.valueOf(rs.getModbusAddr())));
            map.put("keyword3",new TemplateData(rs.getStatus() == 1 ? "已开启":"以关闭"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            map.put("keyword4",new TemplateData(date));
            String r = Md5Utils.convertMD5(rs.getToken());
            String x = Md5Utils.convertMD5(r);
            sendTemplateMessage(x, "", rs.getFromid(), map);
        } catch (Exception e) {
            logger.error("sendTemplateMsg msg is Exception" + e);
        }
    }


    public static JSONObject sendTemplateMessage(String openId, String page, String formid, Map<String, TemplateData> map){
        String accessToken = AccessTokenService.getAccessToken();
        SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
        sendTemplateMessage.setTouser(openId);//openid
        sendTemplateMessage.setTemplate_id(WXLoginFinal.getWXTemplateURL());//templateId
        sendTemplateMessage.setPage(page);
        sendTemplateMessage.setForm_id(formid);
        sendTemplateMessage.setData(map);
        sendTemplateMessage.setEmphasis_keyword("");
        String param =  JSONObject.toJSONString(sendTemplateMessage);
        logger.info("sendTemplateMessage send msg: -------------- " + param);
        pushOneUser(param);
        return null;

    }

    /**
     * 推送
     *
     * @param param
     * @return
     */
    public static String pushOneUser(String param) {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WXTemplateURL);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity strEnt = new StringEntity(param, "utf-8");
            httppost.setEntity(strEnt);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.info("pushOneUser rs : -------------- " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String,TemplateData> map = new HashMap<String,TemplateData>();
        map.put("keyword1",new TemplateData("339208499"));
        map.put("keyword2",new TemplateData("2018年9月30日16:33:44"));
        map.put("keyword3",new TemplateData("***总部"));
        map.put("keyword4",new TemplateData("*****学院"));
        JSONObject js = sendTemplateMessage("o89rs0M0EIzrkiN9Va88mFbQyUdQ", "", "1539830935602",map);
        System.out.println(js);
    }

    public static String getWXTemplateURL() {
        return WXTemplateURL;
    }

    public static void setWXTemplateURL(String WXTemplateURL) {
        CallBackService.WXTemplateURL = WXTemplateURL;
    }

    public static String getWXTemplateCode() {
        return WXTemplateCode;
    }

    public static void setWXTemplateCode(String WXTemplateCode) {
        CallBackService.WXTemplateCode = WXTemplateCode;
    }
}
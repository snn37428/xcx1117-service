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
    private static String wxTemplateCode;

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
            logger.error("=========================================" + rs);
            Map<String,TemplateData> map = new HashMap<String,TemplateData>();
            map.put("keyword1",new TemplateData(rs.getpDesc()));
            map.put("keyword2",new TemplateData(String.valueOf(rs.getModbusAddr())));
            map.put("keyword3",new TemplateData(rs.getStatus() == 1 ? "已开启":"以关闭"));

            logger.error("======================getpDesc===================" + rs.getpDesc());
            logger.error("======================getModbusAddr===================" + rs.getModbusAddr());
            logger.error("======================getStatus===================" + (rs.getStatus() == 1 ? "已开启":"以关闭"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            map.put("keyword4",new TemplateData(date));
            String r = Md5Utils.convertMD5(rs.getToken());
            String x = Md5Utils.convertMD5(r);

            sendTemplateMessage(x, "", rs.getFromid(), map);
            logger.error("======================rs.getToken()===================" + rs.getToken());
            logger.error("======================r===================" + r);
            logger.error("======================x===================" + x);

        } catch (Exception e) {
            logger.error("sendTemplateMsg msg is Exception" + e);
        }
    }


    public static JSONObject sendTemplateMessage(String openId, String page, String formid, Map<String, TemplateData> map){
        String accessToken = AccessTokenService.getAccessToken();
        SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
        sendTemplateMessage.setTouser("okyMN0SIa4m_z39M4iNU4ka0E0AY");//openid
        sendTemplateMessage.setTemplate_id(wxTemplateCode);//templateId
        sendTemplateMessage.setPage(page);
        sendTemplateMessage.setForm_id(formid);
        sendTemplateMessage.setData(map);
        sendTemplateMessage.setEmphasis_keyword("");
        String param =  JSONObject.toJSONString(sendTemplateMessage);
        logger.info("sendTemplateMessage send msg: --------00000000000------ " + param);
        pushOneUser(param, accessToken);
        return null;

    }

    /**
     * 推送
     *
     * @param param
     * @return
     */
    public static String pushOneUser(String param, String accessToken) {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WXLoginFinal.getWXTemplateURL() + "?access_token=" + accessToken);
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
//        Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//        map.put("keyword1",new TemplateData("339208499"));
//        map.put("keyword2",new TemplateData("2018年9月30日16:33:44"));
//        map.put("keyword3",new TemplateData("***总部"));
//        map.put("keyword4",new TemplateData("*****学院"));
//        JSONObject js = sendTemplateMessage("o89rs0M0EIzrkiN9Va88mFbQyUdQ", "", "1539830935602",map);
//        System.out.println(js);
        String  er = "043f91295b3582f266abd69c07baa86a";
        String r = Md5Utils.convertMD5(er);
        String x = Md5Utils.convertMD5(r);
        System.out.println(r);
        System.out.println(x);
    }

    public static void setWxTemplateCode(String wxTemplateCode) {
        CallBackService.wxTemplateCode = wxTemplateCode;
    }
}
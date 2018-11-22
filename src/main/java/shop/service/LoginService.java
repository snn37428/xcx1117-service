//package shop.service;
//
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Service;
//import shop.domain.HttpRequest;
//import shop.domain.ResMap;
//import shop.domain.WXLoginFinal;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by songningning1 on 2017/9/9.
// */
//@Service
//public class LoginService {
//
//    private final static Logger log = LogManager.getLogger(LoginService.class);
//
//    public Map in(String code) {
//        try {
//            String params = "appid=" + WXLoginFinal.getWxAppid().trim() + "&secret=" + WXLoginFinal.getwxSecret().trim() + "&js_code=" + code.trim() + "&grant_type=" + WXLoginFinal.getGrant_type().trim();
//            //发送请求获取openId
//            String data = HttpRequest.sendPost(WXLoginFinal.getUrl().trim(), params);
//            if (StringUtils.isBlank(data)) {
//                log.error("获取微信data失败");
//                return ResMap.failedMap();
//            }
//            JSONObject jsonData = JSONObject.parseObject(data);
//            if (jsonData == null) {
//                log.error("获取微信jsonData失败");
//                return ResMap.failedMap();
//            }
//            String openId = jsonData.get("openid").toString().trim();
//            String sessionKey = jsonData.get("session_key").toString().trim();
//            if (StringUtils.isBlank(openId) || StringUtils.isBlank(sessionKey)) {
//                return ResMap.failedMap();
//            }
//            Map resMap = new HashMap();
//            resMap.put("openId", openId);
//            resMap.put("sessionKey", sessionKey);
//            return ResMap.successMap(resMap);
//        } catch (Exception e) {
//            log.error("获取微信openId失败");
//        }
//        return ResMap.failedMap();
//    }
//
//
//}

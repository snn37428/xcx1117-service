package shop.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import shop.domain.HttpRequest;
import shop.domain.WXLoginFinal;

import javax.annotation.PostConstruct;

public class AccessTokenService {

    private static final Logger logger = Logger.getLogger(AccessTokenService.class);

    private static String accessToken;

    @PostConstruct
    public void init() {
        String wxspAppid = WXLoginFinal.getWxAppid();
        String wxspSecret = WXLoginFinal.getwxSecret();
        String grant_type = "client_credential";
        String params = "grant_type=" + grant_type + "&secret=" + wxspSecret + "&appid=" + wxspAppid;
        String sendGet = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        JSONObject jsonData = JSONObject.parseObject(sendGet);
        String accesstoken = (String) jsonData.get("access_token");
        setAccessToken(accesstoken);
        logger.info("AccessTokenService.init accessToken updated : " + accesstoken);
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessTokenService.accessToken = accessToken;
    }
}

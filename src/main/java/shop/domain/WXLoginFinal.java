package shop.domain;

public class WXLoginFinal {

    private static final String wxAppid = "wxd157147d4c79c241";

    private static final String wxSecret = "029a663423724ac6b1b2b9155e7a0367";

    private static final String grant_type = "authorization_code";

    private static final String url = "https://api.weixin.qq.com/sns/jscode2session?";

    private static final int day = 24 * 60 * 60 * 1;

    private static final int timeOut = 3 * getDay();

    public static String getwxSecret() {
        return wxSecret;
    }

    public static String getGrant_type() {
        return grant_type;
    }

    public static String getUrl() {
        return url;
    }

    public static String getWxAppid() {
        return wxAppid;
    }

    public static String getWxSecret() {
        return wxSecret;
    }

    public static int getDay() {
        return day;
    }

    public static int getTimeOut() {
        return timeOut;
    }

}

package shop.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.domain.ResMap;
import shop.service.CallBackService;
import shop.service.LoginService;
import shop.service.VideoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cb")
public class CallBack {

    private final static Logger log = LogManager.getLogger(LoginService.class);

    @Resource
    private CallBackService callBackService;

    @Resource
    private VideoService videoService;

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "back")
    @ResponseBody
    public Map login(@RequestParam String data) {
        if (StringUtils.isEmpty(data)) {
            log.warn("login in null");
            return ResMap.failedMap();
        }
        log.info("CallBack is request ------" + JSONObject.toJSONString(data));
        callBackService.sendTemplateMsg(data);
        return null;
    }

    @RequestMapping(value = "cc")
    @ResponseBody
    public Map cc(@RequestParam String tt, @RequestParam String s) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(tt)) {
            log.warn("Controller Video token is black or ss is black");
        }
        log.info("Controller Video is request ------ token:" + tt + "  s:" + s);
//        videoService.HttpClientGet(tt, s);
      return loginService.getAuthAndRuest(tt, s);
    }

    @RequestMapping(value = "jw")
    @ResponseBody
    public Map jw(@RequestParam String jw) {
        log.info("jw request ------ jw:" + jw);
        return videoService.getTokenByJw(jw);
    }

    /**
     * 控制权限申请
     *
     * @param token
     * @param code
     */
    @RequestMapping(value = "csc")
    @ResponseBody
    public Map csc(@RequestParam String token, @RequestParam String code) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(code)) {
            Map resMap = new HashMap();
            resMap.put("success", false);
            log.warn("csc token is black or ss is black");
            return resMap;
        }
        log.info("csc is request ------ token:" + token + "  code:" + code);
        return loginService.authSC(token, code);
    }

    /**
     * 授权
     *
     * @param token
     */
    @RequestMapping(value = "ck")
    @ResponseBody
    public Map ck(@RequestParam String token) {
        if (StringUtils.isEmpty(token) ) {
            Map resMap = new HashMap();
            resMap.put("success", false);
            log.warn("ck token is black");
            return resMap;
        }
        log.info("ck is request ------ token:" + token );
        return loginService.authCK(token);
    }

    /**
     * 授权
     * @param code
     */
    @RequestMapping(value = "wk")
    @ResponseBody
    public Map wk(@RequestParam String token, @RequestParam String code) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(code)) {
            Map resMap = new HashMap();
            resMap.put("success", false);
            log.warn("cks token is black or ss is black");
            return resMap;
        }
        log.info("csc is request ------ code:" + code);
        return loginService.authCKS("", code);
    }

}

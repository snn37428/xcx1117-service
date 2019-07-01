package shop.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.domain.ResMap;
import shop.service.AlarmService;
import shop.service.LoginService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class Login {

    private final static Logger log = LogManager.getLogger(LoginService.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private AlarmService alarmService;

    @RequestMapping(value = "in")
    @ResponseBody
    public Map login(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            log.warn("login in null");
            return ResMap.failedMap();
        }
        try {
            alarmService.sendDingDingLogin(code);
        } catch (Exception e) {
            log.warn("sendDingDingLogin in exception");
        }
        try {
            return loginService.in(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "token")
    @ResponseBody
    public Map authToken(@RequestParam String t) {
        log.warn("token is controller is start(code mi) =" + t);
        alarmService.sendDingDingToken(t);
        Map map = new HashMap(2);
        map.put("success", false);
        if (StringUtils.isEmpty(t)) {
            log.warn("token in null");
            return map;
        }
        try {
            return loginService.authProduct(t);
        } catch (Exception e) {
            log.error("authToken is Exception", e);
            return map;
        }
    }

}

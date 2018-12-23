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
import shop.service.LoginService;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class Login {

    private final static Logger log = LogManager.getLogger(LoginService.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "in")
    @ResponseBody
    public Map login(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            log.warn("login in null");
            return ResMap.failedMap();
        }
        try {
            return loginService.in(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

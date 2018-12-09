package shop.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import shop.domain.ResMap;
import shop.service.CallBackService;
import shop.service.LoginService;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/cb")
public class CallBack {

    private final static Logger log = LogManager.getLogger(LoginService.class);

    @Resource
    private CallBackService callBackService;

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

}

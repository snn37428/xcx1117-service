package shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.service.InstructService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/c")
public class Instruct {

    @Resource
    private InstructService instructService;

    @RequestMapping(value = "c")
    @ResponseBody
    public void login(@RequestParam String idm, @RequestParam String sd, @RequestParam String token, @RequestParam String fromId, @RequestParam String idd) {

        instructService.write(idm, sd, token, fromId, idd);
    }

}

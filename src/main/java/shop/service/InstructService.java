package shop.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import shop.dao.InstructMapper;
import shop.domain.Instruct;
import shop.mq.Alarm;
import shop.mq.ProducerService;

import javax.annotation.Resource;
import java.util.Date;

public class InstructService {

    private static final Logger logger = Logger.getLogger(InstructService.class);

    @Resource
    private InstructMapper instructMapper;

    @Resource
    private Alarm alarm ;

    public void write(String addrsess, String status) {

        if (StringUtils.isEmpty(addrsess) || StringUtils.isEmpty(status)) {
            logger.warn("write: param is invalid");
            return;
        }

        Instruct instruct = new Instruct();
        instruct.setModbusAddr(Integer.parseInt(addrsess));
        instruct.setStatus(Integer.parseInt(status));
        instruct.setCreated(new Date());

        int rs = instructMapper.insert(instruct);

        if (rs > 0) {
            logger.warn("write: insert is success");
        }
        instruct.setId(instruct.getId());
        try {
//            ProducerService.send(JSONObject.toJSONString(instruct));
        } catch (Exception e) {
            logger.error("write: mq send is failed, msg : " + JSONObject.toJSONString(instruct));
            logger.error(e);
        }
//       alarm.sendAlarmInfo(1,String.valueOf(instruct.getModbusAddr()), String.valueOf(instruct.getStatus()));
        logger.info("write: mq send is success, msg : " + JSONObject.toJSONString(instruct));
    }

}

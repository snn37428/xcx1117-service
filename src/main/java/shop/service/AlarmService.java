package shop.service;

import org.apache.log4j.Logger;
import shop.dao.CellMapper;
import shop.mq.Alarm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: snn
 * @created: 2019-02-18 22:29
 */
public class AlarmService {

    private static final Logger logger = Logger.getLogger(AlarmService.class);

    private static long l = 0L;

    @Resource
    private Alarm alarm;

    @Resource
    private CellMapper cellMapper;

    @PostConstruct
    private void init() {
        try {
            long ll = cellMapper.count();
            if (ll > 0) {
                l = ll;
            }
        } catch (Exception e) {
            logger.error("init is exception : ", e);
        }
    }


    public void task() {
        try {
            long ll = cellMapper.count();
            if (ll <= 0) {
                logger.error("task count is error");
            }
            if (l == ll) {
                alarm.sendAlarmInfo(2, "", "");
                logger.error("alarm is true online is error");
                return;
            }
            l = ll;
        } catch (Exception e) {
            logger.error("task is exception : ", e);
        }
    }

    /**
     * 发送钉钉控制时候的token
     */
    public void sendDingDingToken(String token) {
        alarm.sendDDingAlarmInfo("甜圆权限：token码授权，token=" + token);
    }

    /**
     * 登录消息推送
     */
    public void sendDingDingLogin(String token) {
        alarm.sendDDingAlarmInfo("甜圆登录统计，登录token=" + token);
    }

    /**
     *  申请权限短信报警
     */
    public void sendPhoneMessage(String type, String code){
        Map<String, String> mapMsg = new HashMap<String, String>(2);
        mapMsg.put("type", type);
        mapMsg.put("code", code);
        String template = "SMS_169635073";
        String singName = "甜圆云通知";
        alarm.send(mapMsg, template, singName);
    }

}

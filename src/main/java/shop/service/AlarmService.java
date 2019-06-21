package shop.service;

import org.apache.log4j.Logger;
import shop.dao.CellMapper;
import shop.mq.Alarm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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


    public void task(){
        try {
            long ll = cellMapper.count();
            if (ll <= 0) {
                logger.error("task count is error");
            }
            if(l == ll) {
                alarm.sendAlarmInfo(2,"","");
                logger.error("alarm is true online is error");
                return;
            }
            l = ll;
        } catch (Exception e) {
            logger.error("task is exception : ", e);
        }
    }

}

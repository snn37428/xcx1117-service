package shop.service;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class InstructService {

    private static final Logger logger = Logger.getLogger(InstructService.class);

    public void write(String addrsess, String status) {

        if (StringUtils.isEmpty(addrsess) || StringUtils.isEmpty(status)) {
            logger.warn("write: param is invalid");
            return;
        }

        logger.warn("write: param is invalid");

    }

}

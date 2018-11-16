package shop.service;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import shop.dao.CellMapper;
import shop.dao.ConfigMapper;
import shop.domain.Cell;
import shop.domain.Config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CellService {

    private static final Logger logger = Logger.getLogger(CellService.class);

    private static List<Integer> listConfigAddress = new ArrayList<Integer>();

    @Resource
    private CellMapper cellMapper;
    @Resource
    private ConfigMapper configMapper;

    private static Map<String, String> urlMap;

    @PostConstruct
    public void init() {
        List<Config> rs = configMapper.readConfig();
        if (CollectionUtils.isEmpty(rs)) {
            logger.warn("init rs null");
        }
        for (Config r : rs) {
            listConfigAddress.add(r.getModbusAddr());
        }
        if (CollectionUtils.isEmpty(listConfigAddress)) {
            logger.warn("init is failed");
        }
    }

    public List<Cell> getCell() {
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (Integer i : listConfigAddress) {
                Cell rs = cellMapper.selectByAddress(i);
                if (rs == null || rs.getCreated() == null) {
                    logger.warn("rs is null");
                }
                if (rs.getModel() == 3) {
                    continue;
                }
                if (rs.getModel() == 4) {
                    rs.setImgurl(urlMap.get(rs.getpName()));
                }
                rs.setNow(sdf.format(rs.getCreated()));
                listCell.add(rs);
            }
        } catch (Exception e) {
            logger.error("getCell selectByAddress is exception : " + e);
        }
        return listCell;
    }

    public static Map<String, String> getUrlMap() {
        return urlMap;
    }

    public static void setUrlMap(Map<String, String> urlMap) {
        CellService.urlMap = urlMap;
    }
}

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

    /**
     * 功能码3
     */
    private static List<Integer> listConfigModel3 = new ArrayList<Integer>();

    /**
     * 功能码4
     */
    private static List<Integer> listConfigModel4 = new ArrayList<Integer>();

    /**
     * 功能码1
     */
    private static List<Integer> listConfigModel1 = new ArrayList<Integer>();

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
            if ("3".equals(r.getModel())) {
                listConfigModel3.add(r.getModbusAddr());
            }
            if ("4".equals(r.getModel())) {
                listConfigModel4.add(r.getModbusAddr());
            }
            if ("1".equals(r.getModel())) {
                listConfigModel1.add(r.getModbusAddr());
            }
        }
        if (CollectionUtils.isEmpty(listConfigModel3) ||
                CollectionUtils.isEmpty(listConfigModel4) ||
                CollectionUtils.isEmpty(listConfigModel1)) {
            logger.warn("init is failed");
        }
    }

    /**
     * 功能码 4
     *
     * @return
     */
    public List<Cell> getCell4() {
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (Integer i : listConfigModel4) {
                Cell rs = cellMapper.selectByAddress(i);
                if (rs == null || rs.getCreated() == null) {
                    logger.warn("rs4 is null");
                    continue;
                }
                rs.setImgurl(urlMap.get(rs.getpName()));
                rs.setNow(sdf.format(rs.getCreated()));
                listCell.add(rs);
            }
        } catch (Exception e) {
            logger.error("getCell4 selectByAddress is exception : " + e);
        }
        return listCell;
    }

    /**
     * 功能码 3
     *
     * @return
     */
    public List<Cell> getCell3() {
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (Integer i : listConfigModel3) {
                Cell rs = cellMapper.selectByAddress(i);
                if (rs == null || rs.getCreated() == null) {
                    logger.warn("rs3 is null");
                }
                rs.setImgurl(urlMap.get(rs.getpName()));
                rs.setNow(sdf.format(rs.getCreated()));
                listCell.add(rs);
            }
        } catch (Exception e) {
            logger.error("getCell3 selectByAddress is exception : " + e);
        }
        return listCell;
    }


    /**
     * 功能码 1
     *
     * @return
     */
    public List<Cell> getCell1() {
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (Integer i : listConfigModel1) {
                Cell rs = cellMapper.selectByAddress(i);
                if (rs == null || rs.getCreated() == null) {
                    logger.warn("rs1 is null");
                }
                rs.setImgurl(urlMap.get(rs.getpName()));
                rs.setNow(sdf.format(rs.getCreated()));
                listCell.add(rs);
            }
        } catch (Exception e) {
            logger.error("getCell1 selectByAddress is exception : " + e);
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

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
import java.util.*;

public class CellService {

    private static final Logger logger = Logger.getLogger(CellService.class);

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

    /**
     * 功能码4 内存缓存
     */
    private static List<Cell> ResCell4 = new ArrayList<Cell>();

    /**
     * 功能码3 内存缓存
     */
    private static List<Cell> ResCell1 = new ArrayList<Cell>();

    /**
     * 心跳内存
     */
    private static String xindate = "";

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
     * 功能码 44
     *
     * @return
     */
    public void getCell44() {
        long k = System.currentTimeMillis();
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<Cell> rs = cellMapper.readSort(4, listConfigModel4.size());
            if (CollectionUtils.isEmpty(rs)) {
                logger.error("getCell44 rs is null");
                return;
            }
            for (Cell c : rs) {
                String dfe = urlMap.get(c.getpName());
                c.setImgurl(dfe);
                c.setNow(sdf.format(c.getCreated()));
            }
            if (rs.size() != listConfigModel4.size()) {
                logger.error("getCell44 read data length is not eqauls");
            }
            listCell = rs;
        } catch (Exception e) {
            logger.error("getCell44 readSort is exception : " + e);
        }
        Collections.sort(listCell, new Comparator<Cell>() {
            public int compare(Cell Cell1, Cell Cell2) {
                return Cell1.getConfigId().compareTo(Cell2.getConfigId());
            }
        });
        ResCell4.clear();
        ResCell4 = listCell;
        logger.info("getCell44 time consuming : " + (System.currentTimeMillis() - k));
    }

    /**
     * 功能码 33
     *
     * @return
     */
    public void getCell33() {
        long k = System.currentTimeMillis();
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<Cell> rs = cellMapper.readSort(3, listConfigModel3.size());
            if (CollectionUtils.isEmpty(rs)) {
                logger.error("getCell33 rs is null");
                return;
            }
            for (Cell c : rs) {
                c.setImgurl(urlMap.get(c.getpName()));
                c.setNow(sdf.format(c.getCreated()));
            }
            if (rs.size() != listConfigModel4.size()) {
                logger.error("getCell33 read data length is not eqauls");
            }
            listCell = rs;
        } catch (Exception e) {
            logger.error("getCell33 readSort is exception : " + e);
        }

        System.out.println(System.currentTimeMillis() - k);
    }


    /**
     * 功能码 11
     *
     * @return
     */
    public void getCell11() {
        long k = System.currentTimeMillis();
        List<Cell> listCell = new ArrayList<Cell>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<Cell> rs = cellMapper.readSort(1, listConfigModel1.size());
            if (CollectionUtils.isEmpty(rs)) {
                logger.error("getCell11 rs is null");
                return;
            }
            for (Cell c : rs) {
                c.setImgurl(urlMap.get(c.getpName()));
                c.setNow(sdf.format(c.getCreated()));
            }
            if (rs.size() != listConfigModel1.size()) {
                logger.error("getCell11 read data length is not eqauls");
            }
            listCell = rs;
        } catch (Exception e) {
            logger.error("getCell11 readSort is exception : " + e);
        }

        Collections.sort(listCell, new Comparator<Cell>() {
            public int compare(Cell Cell1, Cell Cell2) {
                return Cell1.getConfigId().compareTo(Cell2.getConfigId());
            }
        });
        ResCell1.clear();
        ResCell1 = listCell;
        logger.info("getCell11 time consuming : " + (System.currentTimeMillis() - k));
    }

    /**
     * 心跳
     *
     * @return
     */
    public void i() {
        long ks = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cell ii = cellMapper.i();
        if (ii == null || ii.getCreated() == null) {
            logger.warn("rs1 is null");
            return;
        }
        xindate = null;
        xindate = sdf.format(ii.getCreated());
        logger.info("xin tiao : " + (System.currentTimeMillis() - ks));
    }

    /**
     * 功能码 1
     *
     * @return
     */
    public List<Cell> getCell1() {
        long k = System.currentTimeMillis();
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
        System.out.println(System.currentTimeMillis() - k);
        return listCell;
    }


    /**
     * 功能码 4
     *
     * @return
     */
    public List<Cell> getCell4() {
        long ks = System.currentTimeMillis();
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
        System.out.println(System.currentTimeMillis() - ks);
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
                if ("1".equals(rs.getpValue())) {
                    rs.setpValue("100");
                } else {
                    rs.setpValue("0");
                }
                listCell.add(rs);
            }
        } catch (Exception e) {
            logger.error("getCell3 selectByAddress is exception : " + e);
        }
        return listCell;
    }

    public static List<Cell> getResCell4() {
        return ResCell4;
    }

    public static void setResCell4(List<Cell> resCell4) {
        ResCell4 = resCell4;
    }

    public static List<Cell> getResCell1() {
        return ResCell1;
    }

    public static void setResCell1(List<Cell> resCell3) {
        ResCell1 = resCell3;
    }

    public static String getXindate() {
        return xindate;
    }

    public static void setXindate(String xindate) {
        CellService.xindate = xindate;
    }

    public static Map<String, String> getUrlMap() {
        return urlMap;
    }

    public static void setUrlMap(Map<String, String> urlMap) {
        CellService.urlMap = urlMap;
    }
}

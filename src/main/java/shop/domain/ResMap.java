package shop.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songningning1 on 2017/9/9.
 */
public class ResMap {

    /**
     * 返回失败
     *
     * @return
     */
    public static Map<String, Object> failedMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("data", null);
        return map;
    }

    /**
     * 返回失败
     *
     * @return
     */
    public static Map<String, Object> failedMap(String errMsg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("data", errMsg);
        return map;
    }


    /**
     * 返回成功数据
     *
     * @param data 返回的data数据
     * @return
     */
    public static <T> Map successMap(T data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", data);
        return map;
    }

}

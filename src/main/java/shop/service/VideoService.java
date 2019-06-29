package shop.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import shop.domain.ResMap;
import shop.domain.VideoJW;
import shop.val.VideoJWMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 摄像头控制接口
 */
public class VideoService {

    private static final String url = "http://tianyuanfarm.cn:89/cc";

    private final static Logger logger = LogManager.getLogger(VideoService.class);

    @Resource
    private VideoJWMapper videoJWMapper;

    /**
     * Get 请求控制视频云台逻辑 地址，token，控制类型
     *
     * @param token
     * @param s
     */
    public void HttpClientGet(String token, String s) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(url);
            builder.setParameter("token", token);
            builder.setParameter("s", s);
            HttpGet httpGet = new HttpGet(builder.build());
            CloseableHttpResponse Response = client.execute(httpGet);
            HttpEntity entity = Response.getEntity();
            String str = EntityUtils.toString(entity, "UTF-8");
            logger.info("post msg: -------------- " + httpGet.getURI().toString());
            Response.close();
        } catch (Exception e) {
            logger.debug("HttpClientGet is Exception, e:" + e);
        }
    }

    /**
     * 查询video_jw表获取机位与摄像头token对应关系
     *
     * @param jw
     * @return
     */
    public Map getTokenByJw(String jw) {

        if (StringUtils.isEmpty(jw)) {
            logger.warn("jw quest jw is black");
            return ResMap.failedMap();
        }
        VideoJW vjw = videoJWMapper.selectTokenByJw(jw);
        if (vjw == null || StringUtils.isEmpty(vjw.getToken())) {
            return ResMap.failedMap();
        }
        Map resMap = new HashMap();
        resMap.put("token", vjw.getToken());
        resMap.put("success", true);
        return resMap;
    }
}

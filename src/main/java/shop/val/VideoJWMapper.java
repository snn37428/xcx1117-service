package shop.val;

import shop.domain.VideoJW;

public interface VideoJWMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoJW record);

    int insertSelective(VideoJW record);

    VideoJW selectByPrimaryKey(Integer id);

    VideoJW selectTokenByJw(String jw);

    int updateByPrimaryKeySelective(VideoJW record);

    int updateByPrimaryKey(VideoJW record);
}
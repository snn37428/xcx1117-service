package shop.val;

import shop.domain.VideoAuth;

import java.util.List;

public interface VideoAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoAuth record);

    int insertSelective(VideoAuth record);

    VideoAuth selectByPrimaryKey(Integer id);

    VideoAuth selectByAccredit(String token);

    VideoAuth selectByToken(String token);

    List<VideoAuth> selectByTokenForAuth1(String token);

    List<VideoAuth> selectByTokenForAuth2(String token);

    int updateByPrimaryKeySelective(VideoAuth record);

    int updateByToken(Integer id);

    int updateByPrimaryKey(VideoAuth record);
}
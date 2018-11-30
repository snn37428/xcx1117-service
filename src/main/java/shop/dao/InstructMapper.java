package shop.dao;

import shop.domain.Instruct;

public interface InstructMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Instruct record);

    int insertSelective(Instruct record);

    Instruct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Instruct record);

    int updateByPrimaryKey(Instruct record);
}
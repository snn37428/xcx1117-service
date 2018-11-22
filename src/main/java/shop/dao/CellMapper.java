package shop.dao;

import org.apache.ibatis.annotations.Param;
import shop.domain.Cell;

import java.util.List;

public interface CellMapper {

    /**
     * 按plc地址分组
     * @param modbusAddr
     * @return
     */
    Cell selectByAddress(Integer modbusAddr);

    /**
     * 心跳
     * @return
     */
    Cell i();

    /**
     * 安祖排序
     * @return
     */
    List<Cell> readSort(@Param("model")int model, @Param("pageSize")int pageSize);
}
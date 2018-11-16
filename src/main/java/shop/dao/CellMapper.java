package shop.dao;

import shop.domain.Cell;

public interface CellMapper {

    Cell selectByAddress(Integer modbusAddr);

}
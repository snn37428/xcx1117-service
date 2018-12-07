package shop.domain;

import java.util.List;

public class ResponseVo {
    private List<Cell> cells;
    private I xin;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public I getXin() {
        return xin;
    }

    public void setXin(I xin) {
        this.xin = xin;
    }
}

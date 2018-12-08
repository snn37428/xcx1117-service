package shop.domain;

import java.util.List;

public class ResponseVo {
    private List<Cell> cells;
    private Pant xin;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Pant getXin() {
        return xin;
    }

    public void setXin(Pant xin) {
        this.xin = xin;
    }
}

package shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.domain.Cell;
import shop.domain.I;
import shop.service.CellService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductCell {

    @Resource
    private CellService cellService;

    @RequestMapping(value = "al3")
    @ResponseBody
    public List<Cell> in3() {
        return null;
    }

    @RequestMapping(value = "al4")
    @ResponseBody
    public List<Cell> in4() {
        return CellService.getResCell4();

    }

    @RequestMapping(value = "al1")
    @ResponseBody
    public List<Cell> in1() {
        return CellService.getResCell1();
    }

    @RequestMapping(value = "i")
    @ResponseBody
    public I i() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        I i = new I();
        i.setDate(sdf.format(new Date()));
        i.setcDate(CellService.getXindate());
        return i;
    }
}

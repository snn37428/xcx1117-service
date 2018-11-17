package shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.domain.Cell;
import shop.service.CellService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductCell {

    @Resource
    private CellService cellService;

    @RequestMapping(value = "al3")
    @ResponseBody
    public List<Cell> in3() {
        System.out.println("---3");
        return cellService.getCell3();
    }
    @RequestMapping(value = "al4")
    @ResponseBody
    public List<Cell> in4() {
        System.out.println("---4");
        return cellService.getCell4();
    }

    @RequestMapping(value = "al1")
    @ResponseBody
    public List<Cell> in1() {
        System.out.println("---1");
        return cellService.getCell1();
    }
}

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

    @RequestMapping(value = "all")
    @ResponseBody
    public List<Cell> in() {
        System.out.println("---");
        return cellService.getCell();
    }
}

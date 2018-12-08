package shop.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.domain.Pant;
import shop.domain.ResponseVo;
import shop.service.CellService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/product")
public class ProductCell {

    private static final Logger logger = Logger.getLogger(ProductCell.class);

    @Resource
    private CellService cellService;

    @RequestMapping(value = "al4")
    @ResponseBody
    public ResponseVo in4() {
        logger.info("ProductCell request -------------------4");
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCells(CellService.getResCell4());
        responseVo.setXin(xin());
        return responseVo;

    }

    @RequestMapping(value = "al1")
    @ResponseBody
    public ResponseVo in1() {
        logger.info("ProductCell request -------------------1");
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCells(CellService.getResCell1());
        responseVo.setXin(xin());
        return responseVo;
    }


    public Pant xin() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Pant i = new Pant();
        i.setDate(sdf.format(new Date()));
        i.setcDate(CellService.getXindate());
        return i;
    }
}

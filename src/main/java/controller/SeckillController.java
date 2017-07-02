package controller;

import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillSuccess;
import entity.Seckill;
import enums.SeckillEnum;
import exception.RepeatException;
import exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuSitong on 2017/6/29.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * 返回商品列表页
     *
     * @param model
     * @return list.jsp
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

   @RequestMapping(value = "/{seckillid}/detail", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("seckillid") Long seckillid) {
        if (seckillid == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSeckillById(seckillid);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";

    }

    @RequestMapping(value = "/{seckillid}/exposer", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody // 返回类型是json
    public SeckillSuccess<Exposer> exposer(@PathVariable("seckillid") Long seckillid) {
        SeckillSuccess<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillid);
            result = new SeckillSuccess<Exposer>(true, exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillSuccess<Exposer>(false, e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/{seckillid}/{md5}/exection",
            method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody // 返回类型是json
    public SeckillSuccess<SeckillExecution> exection(@PathVariable("seckillid") Long seckillid,
                                                     @PathVariable("md5") String md5,
                                                     @CookieValue(value = "killPhone",required = false) Long killPhone) {
        SeckillSuccess<SeckillExecution> result;
        if (killPhone == null){
            return new SeckillSuccess<SeckillExecution>(false,"未注册");
        }

        try {
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillid,killPhone,md5);
            return new SeckillSuccess<SeckillExecution>(true,seckillExecution);

        }catch (RepeatException e1) {
            SeckillExecution seckillExecution1 = new SeckillExecution(seckillid,SeckillEnum.REPEAT_KILL);
            return new SeckillSuccess<SeckillExecution>(false,seckillExecution1);
        }catch (SeckillCloseException e2){
            SeckillExecution seckillExecution2 = new SeckillExecution(seckillid,SeckillEnum.END);
            return new SeckillSuccess<SeckillExecution>(false,seckillExecution2);
        }catch (Exception e3){
            logger.error(e3.getMessage(),e3);
            SeckillExecution seckillExecution3 = new SeckillExecution(seckillid,SeckillEnum.INNER_ERROR);
            return new SeckillSuccess<SeckillExecution>(false,seckillExecution3);
        }
         }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillSuccess<Long> time() {
        Date date = new Date();
        return new  SeckillSuccess<Long>(true,date.getTime());
    }


}

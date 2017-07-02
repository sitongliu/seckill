package service;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.impl.SeckillServiceImpl;

import java.util.List;

/**
 * Created by LiuSitong on 2017/6/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getSeckillById() throws Exception {
        Seckill seckill = seckillService.getSeckillById(1000L);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long phone = 1556544556;
        String md5 = "0b95fb90e01dcc708ee56a27184c31d4";
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("excution={}", execution);
    }

    @Test
    public void executeSeckillProcedure() throws Exception {
        long seckillId = 1000;
        long phone = 15249698741L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution ex = seckillService.executeSeckillProcedure(seckillId,phone,md5);
        logger.info(ex.getStateInfo());
        }
    }
}
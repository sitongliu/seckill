package dao;

import entity.SuccessSeckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by LiuSitong on 2017/6/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件的路径
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessedSeckill() throws Exception {
        System.out.println(successKilledDao.insertSuccessedSeckill(1001L,1685641255L));
    }


    @Test
    public void queryByIdWithSeckill() throws Exception {

        Long id = 1001L;
        Long phone = 1685641255L;
        SuccessSeckill successSeckill =  successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successSeckill.getSeckillid()+"----"+successSeckill.getUserphone());
        System.out.println(successSeckill.getSeckill());

    }

}
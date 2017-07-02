package service.impl;

import com.mchange.v2.util.DoubleWeakHashMap;
import dao.SeckillDao;
import dao.SuccessKilledDao;
import dao.cache.RedisDao;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import entity.SuccessSeckill;
import enums.SeckillEnum;
import exception.RepeatException;
import exception.SeckillCloseException;
import exception.SeckillException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import service.SeckillService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuSitong on 2017/6/27.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    //混淆的盐值
    private String slat = ";Jjqiouj'L|||;'L;'3eio3u2njksnvm&*^&%&^$%==-0MNBwiepoQ";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 2);
    }

    public Seckill getSeckillById(long seckillid) {
        return seckillDao.queryById(seckillid);
    }

    //暴露秒杀地址
    public Exposer exportSeckillUrl(long seckillid) {

        //采用redis进行缓存优化
        Seckill seckill = redisDao.getSeckill(seckillid);
        if (seckill == null) {
            //访问数据库
            seckill = seckillDao.queryById(seckillid);
            if (seckill == null) {
                return new Exposer(false, seckillid);
            } else {
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStarttime();
        Date endTime = seckill.getEndtime();
        Date now = new Date();

        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillid, startTime.getTime(), endTime.getTime(), now.getTime());
        }

        String md5 = getMD5(seckillid);


        return new Exposer(true, md5, seckillid);
    }

    private String getMD5(long seckillid) {
        String base = seckillid + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    //
    @Transactional
    public SeckillExecution executeSeckill(long seckillid, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatException {
        if (md5 == null || !md5.equals(getMD5(seckillid))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀 减库存 记录成功秒杀的信息
        //减少库存
        try {
            int insertNum = successKilledDao.insertSuccessedSeckill(seckillid, userPhone);
            if (insertNum <= 0) {
                throw new RepeatException("重复秒杀异常");
            } else {
                int updateNum = seckillDao.reduceNum(seckillid, new Date());
                if (updateNum <= 0) {  //秒杀结束
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀没有结束 那就记录数据
                    SuccessSeckill successSeckill = successKilledDao.queryByIdWithSeckill(seckillid, userPhone);
                    return new SeckillExecution(seckillid, SeckillEnum.SUCCESS, successSeckill);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatException e2) {
            throw e2;
        } catch (Exception e3) {
            logger.error(e3.getMessage(), e3);
            throw new SeckillException("seckill inner error:" + e3.getMessage());
        }


    }

    public SeckillExecution executeSeckillProcedure(long seckillid, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillid))) {
            return new SeckillExecution(seckillid, SeckillEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillid", seckillid);
        map.put("userphone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map,"result",-2);
            if (result == 1){
                SuccessSeckill sk = successKilledDao.queryByIdWithSeckill(seckillid,userPhone);
                return new SeckillExecution(seckillid,SeckillEnum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillid,SeckillEnum.stateof(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillid,SeckillEnum.INNER_ERROR);
        }
    }
}

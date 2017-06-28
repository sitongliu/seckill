package service;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatException;
import exception.SeckillCloseException;
import exception.SeckillException;
import javafx.scene.control.Skin;

import java.util.List;

/**
 * Created by LiuSitong on 2017/6/27.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 单个秒杀记录
     * @param seckillid
     * @return
     */
    Seckill getSeckillById(long seckillid);

    /**
     * 秒杀开始时输出秒杀的地址
     * 未开始时输出秒杀的时间和系统时间
     * @param seckillid
     * @return
     */
    Exposer exportSeckillUrl(long seckillid);

    /**
     * 执行秒杀操作
     * @param seckillid
     * @param userPhone
     * @param md5
     */
     SeckillExecution executeSeckill(long seckillid, long userPhone, String md5) throws
             SeckillException, SeckillCloseException, RepeatException;
}

package dao;

import entity.SuccessSeckill;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

/**
 * Created by LiuSitong on 2017/6/27.
 */
public interface SuccessKilledDao {
    /**
     * 插入秒杀成功的记录，联合主键过滤重复
     * @param seckillid
     * @param phonenum
     * @return 如果影响行数>1 表示更新语句更新的行数
     */
    int insertSuccessedSeckill(@Param("seckillid") long seckillid, @Param("phonenum") long phonenum);

    /**
     * 根据id查询成功的记录，并且带出seckill信息
     * @param seckillid
     * @return
     */
    SuccessSeckill queryByIdWithSeckill(@Param("seckillid") long seckillid,@Param("userPhone")long userPhone);

}

package dao;

import entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuSitong on 2017/6/27.
 */

public interface SeckillDao {


    /**
     * 秒杀成功后减少库存
     * @param seckillid 商品的id
     * @param killtime createTime 秒杀成功的时间
     * @return
     */
    int reduceNum(@Param("seckillid") long seckillid,@Param("killtime") Date killtime);

    /**
     * 根据商品的id 进行查询
     * @param seckillid
     * @return
     */
    Seckill queryById(long seckillid);

    /**
     * 查询所有商品 还有分页
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}

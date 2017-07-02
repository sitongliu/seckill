package dao.cache;

import entity.Seckill;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import jdk.nashorn.internal.runtime.ECMAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by LiuSitong on 2017/7/2.
 */
public class RedisDao {
    private JedisPool jedisPool;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(Long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //redis内部没有实现序列化 所以 get操作  get->byte[]->反序列化->Object(Seckill)
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //创建一个空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //返回被反序列的对象  压缩jdk的原来序列化大小的10分之一
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        //set Object ->序列化->byte
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillid();
                byte[] bytes2 = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                //超时缓存
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes2);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}

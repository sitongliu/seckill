package exception;

/**秒杀相关异常
 * Created by LiuSitong on 2017/6/27.
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

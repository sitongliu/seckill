package exception;

/**重复秒杀（runtime exception）
 * Created by LiuSitong on 2017/6/27.
 */
public class RepeatException  extends  SeckillException{


    public RepeatException(String message){
        super(message);
    }

    public RepeatException(String message,Throwable cause){
        super(message,cause);
    }
}

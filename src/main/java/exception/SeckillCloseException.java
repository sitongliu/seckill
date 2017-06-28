package exception;

import dto.SeckillExecution;

/**
 * Created by LiuSitong on 2017/6/27.
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

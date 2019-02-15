package zwt.charge.core.exception;

/**
 * @Author: lilongzhou
 * @Description: 重复提交异常
 * @Date: Created in 18-6-28 上午11:33
 **/
public class DuplicateSubmitException extends RuntimeException {

    public DuplicateSubmitException(String msg) {
        super(msg);
    }

    public DuplicateSubmitException(String msg, Throwable cause) {
        super(msg, cause);
    }


}

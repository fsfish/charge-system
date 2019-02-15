package zwt.charge.core.exception;

import lombok.Getter;

@Getter
public class ZwtException extends Exception {

    private ExceptionCodeEnum code;

    public ZwtException(ExceptionCodeEnum code) {
        super(code.toString());
        this.code = code;
    }
}

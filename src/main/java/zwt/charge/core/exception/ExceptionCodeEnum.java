package zwt.charge.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCodeEnum {

    SUCCESS(200, HttpStatus.OK, "成功"),

    MOBILE_STYLE_ERROR(4300, HttpStatus.BAD_REQUEST, "手机号格式不正确"),

    USER_EXIST_ERROR(4330, HttpStatus.BAD_REQUEST, "用户已存在"),

    USER_NOT_FOUND(4331, HttpStatus.BAD_REQUEST, "用户不存在"),

    REQUEST_ERROR(4320, HttpStatus.BAD_REQUEST, "参数错误"),

    SERVER_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "服务器端错误");

    private int code;

    private HttpStatus httpStatus;

    private String message;

    ExceptionCodeEnum(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

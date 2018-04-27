package cn.wolfcode.wms.util;

public class SecurityException extends RuntimeException {

    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

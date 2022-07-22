package com.tech.usermanagement.util;

/**
 * @author xdebugger
 */
public class CommonRuntimeException extends RuntimeException {

    public CommonRuntimeException(String errorMessage) {
        super(errorMessage);
    }

    public CommonRuntimeException(Throwable cause) {
        super(cause);
    }

    public CommonRuntimeException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}

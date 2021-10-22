package com.socen.ws.common.exception;

/**
 * Ws 系统内部异常
 */
public class WsException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public WsException(String message) {
        super(message);
    }
}

package com.payeasy.pcashclose.service.exception;

import com.payeasy.exceptions.BaseException;

public class PCashCloseException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 8195594911487772768L;

    /**
     * Constructor.
     *
     * @param msg String
     */
    public PCashCloseException(String msg) {
        super(msg);
    }

    /**
     * Coustructor.
     *
     * @param ex Throwable
     */
    public PCashCloseException(Throwable ex) {
        super(ex);
    }

    /**
     * Coustructor.
     *
     * @param msg String
     * @param ex Throwable
     */
    public PCashCloseException(String msg, Throwable ex) {
        super(msg, ex);
    }

}

package com.mamda.tp.exceptions;

public class UserAlreadyTaken extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserAlreadyTaken() {
        super();
    }

    public UserAlreadyTaken(final String message) {
        super(message);
    }
}
package com.viswanath.cronexpparser;

public class InvalidCronException extends Exception {

    public InvalidCronException(Type type) {
        super(String.format("Invalid Cron Expression: %s", type.message));
    }

    enum Type {
        EMPTY_INPUT("empty input"),
        NULL_INPUT("null input"),
        INSUFFICIENT_FIELDS("insufficient fields"),
        MINUTE_FIELD_OUT_OF_RANGE("minute field out of range"),
        MINUTE_FIELD_INVALID_RANGE("invalid range in minute field"),
        MINUTE_FIELD_INVALID_STEP("invalid step value in minute field");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}



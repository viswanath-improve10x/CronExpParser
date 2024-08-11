package com.viswanath.cronexpparser.errors;

public class InvalidCronException extends Exception {

    public InvalidCronException(Type type) {
        super(String.format("Invalid Cron Expression: %s", type.message));
    }

    public enum Type {
        EMPTY_INPUT("empty input"),
        NULL_INPUT("null input"),
        INSUFFICIENT_FIELDS("insufficient fields"),
        MINUTE_FIELD_OUT_OF_RANGE("minute field out of range"),
        MINUTE_FIELD_INVALID_RANGE("invalid range in minute field"),
        MINUTE_FIELD_INVALID_STEP("invalid step value in minute field"),
        HOUR_FIELD_OUT_OF_RANGE("hour field out of range"),
        HOUR_FIELD_INVALID_RANGE("invalid range in hour field"),
        HOUR_FIELD_INVALID_STEP("invalid step value in hour field"),
        DAY_OF_MONTH_FIELD_OUT_OF_RANGE("day of month field out of range"),
        DAY_OF_MONTH_FIELD_INVALID_RANGE("invalid range in day of month field"),
        DAY_OF_MONTH_FIELD_INVALID_STEP("invalid step value in day of month field"),
        MONTH_FIELD_OUT_OF_RANGE("month field out of range"),
        MONTH_FIELD_INVALID_RANGE("invalid range in month field"),
        MONTH_FIELD_INVALID_STEP("invalid step value in month field"),
        DAY_OF_WEEK_FIELD_OUT_OF_RANGE("day of week field out of range"),
        DAY_OF_WEEK_FIELD_INVALID_RANGE("invalid range in day of week field"),
        DAY_OF_WEEK_FIELD_INVALID_STEP("invalid step value in day of week field");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}



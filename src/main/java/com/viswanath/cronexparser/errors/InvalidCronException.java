package com.viswanath.cronexparser.errors;

import com.viswanath.cronexparser.fields.FieldType;

public class InvalidCronException extends Exception {

    private final FieldType fieldType;
    private final Type type;

    public InvalidCronException(Type type) {
        super(String.format("%s", type.getMessage()));
        this.type = type;
        this.fieldType = null;
    }

    public InvalidCronException(FieldType fieldType, Type type) {
        super(String.format("%s in %s", type.getMessage(), fieldType.label));
        this.fieldType = fieldType;
        this.type = type;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        NO_EXPRESSION("No cron expression found"),
        INSUFFICIENT_FIELDS("Insufficient fields"),
        EMPTY_INPUT("Empty value"),
        NULL_INPUT("Null value"),
        OUT_OF_RANGE("Field out of range"),
        INVALID_RANGE("Invalid range"),
        INVALID_STEP("Invalid step value"),;

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
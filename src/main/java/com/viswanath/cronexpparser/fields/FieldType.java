package com.viswanath.cronexpparser.fields;

public enum FieldType {
    MINUTES("minute", 0, 59),
    HOURS("hour", 0, 23),
    DAY_OF_MONTH("day of month", 1, 31),
    MONTH("month", 1, 12),
    DAY_OF_WEEK("day of week", 0, 6);

    public final String label;
    public final int start;
    public final int end;

    FieldType(String label, int start, int end) {
        this.label = label;
        this.start = start;
        this.end = end;
    }
}

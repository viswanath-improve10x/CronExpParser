package com.viswanath.cronexpparser.fields;

public enum FieldType {
    MINUTES("minute", 0, 59),
    HOURS("hour", 0, 23);

    public final String label;
    public final int start;
    public final int end;

    FieldType(String label, int start, int end) {
        this.label = label;
        this.start = start;
        this.end = end;
    }
}

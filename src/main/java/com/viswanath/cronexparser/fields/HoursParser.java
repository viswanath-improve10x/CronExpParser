package com.viswanath.cronexparser.fields;

public class HoursParser extends FieldParser {

    public HoursParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.HOURS;
    }
}


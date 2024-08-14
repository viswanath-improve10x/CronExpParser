package com.viswanath.cronexparser.fields;

public class DaysOfWeekParser extends FieldParser {

    public DaysOfWeekParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.DAY_OF_WEEK;
    }
}

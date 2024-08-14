package com.viswanath.cronexparser.fields;

public class DaysOfMonthParser extends FieldParser {

    public DaysOfMonthParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.DAY_OF_MONTH;
    }
}

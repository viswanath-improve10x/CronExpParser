package com.viswanath.cronexpparser.fields;

public class MonthParser extends FieldParser {

    public MonthParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.MONTH;
    }
}

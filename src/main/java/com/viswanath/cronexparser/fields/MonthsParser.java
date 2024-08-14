package com.viswanath.cronexparser.fields;

public class MonthsParser extends FieldParser {

    public MonthsParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.MONTH;
    }
}

package com.viswanath.cronexparser.fields;

public class MinutesParser extends FieldParser {

    public MinutesParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.MINUTES;
    }
}


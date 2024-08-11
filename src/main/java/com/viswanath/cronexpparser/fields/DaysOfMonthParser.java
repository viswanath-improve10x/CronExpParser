package com.viswanath.cronexpparser.fields;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.expressions.BaseExpression;

import java.util.List;

public class DaysOfMonthParser extends FieldParser {

    public DaysOfMonthParser(String fieldExpression) {
        super(fieldExpression);
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.DAY_OF_MONTH;
    }
}

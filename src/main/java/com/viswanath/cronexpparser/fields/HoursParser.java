package com.viswanath.cronexpparser.fields;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.expressions.BaseExpression;

import java.util.List;

public class HoursParser extends FieldParser {

    public HoursParser(String fieldExpression) {
        super(fieldExpression);
    }

    public List<Integer> parse() throws InvalidCronException {
        BaseExpression expression = findMatchingExpression();
        expression.validate();
        return expression.extract();
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.HOURS;
    }
}

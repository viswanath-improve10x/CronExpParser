package com.viswanath.cronexpparser.fields;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.expressions.*;
import com.viswanath.cronexpparser.utils.Numbers;

import java.util.List;

public class MinutesParser extends FieldParser {

    public MinutesParser(String fieldExpression) {
        super(fieldExpression);
    }

    public List<Integer> parse() throws InvalidCronException {
        BaseExpression expression = findMatchingExpression();
        expression.validate();
        return expression.extract();
    }

    @Override
    protected FieldType getFieldType() {
        return FieldType.MINUTES;
    }
}


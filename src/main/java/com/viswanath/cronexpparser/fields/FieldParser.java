package com.viswanath.cronexpparser.fields;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.expressions.*;

import java.util.List;

public abstract class FieldParser {

    private final String fieldExpression;

    public FieldParser(String fieldExpression) {
        this.fieldExpression = fieldExpression;
    }

    protected String getFieldExpression() {
        return fieldExpression;
    }

    protected final BaseExpression findMatchingExpression() {
        BaseExpression expression;
        if(getFieldExpression().equals("*")) {
            expression = new AnyValueExpression(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains("-")) {
            expression = new RangeExpression(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains("/")) {
            expression = new StepExpression(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains(",")) {
            expression = new CommaSeparatedExpression(getFieldExpression(), getFieldType());
        } else {
            expression = new SpecificExpression(getFieldExpression(), getFieldType());
        }
        return expression;
    }

    public List<Integer> parse() throws InvalidCronException {
        BaseExpression expression = findMatchingExpression();
        expression.validate();
        return expression.extract();
    }

    protected abstract FieldType getFieldType();

}

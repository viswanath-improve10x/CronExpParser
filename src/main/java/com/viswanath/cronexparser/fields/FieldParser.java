package com.viswanath.cronexparser.fields;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.patterns.*;

import java.util.List;

public abstract class FieldParser {

    private final String fieldExpression;

    public FieldParser(String fieldExpression) {
        this.fieldExpression = fieldExpression;
    }

    protected String getFieldExpression() {
        return fieldExpression;
    }

    protected final FieldPattern findMatchingExpression() {
        FieldPattern expression;
        if(getFieldExpression().equals("*")) {
            expression = new StarFieldPattern(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains("-")) {
            expression = new RangeFieldPattern(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains("/")) {
            expression = new StepExpression(getFieldExpression(), getFieldType());
        } else if(getFieldExpression().contains(",")) {
            expression = new CommaFieldPattern(getFieldExpression(), getFieldType());
        } else {
            expression = new ExactFieldPattern(getFieldExpression(), getFieldType());
        }
        return expression;
    }

    public List<Integer> parse() throws InvalidCronException {
        FieldPattern expression = findMatchingExpression();
        expression.validate();
        return expression.extract();
    }

    protected abstract FieldType getFieldType();

}

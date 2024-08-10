package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.FieldType;

import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class BaseExpression {
    private final FieldType fieldType;
    private final String fieldExpression;

    public BaseExpression(String fieldExpression, FieldType fieldType) {
        this.fieldExpression = fieldExpression;
        this.fieldType = fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getFieldExpression() {
        return fieldExpression;
    }

    public void validate() throws InvalidCronException{
        Constraints constraints = getFieldConstrains().get(getFieldType());
        if(constraints != null && !fieldExpression.matches(constraints.regex)) {
            throw new InvalidCronException(constraints.errorType);
        }
    }

    public abstract Map<FieldType, Constraints> getFieldConstrains();

    public abstract List<Integer> extract() throws InvalidCronException ;
}

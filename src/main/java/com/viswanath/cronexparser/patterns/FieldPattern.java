package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.FieldType;

import java.util.List;
import java.util.Map;

public abstract class FieldPattern {
    private final FieldType fieldType;
    private final String fieldExpression;

    public FieldPattern(String fieldExpression, FieldType fieldType) {
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
        if(constraints != null && !fieldExpression.matches(constraints.regex())) {
            throw new InvalidCronException(constraints.fieldType(), constraints.errorType());
        }
    }

    public abstract Map<FieldType, Constraints> getFieldConstrains();

    public abstract List<Integer> extract() throws InvalidCronException ;
}

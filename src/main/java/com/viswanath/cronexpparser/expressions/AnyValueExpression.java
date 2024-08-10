package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.FieldType;
import com.viswanath.cronexpparser.utils.Numbers;

import java.util.List;
import java.util.Map;

public class AnyValueExpression extends BaseExpression {

    public AnyValueExpression(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        return Numbers.list(getFieldType().start, getFieldType().end);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of();
    }
}

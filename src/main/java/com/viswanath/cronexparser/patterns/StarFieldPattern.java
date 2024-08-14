package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.FieldType;
import com.viswanath.cronexparser.utils.Numbers;

import java.util.List;
import java.util.Map;

public class StarFieldPattern extends FieldPattern {

    public StarFieldPattern(String fieldExpression, FieldType fieldType) {
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

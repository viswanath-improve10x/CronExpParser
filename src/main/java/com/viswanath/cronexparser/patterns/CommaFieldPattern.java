package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.FieldType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommaFieldPattern extends FieldPattern {


    public CommaFieldPattern(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        String [] numbers = getFieldExpression().split(",");
        List<Integer> numberList = Arrays.stream(numbers).map(Integer::parseInt).toList();
        int start = getFieldType().start;
        int end = getFieldType().end;
        for (Integer number: numberList) {
            if(number < start || number > end) {
                throw new InvalidCronException(getFieldConstrains().get(getFieldType()).errorType());
            }
        }
        return numberList;
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of();
    }
}
package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.FieldType;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CommaSeparatedExpression extends BaseExpression {


    public CommaSeparatedExpression(String fieldExpression, FieldType fieldType) {
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
                throw new InvalidCronException(getFieldConstrains().get(getFieldType()).errorType);
            }
        }
        return numberList;
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of();
    }
}
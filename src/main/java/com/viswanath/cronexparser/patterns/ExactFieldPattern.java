package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.FieldType;
import com.viswanath.cronexparser.utils.Numbers;

import java.util.List;
import java.util.Map;

import static com.viswanath.cronexparser.errors.InvalidCronException.Type.*;

public class ExactFieldPattern extends FieldPattern {

    public ExactFieldPattern(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of(
                FieldType.MINUTES, new Constraints("^(0?[0-9]|[1-5][0-9])$", FieldType.MINUTES, OUT_OF_RANGE),
                FieldType.HOURS, new Constraints("^(0?[0-9]|1[0-9]|2[0-3])$", FieldType.HOURS, OUT_OF_RANGE),
                FieldType.DAY_OF_MONTH, new Constraints("^(0?[1-9]|[12][0-9]|3[01])$", FieldType.DAY_OF_MONTH, OUT_OF_RANGE),
                FieldType.MONTH, new Constraints("^(0?[1-9]|[1][0-2])$", FieldType.MONTH, OUT_OF_RANGE),
                FieldType.DAY_OF_WEEK, new Constraints("^([0-6])$", FieldType.DAY_OF_WEEK, OUT_OF_RANGE)
        );
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        int number = Integer.parseInt(getFieldExpression());
        return Numbers.list(number);
    }
}
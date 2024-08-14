package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.fields.FieldType;
import com.viswanath.cronexparser.utils.Numbers;
import com.viswanath.cronexparser.errors.InvalidCronException;

import java.util.List;
import java.util.Map;

import static com.viswanath.cronexparser.errors.InvalidCronException.Type.*;

public class RangeFieldPattern extends FieldPattern {

    public RangeFieldPattern(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        String [] range = getFieldExpression().split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        if(start > end) {
            throw  new InvalidCronException(getFieldType(), getFieldConstrains().get(getFieldType()).errorType());
        }
        return Numbers.list(start, end);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of(
                FieldType.MINUTES, new Constraints("^(0?[0-9]|[1-5][0-9])-([0-5][0-9])$", FieldType.MINUTES, INVALID_RANGE),
                FieldType.HOURS, new Constraints("^(0?[0-9]|1[0-9]|2[0-3])-(0?[0-9]|1[0-9]|2[0-3])$", FieldType.HOURS, INVALID_RANGE),
                FieldType.DAY_OF_MONTH, new Constraints("^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|[12][0-9]|3[01])$", FieldType.DAY_OF_MONTH, INVALID_RANGE),
                FieldType.MONTH, new Constraints("^(0?[1-9]|1[0-2])-(0?[1-9]|1[0-2])$", FieldType.MONTH, INVALID_RANGE),
                FieldType.DAY_OF_WEEK, new Constraints("^([0-6])-([0-6])$", FieldType.DAY_OF_WEEK, INVALID_RANGE)
        );
    }
}
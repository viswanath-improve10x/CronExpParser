package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.fields.FieldType;
import com.viswanath.cronexpparser.utils.Numbers;
import com.viswanath.cronexpparser.errors.InvalidCronException;

import java.util.List;
import java.util.Map;

import static com.viswanath.cronexpparser.errors.InvalidCronException.Type.HOUR_FIELD_INVALID_RANGE;
import static com.viswanath.cronexpparser.errors.InvalidCronException.Type.MINUTE_FIELD_INVALID_RANGE;

public class RangeExpression extends BaseExpression {

    public RangeExpression(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        String [] range = getFieldExpression().split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        if(start > end) {
            throw  new InvalidCronException(getFieldConstrains().get(getFieldType()).errorType);
        }
        return Numbers.list(start, end);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of(
                FieldType.MINUTES, new Constraints("^(0?[0-9]|[1-5][0-9])-([0-5][0-9])$", MINUTE_FIELD_INVALID_RANGE),
                FieldType.HOURS, new Constraints("^(0?[0-9]|1[0-9]|2[0-3])-(0?[0-9]|1[0-9]|2[0-3])$", HOUR_FIELD_INVALID_RANGE)
        );
    }
}


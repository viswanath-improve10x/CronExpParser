package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.FieldType;
import com.viswanath.cronexpparser.utils.Numbers;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static com.viswanath.cronexpparser.errors.InvalidCronException.Type.*;

public class SpecificExpression extends BaseExpression {

    public SpecificExpression(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of(
                FieldType.MINUTES, new Constraints("^(0?[0-9]|[1-5][0-9])$", MINUTE_FIELD_OUT_OF_RANGE),
                FieldType.HOURS, new Constraints("^(0?[0-9]|1[0-9]|2[0-3])$", HOUR_FIELD_OUT_OF_RANGE),
                FieldType.DAY_OF_MONTH, new Constraints("^(0?[1-9]|[12][0-9]|3[01])$", DAY_OF_MONTH_FIELD_OUT_OF_RANGE),
                FieldType.MONTH, new Constraints("^(0?[1-9]|[1][0-2])$", MONTH_FIELD_OUT_OF_RANGE),
                FieldType.DAY_OF_WEEK, new Constraints("^([0-6])$", DAY_OF_WEEK_FIELD_OUT_OF_RANGE)
        );
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        int number = Integer.parseInt(getFieldExpression());
        return Numbers.list(number);
    }
}

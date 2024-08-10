package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.fields.FieldType;
import com.viswanath.cronexpparser.utils.Numbers;
import com.viswanath.cronexpparser.errors.InvalidCronException;

import java.util.List;
import java.util.Map;

import static com.viswanath.cronexpparser.errors.InvalidCronException.Type.*;

public class StepExpression extends BaseExpression {
    
    public StepExpression(String fieldExpression, FieldType fieldType) {
        super(fieldExpression, fieldType);
    }

    @Override
    public List<Integer> extract() throws InvalidCronException {
        String [] params = getFieldExpression().split("/");
        int step = Integer.parseInt(params[1]);
        return Numbers.list(getFieldType().start, getFieldType().end, step);
    }

    @Override
    public Map<FieldType, Constraints> getFieldConstrains() {
        return Map.of(
                FieldType.MINUTES, new Constraints("^\\*/([1-5]?[1-9])$", MINUTE_FIELD_INVALID_STEP),
                FieldType.HOURS, new Constraints("^\\*/([1-9]|1[0-9]|2[0-3])$", HOUR_FIELD_INVALID_STEP)
        );
    }
}

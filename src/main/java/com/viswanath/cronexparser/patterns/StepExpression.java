package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.fields.FieldType;
import com.viswanath.cronexparser.utils.Numbers;
import com.viswanath.cronexparser.errors.InvalidCronException;

import java.util.List;
import java.util.Map;

import static com.viswanath.cronexparser.errors.InvalidCronException.Type.*;

public class StepExpression extends FieldPattern {
    
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
                FieldType.MINUTES, new Constraints("^\\*/([1-5]?[1-9])$", FieldType.MINUTES, INVALID_STEP),
                FieldType.HOURS, new Constraints("^\\*/([1-9]|1[0-9]|2[0-3])$", FieldType.HOURS, INVALID_STEP),
                FieldType.DAY_OF_MONTH, new Constraints("^\\*/([1-9]|[12][0-9]|3[0-1])$", FieldType.DAY_OF_MONTH, INVALID_STEP),
                FieldType.MONTH, new Constraints("^\\*/([1-9]|1[0-2])$", FieldType.MONTH, INVALID_STEP),
                FieldType.DAY_OF_WEEK, new Constraints("^\\*/([1-6])$", FieldType.DAY_OF_WEEK, INVALID_STEP)
        );
    }
}

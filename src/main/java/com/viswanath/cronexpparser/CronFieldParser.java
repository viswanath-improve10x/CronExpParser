package com.viswanath.cronexpparser;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.viswanath.cronexpparser.InvalidCronException.Type.MINUTE_FIELD_INVALID_RANGE;

public abstract class CronFieldParser {

    private String fieldExpression;

    public CronFieldParser(String fieldExpression) {
        this.fieldExpression = fieldExpression;
    }

    public List<Integer> extract() {

        return null;
    }

    public abstract Map.Entry<Integer, Integer> getRange();
}

/*

BaseParser  -> SpecificParser
            -> CommaSeparatedParser
            -> StepParser
            -> RangeParser

 */

class RangeExpression {

    private final String fieldExpression;
    private final FieldType fieldType;

    public RangeExpression(String fieldExpression, FieldType fieldType) {
        this.fieldExpression = fieldExpression;
        this.fieldType = fieldType;
    }

    public List<Integer> extract() throws InvalidCronException {
        if(!fieldExpression.matches(fieldType.validationRegex)) {
            throw new InvalidCronException(MINUTE_FIELD_INVALID_RANGE);
        }
        String [] range = fieldExpression.split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        return Numbers.list(start, end);
    }
}

enum FieldType {
    MINUTES("minute", "^(0?[0-9]|[1-5][0-9])-([0-5][0-9])$", 0, 59);

    public String label;
    public String validationRegex;
    public int start;
    public int end;

    FieldType(String label, String validationRegex, int start, int end) {
        this.label = label;
        this.validationRegex = validationRegex;
        this.start = start;
        this.end = end;
    }
}

class MinutesParser {

    private final String fieldExpression;

    public MinutesParser(String fieldExpression) {
        this.fieldExpression = fieldExpression;
    }

    public List<Integer> parse() throws InvalidCronException {
        if(fieldExpression.equals("*")) {
            return Numbers.list(0, 59);
        } else if(fieldExpression.contains("-")) {
            return new RangeExpression(fieldExpression, FieldType.MINUTES).extract();
        } else if(fieldExpression.contains("/")) {
            String [] params = fieldExpression.split("/");
            int step = Integer.parseInt(params[1]);
            return Numbers.list(0, 59, step);
        } else {
            int minutes = Integer.parseInt(fieldExpression);
            return Numbers.list(minutes, minutes);
        }
    }

    public Map.Entry<Integer, Integer> getRange() {
        return new AbstractMap.SimpleEntry<>(1, 12);
    }
}
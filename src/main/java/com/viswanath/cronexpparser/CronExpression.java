package com.viswanath.cronexpparser;

import com.viswanath.cronexpparser.fields.HoursParser;
import com.viswanath.cronexpparser.model.CronData;
import com.viswanath.cronexpparser.utils.Numbers;
import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.MinutesParser;

import java.util.List;

import static com.viswanath.cronexpparser.errors.InvalidCronException.Type.*;

public class CronExpression {

    public String expression;

    public CronExpression(String expression) {
        this.expression = expression;
    }

    public CronData parse() throws InvalidCronException {
        if(expression == null) throw new InvalidCronException(NULL_INPUT);
        if(expression.isBlank()) throw new InvalidCronException(EMPTY_INPUT);
        String [] cronParams = extractCronParams();
        return new CronData(
                new MinutesParser(cronParams[0]).parse(),
                new HoursParser(cronParams[1]).parse(),
                extractDaysOfMonth(cronParams[2]),
                extractMonths(cronParams[3]),
                extractDaysOfWeek(cronParams[4])
        );
    }

    private List<Integer> extractDaysOfMonth(String expression) {
        if(expression.equals("*")) {
            return Numbers.list(1, 31);
        }
        return null;
    }

    private List<Integer> extractMonths(String expression) {
        if(expression.equals("*")) {
            return Numbers.list(1, 12);
        }
        return null;
    }

    private List<Integer> extractDaysOfWeek(String expression) {
        if(expression.equals("*")) {
            return Numbers.list(0, 6);
        }
        return null;
    }
    
    private String[] extractCronParams() throws InvalidCronException {
        String[] params = expression.split("\\s");
        if(params.length != 5) throw new InvalidCronException(INSUFFICIENT_FIELDS);
        return params;
    }
}

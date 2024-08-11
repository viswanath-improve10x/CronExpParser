package com.viswanath.cronexpparser;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.fields.*;
import com.viswanath.cronexpparser.model.CronData;

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
                new DaysOfMonthParser(cronParams[2]).parse(),
                new MonthParser(cronParams[3]).parse(),
                new DaysOfWeekParser(cronParams[4]).parse()
        );
    }

    private String[] extractCronParams() throws InvalidCronException {
        String[] params = expression.split("\\s");
        if(params.length != 5) throw new InvalidCronException(INSUFFICIENT_FIELDS);
        return params;
    }
}

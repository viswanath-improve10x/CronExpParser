package com.viswanath.cronexparser;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.*;
import com.viswanath.cronexparser.model.CronData;

import static com.viswanath.cronexparser.errors.InvalidCronException.Type.*;

public class CronExParser {

    public String expression;

    public CronExParser(String expression) {
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
                new MonthsParser(cronParams[3]).parse(),
                new DaysOfWeekParser(cronParams[4]).parse(),
                cronParams[5]
        );
    }

    private String[] extractCronParams() throws InvalidCronException {
        String[] params = expression.split("\\s", 6);
        if(params.length < 6) throw new InvalidCronException(INSUFFICIENT_FIELDS);
        return params;
    }
}
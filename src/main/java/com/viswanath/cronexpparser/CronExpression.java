package com.viswanath.cronexpparser;

import java.util.List;

import static com.viswanath.cronexpparser.InvalidCronException.Type.*;

public class CronExpression {

    public String expression;

    public CronExpression(String expression) {
        this.expression = expression;
    }

    public CronExpressionData parse() throws InvalidCronException {
        if(expression == null) throw new InvalidCronException(NULL_INPUT);
        if(expression.isBlank()) throw new InvalidCronException(EMPTY_INPUT);
        String [] cronParams = extractCronParams();
        return new CronExpressionData(
                new MinutesParser(cronParams[0]).parse(),
                extractHours(cronParams[1]),
                extractDaysOfMonth(cronParams[2]),
                extractMonths(cronParams[3]),
                extractDaysOfWeek(cronParams[4])
        );
    }

    private List<Integer> extractMinutes(String expression) {
        if(expression.equals("*")) {
            return Numbers.list(0, 59);
        } else if(expression.contains("-")) {
            String [] range = expression.split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            return Numbers.list(start, end);
        } else if(expression.contains("/")) {
            String [] params = expression.split("/");
            int step = Integer.parseInt(params[1]);
            return Numbers.list(0, 59, step);
        } else {
            int minutes = Integer.parseInt(expression);
            return Numbers.list(minutes, minutes);
        }
    }


    private List<Integer> extractHours(String expression) {
        if(expression.equals("*")) {
            return Numbers.list(0, 23);
        }
        return null;
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
        params[0] = getMinutesExpression(params[0]);
        params[1] = getHoursExpression(params[1]);
        params[2] = getDaysOfMonthExpression(params[2]);
        params[3] = getMonthsExpression(params[3]);
        params[4] = getDaysOfWeekExpression(params[4]);
        return params;
    }

    private String getHoursExpression(String hoursExpression) {
        if(hoursExpression.equals("*")) return hoursExpression;
        return null;
    }

    private String getDaysOfMonthExpression(String daysOfMonthExpression) {
        if(daysOfMonthExpression.equals("*")) return daysOfMonthExpression;
        return null;
    }

    private String getMonthsExpression(String monthsExpression) {
        if(monthsExpression.equals("*")) return monthsExpression;
        return null;
    }

    private String getDaysOfWeekExpression(String daysOfWeekExpression) {
        if(daysOfWeekExpression.equals("*")) return daysOfWeekExpression;
        return null;
    }

    private String getMinutesExpression(String minuteExpression) throws InvalidCronException {
        if(minuteExpression.contains("-")) {
            String minuteRangeRegex = "^(0?[0-9]|[1-5][0-9])-([0-5][0-9])$";
            if(!minuteExpression.matches(minuteRangeRegex)) {
                throw new InvalidCronException(MINUTE_FIELD_INVALID_RANGE);
            }
        } else if (minuteExpression.contains("/")) {
            String minuteStepRegex = "^\\*/([1-5]?[1-9])$";
            if(!minuteExpression.matches(minuteStepRegex)) {
                throw new InvalidCronException(MINUTE_FIELD_INVALID_STEP);
            }
        } else if(!minuteExpression.equals("*")) {
            int minutes = Integer.parseInt(minuteExpression);
            if (minutes > 60) {
                throw new InvalidCronException(MINUTE_FIELD_OUT_OF_RANGE);
            }
        }
        return minuteExpression;
    }
}

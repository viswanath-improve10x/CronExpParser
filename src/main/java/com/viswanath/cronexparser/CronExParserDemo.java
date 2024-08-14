package com.viswanath.cronexparser;

import com.viswanath.cronexparser.errors.InvalidCronException;

public class CronExParserDemo {
    public static void main(String[] args) throws InvalidCronException {
        if(args.length == 0) {
            throw new InvalidCronException(InvalidCronException.Type.NO_EXPRESSION);
        }
        CronExParser cronExParser = new CronExParser(args[0]);
        cronExParser.parse().print();
    }
}
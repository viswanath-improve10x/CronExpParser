package com.viswanath.cronexpparser.expressions;

import com.viswanath.cronexpparser.errors.InvalidCronException;

public class Constraints {
    public String regex;
    public InvalidCronException.Type errorType;

    public Constraints(String regex, InvalidCronException.Type errorType) {
        this.regex = regex;
        this.errorType = errorType;
    }
}

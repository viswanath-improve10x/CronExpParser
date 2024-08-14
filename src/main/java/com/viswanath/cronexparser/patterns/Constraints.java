package com.viswanath.cronexparser.patterns;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.fields.FieldType;

public record Constraints(String regex, FieldType fieldType, InvalidCronException.Type errorType) { }

package com.viswanath.cronexpparser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CronExpressionTest {

    private CronExpression cronExpression;
    private static final String NULL_EXPRESSION = null;
    private static final String EMPTY_EXPRESSION = "";
    private static final String SPACES_EXPRESSION = "  ";
    private static final String INSUFFICIENT_FIELDS_EXPRESSION = "* * * *";
    private static final String ALL_STARS_EXPRESSION = "* * * * *";
    private static final String INVALID_MINUTE_FIELD_EXPRESSION = "61 * * * *";
    private static final String SPECIFIC_MINUTE_FIELD_EXPRESSION = "59 * * * *";
    private static final String INVALID_RANGE_MINUTE_FIELD_EXPRESSION = "15-5 * * * *";
    private static final String VALID_RANGE_MINUTE_FIELD_EXPRESSION = "0-30 * * * *";
    private static final String INVALID_STEP_MINUTE_FIELD_EXPRESSION = "*/0 * * * *";
    private static final String VALID_STEP_MINUTE_FIELD_EXPRESSION = "*/5 * * * *";
    private static final String TEST_EXPRESSION = "*/15 0 1,15 * 1-5";

    @BeforeEach
    void setUp() {

    }

    private List<Integer> createListOfNumbers(int start, int end) {
        return createListOfNumbers(start, end, 1);
    }

    private List<Integer> createListOfNumbers(int start, int end, int step) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = start; i <= end; i += step) {
            numbers.add(i);
        }
        return numbers;
    }

    @Test
    void givenEmptyInput_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: empty input";
        cronExpression = new CronExpression(EMPTY_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenSpacesOnlyInput_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: empty input";
        cronExpression = new CronExpression(SPACES_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenNullInput_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: null input";
        cronExpression = new CronExpression(NULL_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenInsufficientFieldsExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: insufficient fields";
        cronExpression = new CronExpression(INSUFFICIENT_FIELDS_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
    @Test
    void givenAllAsteriskExpression_whenParsed_thenShouldExecuteEveryMinute() throws InvalidCronException {
        CronExpressionData expected = new CronExpressionData(
                createListOfNumbers(0, 59),
                createListOfNumbers(0, 23),
                createListOfNumbers(1, 31),
                createListOfNumbers(1, 12),
                createListOfNumbers(0, 6)
        );
        cronExpression = new CronExpression(ALL_STARS_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMinuteOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: minute field out of range";
        cronExpression = new CronExpression(INVALID_MINUTE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMinuteExpression_whenParsed_thenShouldIncludeSpecificMinute() throws InvalidCronException {
        CronExpressionData expected = new CronExpressionData(
                createListOfNumbers(59, 59),
                createListOfNumbers(0, 23),
                createListOfNumbers(1, 31),
                createListOfNumbers(1, 12),
                createListOfNumbers(0, 6)
        );
        cronExpression = new CronExpression(SPECIFIC_MINUTE_FIELD_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInMinuteFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in minute field";
        cronExpression = new CronExpression(INVALID_RANGE_MINUTE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMinuteRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronExpressionData expected = new CronExpressionData(
                createListOfNumbers(0, 30),
                createListOfNumbers(0, 23),
                createListOfNumbers(1, 31),
                createListOfNumbers(1, 12),
                createListOfNumbers(0, 6)
        );
        cronExpression = new CronExpression(VALID_RANGE_MINUTE_FIELD_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInMinuteFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in minute field";
        cronExpression = new CronExpression(INVALID_STEP_MINUTE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueMinuteExpression_whenParsed_thenShouldExecuteEveryFiveMinutes() throws InvalidCronException {
        CronExpressionData expected = new CronExpressionData(
                createListOfNumbers(0, 59, 5),
                createListOfNumbers(0, 23),
                createListOfNumbers(1, 31),
                createListOfNumbers(1, 12),
                createListOfNumbers(0, 6)
        );
        cronExpression = new CronExpression(VALID_STEP_MINUTE_FIELD_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @AfterEach
    void tearDown() {
    }
}
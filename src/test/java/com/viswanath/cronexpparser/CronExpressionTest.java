package com.viswanath.cronexpparser;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.model.CronData;
import com.viswanath.cronexpparser.utils.Numbers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CronExpressionTest {

    private CronExpression cronExpression;

    private static final String NULL_EXPRESSION = null;
    private static final String EMPTY_EXPRESSION = "";
    private static final String SPACES_EXPRESSION = "  ";
    private static final String INSUFFICIENT_FIELDS_EXPRESSION = "* * * *";
    private static final String ALL_STARS_EXPRESSION = "* * * * *";

    // Minute Field Expressions
    private static final String MINUTE_FIELD_OUT_OF_RANGE_EXPRESSION = "61 * * * *";
    private static final String MINUTE_FIELD_SPECIFIC_VALUE_EXPRESSION = "59 * * * *";
    private static final String MINUTE_FIELD_INVALID_RANGE_EXPRESSION = "15-5 * * * *";
    private static final String MINUTE_FIELD_VALID_RANGE_EXPRESSION = "0-30 * * * *";
    private static final String MINUTE_FIELD_INVALID_STEP_EXPRESSION = "*/0 * * * *";
    private static final String MINUTE_FIELD_VALID_STEP_EXPRESSION = "*/5 * * * *";
    private static final String MINUTE_FIELD_MULTIPLE_VALUES_EXPRESSION = "0,30 * * * *";

    // Hour Field Expressions
    private static final String HOUR_OUT_OF_RANGE_FIELD_EXPRESSION = "* 25 * * *";
    private static final String HOUR_FIELD_SPECIFIC_VALUE_EXPRESSION = "* 14 * * *";
    private static final String HOUR_FIELD_INVALID_RANGE_EXPRESSION = "* 15-5 * * *";
    private static final String HOUR_FIELD_VALID_RANGE_EXPRESSION = "* 0-20 * * *";
    private static final String HOUR_FIELD_INVALID_STEP_EXPRESSION = "* */0 * * *";
    private static final String HOUR_FIELD_VALID_STEP_EXPRESSION = "* */5 * * *";
    private static final String HOUR_FIELD_MULTIPLE_VALUES_EXPRESSION = "* 0,10 * * *";

    private static final String TEST_EXPRESSION = "*/15 0 1,15 * 1-5";

    @BeforeEach
    void setUp() {

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
        CronData expected = allStarsCronData();
        cronExpression = new CronExpression(ALL_STARS_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMinuteOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: minute field out of range";
        cronExpression = new CronExpression(MINUTE_FIELD_OUT_OF_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMinuteExpression_whenParsed_thenShouldIncludeSpecificMinute() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(59, 59);
        cronExpression = new CronExpression(MINUTE_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInMinuteFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in minute field";
        cronExpression = new CronExpression(MINUTE_FIELD_INVALID_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMinuteRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(0, 30);
        cronExpression = new CronExpression(MINUTE_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInMinuteFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in minute field";
        cronExpression = new CronExpression(MINUTE_FIELD_INVALID_STEP_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueMinuteExpression_whenParsed_thenShouldExecuteEveryFiveMinutes() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(0, 59, 5);
        cronExpression = new CronExpression(MINUTE_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenListOfMinutesExpression_whenParsed_thenShouldExecuteAtZeroAndThirtyMinutes() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = List.of(0, 30);
        cronExpression = new CronExpression(MINUTE_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenHourOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() throws InvalidCronException {
        String expectedErrorMessage = "Invalid Cron Expression: hour field out of range";
        cronExpression = new CronExpression(HOUR_OUT_OF_RANGE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidHourExpression_whenParsed_thenShouldIncludeSpecificMinute() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(14);
        cronExpression = new CronExpression(HOUR_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInHoursFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in hour field";
        cronExpression = new CronExpression(HOUR_FIELD_INVALID_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }


    @Test
    void givenValidHourRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(0, 20);
        cronExpression = new CronExpression(HOUR_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInHourFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in hour field";
        cronExpression = new CronExpression(HOUR_FIELD_INVALID_STEP_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueInHourExpression_whenParsed_thenShouldExecuteEveryFiveHours() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(0, 23, 5);
        cronExpression = new CronExpression(HOUR_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMultipleHoursInHoursExpression_whenParsed_thenShouldExecuteAtZeroAndTenHours() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = List.of(0, 10);
        cronExpression = new CronExpression(HOUR_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }


    private CronData allStarsCronData() {
        return new CronData(
                Numbers.list(0, 59),
                Numbers.list(0, 23),
                Numbers.list(1, 31),
                Numbers.list(1, 12),
                Numbers.list(0, 6)
        );
    }

    @AfterEach
    void tearDown() {
    }
}
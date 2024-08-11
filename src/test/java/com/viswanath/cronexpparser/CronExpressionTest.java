package com.viswanath.cronexpparser;

import com.viswanath.cronexpparser.errors.InvalidCronException;
import com.viswanath.cronexpparser.model.CronData;
import com.viswanath.cronexpparser.utils.Numbers;
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

    // Day of Month Field Expressions
    private static final String DAY_OF_MONTH_OUT_OF_RANGE_FIELD_EXPRESSION = "* * 32 * *";
    private static final String DAY_OF_MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * 15 * *";
    private static final String DAY_OF_MONTH_FIELD_INVALID_RANGE_EXPRESSION = "* * 15-5 * *";
    private static final String DAY_OF_MONTH_FIELD_VALID_RANGE_EXPRESSION = "* * 1-31 * *";
    private static final String DAY_OF_MONTH_FIELD_INVALID_STEP_EXPRESSION = "* * */0 * *";
    private static final String DAY_OF_MONTH_FIELD_VALID_STEP_EXPRESSION = "* * */5 * *";
    private static final String DAY_OF_MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * 1,10 * *";

    // Month Field Expressions
    private static final String MONTH_OUT_OF_RANGE_FIELD_EXPRESSION = "* * * 16 *";
    private static final String MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * * 8 *";
    private static final String MONTH_FIELD_INVALID_RANGE_EXPRESSION = "* * * 12-5 *";
    private static final String MONTH_FIELD_VALID_RANGE_EXPRESSION = "* * * 6-12 *";
    private static final String MONTH_FIELD_INVALID_STEP_EXPRESSION = "* * * */0 *";
    private static final String MONTH_FIELD_VALID_STEP_EXPRESSION = "* * * */3 *";
    private static final String MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * * 5,10 *";

    // Day of Week Field Expressions
    private static final String DAY_OF_WEEK_OUT_OF_RANGE_FIELD_EXPRESSION = "* * * * 7";
    private static final String DAY_OF_WEEK_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * * * 6";
    private static final String DAY_OF_WEEK_FIELD_INVALID_RANGE_EXPRESSION = "* * * * 5-1";
    private static final String DAY_OF_WEEK_FIELD_VALID_RANGE_EXPRESSION = "* * * * 0-6";
    private static final String DAY_OF_WEEK_FIELD_INVALID_STEP_EXPRESSION = "* * * * */0";
    private static final String DAY_OF_WEEK_FIELD_VALID_STEP_EXPRESSION = "* * * * */2";
    private static final String DAY_OF_WEEK_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * * * 1,4";

    // Further Expressions
    private static final String TEST_EXPRESSION_1 = "*/15 0 1,15 * 1-5";

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

    @Test
    void givenDayOfMonthOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() throws InvalidCronException {
        String expectedErrorMessage = "Invalid Cron Expression: day of month field out of range";
        cronExpression = new CronExpression(DAY_OF_MONTH_OUT_OF_RANGE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidDayOfMonthExpression_whenParsed_thenShouldIncludeSpecificMinute() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(15);
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInDayOfMonthFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in day of month field";
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_INVALID_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidDayOfMonthRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(1, 31);
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInDayOfMonthFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in day of month field";
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_INVALID_STEP_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueInDayOfMonthExpression_whenParsed_thenShouldExecuteEveryFiveDays() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(1, 31, 5);
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMultipleHoursInDayOfMonthExpression_whenParsed_thenShouldExecuteAtOneAndTenDays() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = List.of(1, 10);
        cronExpression = new CronExpression(DAY_OF_MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMonthOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() throws InvalidCronException {
        String expectedErrorMessage = "Invalid Cron Expression: month field out of range";
        cronExpression = new CronExpression(MONTH_OUT_OF_RANGE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMonthExpression_whenParsed_thenShouldIncludeSpecificMonth() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = Numbers.list(8);
        cronExpression = new CronExpression(MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInMonthFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in month field";
        cronExpression = new CronExpression(MONTH_FIELD_INVALID_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidMonthRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronData expected = allStarsCronData();
         expected.months = Numbers.list(6, 12);
        cronExpression = new CronExpression(MONTH_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInMonthFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in month field";
        cronExpression = new CronExpression(MONTH_FIELD_INVALID_STEP_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueInMonthExpression_whenParsed_thenShouldExecuteEveryFiveMonths() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = Numbers.list(1, 12, 3);
        cronExpression = new CronExpression(MONTH_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMultipleValuesInMonthExpression_whenParsed_thenShouldExecuteAtFiveAndTenMonths() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = List.of(5, 10);
        cronExpression = new CronExpression(MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenDayOfWeekOutOfRangeExpression_whenParsed_thenShouldThrowInvalidCronExpression() throws InvalidCronException {
        String expectedErrorMessage = "Invalid Cron Expression: day of week field out of range";
        cronExpression = new CronExpression(DAY_OF_WEEK_OUT_OF_RANGE_FIELD_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }


    @Test
    void givenValidDayOfWeekExpression_whenParsed_thenShouldIncludeSpecificDayOfWeek() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(6);
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidRangeInDayOfWeekFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid range in day of week field";
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_INVALID_RANGE_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidDayOfWeekRangeExpression_whenParsed_thenShouldExecuteWithinRange() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(0, 6);
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenInvalidStepValueInDayOfWeekFieldExpression_whenParsed_thenShouldThrowInvalidCronExpression() {
        String expectedErrorMessage = "Invalid Cron Expression: invalid step value in day of week field";
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_INVALID_STEP_EXPRESSION);
        Exception exception = assertThrows(InvalidCronException.class, () -> {cronExpression.parse();});
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void givenValidStepValueInDayOfWeekExpression_whenParsed_thenShouldExecuteEveryTwoDays() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(0, 6, 2);
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenMultipleValuesInDayOfWeekExpression_whenParsed_thenShouldExecuteAtFiveAndTenMonths() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = List.of(1, 4);
        cronExpression = new CronExpression(DAY_OF_WEEK_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExpression.parse());
    }

    @Test
    void givenValidExpression_whenParsed_thenShouldExecuteAccordingly() throws InvalidCronException {
        CronData expected = new CronData(
            Numbers.list(0, 59, 15),
            Numbers.list(0),
            List.of(1, 15),
            Numbers.list(1, 12),
            Numbers.list(1, 5)
        );
        cronExpression = new CronExpression(TEST_EXPRESSION_1);
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
}
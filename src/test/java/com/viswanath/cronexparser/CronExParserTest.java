package com.viswanath.cronexparser;

import com.viswanath.cronexparser.errors.InvalidCronException;
import com.viswanath.cronexparser.errors.InvalidCronException.Type;
import com.viswanath.cronexparser.fields.FieldType;
import com.viswanath.cronexparser.model.CronData;
import com.viswanath.cronexparser.utils.Numbers;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CronExParserTest {

    private CronExParser cronExParser;

    private static final String NULL_EXPRESSION = null;
    private static final String EMPTY_EXPRESSION = "";
    private static final String BLANK_EXPRESSION = "  ";
    private static final String INSUFFICIENT_FIELDS_EXPRESSION = "* * * * /usr/bin/find";
    private static final String ALL_STARS_EXPRESSION = "* * * * * /usr/bin/find";

    // Minute Field Expressions
    private static final String MINUTE_FIELD_OUT_OF_RANGE_EXPRESSION = "61 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_SPECIFIC_VALUE_EXPRESSION = "59 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_INVALID_RANGE_EXPRESSION = "15-5 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_VALID_RANGE_EXPRESSION = "0-30 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_INVALID_STEP_EXPRESSION = "*/0 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_VALID_STEP_EXPRESSION = "*/5 * * * * /usr/bin/find";
    private static final String MINUTE_FIELD_MULTIPLE_VALUES_EXPRESSION = "0,30 * * * * /usr/bin/find";

    // Hour Field Expressions
    private static final String HOUR_OUT_OF_RANGE_FIELD_EXPRESSION = "* 25 * * * /usr/bin/find";
    private static final String HOUR_FIELD_SPECIFIC_VALUE_EXPRESSION = "* 14 * * * /usr/bin/find";
    private static final String HOUR_FIELD_INVALID_RANGE_EXPRESSION = "* 15-5 * * * /usr/bin/find";
    private static final String HOUR_FIELD_VALID_RANGE_EXPRESSION = "* 0-20 * * * /usr/bin/find";
    private static final String HOUR_FIELD_INVALID_STEP_EXPRESSION = "* */0 * * * /usr/bin/find";
    private static final String HOUR_FIELD_VALID_STEP_EXPRESSION = "* */5 * * * /usr/bin/find";
    private static final String HOUR_FIELD_MULTIPLE_VALUES_EXPRESSION = "* 0,10 * * * /usr/bin/find";

    // Day of Month Field Expressions
    private static final String DAY_OF_MONTH_OUT_OF_RANGE_FIELD_EXPRESSION = "* * 32 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * 15 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_INVALID_RANGE_EXPRESSION = "* * 15-5 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_VALID_RANGE_EXPRESSION = "* * 1-31 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_INVALID_STEP_EXPRESSION = "* * */0 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_VALID_STEP_EXPRESSION = "* * */5 * * /usr/bin/find";
    private static final String DAY_OF_MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * 1,10 * * /usr/bin/find";

    // Month Field Expressions
    private static final String MONTH_OUT_OF_RANGE_FIELD_EXPRESSION = "* * * 16 * /usr/bin/find";
    private static final String MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * * 8 * /usr/bin/find";
    private static final String MONTH_FIELD_INVALID_RANGE_EXPRESSION = "* * * 12-5 * /usr/bin/find";
    private static final String MONTH_FIELD_VALID_RANGE_EXPRESSION = "* * * 6-12 * /usr/bin/find";
    private static final String MONTH_FIELD_INVALID_STEP_EXPRESSION = "* * * */0 * /usr/bin/find";
    private static final String MONTH_FIELD_VALID_STEP_EXPRESSION = "* * * */3 * /usr/bin/find";
    private static final String MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * * 5,10 * /usr/bin/find";

    // Day of Week Field Expressions
    private static final String DAY_OF_WEEK_OUT_OF_RANGE_FIELD_EXPRESSION = "* * * * 7 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_SPECIFIC_VALUE_EXPRESSION = "* * * * 6 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_INVALID_RANGE_EXPRESSION = "* * * * 5-1 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_VALID_RANGE_EXPRESSION = "* * * * 0-6 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_INVALID_STEP_EXPRESSION = "* * * * */0 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_VALID_STEP_EXPRESSION = "* * * * */2 /usr/bin/find";
    private static final String DAY_OF_WEEK_FIELD_MULTIPLE_VALUES_EXPRESSION = "* * * * 1,4 /usr/bin/find";

    // Command Expressions
    private static final String COMMAND_WITH_ONE_PART_IN_EXPRESSION = "* * * * * /usr/bin/find";
    private static final String COMMAND_WITH_TWO_PARTS_IN_EXPRESSION = "* * * * * /usr/bin/find .";
    private static final String COMMAND_WITH_THREE_PARTS_IN_EXPRESSION = "* * * * * /usr/bin/find . -name";
    private static final String COMMAND_WITH_FOUR_PARTS_IN_EXPRESSION = "* * * * * /usr/bin/find . -name 'log'";

    // Further Expressions
    private static final String TEST_EXPRESSION_1 = "*/15 0 1,15 * 1-5 /usr/bin/find";

    @Test
    void givenEmptyExpression_whenParsed_thenShouldThrowInvalidCronExceptionWithEmptyInputType() {
        CronExParser cronExParser = new CronExParser(EMPTY_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.EMPTY_INPUT, exception.getType());
        assertNull(exception.getFieldType());
        assertEquals("Empty value", exception.getMessage());
    }

    @Test
    void givenBlankExpression_whenParsed_thenShouldThrowInvalidCronExceptionWithEmptyInputType() {
        CronExParser cronExParser = new CronExParser(BLANK_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.EMPTY_INPUT, exception.getType());
        assertNull(exception.getFieldType());
        assertEquals("Empty value", exception.getMessage());
    }

    @Test
    void givenNullExpression_whenParsed_thenShouldThrowInvalidCronExceptionWithNullInputType() {
        CronExParser cronExParser = new CronExParser(NULL_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.NULL_INPUT, exception.getType());
        assertNull(exception.getFieldType());
        assertEquals("Null value", exception.getMessage());
    }

    @Test
    void givenInsufficientFieldsExpression_whenParsed_thenShouldThrowInvalidCronExceptionWithInsufficientFields() {
        CronExParser cronExParser = new CronExParser(INSUFFICIENT_FIELDS_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INSUFFICIENT_FIELDS, exception.getType());
        assertNull(exception.getFieldType());
        assertEquals("Insufficient fields", exception.getMessage());
    }

    @Test
    void givenAllStars_whenParsed_thenShouldExecuteEveryMinute() throws InvalidCronException {
        CronData expected = allStarsCronData();
        cronExParser = new CronExParser(ALL_STARS_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMinuteFieldOutOfRange_61_whenParsed_thenShouldThrowInvalidCronExceptionWithOutOfRangeType() {
        CronExParser cronExParser = new CronExParser(MINUTE_FIELD_OUT_OF_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.OUT_OF_RANGE, exception.getType());
        assertEquals(FieldType.MINUTES, exception.getFieldType());
        assertEquals("Field out of range in minute", exception.getMessage());
    }

    @Test
    void givenMinuteFieldSpecificValue_59_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(59);
        cronExParser = new CronExParser(MINUTE_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMinuteFieldInvalidRange_15to5_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidRangeType() {
        CronExParser cronExParser = new CronExParser(MINUTE_FIELD_INVALID_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_RANGE, exception.getType());
        assertEquals(FieldType.MINUTES, exception.getFieldType());
        assertEquals("Invalid range in minute", exception.getMessage());
    }

    @Test
    void givenMinuteFieldValidRange_0to30_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(0, 30);
        cronExParser = new CronExParser(MINUTE_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMinuteFieldInvalidStep_0_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidStepType() {
        CronExParser cronExParser = new CronExParser(MINUTE_FIELD_INVALID_STEP_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_STEP, exception.getType());
        assertEquals(FieldType.MINUTES, exception.getFieldType());
        assertEquals("Invalid step value in minute", exception.getMessage());
    }

    @Test
    void givenMinuteFieldValidStep_5_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = Numbers.list(0, 59, 5);
        cronExParser = new CronExParser(MINUTE_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMinuteFieldMultipleValues_0and30_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.minutes = List.of(0, 30);
        cronExParser = new CronExParser(MINUTE_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenHourFieldOutOfRange_25_whenParsed_thenShouldThrowInvalidCronExceptionWithOutOfRangeType() {
        CronExParser cronExParser = new CronExParser(HOUR_OUT_OF_RANGE_FIELD_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.OUT_OF_RANGE, exception.getType());
        assertEquals(FieldType.HOURS, exception.getFieldType());
        assertEquals("Field out of range in hour", exception.getMessage());
    }

    @Test
    void givenHourFieldSpecificValue_14_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(14);
        cronExParser = new CronExParser(HOUR_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenHourFieldInvalidRange_15to5_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidRangeType() {
        CronExParser cronExParser = new CronExParser(HOUR_FIELD_INVALID_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_RANGE, exception.getType());
        assertEquals(FieldType.HOURS, exception.getFieldType());
        assertEquals("Invalid range in hour", exception.getMessage());
    }

    @Test
    void givenHourFieldValidRange_0to20_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(0, 20);
        cronExParser = new CronExParser(HOUR_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenHourFieldInvalidStep_0_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidStepType() {
        CronExParser cronExParser = new CronExParser(HOUR_FIELD_INVALID_STEP_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_STEP, exception.getType());
        assertEquals(FieldType.HOURS, exception.getFieldType());
        assertEquals("Invalid step value in hour", exception.getMessage());
    }

    @Test
    void givenHourFieldValidStep_5_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = Numbers.list(0, 23, 5);
        cronExParser = new CronExParser(HOUR_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenHourFieldMultipleValues_0and10_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.hours = List.of(0, 10);
        cronExParser = new CronExParser(HOUR_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfMonthFieldOutOfRange_32_whenParsed_thenShouldThrowInvalidCronExceptionWithOutOfRangeType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_MONTH_OUT_OF_RANGE_FIELD_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.OUT_OF_RANGE, exception.getType());
        assertEquals(FieldType.DAY_OF_MONTH, exception.getFieldType());
        assertEquals("Field out of range in day of month", exception.getMessage());
    }

    @Test
    void givenDayOfMonthFieldSpecificValue_15_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(15);
        cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfMonthFieldInvalidRange_15to5_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidRangeType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_INVALID_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_RANGE, exception.getType());
        assertEquals(FieldType.DAY_OF_MONTH, exception.getFieldType());
        assertEquals("Invalid range in day of month", exception.getMessage());
    }

    @Test
    void givenDayOfMonthFieldValidRange_1to31_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(1, 31);
        cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfMonthFieldInvalidStep_0_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidStepType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_INVALID_STEP_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_STEP, exception.getType());
        assertEquals(FieldType.DAY_OF_MONTH, exception.getFieldType());
        assertEquals("Invalid step value in day of month", exception.getMessage());
    }

    @Test
    void givenDayOfMonthFieldValidStep_5_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = Numbers.list(1, 31, 5);
        cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfMonthFieldMultipleValues_1and10_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfMonth = List.of(1, 10);
        cronExParser = new CronExParser(DAY_OF_MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMonthFieldOutOfRange_16_whenParsed_thenShouldThrowInvalidCronExceptionWithOutOfRangeType() {
        CronExParser cronExParser = new CronExParser(MONTH_OUT_OF_RANGE_FIELD_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.OUT_OF_RANGE, exception.getType());
        assertEquals(FieldType.MONTH, exception.getFieldType());
        assertEquals("Field out of range in month", exception.getMessage());
    }

    @Test
    void givenMonthFieldSpecificValue_8_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = Numbers.list(8);
        cronExParser = new CronExParser(MONTH_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMonthFieldInvalidRange_12to5_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidRangeType() {
        CronExParser cronExParser = new CronExParser(MONTH_FIELD_INVALID_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_RANGE, exception.getType());
        assertEquals(FieldType.MONTH, exception.getFieldType());
        assertEquals("Invalid range in month", exception.getMessage());
    }

    @Test
    void givenMonthFieldValidRange_6to12_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
         expected.months = Numbers.list(6, 12);
        cronExParser = new CronExParser(MONTH_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMonthFieldInvalidStep_0_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidStepType() {
        CronExParser cronExParser = new CronExParser(MONTH_FIELD_INVALID_STEP_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_STEP, exception.getType());
        assertEquals(FieldType.MONTH, exception.getFieldType());
        assertEquals("Invalid step value in month", exception.getMessage());
    }

    @Test
    void givenMonthFieldValidStep_3_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = Numbers.list(1, 12, 3);
        cronExParser = new CronExParser(MONTH_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenMonthFieldMultipleValues_5and10_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.months = List.of(5, 10);
        cronExParser = new CronExParser(MONTH_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfWeekFieldOutOfRange_7_whenParsed_thenShouldThrowInvalidCronExceptionWithOutOfRangeType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_WEEK_OUT_OF_RANGE_FIELD_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.OUT_OF_RANGE, exception.getType());
        assertEquals(FieldType.DAY_OF_WEEK, exception.getFieldType());
        assertEquals("Field out of range in day of week", exception.getMessage());
    }


    @Test
    void givenDayOfWeekFieldSpecificValue_6_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(6);
        cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_SPECIFIC_VALUE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfWeekFieldInvalidRange_5to1_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidRangeType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_INVALID_RANGE_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_RANGE, exception.getType());
        assertEquals(FieldType.DAY_OF_WEEK, exception.getFieldType());
        assertEquals("Invalid range in day of week", exception.getMessage());
    }

    @Test
    void givenDayOfWeekFieldValidRange_0to6_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(0, 6);
        cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_VALID_RANGE_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfWeekFieldInvalidStep_0_whenParsed_thenShouldThrowInvalidCronExceptionWithInvalidStepType() {
        CronExParser cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_INVALID_STEP_EXPRESSION);
        InvalidCronException exception = assertThrows(InvalidCronException.class, cronExParser::parse);
        assertEquals(Type.INVALID_STEP, exception.getType());
        assertEquals(FieldType.DAY_OF_WEEK, exception.getFieldType());
        assertEquals("Invalid step value in day of week", exception.getMessage());
    }

    @Test
    void givenDayOfWeekFieldValidStep_2_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = Numbers.list(0, 6, 2);
        cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_VALID_STEP_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenDayOfWeekFieldMultipleValues_1and4_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.daysOfWeek = List.of(1, 4);
        cronExParser = new CronExParser(DAY_OF_WEEK_FIELD_MULTIPLE_VALUES_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenValidExpression_whenParsed_thenShouldExecuteAccordingly() throws InvalidCronException {
        CronData expected = new CronData(
                Numbers.list(0, 59, 15),
                Numbers.list(0),
                List.of(1, 15),
                Numbers.list(1, 12),
                Numbers.list(1, 5),
                "/usr/bin/find");
        cronExParser = new CronExParser(TEST_EXPRESSION_1);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenCommandWithOnePart_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.command = "/usr/bin/find";
        cronExParser = new CronExParser(COMMAND_WITH_ONE_PART_IN_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenCommandWithTwoParts_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.command = "/usr/bin/find .";
        cronExParser = new CronExParser(COMMAND_WITH_TWO_PARTS_IN_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenCommandWithThreeParts_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.command = "/usr/bin/find . -name";
        cronExParser = new CronExParser(COMMAND_WITH_THREE_PARTS_IN_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenCommandWithFourParts_whenParsed_thenShouldReturnExpectedSchedule() throws InvalidCronException {
        CronData expected = allStarsCronData();
        expected.command = "/usr/bin/find . -name 'log'";
        cronExParser = new CronExParser(COMMAND_WITH_FOUR_PARTS_IN_EXPRESSION);
        assertEquals(expected, cronExParser.parse());
    }

    @Test
    void givenComplexExpression_whenParsed_thenShouldReturnExpectedSchedule() {
        String expected = """ 
                    minute        0 15 30 45
                    hour          0
                    day of month  1 15
                    month         1 2 3 4 5 6 7 8 9 10 11 12
                    day of week   1 2 3 4 5
                    command       /usr/bin/find
                    """;
        PrintStream defaultPrintStream = System.out;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        new CronData(
                Numbers.list(0, 59, 15),
                Numbers.list(0),
                List.of(1, 15),
                Numbers.list(1, 12),
                Numbers.list(1, 5),
                "/usr/bin/find"
        ).print();
        System.setOut(defaultPrintStream);
        String actual = outStream.toString();
        assertEquals(expected, actual);
    }

    private CronData allStarsCronData() {
        return new CronData(
                Numbers.list(0, 59),
                Numbers.list(0, 23),
                Numbers.list(1, 31),
                Numbers.list(1, 12),
                Numbers.list(0, 6),
                "/usr/bin/find");
    }
}
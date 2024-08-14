package com.viswanath.cronexparser.model;

import com.viswanath.cronexparser.utils.Numbers;

import java.util.List;
import java.util.Objects;

import static com.viswanath.cronexparser.fields.FieldType.*;

public class CronData {
    public List<Integer> minutes;
    public List<Integer> hours;
    public List<Integer> daysOfMonth;
    public List<Integer> months;
    public List<Integer> daysOfWeek;
    public String command;

    public CronData(List<Integer> minutes, List<Integer> hours, List<Integer> daysOfMonth, List<Integer> months, List<Integer> daysOfWeek, String command) {
        this.minutes = minutes;
        this.hours = hours;
        this.daysOfMonth = daysOfMonth;
        this.months = months;
        this.daysOfWeek = daysOfWeek;
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CronData cronData = (CronData) o;
        return Objects.equals(minutes, cronData.minutes) && Objects.equals(hours, cronData.hours) && Objects.equals(daysOfMonth, cronData.daysOfMonth) && Objects.equals(months, cronData.months) && Objects.equals(daysOfWeek, cronData.daysOfWeek) && Objects.equals(command, cronData.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes, hours, daysOfMonth, months, daysOfWeek, command);
    }

    @Override
    public String toString() {
        return "CronData{" +
                "minutes=" + minutes +
                ", hours=" + hours +
                ", daysOfMonth=" + daysOfMonth +
                ", months=" + months +
                ", daysOfWeek=" + daysOfWeek +
                ", command='" + command + '\'' +
                '}';
    }

    public void print() {
        System.out.printf("%-14s%s\n", MINUTES.label, Numbers.printable(minutes));
        System.out.printf("%-14s%s\n", HOURS.label, Numbers.printable(hours));
        System.out.printf("%-14s%s\n", DAY_OF_MONTH.label, Numbers.printable(daysOfMonth));
        System.out.printf("%-14s%s\n", MONTH.label, Numbers.printable(months));
        System.out.printf("%-14s%s\n", DAY_OF_WEEK.label, Numbers.printable(daysOfWeek));
        System.out.printf("%-14s%s\n", "command", command);
    }
}

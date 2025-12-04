package com.candescent.PartyService.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date and time utilities.
 */
public final class DateTimeUtils {

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ISO_DATETIME_TZ_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";
    public static final String STANDARD_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String US_DATE_FORMAT = "MM/dd/yyyy";

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT);
    private static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATETIME_FORMAT);
    private static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT);
    private static final DateTimeFormatter STANDARD_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATETIME_FORMAT);

    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** Gets current date-time. */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /** Gets current date. */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /** Gets current time. */
    public static LocalTime currentTime() {
        return LocalTime.now();
    }

    /** Gets current timestamp in milliseconds. */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /** Gets current instant. */
    public static Instant currentInstant() {
        return Instant.now();
    }

    /** Formats date to ISO format (yyyy-MM-dd). */
    public static String formatToIsoDate(LocalDate date) {
        return date == null ? null : date.format(ISO_DATE_FORMATTER);
    }

    /** Formats date-time to ISO format. */
    public static String formatToIsoDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(ISO_DATETIME_FORMATTER);
    }

    /** Formats date to standard format (dd/MM/yyyy). */
    public static String formatToStandardDate(LocalDate date) {
        return date == null ? null : date.format(STANDARD_DATE_FORMATTER);
    }

    /** Formats date-time to standard format. */
    public static String formatToStandardDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(STANDARD_DATETIME_FORMATTER);
    }

    /** Formats date-time using custom pattern. */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /** Parses ISO date string. */
    public static LocalDate parseIsoDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, ISO_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Parses ISO date-time string. */
    public static LocalDateTime parseIsoDateTime(String dateTimeString) {
        if (StringUtils.isBlank(dateTimeString)) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, ISO_DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Converts Date to LocalDateTime. */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /** Converts LocalDateTime to Date. */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /** Converts epoch milliseconds to LocalDateTime. */
    public static LocalDateTime fromEpochMillis(long epochMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault());
    }

    /** Converts LocalDateTime to epoch milliseconds. */
    public static long toEpochMillis(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /** Calculates days between two dates. */
    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start, end);
    }

    /** Calculates hours between two date-times. */
    public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.HOURS.between(start, end);
    }

    /** Checks if date is in the past. */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(today());
    }

    /** Checks if date is in the future. */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(today());
    }

    /** Checks if date-time is in the past. */
    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isBefore(now());
    }

    /** Checks if date-time is in the future. */
    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(now());
    }

    /** Gets start of day (00:00:00). */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }

    /** Gets end of day (23:59:59.999999999). */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MAX);
    }

    /** Adds business days (excludes weekends). */
    public static LocalDate addBusinessDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (result.getDayOfWeek() != DayOfWeek.SATURDAY && result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                addedDays++;
            }
        }
        return result;
    }

    /** Checks if date is a weekend. */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}

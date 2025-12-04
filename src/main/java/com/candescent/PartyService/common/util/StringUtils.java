package com.candescent.PartyService.common.util;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * String manipulation utilities.
 */
public final class StringUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{1,14}$"
    );

    private StringUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** Checks if string is null or empty. */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /** Checks if string is not null and not empty. */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /** Checks if string is null, empty, or whitespace only. */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /** Checks if string is not blank. */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /** Trims string and returns null if result is empty. */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }
        String trimmed = str.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /** Trims string and returns empty string if input is null. */
    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    /** Returns default value if string is empty. */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /** Returns default value if string is blank. */
    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    /** Capitalizes first letter. */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /** Converts first letter to lowercase. */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /** Validates email format. */
    public static boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /** Validates phone number format (E.164). */
    public static boolean isValidPhoneNumber(String phone) {
        if (isBlank(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim().replaceAll("[\\s()-]", "")).matches();
    }

    /** Generates random UUID. */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /** Generates UUID without hyphens. */
    public static String generateCompactUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /** Truncates string to max length. */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /** Truncates string with ellipsis. */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        if (maxLength <= 3) {
            return str.substring(0, maxLength);
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    /** Joins collection with delimiter. */
    public static String join(Collection<String> collection, String delimiter) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        return String.join(delimiter, collection);
    }

    /** Masks string keeping first and last characters. */
    public static String mask(String str) {
        if (isEmpty(str) || str.length() <= 2) {
            return str;
        }
        int length = str.length();
        return str.charAt(0) + "*".repeat(length - 2) + str.charAt(length - 1);
    }

    /** Masks email showing first 2 characters and domain. */
    public static String maskEmail(String email) {
        if (isBlank(email) || !email.contains("@")) {
            return email;
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email;
        }
        return email.substring(0, 2) + "***" + email.substring(atIndex);
    }
}

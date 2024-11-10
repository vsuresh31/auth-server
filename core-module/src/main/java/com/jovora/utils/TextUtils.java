package com.jovora.utils;

import java.util.regex.Pattern;

public class TextUtils {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private TextUtils() {}

    public static boolean isEmail(String data) {
        return pattern.matcher(data).matches();
    }
}

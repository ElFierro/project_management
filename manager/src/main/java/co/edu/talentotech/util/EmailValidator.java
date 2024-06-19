package co.edu.talentotech.util;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        return !email.contains(" ") && pattern.matcher(email).matches();
    }
}

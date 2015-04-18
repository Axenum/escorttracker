package com.allits.escorttracker.common;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
    private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    private static Pattern pattern = java.util.regex.Pattern.compile(
            "^" + ATOM + "+(\\." + ATOM + "+)*@"
                    + DOMAIN
                    + "|"
                    + IP_DOMAIN
                    + ")$",
            java.util.regex.Pattern.CASE_INSENSITIVE
    );

    public static boolean isValid(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}


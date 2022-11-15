package com.nl.gerimedica.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static LocalDate parseDate(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static Integer parseInteger(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        return Integer.parseInt(text);
    }
}

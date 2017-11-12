package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final SimpleDateFormat MAIN_FORMATTER = new SimpleDateFormat("EEE, MMMMM d, yyyy h:mm a");
    private static final SimpleDateFormat SUB_FORMATTER = new SimpleDateFormat("EEE, MMMMM d, yyyy h:mm:ss a");


    public static Date String2Date(String dateString) {
        return String2Date(dateString, MAIN_FORMATTER);
    }

    private static Date String2Date(String dateString, SimpleDateFormat converter) {
        Date result = null;
        try {
            result = converter.parse(dateString);
        }catch (ParseException ex) {
            Logger.print("Parse_Exception: " + ex.getMessage(),"DateConverter");
            String2Date(dateString, SUB_FORMATTER);
        }
        return result;
    }
}

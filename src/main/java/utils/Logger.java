package utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private String message;

    public Logger(String message, String caller) {
        String timeStamp = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss").format(Calendar.getInstance());
        System.out.format("OUTPUT: %s | %s: %s", timeStamp, caller, message);
    }

    public static void print(String message, String caller) {
        String timeStamp = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.format("OUTPUT: %s | %s: %s \n", timeStamp, caller, message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

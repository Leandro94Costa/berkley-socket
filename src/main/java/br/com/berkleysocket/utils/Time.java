package br.com.berkleysocket.utils;

import java.util.Calendar;

public class Time {

    public static String getTime() {
        Calendar now = Calendar.getInstance();
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String year = String.valueOf(now.get(Calendar.YEAR));
        String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        String seconds = String.valueOf(now.get(Calendar.SECOND));
        return day + "/" + month + "/" + year + " " + hour + ":" + minutes + ":" + seconds;
    }
}

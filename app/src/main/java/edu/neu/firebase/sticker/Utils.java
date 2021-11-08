package edu.neu.firebase.sticker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static String formatTime(String time) {
        Date date = new Date(Long.parseLong(time));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formattedDate = format.format(date);

        return formattedDate;
    }

    public static String showGreetingWordsByCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 12) {
            return "Good Morning";
        } else if (timeOfDay < 17) {
            return "Good Afternoon";
        } else {
            return timeOfDay < 21 ? "Good Evening" : "Good Night";
        }
    }
}

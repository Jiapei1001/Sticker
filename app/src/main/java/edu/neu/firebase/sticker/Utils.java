package edu.neu.firebase.sticker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Utils {

    public static String formatTime(String time) {
        Date date = new Date(Long.parseLong(time));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formattedDate = format.format(date);

        return formattedDate;
    }
  
    public static HashMap<String, Integer> loadStickerMap() {
        HashMap<String, Integer> stickerMap = new HashMap<String, Integer>() {{
            put("sticker1", R.drawable.sticker1);
            put("sticker2", R.drawable.sticker2);
            put("sticker3", R.drawable.sticker3);
            put("sticker4", R.drawable.sticker4);
            put("sticker5", R.drawable.sticker5);
            put("sticker6", R.drawable.sticker6);
            put("sticker7", R.drawable.sticker7);
            put("sticker8", R.drawable.sticker8);
            put("sticker9", R.drawable.sticker9);
            put("sticker10", R.drawable.sticker10);
            put("sticker11", R.drawable.sticker11);
            put("sticker12", R.drawable.sticker12);
            put("sticker13", R.drawable.sticker13);
            put("sticker14", R.drawable.sticker14);
            put("sticker15", R.drawable.sticker15);
        }};

        return stickerMap;
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

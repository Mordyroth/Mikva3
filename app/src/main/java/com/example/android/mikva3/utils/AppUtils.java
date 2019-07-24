package com.example.android.mikva3.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppUtils {

    public static String DEVICES_DB = "Devices";
    public static String ROOM_TABLE = "Room";
    public static String ROOM_NUMBER = "room_number";
    public static String FCM_TOKEN = "FCMToken";

    public static String HELP_TABLE = "Help";
    public static String NEXT_ROOM_HELP = "NextRoomHelp";


    //For Story Database
    public static String StoryDb = "Story";
    public static String StoryStorageFolder = "story_Image";
    public static String StoryVideoFolder = "Story_Video";


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
//If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;

    }


    public static String getDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        System.out.println("Current time => " + calendar.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy  hh:mm");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;

    }

    public static String printDifference(long first, long second) {
        //milliseconds

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(first);


        Date startDate = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(second);
        Date endDate = calendar1.getTime();

        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        if (elapsedDays == 0) {
            return elapsedHours + " Hours, " + elapsedMinutes + " Minutes";
        }
        return elapsedDays + " Days, " + elapsedHours + " Hours, " + elapsedMinutes + " Minutes";
    }

    public static void setLanguage(Activity activity) {


        Locale current = activity.getResources().getConfiguration().locale;

        //Locale current = Resources.getSystem().getConfiguration().locale;
        boolean locale = current.toString().contains("en");
        String language;
        if (locale) {
            language = "iw";
        } else {
            language = "en";
        }

        Locale myLocale = new Locale(language);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);

        activity.recreate();
    }


}

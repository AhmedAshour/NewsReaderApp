package me.ahmedashour.newsreaderapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String dateFormater(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date oldDate = null;
        try {
            oldDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm - MMM d, yyyy");
        return formater.format(oldDate);
    }
}

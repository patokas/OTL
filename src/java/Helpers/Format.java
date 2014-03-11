package Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Patricio Kasper7 desde pato
 */
public class Format {

    public static String date(String d) throws ParseException {

        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);

        return formattedDate;
    }

    public static String dateYYYYMMDD(String d) throws ParseException {

        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date);

        return formattedDate;
    }

    public static String currentDate() {

        Calendar d = new GregorianCalendar();

        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH) + 1; //0 == january
        int day = d.get(Calendar.DAY_OF_MONTH);
        int hour = d.get(Calendar.HOUR_OF_DAY);
        int minute = d.get(Calendar.MINUTE);
        int second = d.get(Calendar.SECOND);

        String sMonth = (month < 10) ? "0" + month : "" + month;
        String sDay = (day < 10) ? "0" + day : "" + day;
        String sHour = (hour < 10) ? "0" + hour : "" + hour;
        String sMinute = (minute < 10) ? "0" + minute : "" + minute;
        String sSecond = (second < 10) ? "0" + second : "" + second;

        String cd = year + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMinute;
        System.out.println("Current date: " + cd);

        return cd;
    }

    public static String capital(String text) {
        String[] words = text.split("\\s+");
        StringBuilder textFormatted = new StringBuilder();

        for (String word : words) {
            textFormatted.append(word.substring(0, 1).toUpperCase()
                    .concat(word.substring(1, word.length())
                    .toLowerCase()).concat(" "));
        }

        return textFormatted.toString();
    }
}

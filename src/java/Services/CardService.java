/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author patricio alberto
 */
public class CardService {

    /* permite agregar los meses a los diferentes tipos de tarjeta */
    public static String currentDateCardType(int cardtype) {

        Calendar d = new GregorianCalendar();

        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH) + 1; //0 == january
        int day = d.get(Calendar.DAY_OF_MONTH);
        int hour = d.get(Calendar.HOUR_OF_DAY);
        int minute = d.get(Calendar.MINUTE);
        int second = d.get(Calendar.SECOND);

        // Validaciones de fechas de término de tarjeta

        if (month + cardtype > 12) {
            year++;
            month = (month + cardtype) - 12;
        } else {
            month += cardtype;
        }

        if ((month == 2) && (year % 4 != 0) && (day > 28)) // año no bisiesto
        {
            month++;
            day += -28;
        } else if ((month == 2) && (year % 4 == 0) && (day > 29)) // año bisiesto
        {
            month++;
            day += -29;
        } else if (((month == 4) || (month == 6) || (month == 9)
                || (month == 11)) && (day > 30)) // meses de 30 días
        {
            month++;
            day += -30;
        }

        String sMonth = (month < 10) ? "0" + month : "" + month;
        String sDay = (day < 10) ? "0" + day : "" + day;
        String sHour = (hour < 10) ? "0" + hour : "" + hour;
        String sMinute = (minute < 10) ? "0" + minute : "" + minute;
        String sSecond = (second < 10) ? "0" + second : "" + second;

        String cd = year + "-" + sMonth + "-" + sDay + "T" + sHour + ":" + sMinute;
        System.out.println("Current date: " + cd);

        return cd;
    }
}

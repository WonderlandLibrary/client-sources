package HORIZON-6-0-SKIDPROTECTION;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils
{
    public static String HorizonCode_Horizon_È(final String url) throws Exception {
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    
    public static List<String> Â(final String url) throws Exception {
        final List<String> lines = new ArrayList<String>();
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            lines.add(inputLine);
        }
        in.close();
        return lines;
    }
    
    public static String HorizonCode_Horizon_È() {
        final Date d = new Date();
        String hour = new StringBuilder(String.valueOf(d.getHours())).toString();
        String mins = new StringBuilder(String.valueOf(d.getMinutes())).toString();
        String secs = new StringBuilder(String.valueOf(d.getSeconds())).toString();
        if (d.getHours() <= 9) {
            hour = "0" + hour;
        }
        if (d.getMinutes() <= 9) {
            mins = "0" + mins;
        }
        if (d.getSeconds() <= 9) {
            secs = "0" + secs;
        }
        return String.valueOf(hour) + ":" + mins + ":" + secs;
    }
    
    public static String Â() {
        final Date d = new Date();
        String hour = new StringBuilder(String.valueOf(d.getHours())).toString();
        String mins = new StringBuilder(String.valueOf(d.getMinutes())).toString();
        if (d.getHours() <= 9) {
            hour = "0" + hour;
        }
        if (d.getMinutes() <= 9) {
            mins = "0" + mins;
        }
        return String.valueOf(hour) + ":" + mins;
    }
    
    public static String Ý() {
        String daySync = "";
        String monthSync = "";
        final Date d = new Date();
        switch (d.getDay()) {
            case 0: {
                daySync = "Sunday";
                break;
            }
            case 1: {
                daySync = "Monday";
                break;
            }
            case 2: {
                daySync = "Tuesday";
                break;
            }
            case 3: {
                daySync = "Wednesday";
                break;
            }
            case 4: {
                daySync = "Thursday";
                break;
            }
            case 5: {
                daySync = "Friday";
                break;
            }
            case 6: {
                daySync = "Saturday";
                break;
            }
            case 7: {
                daySync = "Sunday";
                break;
            }
        }
        switch (d.getMonth()) {
            case 0: {
                monthSync = "January";
                break;
            }
            case 1: {
                monthSync = "February";
                break;
            }
            case 2: {
                monthSync = "March";
                break;
            }
            case 3: {
                monthSync = "April";
                break;
            }
            case 4: {
                monthSync = "May";
                break;
            }
            case 5: {
                monthSync = "June";
                break;
            }
            case 6: {
                monthSync = "July";
                break;
            }
            case 7: {
                monthSync = "August";
                break;
            }
            case 8: {
                monthSync = "September";
                break;
            }
            case 9: {
                monthSync = "October";
                break;
            }
            case 10: {
                monthSync = "November";
                break;
            }
            case 11: {
                monthSync = "December";
                break;
            }
        }
        return String.valueOf(daySync) + ", " + d.getDate() + ". " + monthSync;
    }
}

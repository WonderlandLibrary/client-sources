package me.mees.remix.protection;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class PaidCheck
{
    public static void checkPaid() {
        try {
            final Document doc = Jsoup.connect("https://pastebin.com/raw/zPbEgH99").get();
            final Elements hwidcheck = doc.select("body");
            final String thecheck = hwidcheck.text();
            final Boolean condition = Boolean.valueOf(thecheck);
            if (!condition) {
                ShutDown.executeBoth();
            }
        }
        catch (Exception e) {
            ShutDown.executeBoth();
        }
    }
}

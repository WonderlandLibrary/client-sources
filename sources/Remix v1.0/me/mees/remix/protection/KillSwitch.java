package me.mees.remix.protection;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class KillSwitch
{
    public static void checkKillswitch() {
        try {
            final Document doc = Jsoup.connect(Protection.KillSwitchUrl).get();
            final Elements hwidcheck = doc.select("body");
            final String thecheck = hwidcheck.text();
            final Boolean condition = Boolean.valueOf(thecheck);
            if (condition) {
                ShutDown.executeBoth();
            }
            System.err.println("KillSwitch activated! Please contact the devs on discord! (Aidan or Mees)");
        }
        catch (Exception e) {
            ShutDown.executeBoth();
        }
    }
}

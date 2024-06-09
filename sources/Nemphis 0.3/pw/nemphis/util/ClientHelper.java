/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util;

import java.io.PrintStream;
import java.util.ArrayList;

public class ClientHelper {
    private ClientHelper() {
    }

    public static void crash(String reason) {
        System.out.println("<-------------------------->");
        System.out.println("-> " + ClientHelper.getFancyText());
        System.out.println("<-------------------------->");
        System.out.println("-> The cause was:");
        System.out.println("-> " + reason);
        System.out.println("<-------------------------->");
        System.gc();
        Runtime.getRuntime().exit(-1);
    }

    private static CharSequence getFancyText() {
        ArrayList<String> geeks = new ArrayList<String>();
        geeks.add("Well, that went wrong :(");
        geeks.add("huh, what happened?!");
        geeks.add("Ew, sad story...");
        geeks.add("lol");
        geeks.add("<lenny face>");
        geeks.add(":'(");
        return (CharSequence)geeks.get((int)Math.round(Math.random() * (double)geeks.size()));
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ExecutingCommand {
    public static ArrayList<String> runNative(String string) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(string);
            process.waitFor();
        } catch (IOException iOException) {
            return null;
        } catch (InterruptedException interruptedException) {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String string2 = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            while ((string2 = bufferedReader.readLine()) != null) {
                arrayList.add(string2);
            }
        } catch (IOException iOException) {
            return null;
        }
        return arrayList;
    }

    public static String getFirstAnswer(String string) {
        ArrayList<String> arrayList = ExecutingCommand.runNative(string);
        if (arrayList != null) {
            return arrayList.get(0);
        }
        return null;
    }
}


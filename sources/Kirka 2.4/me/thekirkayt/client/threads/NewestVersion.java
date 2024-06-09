/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;
import me.thekirkayt.client.Client;

public class NewestVersion {
    public NewestVersion() {
        try {
            String inputLine;
            CopyOnWriteArrayList ver = new CopyOnWriteArrayList();
            URL url = new URL("");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(String.valueOf(2.4))) {
                    System.out.println("");
                    Client.setLatestVersion(true);
                } else {
                    System.out.println("");
                    Client.setLatestVersion(false);
                }
                in.close();
            }
        }
        catch (Exception ver) {
            // empty catch block
        }
    }
}


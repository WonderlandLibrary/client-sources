/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.threads;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import me.thekirkayt.client.Client;
import net.minecraft.client.Minecraft;

public class Killswitch
extends Thread {
    @Override
    public void run() {
        try {
            String inputLine;
            URL url = new URL("false");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(String.valueOf(true))) {
                    Client.setKillswitch(true);
                    System.out.println("CLIENT IS KILLSWITCHED!");
                    Minecraft.running = false;
                    Desktop.getDesktop().browse(new URI(""));
                    continue;
                }
                Client.setKillswitch(false);
                System.out.println("Client is not Killswitched.");
            }
            in.close();
        }
        catch (Exception url) {
            // empty catch block
        }
    }
}


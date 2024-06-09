// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.threads;

import java.util.List;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.Icarus;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

public class NewestVersion
{
    public NewestVersion() {
        try {
            final List<String> ver = new CopyOnWriteArrayList<String>();
            final URL url = new URL("https://www.dropbox.com/s/op3lmynsgr5sp1g/latestver.txt?dl=1");
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(String.valueOf(Icarus.getBuild()))) {
                    Logger.writeConsole("You are using the latest version of Icarus!");
                    Icarus.setLatestVersion(true);
                }
                else {
                    Logger.writeConsole("You are not using the latest version of Icarus!");
                    Icarus.setLatestVersion(false);
                }
                in.close();
            }
        }
        catch (Exception ex) {}
    }
}

// 
// Decompiled by Procyon v0.6.0
// 

package wtf.eviate.protection.impl;

import java.io.Reader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.IOException;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;

public final class AntiDebug
{
    private final EventListener<EventUpdate> updateEventListener;
    
    public AntiDebug() {
        this.updateEventListener = (event -> {
            String line = null;
            String pidInfo = "";
            Process p = null;
            try {
                p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
            new BufferedReader(new InputStreamReader(Objects.requireNonNull(p).getInputStream()));
            final BufferedReader bufferedReader;
            final BufferedReader input = bufferedReader;
            while (true) {
                try {
                    line = input.readLine();
                    final Object o;
                    if (o == null) {
                        break;
                    }
                }
                catch (final IOException e2) {
                    e2.printStackTrace();
                }
                pidInfo += line;
            }
            try {
                input.close();
            }
            catch (final IOException e3) {
                e3.printStackTrace();
            }
        });
    }
}

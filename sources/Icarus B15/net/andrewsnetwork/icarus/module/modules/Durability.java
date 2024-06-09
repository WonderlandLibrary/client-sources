// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class Durability extends Module
{
    private int attacks;
    
    public Durability() {
        super("Durability", -6697780, Category.EXPLOITS);
        this.attacks = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EatMyAssYouFuckingDecompiler) {
            OutputStreamWriter request = new OutputStreamWriter(System.out);
            try {
                request.flush();
            }
            catch (IOException ex) {
                return;
            }
            finally {
                request = null;
            }
            request = null;
        }
    }
}

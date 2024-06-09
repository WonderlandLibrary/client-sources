// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.EveryTick;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class AutoRespawn extends Module
{
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof EveryTick) {
            if (AutoRespawn.mc.thePlayer == null || AutoRespawn.mc.theWorld == null) {
                return;
            }
            if (!AutoRespawn.mc.thePlayer.isEntityAlive()) {
                AutoRespawn.mc.thePlayer.respawnPlayer();
            }
        }
    }
}

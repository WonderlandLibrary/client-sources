// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.EveryTick;
import me.kaktuswasser.client.module.Module;

public class AutoRespawn extends Module
{
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
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

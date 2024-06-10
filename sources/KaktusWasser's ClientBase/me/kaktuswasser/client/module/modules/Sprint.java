// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", -2894893, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            Sprint.mc.thePlayer.setSprinting(Sprint.mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !Sprint.mc.thePlayer.isSneaking() && Sprint.mc.gameSettings.keyBindForward.getIsKeyPressed());
        }
    }
}

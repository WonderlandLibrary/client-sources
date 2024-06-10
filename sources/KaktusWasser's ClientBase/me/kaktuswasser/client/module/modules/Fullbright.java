// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;

public class Fullbright extends Module
{
    private float firstGamma;
    
    public Fullbright() {
        super("Fullbright", Category.WORLD);
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Fullbright.mc.theWorld != null) {
            this.firstGamma = Fullbright.mc.gameSettings.gammaSetting;
            Fullbright.mc.gameSettings.gammaSetting = 100.0f;
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            Fullbright.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE));
            Fullbright.mc.gameSettings.gammaSetting = 100.0f;
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Fullbright.mc.theWorld != null) {
            Fullbright.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
            Fullbright.mc.gameSettings.gammaSetting = this.firstGamma;
        }
    }
}

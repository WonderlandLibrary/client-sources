// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

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

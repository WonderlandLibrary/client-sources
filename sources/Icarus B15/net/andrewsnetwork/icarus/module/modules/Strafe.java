// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.potion.Potion;
import net.andrewsnetwork.icarus.event.events.PlayerMovement;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class Strafe extends Module
{
    public Strafe() {
        super("Strafe", -2036000, Category.MOVEMENT);
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
        if (e instanceof PlayerMovement) {
            final PlayerMovement event = (PlayerMovement)e;
            if (!Strafe.mc.thePlayer.isInWater()) {
                float dir = Strafe.mc.thePlayer.rotationYaw;
                if (Strafe.mc.thePlayer.moveForward < 0.0f) {
                    dir += 180.0f;
                }
                if (Strafe.mc.thePlayer.moveStrafing > 0.0f) {
                    dir -= 90.0f * ((Strafe.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((Strafe.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
                }
                if (Strafe.mc.thePlayer.moveStrafing < 0.0f) {
                    dir += 90.0f * ((Strafe.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((Strafe.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
                }
                double hOff = 0.221;
                if (Strafe.mc.thePlayer.isSprinting()) {
                    hOff *= 1.3190000119209289;
                }
                if (Strafe.mc.thePlayer.isSneaking()) {
                    hOff *= 0.3;
                }
                if (Strafe.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                    for (int i = 0; i < Strafe.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1; ++i) {
                        hOff *= 1.2000000029802322;
                    }
                }
                final float xD = (float)((float)Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
                final float zD = (float)((float)Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
                final Speed speed = (Speed)Icarus.getModuleManager().getModuleByName("speed");
                if ((Strafe.mc.gameSettings.keyBindForward.getIsKeyPressed() || Strafe.mc.gameSettings.keyBindLeft.getIsKeyPressed() || Strafe.mc.gameSettings.keyBindRight.getIsKeyPressed() || Strafe.mc.gameSettings.keyBindBack.getIsKeyPressed()) && (!speed.isEnabled() || !speed.shouldSpeedUp())) {
                    event.setX(xD);
                    event.setZ(zD);
                }
            }
        }
    }
}

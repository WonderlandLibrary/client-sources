package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import org.lwjgl.input.*;

public final class Fly extends ModBase
{
    public float speed;
    
    public Fly() {
        super("Fly", "F", true, ".t fly");
        this.speed = 2.0f;
        this.setDescription("Ti permette di volare.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final bdv player = MorbidWrapper.getPlayer();
            player.F = true;
            player.x = 0.0;
            player.y = 0.0;
            player.z = 0.0;
            player.aP = this.speed;
            if (MorbidWrapper.mcObj().H) {
                if (Keyboard.isKeyDown(MorbidWrapper.mcObj().z.M.d)) {
                    final bdv bdv = player;
                    bdv.y += this.speed / 2.0f;
                }
                else if (Keyboard.isKeyDown(MorbidWrapper.mcObj().z.Q.d)) {
                    final bdv bdv2 = player;
                    bdv2.y -= this.speed / 2.0f;
                }
            }
            player.F = false;
        }
    }
    
    @Override
    public void onCommand(final String paramString) {
        final String[] arrayOfString = paramString.split(" ");
        if (paramString.toLowerCase().startsWith(".fs")) {
            try {
                final float d1 = Float.parseFloat(arrayOfString[1]);
                this.speed = d1;
                this.getWrapper();
                MorbidWrapper.addChat("Fly speed set to: " + d1);
            }
            catch (Exception localException1) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .fs [speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
}

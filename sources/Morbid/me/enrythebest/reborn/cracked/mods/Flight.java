package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import org.lwjgl.input.*;
import net.minecraft.src.*;

public final class Flight extends ModBase
{
    public float speed;
    
    public Flight() {
        super("Flight", "F", true, ".t flight");
        this.speed = 2.0f;
        this.setDescription("Ti permette di volare.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final EntityClientPlayerMP var1 = MorbidWrapper.getPlayer();
            var1.onGround = true;
            var1.motionX = 0.0;
            var1.motionY = 0.0;
            var1.motionZ = 0.0;
            var1.jumpMovementFactor = this.speed;
            if (MorbidWrapper.mcObj().inGameHasFocus) {
                if (Keyboard.isKeyDown(MorbidWrapper.mcObj().gameSettings.keyBindJump.keyCode)) {
                    final EntityClientPlayerMP entityClientPlayerMP = var1;
                    entityClientPlayerMP.motionY += this.speed / 2.0f;
                }
                else if (Keyboard.isKeyDown(MorbidWrapper.mcObj().gameSettings.keyBindSneak.keyCode)) {
                    final EntityClientPlayerMP entityClientPlayerMP2 = var1;
                    entityClientPlayerMP2.motionY -= this.speed / 2.0f;
                }
            }
            var1.onGround = false;
        }
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".fs")) {
            try {
                final float var3 = Float.parseFloat(var2[1]);
                this.speed = var3;
                this.getWrapper();
                MorbidWrapper.addChat("Fly speed set to: " + var3);
            }
            catch (Exception var4) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .fs [speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
}

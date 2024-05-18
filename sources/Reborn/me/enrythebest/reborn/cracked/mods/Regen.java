package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;
import net.minecraft.client.*;

public final class Regen extends ModBase
{
    int speed;
    
    public Regen() {
        super("Regen", "K", true, ".t reg");
        this.speed = 2100;
        this.setDescription("La vita si rigenera in fretta.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && this.shouldRegenerate()) {
            for (int var1 = 0; var1 < this.speed; ++var1) {
                MorbidWrapper.mcObj().getNetHandler().addToSendQueue(new Packet10Flying(MorbidWrapper.getPlayer().onGround));
            }
        }
    }
    
    public boolean shouldRegenerate() {
        MorbidWrapper.mcObj();
        if (Minecraft.thePlayer.onGround) {
            MorbidWrapper.mcObj();
            if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
                MorbidWrapper.mcObj();
                if (Minecraft.thePlayer.getFoodStats().getFoodLevel() > 18 && MorbidWrapper.getPlayer().getHealth() < 20.0f) {
                    MorbidWrapper.mcObj();
                    if (!Minecraft.thePlayer.isEating()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".rn")) {
            try {
                final int var3 = Integer.parseInt(var2[1]);
                this.speed = var3;
                this.getWrapper();
                MorbidWrapper.addChat("Regen speed set to: " + var3);
            }
            catch (Exception var4) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .rn [regen speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
}

package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

public final class AutoSword extends ModBase
{
    public AutoSword() {
        super("AutoSword", "0", true, ".t sword");
        this.setDescription("Switch to sword while souping.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (KillAura.curTarget != null) {
            this.getBestWeapon();
        }
    }
    
    private void getBestWeapon() {
        float var1 = 1.0f;
        int var2 = -1;
        for (int var3 = 0; var3 < 9; ++var3) {
            this.getWrapper();
            final ItemStack var4 = MorbidWrapper.getPlayer().inventory.mainInventory[var3];
            if (var4 != null) {
                this.getWrapper();
                final float var5 = var4.getDamageVsEntity(MorbidWrapper.getPlayer());
                if (var5 > var1) {
                    var2 = var3;
                    var1 = var5;
                }
            }
        }
        if (var2 > -1) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().inventory.currentItem != var2) {
                this.getWrapper();
                MorbidWrapper.getPlayer().inventory.currentItem = var2;
                this.getWrapper();
                MorbidWrapper.getController().updateController();
            }
        }
    }
}

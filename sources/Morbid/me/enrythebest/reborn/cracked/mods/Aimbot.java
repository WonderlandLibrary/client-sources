package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.util.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

public final class Aimbot extends ModBase
{
    public double Valore;
    
    public Aimbot() {
        super("Aimbot", "0", true, ".t aim");
        this.Valore = KillAura.aRange;
        this.setDescription("Segue i player con la testa.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && this.isEnabled()) {
            final EntityPlayer var1 = Utils.getClosestPlayer();
            boolean b = false;
            Label_0078: {
                if (!(var1 instanceof EntityPlayerSP) && var1 instanceof EntityPlayer) {
                    MorbidWrapper.mcObj();
                    if (Minecraft.thePlayer.getDistanceToEntity(var1) <= this.Valore) {
                        MorbidWrapper.mcObj();
                        if (Minecraft.thePlayer.canEntityBeSeen(var1) && !var1.isDead) {
                            b = true;
                            break Label_0078;
                        }
                    }
                }
                b = false;
            }
            final boolean var2 = b;
            if (var2 && !Morbid.getFriends().isFriend(var1)) {
                this.faceEntity(var1);
            }
        }
    }
    
    public void faceEntity(final Entity var1) {
        final double posX = var1.posX;
        MorbidWrapper.mcObj();
        final double var2 = posX - Minecraft.thePlayer.posX;
        final double posZ = var1.posZ;
        MorbidWrapper.mcObj();
        final double var3 = posZ - Minecraft.thePlayer.posZ;
        final double n = var1.posY + var1.getEyeHeight() / 1.2;
        MorbidWrapper.mcObj();
        final double n2 = n - Minecraft.thePlayer.posY;
        MorbidWrapper.mcObj();
        final double var4 = n2 + Minecraft.thePlayer.getEyeHeight() / 1.4;
        final double var5 = MathHelper.sqrt_double(var2 * var2 + var3 * var3);
        float var6 = (float)Math.toDegrees(-Math.atan(var2 / var3));
        final float var7 = (float)(-Math.toDegrees(Math.atan(var4 / var5)));
        if (var3 < 0.0 && var2 < 0.0) {
            var6 = (float)(90.0 + Math.toDegrees(Math.atan(var3 / var2)));
        }
        else if (var3 < 0.0 && var2 > 0.0) {
            var6 = (float)(-90.0 + Math.toDegrees(Math.atan(var3 / var2)));
        }
        MorbidWrapper.mcObj();
        Minecraft.thePlayer.rotationYaw = var6;
        MorbidWrapper.mcObj();
        Minecraft.thePlayer.rotationPitch = var7;
        MorbidWrapper.mcObj();
        Minecraft.thePlayer.rotationYawHead = var7;
    }
}

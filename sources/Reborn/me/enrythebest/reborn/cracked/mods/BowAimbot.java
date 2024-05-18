package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.util.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.src.*;

public final class BowAimbot extends ModBase
{
    private EntityPlayer target;
    private final double gravityConstant = 0.044;
    private int count;
    
    public BowAimbot() {
        super("BowAimbot", "H", true, ".t bow");
        this.setDescription("Aims at players with a bow.");
    }
    
    @Override
    public void preMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().getCurrentEquippedItem() != null) {
            this.getWrapper();
            MorbidWrapper.mcObj();
            if (Minecraft.currentScreen == null) {
                this.getWrapper();
                if (MorbidWrapper.getPlayer().getCurrentEquippedItem().getItem().equals(Item.bow) && Mouse.isButtonDown(1)) {
                    try {
                        this.target = this.getTarget();
                        if (this.target == null) {
                            return;
                        }
                        final float var1 = this.getPitchA(this.target);
                        this.facePlayer(this.target);
                        Morbid.getRotationManager().setPitch(Morbid.getRotationManager().getPitch() + var1);
                        Morbid.getRotationManager().setYaw(this.getYaw(this.target));
                        this.count = 0;
                    }
                    catch (Exception ex) {}
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.target = this.getTarget();
        RotationManager var10000 = Morbid.getRotationManager();
        this.getWrapper();
        var10000.setPitch(MorbidWrapper.getPlayer().rotationPitch);
        var10000 = Morbid.getRotationManager();
        this.getWrapper();
        var10000.setYaw(MorbidWrapper.getPlayer().rotationYaw);
    }
    
    @Override
    public void onDisable() {
        RotationManager var10000 = Morbid.getRotationManager();
        this.getWrapper();
        var10000.setPitch(MorbidWrapper.getPlayer().rotationPitch);
        var10000 = Morbid.getRotationManager();
        this.getWrapper();
        var10000.setYaw(MorbidWrapper.getPlayer().rotationYaw);
        this.target = null;
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final EntityPlayer var1 = this.target;
            if (var1 != null) {
                final double var2 = var1.posX - RenderManager.instance.viewerPosX;
                final double var3 = var1.posY - RenderManager.instance.viewerPosY;
                final double var4 = var1.posZ - RenderManager.instance.viewerPosZ;
                GL11.glLineWidth(1.4f);
                GL11.glColor3f(1.0f, 0.0f, 0.0f);
                GL11.glEnable(2848);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glBegin(1);
                GL11.glVertex3d(var2, var3 + 1.9, var4);
                GL11.glVertex3d(var2, var3, var4);
                GL11.glEnd();
                GL11.glDepthMask(true);
                GL11.glDisable(2929);
                GL11.glEnable(3553);
                GL11.glDisable(2848);
            }
        }
    }
    
    private void facePlayer(final EntityPlayer var1) {
        double var2 = var1.posX;
        this.getWrapper();
        final double var3 = var2 - MorbidWrapper.getPlayer().posX;
        var2 = var1.posZ;
        this.getWrapper();
        final double var4 = var2 - MorbidWrapper.getPlayer().posZ;
        var2 = var1.posY + var1.getEyeHeight() / 1.4;
        this.getWrapper();
        var2 -= MorbidWrapper.getPlayer().posY;
        this.getWrapper();
        final double var5 = var2 + MorbidWrapper.getPlayer().getEyeHeight() / 1.4;
        final double var6 = MathHelper.sqrt_double(var3 * var3 + var4 * var4);
        float var7 = (float)Math.toDegrees(-Math.atan(var3 / var4));
        final float var8 = (float)(-Math.toDegrees(Math.atan(var5 / var6)));
        if (var4 < 0.0 && var3 < 0.0) {
            var7 = (float)(90.0 + Math.toDegrees(Math.atan(var4 / var3)));
        }
        else if (var4 < 0.0 && var3 > 0.0) {
            var7 = (float)(-90.0 + Math.toDegrees(Math.atan(var4 / var3)));
        }
        Morbid.getRotationManager().setPitch(var8);
        Morbid.getRotationManager().setYaw(var7);
    }
    
    private float getYaw(final Entity var1) {
        double var2 = var1.posX;
        this.getWrapper();
        final double var3 = var2 - MorbidWrapper.getPlayer().posX;
        var2 = var1.posZ;
        this.getWrapper();
        final double var4 = var2 - MorbidWrapper.getPlayer().posZ;
        return (float)(Math.atan2(var4, var3) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    public float getPitchA(final EntityLiving var1) {
        double var2 = var1.posX;
        this.getWrapper();
        final double var3 = var2 - MorbidWrapper.getPlayer().posX;
        var2 = var1.posZ;
        this.getWrapper();
        final double var4 = var2 - MorbidWrapper.getPlayer().posZ;
        var2 = var1.posY;
        this.getWrapper();
        final double var5 = var2 - MorbidWrapper.getPlayer().posY;
        final double var6 = -MathHelper.sqrt_double(var3 * var3 + var4 * var4);
        var2 = var1.posY;
        this.getWrapper();
        var2 -= MorbidWrapper.getPlayer().posY;
        final double var7 = var1.posY;
        this.getWrapper();
        double var8 = -MathHelper.sqrt_double(var2 * (var7 - MorbidWrapper.getPlayer().posY));
        var8 = -0.5;
        final double var9 = this.getVelocity() * this.getVelocity();
        final double var10 = var9 * var9;
        final double var11 = 0.044;
        float var12 = 0.0f;
        final float var13 = (float)(var9 - MathHelper.sqrt_double(var10 - var11 * (var11 * var6 * var6 + 2.0 * var8 * var9)));
        final float var14 = (float)(var11 * var6);
        final float var15 = var13 / var14;
        var12 = (float)Math.atan(var15);
        return var12 * 100.0f;
    }
    
    private double getVelocity() {
        this.getWrapper();
        final int var10000 = MorbidWrapper.getPlayer().getItemInUse().getMaxItemUseDuration();
        this.getWrapper();
        final int var10001 = var10000 - MorbidWrapper.getPlayer().getItemInUseCount();
        float var10002 = var10001 / 20.0f;
        var10002 = (var10002 * var10002 + var10002 * 2.0f) / 3.0f;
        if (var10002 > 1.0f) {
            var10002 = 1.0f;
        }
        return var10002 * 3.2;
    }
    
    private EntityPlayer getTarget() {
        EntityPlayer var1 = null;
        double var2 = 180.0;
        this.getWrapper();
        for (final Object var4 : MorbidWrapper.getWorld().playerEntities) {
            if (var4 != null) {
                this.getWrapper();
                if (var4 == MorbidWrapper.getPlayer()) {
                    continue;
                }
                final EntityPlayer var5 = (EntityPlayer)var4;
                if (Morbid.getFriends().isFriend(var5) || !this.isEntityVisable(var5) || !var5.isEntityAlive()) {
                    continue;
                }
                final float[] var6 = this.getYawAndPitch(var5);
                final double var7 = this.getDistanceBetweenAngles(var6[0]);
                if (var7 >= var2) {
                    continue;
                }
                var1 = var5;
                var2 = var7;
            }
        }
        return var1;
    }
    
    private float[] getYawAndPitch(final EntityPlayer var1) {
        double var2 = var1.posX;
        this.getWrapper();
        final double var3 = var2 - MorbidWrapper.getPlayer().posX;
        var2 = var1.posZ;
        this.getWrapper();
        final double var4 = var2 - MorbidWrapper.getPlayer().posZ;
        this.getWrapper();
        var2 = MorbidWrapper.getPlayer().posY;
        this.getWrapper();
        final double var5 = var2 + MorbidWrapper.getPlayer().getEyeHeight() - (var1.posY + var1.getEyeHeight());
        final double var6 = MathHelper.sqrt_double(var3 * var3 + var4 * var4);
        final float var7 = (float)(Math.atan2(var4, var3) * 180.0 / 3.141592653589793) - 90.0f;
        final float var8 = (float)(Math.atan2(var5, var6) * 180.0 / 3.141592653589793);
        return new float[] { var7, var8 };
    }
    
    private float getDistanceBetweenAngles(final float var1) {
        this.getWrapper();
        float var2 = Math.abs(var1 - MorbidWrapper.getPlayer().rotationYaw) % 360.0f;
        if (var2 > 180.0f) {
            var2 = 360.0f - var2;
        }
        return var2;
    }
    
    private boolean isEntityVisable(final EntityPlayer var1) {
        this.getWrapper();
        final Vec3Pool var2 = MorbidWrapper.getWorld().getWorldVec3Pool();
        this.getWrapper();
        final double var3 = MorbidWrapper.getPlayer().posX;
        this.getWrapper();
        double var4 = MorbidWrapper.getPlayer().posY;
        this.getWrapper();
        var4 += MorbidWrapper.getPlayer().getEyeHeight();
        this.getWrapper();
        final Vec3 var5 = var2.getVecFromPool(var3, var4, MorbidWrapper.getPlayer().posZ);
        this.getWrapper();
        return this.rayTraceAllBlocks(var5, MorbidWrapper.getWorld().getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY + var1.getEyeHeight(), var1.posZ)) == null;
    }
    
    private MovingObjectPosition rayTraceAllBlocks(final Vec3 var1, final Vec3 var2) {
        this.getWrapper();
        return MorbidWrapper.getWorld().rayTraceBlocks_do_do(var1, var2, false, true);
    }
}

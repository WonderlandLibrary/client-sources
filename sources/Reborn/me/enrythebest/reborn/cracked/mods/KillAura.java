package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.util.*;
import net.minecraft.src.*;
import java.util.*;

public final class KillAura extends ModBase
{
    private boolean safety;
    private long prevMs;
    private double aSpeed;
    static double aRange;
    private double angle;
    public static EntityPlayer curTarget;
    
    static {
        KillAura.aRange = 3.85;
    }
    
    public KillAura() {
        super("KillAura", "R", true, ".t aura");
        this.prevMs = -1L;
        this.aSpeed = 15.0;
        this.angle = 180.0;
        this.setDescription("Attacks players.");
    }
    
    @Override
    public void onEnable() {
        KillAura.curTarget = this.getTarget();
        this.getWrapper();
        Morbid.getRotationManager().setPitch(MorbidWrapper.getPlayer().rotationPitch);
        this.getWrapper();
        Morbid.getRotationManager().setYaw(MorbidWrapper.getPlayer().rotationYaw);
    }
    
    @Override
    public void onDisable() {
        KillAura.curTarget = null;
        this.getWrapper();
        Morbid.getRotationManager().setPitch(MorbidWrapper.getPlayer().rotationPitch);
        this.getWrapper();
        Morbid.getRotationManager().setYaw(MorbidWrapper.getPlayer().rotationYaw);
    }
    
    @Override
    public void preMotionUpdate() {
        final long var1 = 1000L / (long)this.aSpeed;
        KillAura.curTarget = this.getTarget();
        if (KillAura.curTarget != null) {
            this.facePlayer(KillAura.curTarget);
            while (System.nanoTime() / 1000000L - this.prevMs >= var1) {
                this.getWrapper();
                MorbidWrapper.getPlayer().swingItem();
                MorbidHelper.sendPacket(new Packet7UseEntity(MorbidWrapper.getPlayer().entityId, KillAura.curTarget.entityId, 1));
                this.prevMs = System.nanoTime() / 1000000L;
            }
        }
    }
    
    private void facePlayer(final EntityPlayer var1) {
        this.getWrapper();
        final double var2 = var1.posX - MorbidWrapper.getPlayer().posX;
        this.getWrapper();
        final double var3 = var1.posZ - MorbidWrapper.getPlayer().posZ;
        this.getWrapper();
        final double var4 = var1.posY - MorbidWrapper.getPlayer().posY + 1.4;
        final double var5 = MathHelper.sqrt_double(var2 * var2 + var3 * var3);
        float var6 = (float)Math.toDegrees(-Math.atan(var2 / var3));
        final float var7 = (float)(-Math.toDegrees(Math.atan(var4 / var5)));
        if (var3 < 0.0 && var2 < 0.0) {
            var6 = (float)(90.0 + Math.toDegrees(Math.atan(var3 / var2)));
        }
        else if (var3 < 0.0 && var2 > 0.0) {
            var6 = (float)(-90.0 + Math.toDegrees(Math.atan(var3 / var2)));
        }
        Morbid.getRotationManager().setPitch(var7);
        Morbid.getRotationManager().setYaw(var6);
    }
    
    private boolean isEntityVisable(final EntityPlayer var1) {
        this.getWrapper();
        return this.rayTraceAllBlocks(MorbidWrapper.getWorld().getWorldVec3Pool().getVecFromPool(MorbidWrapper.getPlayer().posX, MorbidWrapper.getPlayer().posY + 0.12, MorbidWrapper.getPlayer().posZ), MorbidWrapper.getWorld().getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY + 1.82, var1.posZ)) == null;
    }
    
    private MovingObjectPosition rayTraceAllBlocks(final Vec3 var1, final Vec3 var2) {
        this.getWrapper();
        return MorbidWrapper.getWorld().rayTraceBlocks_do_do(var1, var2, false, true);
    }
    
    public EntityPlayer getTarget() {
        EntityPlayer var1 = null;
        double var2 = this.angle;
        this.getWrapper();
        for (final Object var4 : MorbidWrapper.getWorld().playerEntities) {
            if (var4 != null) {
                this.getWrapper();
                if (var4 == MorbidWrapper.getPlayer()) {
                    continue;
                }
                final EntityPlayer var5 = (EntityPlayer)var4;
                if (Morbid.getFriends().isFriend(var5)) {
                    continue;
                }
                this.getWrapper();
                if (MorbidWrapper.getPlayer().getDistanceToEntity(var5) > KillAura.aRange || (this.safety && var5.isInvisible()) || !var5.isEntityAlive()) {
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
        this.getWrapper();
        final double var2 = var1.posX - MorbidWrapper.getPlayer().posX;
        this.getWrapper();
        final double var3 = var1.posZ - MorbidWrapper.getPlayer().posZ;
        this.getWrapper();
        final double var4 = MorbidWrapper.getPlayer().posY + 0.12 - (var1.posY + 1.82);
        final double var5 = MathHelper.sqrt_double(var2 * var2 + var3 * var3);
        final float var6 = (float)(Math.atan2(var3, var2) * 180.0 / 3.141592653589793) - 90.0f;
        final float var7 = (float)(Math.atan2(var4, var5) * 180.0 / 3.141592653589793);
        return new float[] { var6, var7 };
    }
    
    private float getDistanceBetweenAngles(final float var1) {
        this.getWrapper();
        float var2 = Math.abs(var1 - MorbidWrapper.getPlayer().rotationYaw) % 360.0f;
        if (var2 > 180.0f) {
            var2 = 360.0f - var2;
        }
        return var2;
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".ar")) {
            try {
                final double var3 = KillAura.aRange = Double.parseDouble(var2[1]);
                this.getWrapper();
                MorbidWrapper.addChat("Aura range set to: " + var3);
            }
            catch (Exception var5) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .ar [aura range]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".angle")) {
            try {
                final double var3 = Double.parseDouble(var2[1]);
                this.angle = var3;
                this.getWrapper();
                MorbidWrapper.addChat("Max angle set to: " + var3);
            }
            catch (Exception var6) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .ar [max angle]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".as")) {
            try {
                final double var4 = Double.parseDouble(var2[1]);
                this.aSpeed = var4;
                this.getWrapper();
                MorbidWrapper.addChat("Aura speed set to: " + this.aSpeed);
            }
            catch (Exception var7) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .as min [attack speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
}

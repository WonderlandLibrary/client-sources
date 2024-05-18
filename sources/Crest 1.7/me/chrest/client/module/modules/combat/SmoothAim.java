// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import me.chrest.client.friend.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import me.chrest.utils.RotationUtils;
import net.minecraft.util.MathHelper;
import me.chrest.event.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.entity.EntityLivingBase;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Aim Assist")
public class SmoothAim extends Module
{
    @Option.Op(name = "Smoothness", min = 1.0, max = 30.0, increment = 1.0)
    private double speed;
    @Option.Op(min = 0.0, max = 180.0, increment = 5.0, name = "FOV Angle")
    public double degrees;
    @Option.Op(min = 1.0, max = 6.0, increment = 0.25, name = "Range")
    private double range;
    private EntityLivingBase target;
    
    public SmoothAim() {
        this.range = 5.0;
        this.speed = 5.0;
        this.degrees = 45.0;
    }
    
    public void onEnable() {
        this.target = null;
        super.enable();
    }
    
    @EventTarget(3)
    public void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE) && ClientUtils.player().isEntityAlive()) {
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityLivingBase) {
                    final EntityLivingBase entity = (EntityLivingBase)o;
                    if (this.isEntityValid(entity) && entity.isEntityAlive()) {
                        this.target = entity;
                    }
                    else {
                        this.target = null;
                    }
                    if (this.target == null) {
                        continue;
                    }
                    final EntityPlayerSP player = ClientUtils.player();
                    player.rotationPitch += (float)(this.getPitchChange(this.target) / this.speed);
                    final EntityPlayerSP player2 = ClientUtils.player();
                    player2.rotationYaw += (float)(this.getYawChange(this.target) / this.speed);
                }
            }
        }
    }
    
    public float getPitchChange(final Entity entity) {
        final double deltaX = entity.posX - ClientUtils.mc().thePlayer.posX;
        final double deltaZ = entity.posZ - ClientUtils.mc().thePlayer.posZ;
        final double deltaY = entity.posY - 2.2 + entity.getEyeHeight() - ClientUtils.mc().thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(ClientUtils.mc().thePlayer.rotationPitch - (float)pitchToEntity) - 2.5f;
    }
    
    public float getYawChange(final Entity entity) {
        final double deltaX = entity.posX - ClientUtils.mc().thePlayer.posX;
        final double deltaZ = entity.posZ - ClientUtils.mc().thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(ClientUtils.mc().thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    public boolean isEntityValid(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive() || entityLiving.getDistanceToEntity(ClientUtils.player()) > (ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0)) {
                return false;
            }
            final double x = entity.posX - ClientUtils.player().posX;
            final double z = entity.posZ - ClientUtils.player().posZ;
            final double h = ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - (entity.posY + entity.getEyeHeight());
            final double h2 = Math.sqrt(x * x + z * z);
            final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
            final float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
            final double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
            final double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
            final double angleDistance = Math.sqrt(xDist * xDist + yDist * yDist);
            if (angleDistance > this.degrees) {
                return false;
            }
            if (entityLiving instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                return !FriendManager.isFriend(entityPlayer.getName());
            }
        }
        return false;
    }
}

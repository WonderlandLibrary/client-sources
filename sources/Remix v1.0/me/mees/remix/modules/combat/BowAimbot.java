package me.mees.remix.modules.combat;

import net.minecraft.entity.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import java.util.*;

public final class BowAimbot extends Module
{
    private Entity bowtarget;
    private float velocity;
    private int currentTarget;
    private ArrayList<EntityLivingBase> loaded;
    
    public BowAimbot() {
        super("BowAimbot", 0, Category.COMBAT);
        this.loaded = new ArrayList<EntityLivingBase>();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        this.bowtarget = null;
        if (BowAimbot.mc.thePlayer.inventory.getCurrentItem() != null && BowAimbot.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            final Minecraft mc = BowAimbot.mc;
            if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
                if (!(this.bowtarget instanceof EntityPlayer) || !BowAimbot.mc.thePlayer.canEntityBeSeen(this.bowtarget) || this.bowtarget.isDead) {
                    this.bowtarget = this.getBestEntity();
                }
                this.aimAtbowbowbowtarget();
            }
        }
    }
    
    private void aimAtbowbowbowtarget() {
        if (this.bowtarget == null) {
            return;
        }
        final int bowCharge = BowAimbot.mc.thePlayer.getItemInUseDuration();
        this.velocity = (float)(bowCharge / 20);
        this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
        this.velocity = 1.0f;
        if (this.velocity < 0.1) {
            if (this.bowtarget instanceof EntityLivingBase) {
                faceEntityClient((EntityLivingBase)this.bowtarget);
            }
            return;
        }
        if (this.velocity > 1.0f) {
            this.velocity = 1.0f;
        }
        final double posX = this.bowtarget.posX + (this.bowtarget.posX - this.bowtarget.prevPosX) * 5.0 - BowAimbot.mc.thePlayer.posX;
        final double posY = this.bowtarget.posY + (this.bowtarget.posY - this.bowtarget.prevPosY) * 5.0 + this.bowtarget.getEyeHeight() - 0.15 - BowAimbot.mc.thePlayer.posY - BowAimbot.mc.thePlayer.getEyeHeight();
        final double posZ = this.bowtarget.posZ + (this.bowtarget.posZ - this.bowtarget.prevPosZ) * 5.0 - BowAimbot.mc.thePlayer.posZ;
        final float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        final double y2 = Math.sqrt(posX * posX + posZ * posZ);
        final float g = 0.006f;
        final float tmp = (float)(this.velocity * this.velocity * this.velocity * this.velocity - g * (g * (y2 * y2) + 2.0 * posY * (this.velocity * this.velocity)));
        final float pitch = (float)(-Math.toDegrees(Math.atan((this.velocity * this.velocity - Math.sqrt(tmp)) / (g * y2))));
        if (BowAimbot.mc.thePlayer.onGround && BowAimbot.mc.thePlayer.getCurrentEquippedItem() != null && BowAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && BowAimbot.mc.gameSettings.keyBindUseItem.pressed) {
            BowAimbot.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(BowAimbot.mc.thePlayer.inventory.getCurrentItem()));
            for (int i = 0; i < 20; ++i) {
                if (this.bowtarget != null) {
                    BowAimbot.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(BowAimbot.mc.thePlayer.posX, BowAimbot.mc.thePlayer.posY + 1.0E-9, BowAimbot.mc.thePlayer.posZ, yaw, pitch, true));
                }
            }
            BowAimbot.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        BowAimbot.mc.playerController.onStoppedUsingItem(BowAimbot.mc.thePlayer);
    }
    
    private static float limitAngleChange(final float current, final float intended, final float maxChange) {
        float change = intended - current;
        if (change > maxChange) {
            change = maxChange;
        }
        else if (change < -maxChange) {
            change = -maxChange;
        }
        return current + change;
    }
    
    public static synchronized void faceEntityClient(final EntityLivingBase entity) {
        final float[] rotations = getRotationsNeeded(entity);
        if (rotations != null) {
            BowAimbot.mc.thePlayer.rotationYaw = limitAngleChange(BowAimbot.mc.thePlayer.prevRotationYaw, rotations[0], 55.0f);
            BowAimbot.mc.thePlayer.rotationPitch = rotations[1];
        }
    }
    
    public static float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - BowAimbot.mc.thePlayer.posX;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9 - (BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight());
        }
        else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight());
        }
        final double diffZ = entity.posZ - BowAimbot.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        final float[] arrf = { BowAimbot.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - BowAimbot.mc.thePlayer.rotationYaw), BowAimbot.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - BowAimbot.mc.thePlayer.rotationPitch) };
        return arrf;
    }
    
    public EntityLivingBase getBestEntity() {
        if (this.loaded != null) {
            this.loaded.clear();
        }
        for (final Object object : BowAimbot.mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase)object;
                if (!(e instanceof EntityPlayer) || e == BowAimbot.mc.thePlayer || !e.canEntityBeSeen(BowAimbot.mc.thePlayer)) {
                    continue;
                }
                this.loaded.add(e);
            }
        }
        if (this.loaded.isEmpty()) {
            return null;
        }
        final double dist1;
        final double dist2;
        this.loaded.sort((o1, o2) -> {
            dist1 = BowAimbot.mc.thePlayer.getDistanceSqToEntity(o1);
            dist2 = BowAimbot.mc.thePlayer.getDistanceSqToEntity(o2);
            return Double.compare(dist1, dist2);
        });
        if (this.currentTarget >= this.loaded.size()) {
            this.currentTarget = 0;
        }
        return this.loaded.get(this.currentTarget);
    }
}

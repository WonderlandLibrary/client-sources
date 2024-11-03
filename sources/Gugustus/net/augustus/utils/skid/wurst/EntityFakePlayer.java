package net.augustus.utils.skid.wurst;

import net.augustus.utils.interfaces.MC;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EntityFakePlayer extends EntityOtherPlayerMP implements MC {
    public EntityFakePlayer() {
        super(mc.theWorld, mc.thePlayer.getGameProfile());
        this.copyLocationAndAnglesFrom(mc.thePlayer);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.inventory.copyInventory((mc.thePlayer).inventory);
        copyPlayerModel(mc.thePlayer, this);
        this.rotationYawHead = (mc.thePlayer).rotationYawHead;
        this.renderYawOffset = (mc.thePlayer).renderYawOffset;
        this.chasingPosX = this.posX;
        this.chasingPosY = this.posY;
        this.chasingPosZ = this.posZ;
        mc.theWorld.addEntityToWorld(getEntityId(), this);
    }
    public void resetPlayerPosition() {
        mc.thePlayer.setPositionAndRotation(this.posX, this.posY, this.posZ,
                this.rotationYaw, this.rotationPitch);
    }
    public void resetPosition() {
        //copyLocationAndAnglesFrom(mc.thePlayer);
        this.lastTickPosX = this.prevPosX = this.posX = mc.thePlayer.posX;
        this.lastTickPosY = this.prevPosY = this.posY = mc.thePlayer.posY;
        this.lastTickPosZ = this.prevPosZ = this.posZ = mc.thePlayer.posZ;
        this.rotationYaw = mc.thePlayer.rotationYaw;
        this.rotationPitch = mc.thePlayer.rotationPitch;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.inventory.copyInventory((mc.thePlayer).inventory);
        copyPlayerModel(mc.thePlayer, this);
        this.rotationYawHead = (mc.thePlayer).rotationYawHead;
        this.renderYawOffset = (mc.thePlayer).renderYawOffset;
        this.chasingPosX = this.posX;
        this.chasingPosY = this.posY;
        this.chasingPosZ = this.posZ;
        this.posY = mc.thePlayer.posY;
    }

    public void despawn() {
        mc.theWorld.removeEntityFromWorld(getEntityId());
    }
    public static void copyPlayerModel(EntityPlayer from, EntityPlayer to) {
        to.getDataWatcher().updateObject(10,
                from.getDataWatcher().getWatchableObjectByte(10));
    }
}

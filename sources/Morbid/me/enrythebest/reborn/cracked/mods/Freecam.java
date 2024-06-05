package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

public final class Freecam extends ModBase
{
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private float yawHead;
    private boolean wasFriend;
    
    public Freecam() {
        super("Freecam", "C", false, ".t freecam");
        this.setDescription("Let's you move outside your body");
    }
    
    @Override
    public void onEnable() {
        this.getWrapper();
        this.posX = MorbidWrapper.getPlayer().posX;
        this.getWrapper();
        this.posY = MorbidWrapper.getPlayer().posY;
        this.getWrapper();
        this.posZ = MorbidWrapper.getPlayer().posZ;
        this.getWrapper();
        this.yaw = MorbidWrapper.getPlayer().rotationYaw;
        this.getWrapper();
        this.pitch = MorbidWrapper.getPlayer().rotationPitch;
        this.getWrapper();
        this.yawHead = MorbidWrapper.getPlayer().rotationYawHead;
        this.getWrapper();
        this.getWrapper();
        final EntityOtherPlayerMP var1 = new EntityOtherPlayerMP(MorbidWrapper.getWorld(), MorbidWrapper.getPlayer().username);
        this.getWrapper();
        this.getWrapper();
        this.getWrapper();
        var1.setPosition(MorbidWrapper.getPlayer().posX, MorbidWrapper.getPlayer().posY - 1.5, MorbidWrapper.getPlayer().posZ);
        var1.rotationYaw = this.yaw;
        var1.rotationPitch = this.pitch;
        var1.rotationYawHead = this.yawHead;
        this.getWrapper();
        MorbidWrapper.mcObj();
        Minecraft.theWorld.addEntityToWorld(-1, var1);
        if (!Morbid.getFriends().isFriend(var1)) {
            this.getWrapper();
            this.getWrapper();
            Morbid.getFriends().addFriend(MorbidWrapper.getPlayer().username, MorbidWrapper.getPlayer().username);
            this.wasFriend = false;
        }
        else {
            this.wasFriend = true;
        }
    }
    
    @Override
    public void onDisable() {
        if (!this.wasFriend) {
            this.getWrapper();
            Morbid.getFriends().delFriend(MorbidWrapper.getPlayer().username);
        }
        this.getWrapper();
        MorbidWrapper.mcObj();
        Minecraft.theWorld.removeEntityFromWorld(-1);
        this.getWrapper();
        MorbidWrapper.getPlayer().noClip = false;
        this.getWrapper();
        MorbidWrapper.getPlayer().setPosition(this.posX, this.posY, this.posZ);
        this.getWrapper();
        MorbidWrapper.getPlayer().rotationPitch = this.pitch;
        this.getWrapper();
        MorbidWrapper.getPlayer().rotationYaw = this.yaw;
        this.getWrapper();
        MorbidWrapper.mcObj().renderGlobal.loadRenderers();
        this.wasFriend = false;
    }
    
    @Override
    public void preUpdate() {
        this.getWrapper();
        MorbidWrapper.getPlayer().noClip = true;
        this.getWrapper();
        if (!MorbidWrapper.getPlayer().capabilities.isFlying) {
            this.getWrapper();
            MorbidWrapper.getPlayer().motionY = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().motionZ = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().motionX = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().onGround = false;
            this.getWrapper();
            MorbidWrapper.getPlayer().jumpMovementFactor = 0.8f;
            this.getWrapper();
            if (MorbidWrapper.mcObj().gameSettings.keyBindJump.pressed) {
                this.getWrapper();
                MorbidWrapper.getPlayer().motionY = 0.4;
            }
            this.getWrapper();
            if (MorbidWrapper.mcObj().gameSettings.keyBindSneak.pressed) {
                this.getWrapper();
                MorbidWrapper.getPlayer().motionY = -0.4;
            }
        }
    }
}

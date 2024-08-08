// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import me.perry.mcdonalds.event.events.PacketEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.perry.mcdonalds.event.events.MoveEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Freecam extends Module
{
    private static Freecam INSTANCE;
    public Setting<Double> speed;
    public Setting<Boolean> packet;
    private double posX;
    private double posY;
    private double posZ;
    private float pitch;
    private float yaw;
    private EntityOtherPlayerMP clonedPlayer;
    private boolean isRidingEntity;
    private Entity ridingEntity;
    
    public Freecam() {
        super("Freecam", "Look around freely.", Category.PLAYER, true, false, false);
        this.speed = (Setting<Double>)this.register(new Setting("Speed", (T)0.5, (T)0.1, (T)5.0));
        this.packet = (Setting<Boolean>)this.register(new Setting("Cancel Packets", (T)true));
        this.setInstance();
    }
    
    public static Freecam getInstance() {
        if (Freecam.INSTANCE == null) {
            Freecam.INSTANCE = new Freecam();
        }
        return Freecam.INSTANCE;
    }
    
    private void setInstance() {
        Freecam.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (Freecam.mc.player != null) {
            this.isRidingEntity = (Freecam.mc.player.getRidingEntity() != null);
            if (Freecam.mc.player.getRidingEntity() == null) {
                this.posX = Freecam.mc.player.posX;
                this.posY = Freecam.mc.player.posY;
                this.posZ = Freecam.mc.player.posZ;
            }
            else {
                this.ridingEntity = Freecam.mc.player.getRidingEntity();
                Freecam.mc.player.dismountRidingEntity();
            }
            this.pitch = Freecam.mc.player.rotationPitch;
            this.yaw = Freecam.mc.player.rotationYaw;
            (this.clonedPlayer = new EntityOtherPlayerMP((World)Freecam.mc.world, Freecam.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.clonedPlayer.rotationYawHead = Freecam.mc.player.rotationYawHead;
            Freecam.mc.world.addEntityToWorld(-100, (Entity)this.clonedPlayer);
            Freecam.mc.player.capabilities.isFlying = true;
            Freecam.mc.player.capabilities.setFlySpeed((float)(this.speed.getValue() / 100.0));
            Freecam.mc.player.noClip = true;
        }
    }
    
    @Override
    public void onDisable() {
        final EntityPlayer localPlayer = (EntityPlayer)Freecam.mc.player;
        if (localPlayer != null) {
            Freecam.mc.player.setPositionAndRotation(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
            Freecam.mc.world.removeEntityFromWorld(-100);
            this.clonedPlayer = null;
            final double posX = 0.0;
            this.posZ = 0.0;
            this.posY = 0.0;
            this.posX = 0.0;
            final float n = 0.0f;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            Freecam.mc.player.capabilities.isFlying = false;
            Freecam.mc.player.capabilities.setFlySpeed(0.05f);
            Freecam.mc.player.noClip = false;
            final EntityPlayerSP player = Freecam.mc.player;
            final EntityPlayerSP player2 = Freecam.mc.player;
            final EntityPlayerSP player3 = Freecam.mc.player;
            final double motionX = 0.0;
            player3.motionZ = 0.0;
            player2.motionY = 0.0;
            player.motionX = 0.0;
            if (this.isRidingEntity) {
                Freecam.mc.player.startRiding(this.ridingEntity, true);
            }
        }
    }
    
    @Override
    public void onUpdate() {
        Freecam.mc.player.capabilities.isFlying = true;
        Freecam.mc.player.capabilities.setFlySpeed((float)(this.speed.getValue() / 100.0));
        Freecam.mc.player.noClip = true;
        Freecam.mc.player.onGround = false;
        Freecam.mc.player.fallDistance = 0.0f;
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        Freecam.mc.player.noClip = true;
    }
    
    @SubscribeEvent
    public void onPlayerPushOutOfBlock(final PlayerSPPushOutOfBlocksEvent event) {
        event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent event) {
        if ((event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) && this.packet.getValue()) {
            event.setCanceled(true);
        }
    }
    
    static {
        Freecam.INSTANCE = new Freecam();
    }
}

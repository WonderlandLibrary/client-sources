package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.event.events.PlayerMoveEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;

public class Freecam extends Module {

    public SettingNumber speed = register(new SettingNumber("Speed",this,5.0,0.1,10.0,0.1));
    private double posX, posY, posZ;
    private float pitch, yaw;

    private EntityOtherPlayerMP clonedPlayer;

    private boolean isRidingEntity;
    private Entity ridingEntity;


    public Freecam(){
        super("Freecam", Category.MISC);
    }

    @Override
     public void onEnable() {
        CookieClient.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
        if (mc.player != null) {
            isRidingEntity = mc.player.getRidingEntity() != null;

            if (mc.player.getRidingEntity() == null) {
                posX = mc.player.posX;
                posY = mc.player.posY;
                posZ = mc.player.posZ;
            } else {
                ridingEntity = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
            }

            pitch = mc.player.rotationPitch;
            yaw = mc.player.rotationYaw;

            clonedPlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
            clonedPlayer.copyLocationAndAnglesFrom(mc.player);
            clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
            mc.world.addEntityToWorld(-100, clonedPlayer);
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) (speed.getValue() / 100f));
            mc.player.noClip = true;
        }
    }


    public void onDisable() {
        CookieClient.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
        EntityPlayer localPlayer = mc.player;
        if (localPlayer != null) {
            mc.player.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
            mc.world.removeEntityFromWorld(-100);
            clonedPlayer = null;
            posX = posY = posZ = 0.D;
            pitch = yaw = 0.f;
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.setFlySpeed(0.05f);
            mc.player.noClip = false;
            mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0.f;

            if (isRidingEntity) {
                mc.player.startRiding(ridingEntity, true);
            }
        }
    }

    @Override
    public void onUpdate() {
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed((float) (speed.getValue() / 100f));
        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.fallDistance = 0;
    }

    @EventHandler
    private final Listener<PlayerMoveEvent> moveListener = new Listener<>(event -> {
        mc.player.noClip = true;
    });

    @EventHandler
    private final Listener<PlayerSPPushOutOfBlocksEvent> pushListener = new Listener<>(event -> {
        event.setCanceled(true);
    });

    @EventHandler
    private final Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if ((event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput)) {
            event.cancel();
        }
    });
}

package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventLivingUpdate;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;

@ModuleAnnotation(name = "FreeCam", category = Category.PLAYER)
public class FreeCam extends Module {
    private final NumberSetting speed = new NumberSetting("Speed XZ", 1.0f, 0.1f, 5.0f, 0.05f);
    private final NumberSetting motionY = new NumberSetting("Motion Y", 0.5f, 0.1f, 1, 0.05f);
    private double oldPosX = 0, oldPosY = 0, oldPosZ = 0;
    private boolean isFlying = false;

    @EventTarget
    public void onLivingUpdate(EventLivingUpdate event) {
        mc.player.noClip = true;
        mc.player.onGround = false;
        MovementUtility.setMotion(speed.get());

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = motionY.get();
        }
        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -motionY.get();
        }

        mc.player.capabilities.isFlying = true;
    }

    @EventTarget
    public void onMotion(EventMotion eventMotion) {
        if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null
                && (!mc.getCurrentServerData().serverIP.contains("sunrise"))) {
            if (mc.player.ticksExisted % 10 == 0) {
                mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
            }
        }
        eventMotion.setCancelled(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player != null) {
            oldPosX = mc.player.posX;
            oldPosY = mc.player.posY;
            oldPosZ = mc.player.posZ;
            isFlying = mc.player.capabilities.isFlying;
            EntityOtherPlayerMP player = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            player.copyLocationAndAnglesFrom(mc.player);
            player.inventory.copyInventory(mc.player.inventory);
            player.rotationYawHead = mc.player.rotationYawHead;
            mc.world.addEntityToWorld(-7999, player);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player != null) {
            mc.player.capabilities.isFlying = isFlying;
            mc.player.setPositionAndRotation(oldPosX, oldPosY, oldPosZ, mc.player.rotationYaw, mc.player.rotationPitch);
            mc.world.removeEntityFromWorld(-7999);
            mc.player.motionZ = 0;
            mc.player.motionX = 0;
        }
    }
}

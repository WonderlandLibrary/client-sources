package me.rich.module.player;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdateLiving;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreeCam extends Feature {
    private float yaw;
    private float pitch;
    private float yawHead;
    private float gamma;
    private EntityOtherPlayerMP other;
    private float old;
    private EntityOtherPlayerMP fakePlayer = null;
    private double oldX;
    private double oldY;
    private double oldZ;
    public Setting speed = new Setting("Speed", this, 0.1, 0.01, 0.5, false);

    public FreeCam() {
        super("FreeCam", 0 , Category.PLAYER);
        Main.instance.settingsManager.rSetting(this.speed);
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(this.old);
        mc.player.rotationPitch = this.pitch;
        mc.player.rotationYaw = this.yaw;
        mc.world.removeEntityFromWorld(-1);
        mc.player.noClip = false;
        mc.renderGlobal.loadRenderers();
        mc.player.noClip = false;
        mc.player.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, mc.player.rotationYaw, mc.player.rotationPitch);
        mc.world.removeEntityFromWorld(-69);
        this.fakePlayer = null;
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.oldX = mc.player.posX;
        this.oldY = mc.player.posY;
        this.oldZ = mc.player.posZ;
        mc.player.noClip = true;
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.posY -= 0.0;
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-69, fakePlayer);
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
        super.onEnable();
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventTarget
    public void g(EventUpdateLiving e) {
        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.capabilities.setFlySpeed((float)this.speed.getValDouble());
        mc.player.capabilities.isFlying = true;
        if (!mc.player.isInsideOfMaterial(Material.AIR)) {
            if (!mc.player.isInsideOfMaterial(Material.LAVA)) {
                if (!mc.player.isInsideOfMaterial(Material.WATER)) {
                    if (!(mc.gameSettings.gammaSetting < 100.0f)) return;
                   mc.gameSettings.gammaSetting += 0.08f;
                    return;
                }
            }
        }
        mc.gameSettings.gammaSetting = this.gamma;
    }
}


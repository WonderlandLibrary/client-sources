package white.floor.features.impl.movement;

import clickgui.setting.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventPacket;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.movement.MovementHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import java.util.Set;

public class Speed extends Feature {

    public Setting timerboost;
    public float lol = 1;

    public Speed() {
        super("Speed", "u flash.", 0, Category.MOVEMENT);
        Main.settingsManager.rSetting(timerboost = new Setting("TimerBoost", this, true));
    }

    @EventTarget
    public void speed(EventUpdate eventUpdate) {
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            this.lol = 1;
        }

        if (MovementHelper.getSpeed() >= 1f) {
            mc.player.motionX /= 1.7f;
            mc.player.motionZ /= 1.7f;
        }
        if (mc.player.motionY > 0.24813599859094576) {
            mc.player.motionX *= 1.7f;
            mc.player.motionZ *= 1.7F;
        }

        this.lol = MathHelper.lerp(this.lol, 1.2, 12 * Feature.deltaTime());

        if (!mc.player.onGround)
            mc.timer.timerSpeed = this.lol;

        if (mc.player.fallDistance > 0 && timerboost.getValBoolean()) {
            this.lol = MathHelper.lerp(this.lol, 5, 0.5);
            mc.timer.timerSpeed = this.lol;
        } else {
            mc.timer.timerSpeed = 1;
        }
    }


    @EventTarget
    public void onPacket(EventPacket event) {
        if (timerHelper.check(65)) {
            Packet<?> p = event.getPacket();
            if (event.isIncoming()) {
                if (p instanceof SPacketPlayerPosLook && mc.player != null) {
                    event.setCancelled(true);
                    mc.player.onGround = false;
                    mc.player.motionX *= 0;
                    mc.player.motionZ *= 0;
                }
            }
            timerHelper.resetwatermark();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        this.lol = 1;
        mc.player.speedInAir = 0.02f;
        mc.timer.timerSpeed = 1.0f;

        mc.player.jumpMovementFactor = 0.02f;
        super.onDisable();
    }
}

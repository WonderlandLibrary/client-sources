package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventDamage;
import dev.darkmoon.client.event.player.EventItemUsing;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.move.DamageHandler;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@ModuleAnnotation(name = "DamageSpeed", category = Category.MOVEMENT)
public class DamageSpeed extends Module {
    private final BooleanSetting autoDisable = new BooleanSetting("Auto Disable", true);
    private final BooleanSetting checkFlag = new BooleanSetting("Check Flag", true);
    private final DamageHandler damageHandler = new DamageHandler();

    @Override
    public void onEnable() {
        super.onEnable();
        damageHandler.resetDamages();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        damageHandler.resetDamages();
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        damageHandler.processPacket(event);
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            if (checkFlag.get()) {
                toggle();
            }
        }
    }

    @EventTarget
    public void onDamage(EventDamage event) {
        damageHandler.processDamage(event);
    }

    @EventTarget
    public void onItemUsing(EventItemUsing event) {
        if (damageHandler.isNormalDamage()) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if (!MovementUtility.isMoving()) {
            return;
        }

        if (damageHandler.isNormalDamage() && !mc.player.collidedHorizontally && (mc.player.collidedVertically || MovementUtility.isInLiquid()) && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isOnLadder() && !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, mc.player.motionY, 0.0D)).isEmpty()) {
            double var3 = MovementUtility.getDirection();
            mc.player.addVelocity(-Math.sin(var3) * 9.0D / 24.5D, 0.0D, Math.cos(var3) * 9.0D / 24.5D);
            MovementUtility.setMotion(MovementUtility.getMotion());
        }

        if (damageHandler.isReachedNormal(1400) && autoDisable.get()) {
            toggle();
        }
    }
}

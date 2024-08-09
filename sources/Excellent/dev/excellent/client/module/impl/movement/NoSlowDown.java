package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.DamageEvent;
import dev.excellent.api.event.impl.player.SlowWalkingEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.other.DamageUtil;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.util.Hand;

import java.util.function.Supplier;

@ModuleInfo(name = "No Slow Down", description = "Позволяет вам не замедляться при использовании предмета.", category = Category.MOVEMENT)
public class NoSlowDown extends Module {
    public static Singleton<NoSlowDown> singleton = Singleton.create(() -> Module.link(NoSlowDown.class));
    private final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Матрикс"),
                    new SubMode("RW"),
                    new SubMode("Грим"),
                    new SubMode("Ванилла")
            );

    private final Supplier<Boolean> onElytra = () -> mc.player.isElytraFlying();

    public TimerUtil timerUtil1 = TimerUtil.create();
    public TimerUtil timerUtil = TimerUtil.create();

    boolean restart = false;

    private final DamageUtil damageUtil = new DamageUtil();
    private final Listener<SlowWalkingEvent> onSlowWalking = event -> {
        if (mc.player == null || onElytra.get()) return;
        processNoSlow(event);
    };
    private final Listener<DamageEvent> onDamage = event -> {
        if (mc.player == null || onElytra.get()) return;
        damageUtil.processDamage(event);
    };
    private final Listener<PacketEvent> onPacket = event -> {
        if (mc.player == null || onElytra.get()) return;
        if (event.isReceive()) {
            damageUtil.onPacketEvent(event);
        }
    };

    private void processNoSlow(SlowWalkingEvent event) {
        if (mc.player.isHandActive()) {
            if (!mc.player.collidedHorizontally && MoveUtil.isMoving() && (mc.gameSettings.keyBindSprint.isKeyDown() || Sprint.singleton.get().isEnabled()))
                mc.player.setSprinting(true);
            switch (mode.getValue().getName()) {
                case "Матрикс" -> handleMatrixMode(event);
                //case "RW" -> handleGrimNewMode(event);
                case "Грим" -> handleGrimACMode(event);
                case "Ванилла" -> event.cancel();
            }
        }
    }

    private void handleMatrixMode(SlowWalkingEvent event) {
        boolean isFalling = (double) mc.player.fallDistance > 0.725;
        float speedMultiplier;
        event.cancel();
        if (mc.player.isOnGround() && !mc.player.movementInput.jump) {
            if (mc.player.ticksExisted % 2 == 0) {
                boolean isNotStrafing = mc.player.moveStrafing == 0.0F;
                speedMultiplier = isNotStrafing ? 0.5F : 0.4F;
                mc.player.motion.x *= speedMultiplier;
                mc.player.motion.z *= speedMultiplier;
            }
        } else if (isFalling) {
            boolean isVeryFastFalling = (double) mc.player.fallDistance > 1.4;
            speedMultiplier = isVeryFastFalling ? 0.95F : 0.97F;
            mc.player.motion.x *= speedMultiplier;
            mc.player.motion.z *= speedMultiplier;
        }
    }

    private final Listener<PacketEvent> onPacket1 = event -> {
        if (mc.player == null) return;
        if (mode.is("RW")) {
            if (event.getPacket() instanceof SHeldItemChangePacket) {
                if (timerUtil1.hasReached(200)) {
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    timerUtil1.reset();
                }
                event.cancel();
            }
            if (event.getPacket() instanceof SEntityVelocityPacket) {
                if (((SEntityVelocityPacket) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                    restart = true;
                    timerUtil.reset();
                }
            }
            if (restart) {
                if (timerUtil.hasReached(1600)) {
                    restart = false;
                    timerUtil.reset();
                }
            }
        }
    };

    private final Listener<SlowWalkingEvent> onSlowWalking1 = event -> {
        if (mode.is("RW")) {
            if (mc.player.isInWater() || restart) {
                if (mc.player.getActiveHand() == Hand.MAIN_HAND) {
                    if (mc.player.getHeldItemOffhand().getUseAction() == UseAction.NONE) {
                        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                        event.cancel();
                    }
                }
            }
            if (mc.player.getActiveHand() == Hand.OFF_HAND) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                event.cancel();
            }
        }
    };

    private void handleGrimNewMode(SlowWalkingEvent event) {
        this.damageUtil.time(1500);

        if ((mc.player.getHeldItemOffhand().getUseAction() == UseAction.BLOCK
                || mc.player.getHeldItemOffhand().getUseAction() == UseAction.EAT) && mc.player.getActiveHand() == Hand.MAIN_HAND) {
            return;
        }

        if (mc.player.isOnGround() && !mc.player.movementInput.jump && !damageUtil.isNormalDamage()) {
            return;
        }

        if (mc.player.getActiveHand() == Hand.MAIN_HAND) {
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
            event.cancel();
            return;
        }
        event.cancel();
        sendItemChangePacket();
    }

    private void handleGrimACMode(SlowWalkingEvent event) {
        if (mc.player.getHeldItemOffhand().getUseAction() == UseAction.BLOCK && mc.player.getActiveHand() == Hand.MAIN_HAND || mc.player.getHeldItemOffhand().getUseAction() == UseAction.EAT && mc.player.getActiveHand() == Hand.MAIN_HAND) {
            return;
        }
        if (mc.player.getActiveHand() == Hand.MAIN_HAND) {
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
            event.cancel();
            return;
        }
        event.cancel();
        sendItemChangePacket();
    }

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private void sendItemChangePacket() {
        if (MoveUtil.isMoving()) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket((mc.player.inventory.currentItem % 8 + 1)));
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
        }
    }
}

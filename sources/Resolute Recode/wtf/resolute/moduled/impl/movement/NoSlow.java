package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.NoSlowEvent;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.utiled.math.TimerUtil;
import wtf.resolute.utiled.player.MoveUtils;
import lombok.ToString;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;

@ToString
@ModuleAnontion(name = "NoSlow", type = Categories.Movement,server = "")
public class NoSlow extends Module {

    private final ModeSetting mode = new ModeSetting("Мод", "Matrix", "Matrix", "Grim");

    public NoSlow() {
        addSettings(mode);
    }

    int ticks = 0;
    boolean restart = false;
    public TimerUtil timerUtil = new TimerUtil();

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player.isHandActive()) {
            ticks++;
        } else {
            ticks = 0;
        }

    }

    @Subscribe
    public void onEating(NoSlowEvent e) {
        handleEventUpdate(e);
    }


    private void handleEventUpdate(NoSlowEvent eventNoSlow) {
        if (mc.player.isHandActive()) {
            switch (mode.get()) {
                case "Grim" -> handleGrimACMode(eventNoSlow);
                case "Matrix" -> handleMatrixMode(eventNoSlow);
                case "Grim New" -> handleMatrixMode(eventNoSlow);
            }
        }
    }
    private void handleMatrixMode(NoSlowEvent eventNoSlow) {
        boolean isFalling = (double) mc.player.fallDistance > 0.725;
        float speedMultiplier;
        eventNoSlow.cancel();
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
    private void handleGrimACMode(NoSlowEvent noSlow) {
        boolean offHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.OFF_HAND;
        boolean mainHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.MAIN_HAND;
        if (!(mc.player.getItemInUseCount() < 25 && mc.player.getItemInUseCount() > 4) && mc.player.getHeldItemOffhand().getItem() != Items.SHIELD) {
            return;
        }
        if (mc.player.isHandActive() && !mc.player.isPassenger()) {
            mc.playerController.syncCurrentPlayItem();
            if (offHandActive && !mc.player.getCooldownTracker().hasCooldown(mc.player.getHeldItemOffhand().getItem())) {
                int old = mc.player.inventory.currentItem;
                mc.player.connection.sendPacket(new CHeldItemChangePacket(old + 1 > 8 ? old - 1 : old + 1));
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                mc.player.setSprinting(false);
                noSlow.cancel();
            }

            if (mainHandActive && !mc.player.getCooldownTracker().hasCooldown(mc.player.getHeldItemMainhand().getItem())) {
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));

                if (mc.player.getHeldItemOffhand().getUseAction().equals(UseAction.NONE)) {
                    noSlow.cancel();
                }
            }
            mc.playerController.syncCurrentPlayItem();
        }
    }

    private void sendItemChangePacket() {
        if (MoveUtils.isMoving()) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket((mc.player.inventory.currentItem % 8 + 1)));
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
        }
    }

    private boolean canCancel() {
        boolean isHandActive = mc.player.isHandActive();
        boolean isLevitation = mc.player.isPotionActive(Effects.LEVITATION);
        boolean isOnGround = mc.player.isOnGround();
        boolean isJumpPressed = mc.gameSettings.keyBindJump.pressed;
        boolean isElytraFlying = mc.player.isElytraFlying();

        if (isLevitation || isElytraFlying) {
            return false;
        }

        return (isOnGround || isJumpPressed) && isHandActive;
    }
}

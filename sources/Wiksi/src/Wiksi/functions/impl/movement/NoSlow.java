//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.events.NoSlowEvent;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.utils.player.MoveUtils;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(
        name = "NoSlow",
        type = Category.Movement
)
public class NoSlow extends Function {
    private final ModeSetting mode = new ModeSetting("Мод", "Matrix", new String[]{"Matrix", "Grim", "RW"});
    int ticks = 0;

    public NoSlow() {
        this.addSettings(new Setting[]{this.mode});
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player.isHandActive()) {
            ++this.ticks;
        } else {
            this.ticks = 0;
        }

    }

    @Subscribe
    public void onEating(NoSlowEvent e) {
        this.handleEventUpdate(e);
    }

    private void handleEventUpdate(NoSlowEvent eventNoSlow) {
        if (mc.player.isHandActive()) {
            switch ((String)this.mode.get()) {
                case "Grim" -> this.handleGrimACMode(eventNoSlow);
                case "Matrix" -> this.handleMatrixMode(eventNoSlow);
                case "RW" -> this.handleGrimACMode1(eventNoSlow);
            }
        }

    }

    private void handleMatrixMode(NoSlowEvent eventNoSlow) {
        boolean isFalling = (double)mc.player.fallDistance > 0.725;
        eventNoSlow.cancel();
        Vector3d var10000;
        float speedMultiplier;
        boolean isVeryFastFalling;
        if (mc.player.isOnGround() && !mc.player.movementInput.jump) {
            if (mc.player.ticksExisted % 2 == 0) {
                isVeryFastFalling = mc.player.moveStrafing == 0.0F;
                speedMultiplier = isVeryFastFalling ? 0.5F : 0.4F;
                var10000 = mc.player.motion;
                var10000.x *= (double)speedMultiplier;
                var10000 = mc.player.motion;
                var10000.z *= (double)speedMultiplier;
            }
        } else if (isFalling) {
            isVeryFastFalling = (double)mc.player.fallDistance > 1.4;
            speedMultiplier = isVeryFastFalling ? 0.95F : 0.97F;
            var10000 = mc.player.motion;
            var10000.x *= (double)speedMultiplier;
            var10000 = mc.player.motion;
            var10000.z *= (double)speedMultiplier;
        }

    }

    private void handleGrimACMode(NoSlowEvent noSlow) {
        boolean offHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.OFF_HAND;
        boolean mainHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.MAIN_HAND;
        if (mc.player.getItemInUseCount() < 25 && mc.player.getItemInUseCount() > 4 || mc.player.getHeldItemOffhand().getItem() == Items.SHIELD) {
            if (mc.player.isHandActive() && !mc.player.isPassenger()) {
                mc.playerController.syncCurrentPlayItem();
                if (offHandActive && !mc.player.getCooldownTracker().hasCooldown(mc.player.getHeldItemOffhand().getItem())) {
                    int old = mc.player.inventory.currentItem;
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(old + 1 > 8 ? old - 1 : old + 1));
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    mc.player.setSprinting(true);
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
    }

    private void handleGrimACMode1(NoSlowEvent noSlow) {
        boolean offHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.OFF_HAND;
        boolean mainHandActive = mc.player.isHandActive() && mc.player.getActiveHand() == Hand.MAIN_HAND;
        if (mc.player.getItemInUseCount() < 25 && mc.player.getItemInUseCount() > 4 || mc.player.getHeldItemOffhand().getItem() == Items.SHIELD) {
            if (mc.player.isHandActive() && !mc.player.isPassenger()) {
                mc.playerController.syncCurrentPlayItem();
                if (offHandActive && !mc.player.getCooldownTracker().hasCooldown(mc.player.getHeldItemOffhand().getItem())) {
                    int old = mc.player.inventory.currentItem;
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(old + 1 > 8 ? old - 1 : old + 1));
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    mc.player.setSprinting(true);
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
    }

    private void sendItemChangePacket() {
        if (MoveUtils.isMoving()) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
        }

    }

    private boolean canCancel() {
        boolean isHandActive = mc.player.isHandActive();
        boolean isLevitation = mc.player.isPotionActive(Effects.LEVITATION);
        boolean isOnGround = mc.player.isOnGround();
        boolean isJumpPressed = mc.gameSettings.keyBindJump.pressed;
        boolean isElytraFlying = mc.player.isElytraFlying();
        if (!isLevitation && !isElytraFlying) {
            return (isOnGround || isJumpPressed) && isHandActive;
        } else {
            return false;
        }
    }

    public String toString() {
        return "NoSlow(mode=" + this.mode + ", ticks=" + this.ticks + ")";
    }
}

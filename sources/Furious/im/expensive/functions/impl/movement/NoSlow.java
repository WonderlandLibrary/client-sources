package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.events.NoSlowEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.player.MoveUtils;
import lombok.ToString;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@ToString
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
        Minecraft var10000 = mc;
        if (Minecraft.player.isHandActive()) {
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
        Minecraft var10000 = mc;
        if (Minecraft.player.isHandActive()) {
            switch ((String)this.mode.get()) {
                case "Grim" -> this.handleGrimACMode(eventNoSlow);
                case "Matrix" -> this.handleMatrixMode(eventNoSlow);
                case "RW" -> this.handleGrimACMode1(eventNoSlow);
            }
        }

    }

    private void handleMatrixMode(NoSlowEvent eventNoSlow) {
        Minecraft var10000 = mc;
        boolean isFalling = (double)Minecraft.player.fallDistance > 0.725;
        eventNoSlow.cancel();
        var10000 = mc;
        float speedMultiplier;
        boolean isNotStrafing;
        Vector3d var5;
        if (Minecraft.player.isOnGround()) {
            var10000 = mc;
            if (!Minecraft.player.movementInput.jump) {
                var10000 = mc;
                if (Minecraft.player.ticksExisted % 2 == 0) {
                    var10000 = mc;
                    isNotStrafing = Minecraft.player.moveStrafing == 0.0F;
                    speedMultiplier = isNotStrafing ? 0.5F : 0.4F;
                    var10000 = mc;
                    var5 = Minecraft.player.motion;
                    var5.x *= (double)speedMultiplier;
                    var10000 = mc;
                    var5 = Minecraft.player.motion;
                    var5.z *= (double)speedMultiplier;
                }

                return;
            }
        }

        if (isFalling) {
            var10000 = mc;
            isNotStrafing = (double)Minecraft.player.fallDistance > 1.4;
            speedMultiplier = isNotStrafing ? 0.95F : 0.97F;
            var10000 = mc;
            var5 = Minecraft.player.motion;
            var5.x *= (double)speedMultiplier;
            var10000 = mc;
            var5 = Minecraft.player.motion;
            var5.z *= (double)speedMultiplier;
        }

    }

    private void handleGrimACMode(NoSlowEvent noSlow) {
        Minecraft var10000;
        boolean var5;
        label56: {
            var10000 = mc;
            if (Minecraft.player.isHandActive()) {
                var10000 = mc;
                if (Minecraft.player.getActiveHand() == Hand.OFF_HAND) {
                    var5 = true;
                    break label56;
                }
            }

            var5 = false;
        }

        boolean offHandActive;
        label51: {
            offHandActive = var5;
            var10000 = mc;
            if (Minecraft.player.isHandActive()) {
                var10000 = mc;
                if (Minecraft.player.getActiveHand() == Hand.MAIN_HAND) {
                    var5 = true;
                    break label51;
                }
            }

            var5 = false;
        }

        boolean mainHandActive;
        label46: {
            mainHandActive = var5;
            var10000 = mc;
            if (Minecraft.player.getItemInUseCount() < 25) {
                var10000 = mc;
                if (Minecraft.player.getItemInUseCount() > 4) {
                    break label46;
                }
            }

            var10000 = mc;
            if (Minecraft.player.getHeldItemOffhand().getItem() != Items.SHIELD) {
                return;
            }
        }

        var10000 = mc;
        if (Minecraft.player.isHandActive()) {
            var10000 = mc;
            if (!Minecraft.player.isPassenger()) {
                mc.playerController.syncCurrentPlayItem();
                Minecraft var10001;
                CooldownTracker var6;
                if (offHandActive) {
                    var10000 = mc;
                    var6 = Minecraft.player.getCooldownTracker();
                    var10001 = mc;
                    if (!var6.hasCooldown(Minecraft.player.getHeldItemOffhand().getItem())) {
                        var10000 = mc;
                        int old = Minecraft.player.inventory.currentItem;
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(old + 1 > 8 ? old - 1 : old + 1));
                        var10000 = mc;
                        Minecraft var10003 = mc;
                        Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(Minecraft.player.inventory.currentItem));
                        var10000 = mc;
                        Minecraft.player.setSprinting(true);
                        noSlow.cancel();
                    }
                }

                if (mainHandActive) {
                    var10000 = mc;
                    var6 = Minecraft.player.getCooldownTracker();
                    var10001 = mc;
                    if (!var6.hasCooldown(Minecraft.player.getHeldItemMainhand().getItem())) {
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                        var10000 = mc;
                        if (Minecraft.player.getHeldItemOffhand().getUseAction().equals(UseAction.NONE)) {
                            noSlow.cancel();
                        }
                    }
                }

                mc.playerController.syncCurrentPlayItem();
            }
        }

    }

    private void handleGrimACMode1(NoSlowEvent noSlow) {
        Minecraft var10000;
        boolean var5;
        label56: {
            var10000 = mc;
            if (Minecraft.player.isHandActive()) {
                var10000 = mc;
                if (Minecraft.player.getActiveHand() == Hand.OFF_HAND) {
                    var5 = true;
                    break label56;
                }
            }

            var5 = false;
        }

        boolean offHandActive;
        label51: {
            offHandActive = var5;
            var10000 = mc;
            if (Minecraft.player.isHandActive()) {
                var10000 = mc;
                if (Minecraft.player.getActiveHand() == Hand.MAIN_HAND) {
                    var5 = true;
                    break label51;
                }
            }

            var5 = false;
        }

        boolean mainHandActive;
        label46: {
            mainHandActive = var5;
            var10000 = mc;
            if (Minecraft.player.getItemInUseCount() < 25) {
                var10000 = mc;
                if (Minecraft.player.getItemInUseCount() > 4) {
                    break label46;
                }
            }

            var10000 = mc;
            if (Minecraft.player.getHeldItemOffhand().getItem() != Items.SHIELD) {
                return;
            }
        }

        var10000 = mc;
        if (Minecraft.player.isHandActive()) {
            var10000 = mc;
            if (!Minecraft.player.isPassenger()) {
                mc.playerController.syncCurrentPlayItem();
                Minecraft var10001;
                CooldownTracker var6;
                if (offHandActive) {
                    var10000 = mc;
                    var6 = Minecraft.player.getCooldownTracker();
                    var10001 = mc;
                    if (!var6.hasCooldown(Minecraft.player.getHeldItemOffhand().getItem())) {
                        var10000 = mc;
                        int old = Minecraft.player.inventory.currentItem;
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(old + 1 > 8 ? old - 1 : old + 1));
                        var10000 = mc;
                        Minecraft var10003 = mc;
                        Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(Minecraft.player.inventory.currentItem));
                        var10000 = mc;
                        Minecraft.player.setSprinting(true);
                        noSlow.cancel();
                    }
                }

                if (mainHandActive) {
                    var10000 = mc;
                    var6 = Minecraft.player.getCooldownTracker();
                    var10001 = mc;
                    if (!var6.hasCooldown(Minecraft.player.getHeldItemMainhand().getItem())) {
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                        var10000 = mc;
                        if (Minecraft.player.getHeldItemOffhand().getUseAction().equals(UseAction.NONE)) {
                            noSlow.cancel();
                        }
                    }
                }

                mc.playerController.syncCurrentPlayItem();
            }
        }

    }

    private void sendItemChangePacket() {
        if (MoveUtils.isMoving()) {
            Minecraft var10000 = mc;
            Minecraft var10003 = mc;
            Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(Minecraft.player.inventory.currentItem % 8 + 1));
            var10000 = mc;
            var10003 = mc;
            Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(Minecraft.player.inventory.currentItem));
        }

    }

    private boolean canCancel() {
        Minecraft var10000 = mc;
        boolean isHandActive = Minecraft.player.isHandActive();
        var10000 = mc;
        boolean isLevitation = Minecraft.player.isPotionActive(Effects.LEVITATION);
        var10000 = mc;
        boolean isOnGround = Minecraft.player.isOnGround();
        boolean isJumpPressed = mc.gameSettings.keyBindJump.pressed;
        var10000 = mc;
        boolean isElytraFlying = Minecraft.player.isElytraFlying();
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

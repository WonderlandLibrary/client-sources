/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.NoSlowEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;

@FunctionRegister(name="NoSlow", type=Category.Movement)
public class NoSlow
extends Function {
    private final ModeSetting mode = new ModeSetting("\u041c\u043e\u0434", "Matrix", "Matrix", "Grim");
    int ticks = 0;

    public NoSlow() {
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        this.ticks = NoSlow.mc.player.isHandActive() ? ++this.ticks : 0;
    }

    @Subscribe
    public void onEating(NoSlowEvent noSlowEvent) {
        this.handleEventUpdate(noSlowEvent);
    }

    private void handleEventUpdate(NoSlowEvent noSlowEvent) {
        if (NoSlow.mc.player.isHandActive()) {
            switch ((String)this.mode.get()) {
                case "Grim": {
                    this.handleGrimACMode(noSlowEvent);
                    break;
                }
                case "Matrix": {
                    this.handleMatrixMode(noSlowEvent);
                }
            }
        }
    }

    private void handleMatrixMode(NoSlowEvent noSlowEvent) {
        boolean bl = (double)NoSlow.mc.player.fallDistance > 0.725;
        noSlowEvent.cancel();
        if (NoSlow.mc.player.isOnGround() && !NoSlow.mc.player.movementInput.jump) {
            if (NoSlow.mc.player.ticksExisted % 2 == 0) {
                boolean bl2 = NoSlow.mc.player.moveStrafing == 0.0f;
                float f = bl2 ? 0.5f : 0.4f;
                NoSlow.mc.player.motion.x *= (double)f;
                NoSlow.mc.player.motion.z *= (double)f;
            }
        } else if (bl) {
            boolean bl3 = (double)NoSlow.mc.player.fallDistance > 1.4;
            float f = bl3 ? 0.95f : 0.97f;
            NoSlow.mc.player.motion.x *= (double)f;
            NoSlow.mc.player.motion.z *= (double)f;
        }
    }

    private void handleGrimACMode(NoSlowEvent noSlowEvent) {
        boolean bl;
        boolean bl2 = NoSlow.mc.player.isHandActive() && NoSlow.mc.player.getActiveHand() == Hand.OFF_HAND;
        boolean bl3 = bl = NoSlow.mc.player.isHandActive() && NoSlow.mc.player.getActiveHand() == Hand.MAIN_HAND;
        if ((NoSlow.mc.player.getItemInUseCount() >= 25 || NoSlow.mc.player.getItemInUseCount() <= 4) && NoSlow.mc.player.getHeldItemOffhand().getItem() != Items.SHIELD) {
            return;
        }
        if (NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isPassenger()) {
            NoSlow.mc.playerController.syncCurrentPlayItem();
            if (bl2 && !NoSlow.mc.player.getCooldownTracker().hasCooldown(NoSlow.mc.player.getHeldItemOffhand().getItem())) {
                int n = NoSlow.mc.player.inventory.currentItem;
                NoSlow.mc.player.connection.sendPacket(new CHeldItemChangePacket(n + 1 > 8 ? n - 1 : n + 1));
                NoSlow.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlow.mc.player.inventory.currentItem));
                NoSlow.mc.player.setSprinting(true);
                noSlowEvent.cancel();
            }
            if (bl && !NoSlow.mc.player.getCooldownTracker().hasCooldown(NoSlow.mc.player.getHeldItemMainhand().getItem())) {
                NoSlow.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                if (NoSlow.mc.player.getHeldItemOffhand().getUseAction().equals((Object)UseAction.NONE)) {
                    noSlowEvent.cancel();
                }
            }
            NoSlow.mc.playerController.syncCurrentPlayItem();
        }
    }

    private void sendItemChangePacket() {
        if (MoveUtils.isMoving()) {
            NoSlow.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlow.mc.player.inventory.currentItem % 8 + 1));
            NoSlow.mc.player.connection.sendPacket(new CHeldItemChangePacket(NoSlow.mc.player.inventory.currentItem));
        }
    }

    private boolean canCancel() {
        boolean bl = NoSlow.mc.player.isHandActive();
        boolean bl2 = NoSlow.mc.player.isPotionActive(Effects.LEVITATION);
        boolean bl3 = NoSlow.mc.player.isOnGround();
        boolean bl4 = NoSlow.mc.gameSettings.keyBindJump.pressed;
        boolean bl5 = NoSlow.mc.player.isElytraFlying();
        if (bl2 || bl5) {
            return true;
        }
        return (bl3 || bl4) && bl;
    }

    public String toString() {
        return "NoSlow(mode=" + this.mode + ", ticks=" + this.ticks + ")";
    }
}


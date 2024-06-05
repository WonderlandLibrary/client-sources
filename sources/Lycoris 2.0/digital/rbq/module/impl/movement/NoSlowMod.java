/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.UseItemEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.AuraMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.utils.PlayerUtils;

@Label(value="No Slow")
@Category(value=ModuleCategory.MOVEMENT)
@Aliases(value={"noslow", "noslowdown"})
public final class NoSlowMod
extends Module {
    public final BoolOption ncp = new BoolOption("NCP", true);

    public NoSlowMod() {
        this.addOptions(this.ncp);
    }

    @Listener(value=UseItemEvent.class)
    public final void onUseItem(UseItemEvent event) {
        event.setCancelled();
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        if (this.ncp.getValue().booleanValue() && this.isBlocking() && NoSlowMod.mc.thePlayer.isMoving() && NoSlowMod.mc.thePlayer.onGround) {
            if (event.isPre()) {
                NoSlowMod.mc.playerController.syncCurrentPlayItem();
                mc.getNetHandler().addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else {
                NoSlowMod.mc.playerController.syncCurrentPlayItem();
                mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(NoSlowMod.mc.thePlayer.getCurrentEquippedItem()));
            }
        }
    }

    private boolean isBlocking() {
        return PlayerUtils.isHoldingSword() && NoSlowMod.mc.thePlayer.isBlocking() && !AuraMod.blocking;
    }
}


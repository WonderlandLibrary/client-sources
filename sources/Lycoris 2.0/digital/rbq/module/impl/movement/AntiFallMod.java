/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.utils.PlayerUtils;
import digital.rbq.utils.Stopwatch;

@Label(value="Anti Fall")
@Category(value=ModuleCategory.MOVEMENT)
@Aliases(value={"antifall", "antivoid", "novoid"})
public final class AntiFallMod
extends Module {
    private final Stopwatch fallStopwatch = new Stopwatch();
    public final DoubleOption distance = new DoubleOption("Distance", 5.0, 3.0, 10.0, 0.5);

    public AntiFallMod() {
        this.addOptions(this.distance);
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityPlayerSP player = AntiFallMod.mc.thePlayer;
        if ((double)player.fallDistance > (Double)this.distance.getValue() && !player.capabilities.isFlying && this.fallStopwatch.elapsed(250L) && !PlayerUtils.isBlockUnder()) {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + (Double)this.distance.getValue() + 1.0, player.posZ, false));
            this.fallStopwatch.reset();
        }
    }
}


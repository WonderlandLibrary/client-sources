package digital.rbq.module.implement.Ghost;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import digital.rbq.Lycoris;
import digital.rbq.event.LoopEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.value.FloatValue;
import digital.rbq.utility.DelayTimer;
import digital.rbq.utility.FriendManager;

public class TriggerBot extends Module {
    public static FloatValue cps = new FloatValue("TriggerBot", "APS", 10.0f, 1.0f, 20.0f, 1.0f);

    private DelayTimer timer = new DelayTimer();

    public TriggerBot() {
        super("TriggerBot", Category.Ghost, false);
    }

    @EventTarget
    public void onUpdate(LoopEvent event) {
        if (this.mc.theWorld == null || this.mc.objectMouseOver.entityHit == null || ModuleManager.killAuraMod.isEnabled())
            return;

        if (this.mc.objectMouseOver.entityHit instanceof EntityPlayer && Lycoris.teams.getValue() && FriendManager.isTeam((EntityPlayer) this.mc.objectMouseOver.entityHit))
            return;

        if (this.timer.hasPassed(1000.0000f / cps.getValue())) {
            if (mc.thePlayer.isBlocking()) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
                this.timer.reset();
            } else {
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
                this.timer.reset();
            }
        }
    }
}

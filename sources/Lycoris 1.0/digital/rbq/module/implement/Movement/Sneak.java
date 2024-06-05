package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import digital.rbq.event.PostUpdateEvent;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

/**
 * Created by John on 2016/12/12.
 */
public class Sneak extends Module {
    public Sneak() {
        super("Sneak", Category.Movement, false);
    }

    @EventTarget
    public void onUpdate(PreUpdateEvent event) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }

    @EventTarget
    public void onUpdatePost(PostUpdateEvent event) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    }
}

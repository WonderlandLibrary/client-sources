package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import digital.rbq.event.PacketReceiveEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Movement.longjump.Dev;
import digital.rbq.module.implement.Movement.longjump.Hypixel;
import digital.rbq.module.implement.Movement.longjump.Normal;
import digital.rbq.module.implement.Movement.longjump.Old;
import digital.rbq.module.value.BooleanValue;

public class LongJump extends Module {
    public LongJump() {
        super("LongJump", Category.Movement, true, new Normal(), new Old(), new Dev());
    }

    public static BooleanValue autoToggle = new BooleanValue("LongJump", "Auto Toggle", false);

    @EventTarget
    public void onLagback(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            this.toggle();
        }
    }
}

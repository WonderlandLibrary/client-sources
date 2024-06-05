package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.util.EnumChatFormatting;
import digital.rbq.Lycoris;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.event.TickEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.ModeValue;

public class Spider extends Module{
    public static ModeValue mode = new ModeValue("Spider", "Mode", "Vanilla");

    private double jumpY;

    public Spider() {
        super("Spider", Category.Movement, mode);
    }

    @EventTarget
    public void onUpdate(PreUpdateEvent event) {
        if (this.mc.thePlayer.isCollidedHorizontally) {
            if (mode.getValue().equals("Vanilla"))
                this.mc.thePlayer.motionY = 0.3f;
        }

        if(this.mc.thePlayer.onGround){
            this.jumpY = this.mc.thePlayer.posY;
        }
    }
}

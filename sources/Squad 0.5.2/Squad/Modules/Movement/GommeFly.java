package Squad.Modules.Movement;

import Squad.Events.EventUpdate;
import Squad.Modules.Settings.Gomme;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class GommeFly extends Module {

    public GommeFly() {
        super("GommeFly", Keyboard.KEY_NONE, 0x88, Category.Movement);
    }

    TimeHelper RebellQSG = new TimeHelper();

    public void onEnable() {
        EventManager.register(this);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition());
        mc.thePlayer.motionZ -= -3.6363125;
        mc.thePlayer.motionX -= -3.53432242;

    }
    }


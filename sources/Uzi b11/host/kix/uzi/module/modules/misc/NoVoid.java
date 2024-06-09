package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/25/2017.
 */
public class NoVoid extends Module {

    public NoVoid() {
        super("NoVoid", 0, Category.MISC);
    }

    @SubscribeEvent
    public void onMotion(UpdateEvent e){
            if (mc.thePlayer.fallDistance >= 6 && mc.thePlayer.isEntityAlive()) {
                mc.thePlayer.motionY = 0.0;
            }
        }
}

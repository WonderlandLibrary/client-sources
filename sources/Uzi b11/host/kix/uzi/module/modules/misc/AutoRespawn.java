package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class AutoRespawn extends Module {

    public AutoRespawn() {
        super("AutoRespawn", 0, Category.MISC);
    }

    @SubscribeEvent
    public void updateGame(UpdateEvent event) {
        if (mc.thePlayer.isDead && this.isEnabled()) {
            mc.thePlayer.respawnPlayer();
        }
    }

}

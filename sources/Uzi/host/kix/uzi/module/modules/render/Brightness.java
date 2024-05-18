package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/25/2017.
 */
public class Brightness extends Module {

    public Brightness() {
        super("Brightness", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e){
        if(this.isEnabled()) {
            mc.gameSettings.gammaSetting = 10000F;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1F;
    }
}

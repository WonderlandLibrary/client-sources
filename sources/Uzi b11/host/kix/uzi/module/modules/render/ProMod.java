package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by myche on 2/26/2017.
 */
public class ProMod extends Module {

    public ProMod() {
        super("ProMod", 0, Category.RENDER);
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
        if(this.isEnabled()) {
            mc.gameSettings.fovSetting = 500F;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.fovSetting = 60F;
    }
}

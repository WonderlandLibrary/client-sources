package host.kix.uzi.module.modules.world;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", 0, Category.WORLD);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event){
        if(isEnabled()){
            mc.rightClickDelayTimer = 0;
        }else{
            mc.rightClickDelayTimer = 4;

        }
    }

}

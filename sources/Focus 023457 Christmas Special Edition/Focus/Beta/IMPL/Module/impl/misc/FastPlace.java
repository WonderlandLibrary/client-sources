package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;

public class FastPlace extends Module {

    public FastPlace(){
        super("FastPlace", new String[0], Type.MISC, "Allow's to placing block's faster");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e){
        mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable(){
        mc.rightClickDelayTimer =6;
        super.onDisable();
    }
}

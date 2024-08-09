package wtf.resolute.moduled.impl.player;

import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "KTLeave", type = Categories.Player,server = "")
public class KTLeave extends Module {

    @Override
    public void onEnable() {
        super.onEnable();
       // mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, (BlockRayTraceResult) mc.player.pick(4.5f, 1, false));

        this.setState(false, false);
    }
}

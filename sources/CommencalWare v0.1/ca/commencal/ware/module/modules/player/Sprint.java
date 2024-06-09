package ca.commencal.ware.module.modules.player;

import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;
import ca.commencal.ware.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", ModuleCategory.PLAYER);
    }

//    @Override
    public void onUpdate() {
        mc.player.setSprinting(true);
    }
}

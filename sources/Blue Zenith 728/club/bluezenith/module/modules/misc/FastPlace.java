package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", ModuleCategory.MISC);
    }

    @Listener
    public void the(UpdatePlayerEvent event) {
        mc.rightClickDelayTimer = 0;
    }
}

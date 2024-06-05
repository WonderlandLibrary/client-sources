package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

import java.awt.*;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", new String[]{"fplace", "fc"}, ModuleType.Player);
        this.setColor(new Color(226, 197, 78).getRGB());
    }

    @EventHandler
    private void onTick(EventTick e) {
        mc.rightClickDelayTimer = 0;
    }
}
 
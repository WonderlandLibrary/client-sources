package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;

import java.awt.Color;

public class Sprint extends Module {
    private Option<Boolean> omni = new Option<Boolean>("All-Direction", "All-Direction", true);

    public Sprint() {
        super("Sprint", new String[] { "run" }, ModuleType.Move);
        this.setColor(new Color(158, 205, 125).getRGB());
        this.addValues(this.omni);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        if (this.mc.thePlayer.getFoodStats().getFoodLevel() > 6 )
            this.mc.thePlayer.setSprinting(true);
        }
    }

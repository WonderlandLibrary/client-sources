package rip.helium.cheat.impl.misc;

import me.hippo.systems.lwjeb.annotation.Collect;
import rip.helium.cheat.Cheat;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.utils.property.impl.DoubleProperty;

public class VClip extends Cheat{
	
    public VClip() {
        super("VClip", "clips down.");
    }
    
    private DoubleProperty amount = new DoubleProperty("Amount", "The amount of blocks you want to vclip.", null, 10, 1, 20.0, 0.1, null);
	


}

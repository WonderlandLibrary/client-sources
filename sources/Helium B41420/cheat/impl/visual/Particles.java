package rip.helium.cheat.impl.visual;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.util.EnumParticleTypes;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerMoveEvent;
import rip.helium.utils.property.impl.StringsProperty;

public class Particles extends Cheat {
	
    private StringsProperty modeProperty = new StringsProperty("Mode", "How this cheat will function.", null,
            false, true, new String[] {"Cloud", "Heart"}, new Boolean[] {false, true});

    public Particles() {
        super("Particles", "See particles behind u!", CheatCategory.VISUAL);
    }
    
    
    
    @Collect
    public void move(PlayerMoveEvent e) {
    	if (mc.thePlayer.isMoving()) {
    		if (modeProperty.getSelectedStrings().get(0).equalsIgnoreCase("Cloud")) {
    			mc.effectRenderer.emitParticleAtEntity(mc.thePlayer, EnumParticleTypes.CLOUD);
    		}
    	}
    }
}

package rip.helium.cheat.impl.visual;

import me.hippo.systems.lwjeb.annotation.*;
import rip.helium.cheat.*;
import rip.helium.event.minecraft.*;
import rip.helium.utils.*;
import rip.helium.utils.property.abs.*;
import rip.helium.utils.property.impl.*;

public class BlockAnimation extends Cheat
{
    public StringsProperty mode;
    
    public BlockAnimation() {
        super("Animations", "Different Sword block animations", CheatCategory.VISUAL);
        this.mode = new StringsProperty("Mode", "How this cheat will function.", null, false, true, new String[] { "Helium", "Spin", "Sigma", "Slide", "Slide2", "Kansio", "Poke", "Vanilla" }, new Boolean[] { true, false, false, false, false, false, false, false });
        this.registerProperties(this.mode);
    }
    
    @Collect
    public void onPlayerUpdate(final PlayerUpdateEvent e) {
        this.setMode(this.mode.getSelectedStrings().get(0));
    }
}

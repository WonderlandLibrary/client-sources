package mathax.client.systems.modules.misc;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoPauseOnLostFocus extends Module {

    public NoPauseOnLostFocus() {
        super(Categories.Experimental, Items.ALLAY_SPAWN_EGG, "no-pause-on-lost-focus", "allow alt+tab without pause");
        mc.options.pauseOnLostFocus = !isActive();
    }

    @Override
    public boolean onActivate() {
        mc.options.pauseOnLostFocus = false;
        return false;
    }

    @Override
    public void onDeactivate() {
        mc.options.pauseOnLostFocus = true;
    }

}

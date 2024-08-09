package mathax.client.systems.modules.experimental;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class SuperPanic extends Module {
    public SuperPanic() {
        super(Categories.Experimental, Items.STRIPPED_CRIMSON_HYPHAE, "SuperPanic", "For when you need all it all to end");
    }

    public boolean onActivate() {
        mc.stop();
        mc.close();
        mc.getWindow().close();
        mc.getWindow().shouldClose();
        mc.getWindow().setFramerateLimit(-200);
        //we need all 5 of these, trust me


        return false;
    }
}



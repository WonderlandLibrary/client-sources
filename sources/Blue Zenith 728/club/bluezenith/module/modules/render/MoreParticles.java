package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ListValue;

public class MoreParticles extends Module {

    public final ListValue types = new ListValue("Particles", "Criticals", "Enchant").setIndex(2);
    public final IntegerValue multiplier = new IntegerValue("Multiplier", 1, 1, 20, 1).setIndex(1);
    public MoreParticles() {
        super("Particles", ModuleCategory.RENDER);
    }

}

package digital.rbq.module.implement.Misc;

import digital.rbq.Lycoris;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

public class Panic extends Module {
    public Panic() {
        super("Panic", Category.Misc, false);
    }

    @Override
    public void onEnable() {
        for (Module mod : Lycoris.INSTANCE.getModuleManager().getModList()) {
            if (mod.isEnabled())
                mod.setEnabled(false);
        }
    }
}

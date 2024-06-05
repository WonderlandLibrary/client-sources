package digital.rbq.module.implement.Misc;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Misc.disabler.Hypixel;

public class Disabler extends Module {

    public Disabler() {
        super("Disabler", Category.Misc, true, new Hypixel());
    }
}

/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.world;

import digital.rbq.annotations.Label;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.DoubleOption;

@Label(value="Time Changer")
@Category(value=ModuleCategory.WORLD)
@Aliases(value={"timechanger", "worldtime"})
public final class TimeChangerMod
extends Module {
    public final DoubleOption time = new DoubleOption("Time", 16000.0, 1.0, 24000.0, 100.0);

    public TimeChangerMod() {
        this.addOptions(this.time);
    }
}


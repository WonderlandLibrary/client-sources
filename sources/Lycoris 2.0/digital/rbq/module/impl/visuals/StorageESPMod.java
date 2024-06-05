/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import java.awt.Color;
import digital.rbq.annotations.Label;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.EnumOption;

@Label(value="Storage ESP")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"storageesp", "chestesp"})
public final class StorageESPMod
extends Module {
    private final int chestColor = new Color(250, 138, 19).getRGB();
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.BOX);

    public static enum Mode {
        BOX;

    }
}


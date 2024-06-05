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
import digital.rbq.module.option.impl.ColorOption;

@Label(value="Damage Color")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"damagecolor"})
public final class DamageColorMod
extends Module {
    public static final ColorOption color = new ColorOption("Color", new Color(255, 0, 0, 76));

    public DamageColorMod() {
        this.addOptions(color);
    }
}


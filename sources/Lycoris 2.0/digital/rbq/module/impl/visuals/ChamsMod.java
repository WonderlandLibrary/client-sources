/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import digital.rbq.annotations.Label;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.render.Palette;

@Label(value="Chams")
@Category(value=ModuleCategory.VISUALS)
public final class ChamsMod
extends Module {
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.COLOR);
    public final EnumOption<Palette> player = new EnumOption<Palette>("Player", Palette.PURPLE);
    public final EnumOption<Palette> playerBehindWalls = new EnumOption<Palette>("Player behind walls", Palette.PURPLE);
    public static final int HANDCOL = -1253464587;

    public ChamsMod() {
        this.addOptions(this.mode, this.player, this.playerBehindWalls);
    }

    public static enum Mode {
        COLOR,
        TEXTURED;

    }
}


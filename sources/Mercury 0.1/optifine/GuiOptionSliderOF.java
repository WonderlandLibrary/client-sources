/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;
import optifine.IOptionControl;

public class GuiOptionSliderOF
extends GuiOptionSlider
implements IOptionControl {
    private GameSettings.Options option = null;

    public GuiOptionSliderOF(int id2, int x2, int y2, GameSettings.Options option) {
        super(id2, x2, y2, option);
        this.option = option;
    }

    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}


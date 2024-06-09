/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;
import optifine.IOptionControl;

public class GuiOptionButtonOF
extends GuiOptionButton
implements IOptionControl {
    private GameSettings.Options option = null;

    public GuiOptionButtonOF(int id2, int x2, int y2, GameSettings.Options option, String text) {
        super(id2, x2, y2, option, text);
        this.option = option;
    }

    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}


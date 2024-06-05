/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import digital.rbq.annotations.Label;
import digital.rbq.clickgui.ClickGuiScreen;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;

@Label(value="Click GUI")
@Bind(value="RSHIFT")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"clickgui", "gui"})
public final class ClickGUIMod
extends Module {
    public ClickGUIMod() {
        this.setHidden(true);
    }

    @Override
    public void onEnabled() {
        mc.displayGuiScreen(ClickGuiScreen.getInstance());
        this.setEnabled(false);
    }
}


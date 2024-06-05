/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.GUI.clickgui.ClickUi;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;

public class ClickGui
extends Module {
    public static final Mode<Enum> mode = new Mode("Modes", "Modes", (Enum[])Modes.values(), (Enum)Modes.DrunkClient);

    public ClickGui() {
        super("ClickUI", new String[0], Type.RENDER, "No");
        this.addValues(mode);
    }

    @Override
    public void onEnable() {
        switch (mode.getModeAsString()) {
            case "DrunkClient": {
                mc.displayGuiScreen(new ClickUi());
                this.setEnabled(false);
                return;
            }
        }
    }

    static enum Modes {
        DrunkClient;

    }
}


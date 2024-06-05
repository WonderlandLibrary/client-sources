/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.drunkclient;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;

public class Cape
extends Module {
    public Mode<Enum> mode = new Mode("Modes", "Modes", (Enum[])Styles.values(), (Enum)Styles.Drunk3);

    public Cape() {
        super("Cape", new String[0], Type.DrunkClient, "No");
        this.addValues(this.mode);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if (this.mode.getModeAsString().equalsIgnoreCase("Drunk1")) {
            this.setSuffix("Beer");
            return;
        }
        if (this.mode.getModeAsString().equalsIgnoreCase("Drunk2")) {
            this.setSuffix("Paint");
            return;
        }
        if (!this.mode.getModeAsString().equalsIgnoreCase("Drunk3")) return;
        this.setSuffix("Developer");
    }

    static enum Styles {
        Drunk1,
        Drunk2,
        Drunk3;

    }
}


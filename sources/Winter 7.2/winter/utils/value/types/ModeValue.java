/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.value.types;

import winter.module.Module;
import winter.module.modules.modes.Mode;
import winter.utils.value.Value;

public class ModeValue
extends Value {
    private Module mod;

    public ModeValue(Module mod) {
        super("Mode");
        this.mod = mod;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Module getMod() {
        return this.mod;
    }

    public void previousMode() {
        this.mod.mode(this.mod.getPreviousMode().getDisplay());
    }

    public void nextMode() {
        this.mod.mode(this.mod.getNextMode().getDisplay());
    }
}


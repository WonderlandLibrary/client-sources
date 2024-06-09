/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod(displayName="NameProtect", shown=false)
public class NameProtect
extends Module {
    @Option.Op
    private boolean colored = true;

    @EventTarget
    public void unUpdate(UpdateEvent event) {
    }

    public boolean getColored() {
        return this.colored;
    }
}


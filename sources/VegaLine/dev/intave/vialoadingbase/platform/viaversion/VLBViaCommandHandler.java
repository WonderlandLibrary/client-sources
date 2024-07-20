/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.platform.viaversion;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import dev.intave.vialoadingbase.command.impl.LeakDetectSubCommand;

public class VLBViaCommandHandler
extends ViaCommandHandler {
    public VLBViaCommandHandler() {
        this.registerVLBDefaults();
    }

    public void registerVLBDefaults() {
        this.registerSubCommand(new LeakDetectSubCommand());
    }
}


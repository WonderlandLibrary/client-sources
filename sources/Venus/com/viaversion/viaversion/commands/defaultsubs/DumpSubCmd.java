/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.util.DumpUtil;

public class DumpSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "dump";
    }

    @Override
    public String description() {
        return "Dump information about your server, this is helpful if you report bugs.";
    }

    @Override
    public boolean execute(ViaCommandSender viaCommandSender, String[] stringArray) {
        DumpUtil.postDump(viaCommandSender.getUUID()).whenComplete((arg_0, arg_1) -> DumpSubCmd.lambda$execute$0(viaCommandSender, arg_0, arg_1));
        return false;
    }

    private static void lambda$execute$0(ViaCommandSender viaCommandSender, String string, Throwable throwable) {
        if (throwable != null) {
            viaCommandSender.sendMessage("\u00a74" + throwable.getMessage());
            return;
        }
        viaCommandSender.sendMessage("\u00a72We've made a dump with useful information, report your issue and provide this url: " + string);
    }
}


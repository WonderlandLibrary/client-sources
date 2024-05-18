/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.modules.render.hud.tabgui.LucidTabGui;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"font", "ft"})
public class Font
extends Command {
    @Override
    public void runCommand(String[] args) {
        if (!LucidTabGui.newFont) {
            LucidTabGui.newFont = true;
            ClientUtils.sendMessage("Enabled new Font.", true);
        } else {
            LucidTabGui.newFont = true;
            ClientUtils.sendMessage("Disabled new Font.", true);
        }
    }

    @Override
    public String getHelp() {
        return "Font - font <ft> - Enables new Font.";
    }
}


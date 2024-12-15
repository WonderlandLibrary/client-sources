package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.module.Module;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.localization.Localization;
import net.minecraft.util.EnumChatFormatting;
public final class Toggle extends Command {

    public Toggle() {
        super("command.toggle.description", "toggle", "t");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length != 2) {
            error(String.format(".%s <module>", args[0]));
            return;
        }
        final Module module = Client.INSTANCE.getModuleManager().get(args[1]);
        if (module == null) {
            ChatUtil.display(Localization.get("command.bind.invalidmodule"));
            return;
        }
        module.toggle();
        ChatUtil.display(
                Localization.get("command.toggle.toggled"),
                module.getAliases()[0] + " " + (module.isEnabled() ? EnumChatFormatting.GREEN + "on" : EnumChatFormatting.RED + "off")
        );
    }
}
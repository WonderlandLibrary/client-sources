package dev.excellent.client.command.impl;

import dev.excellent.api.user.UsernameManager;
import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.util.text.TextFormatting;

public final class LoginCommand extends Command {

    public LoginCommand() {
        super("", "login");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length == 2) {
            mc.session = new net.minecraft.util.Session(args[1], "", "", "mojang");
            UsernameManager.saveUsername(mc.session.getUsername());
            ChatUtil.addText(TextFormatting.RED + "\nНик изменён на - &7[&f" + args[1] + "&7]\n&c(Требуется перезаход)");
        } else {
            error();
        }
    }
}
package dev.vertic.command.impl;

import dev.vertic.Client;
import dev.vertic.command.Command;
import dev.vertic.module.Module;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "toggle", "t");
    }

    @Override
    public void call(String[] args) {
        if (args.length > 1) {
            Module m = Client.instance.getModuleManager().getModuleNoSpace(args[1]);
            if (m != null) {
                m.toggle();
                addChatMessage((m.isEnabled() ? EnumChatFormatting.GREEN + "Enabled " + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Disabled " + EnumChatFormatting.RESET) + args[1]);
            } else {
                addChatMessage(args[1] + " is not a valid module.");
            }
        } else {
            addChatMessage(".toggle <module> / .t <module>");
        }
    }
}

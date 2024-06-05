package me.vaziak.sensation.client.api.command.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.command.Command;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Sets the keybind for a module", "<module> <key>", new String[]{"b", "key"});
    }

    @Override
    public boolean onCommand(String[] args) {
        if (args.length == 3) {
            List<Module> cheats = Sensation.instance.cheatManager.searchRegistryReplaceSpaces(args[1]);
            int keyCode = Keyboard.getKeyIndex(args[2].toUpperCase());

            if (cheats.size() < 1 || cheats.get(0) == null) {
                ChatUtils.log("Could not find module '" + args[1] + "'");
                return true;
            }

            Module cheat = cheats.get(0);

            cheat.setBind(keyCode);
            ChatUtils.log("Bound " + EnumChatFormatting.RED + cheat.getId() + EnumChatFormatting.GRAY + " to " + EnumChatFormatting.RED + Keyboard.getKeyName(keyCode));
        } else {
            return false;
        }

        return true;
    }
}

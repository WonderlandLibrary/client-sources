package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.*;
import org.lwjgl.input.*;
import me.satisfactory.base.module.*;

public class CommandBind extends Command
{
    public CommandBind() {
        super("Bind", "bind");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length != 2) {
            MiscellaneousUtil.sendInfo("An error has occured! Please try .Bind <Module> <Key>");
        }
        else {
            try {
                final Module module = Base.INSTANCE.getModuleManager().getModByName(args[0]);
                final String keyId = args[1];
                final int key = Keyboard.getKeyIndex(keyId.toUpperCase());
                if (module != null) {
                    if (keyId.toUpperCase().equals("NONE")) {
                        MiscellaneousUtil.sendInfo("Cleared the keybind of the module " + module.getName());
                        module.setKeybind(0);
                    }
                    else if (Keyboard.getKeyIndex(keyId.toUpperCase()) == 0) {
                        MiscellaneousUtil.sendInfo("Could not find the key " + keyId + ".§f Please try .bind <module> <key>");
                    }
                    else {
                        module.setKeybind(key);
                        MiscellaneousUtil.sendInfo(String.format("" + module.getName() + "'s§f keybind as successfully been bound to " + keyId.toString().toUpperCase() + "§f.", new Object[0]));
                    }
                }
                else {
                    MiscellaneousUtil.sendInfo("Could not find the module " + args[0] + "!");
                }
            }
            catch (Exception e) {
                System.out.println(e);
                MiscellaneousUtil.sendInfo("Could not find the module " + args[0] + "!");
            }
        }
    }
}

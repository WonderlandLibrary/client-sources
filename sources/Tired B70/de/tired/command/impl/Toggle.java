package de.tired.command.impl;

import de.tired.api.annotations.CommandAnnotation;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.command.Command;
import de.tired.interfaces.IHook;
import de.tired.module.Module;
import de.tired.tired.Tired;

@CommandAnnotation(name = "Toggle", help = "ToggleSuss", alias = {"toggle", "t"})
public class Toggle extends Command implements IHook {

    @Override
    public void doCommand(String[] args) {
        if (args.length != 1) {
            IngameChatLog.INGAME_CHAT_LOG.doLog("You can toggle with .t name or with .toggle name");
        }

        for (Module module : Tired.INSTANCE.moduleManager.getModuleList()) {
            if (!args[0].equalsIgnoreCase(module.getName())) continue;
            module.executeMod();
            IngameChatLog.INGAME_CHAT_LOG.doLog(module.getName() + " was toggled");
            break;
        }
        super.doCommand(args);
    }

}

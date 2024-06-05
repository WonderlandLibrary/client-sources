package club.mega.command.impl;

import club.mega.Mega;
import club.mega.command.Command;
import club.mega.file.ModuleSaver;
import club.mega.module.Module;
import club.mega.util.ChatUtil;

@Command.Info(name = "Toggle", description = "Toggle Modules", usage = ".toggle <module>", aliases = {"toggle", "t"})
public final class ToggleCommand extends Command {

    @Override
    public void execute(final String[] args) {
        final Module module = Mega.INSTANCE.getModuleManager().getModule(args[0]);
        if(module != null) {
            module.toggle();
            ModuleSaver.save();
            ChatUtil.sendMessage("Toggled module " + module.getName());
        } else {
            ChatUtil.sendMessage("Invalid Module");
        }
    }
}

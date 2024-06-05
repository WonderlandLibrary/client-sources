package club.mega.command.impl;

import club.mega.Mega;
import club.mega.command.Command;
import club.mega.file.ModuleSaver;
import club.mega.module.Module;
import club.mega.util.ChatUtil;
import org.lwjgl.input.Keyboard;

@Command.Info(name = "Bind", description = "Bind Modules", usage = ".bind <module> <key>", aliases = {"bind", "b"})
public final class BindCommand extends Command {

    @Override
    public void execute(final String[] args) {
        final Module module = Mega.INSTANCE.getModuleManager().getModule(args[0]);
        if(module != null) {
            module.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
            ModuleSaver.save();
            ChatUtil.sendMessage("Bound ``" + module.getName() + "`` to " + Keyboard.getKeyName(module.getKey()));
        } else {
            ChatUtil.sendMessage("Invalid Module");
        }
    }
}

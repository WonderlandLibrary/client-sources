package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.module.Module;
import fr.dog.util.player.ChatUtil;
import org.lwjglx.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "b", "blind");
    }

    @Override
    public void execute(final String[] args, final String message) {
        try {
            Module module = Dog.getInstance().getModuleManager().getModule(args[1]);
            int keyCode = Keyboard.getKeyIndex(args[2].toUpperCase());

            module.setKeyBind(keyCode);

            ChatUtil.display(String.format("Module %s has been bound to %s", module.getName(), Keyboard.getKeyName(module.getKeyBind())));
        } catch (Throwable throwable) {
            ChatUtil.display("Usage: .bind [module] [key]");
        }
    }
}
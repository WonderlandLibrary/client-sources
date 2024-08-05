package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.module.Module;
import fr.dog.util.player.ChatUtil;



public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "t");
    }

    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if (words.length < 1 || words.length > 2) {
            ChatUtil.display("Invalid arguments! Usage: .toggle <module>");
            return;
        }

        String mds = words[1].toLowerCase();

        Module module = Dog.getInstance().getModuleManager().getModule(mds);

        if(module != null){
            module.setEnabled(!module.isEnabled());
            ChatUtil.display("Toggled module " + module.getName() + " to : " + module.isEnabled());
        }
    }
}
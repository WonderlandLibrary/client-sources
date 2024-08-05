package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.module.Module;
import fr.dog.util.player.ChatUtil;

import java.util.Objects;


public class CustomNameCommand extends Command {

    public CustomNameCommand() {
        super("customname", "cn");
    }

    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if (words.length < 1 || words.length > 3) {
            ChatUtil.display("Invalid arguments! Usage: .cn <module/reset/list> <namewitoutspaces>");
            return;
        }

        String mds = words[1].toLowerCase();

        if(mds.equalsIgnoreCase("reset")) {
            Dog.getInstance().getModuleManager().getObjects().forEach(e -> {
                e.setCustomName(e.getName());
            });
            return;
        }
        if(mds.equalsIgnoreCase("list")) {
            Dog.getInstance().getModuleManager().getObjects().forEach(e -> {
                if(!Objects.equals(e.getCustomName(), e.getName())){
                    ChatUtil.display(e.getName() + " > " + e.getCustomName());
                }
            });
            return;
        }

        Module module = Dog.getInstance().getModuleManager().getModule(mds);

        if(module != null && words.length > 2){
            String masd = words[2];
            module.setCustomName(masd);
            ChatUtil.display("Changed name of module : " + module.getName() + " to : " + module.getCustomName());
        }else{
            ChatUtil.display("Invalid arguments! Usage: .cn <module/reset/list> <namewitoutspaces>");
        }
    }
}
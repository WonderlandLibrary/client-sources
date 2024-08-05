package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.component.impl.player.HypixelComponent;
import fr.dog.module.impl.render.Overlay;
import fr.dog.util.player.ChatUtil;
import org.lwjglx.input.Mouse;

public class HypixelAPICommand extends Command {
    public HypixelAPICommand() {
        super("setkey", "sk");
    }
    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if(words.length < 1){
            ChatUtil.display(".setkey <APIKEY>");
            ChatUtil.display("You can get a hypixel API key at developer.hypixel.net");
            return;
        }


        if(words[1].equalsIgnoreCase("reset")){

            Dog.getInstance().getModuleManager().getModule(Overlay.class).playerData.clear();
            System.gc();
            Mouse.scrollEvents = new double[999999];
            return;

        }


        HypixelComponent.HYPIXEL_APIKEY = words[1];
        ChatUtil.display("Hypixel API key is now set to : " + HypixelComponent.HYPIXEL_APIKEY);

    }
}

package me.teus.eclipse.utils.managers;

import me.teus.eclipse.commands.Command;
import me.teus.eclipse.commands.impl.BindCommand;
import me.teus.eclipse.commands.impl.Toggle;
import me.teus.eclipse.commands.impl.test;
import me.teus.eclipse.events.player.EventChat;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public static List<Command> commands = new ArrayList<Command>();

    public String prefix = ".";

    public static void initialize(){
        commands.add(new BindCommand());
        commands.add(new Toggle());
        commands.add(new test());

        for (Command cmds : commands){
            System.out.println("Loaded " + cmds.name);
        }
    }

    public void handleChat(EventChat event){
        String message = event.getMessage();

        if(!message.startsWith(prefix))
            return;

        event.setCancelled(true);

        message = message.substring(prefix.length());

        if(message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            for(Command c : commands){
                if(c.aliases.contains(commandName)){
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                }
            }
        }
    }

}

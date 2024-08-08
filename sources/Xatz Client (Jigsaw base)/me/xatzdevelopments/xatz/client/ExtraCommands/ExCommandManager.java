package me.xatzdevelopments.xatz.client.ExtraCommands;

import java.util.ArrayList;

import me.xatzdevelopments.xatz.client.ExtraCommands.ExCommands.*;
import me.xatzdevelopments.xatz.utils.Wrapper;

public class ExCommandManager {

    public ArrayList<ExCommand> commands = new ArrayList<>();

    public ExCommandManager(){
//        commands.add(new Toggle());
//        commands.add(new Bind());
        commands.add(new PlayMusic());
//        commands.add(new Hypixel());
//        commands.add(new Replay());
        //commands.add(new Songs());
    }

    public void call(String input){
        String[] split = input.split(" ");
        String commandName = split[0];
        String args = input.substring(commandName.length()).trim();
        for(ExCommand c : commands){
            if(c.getName().equalsIgnoreCase(commandName)){
                c.onCommand(args, args.split(" "));
                return;
            }
        }
        Wrapper.tellPlayer("Invalid Command! try .help for list of other commands");
    }
}

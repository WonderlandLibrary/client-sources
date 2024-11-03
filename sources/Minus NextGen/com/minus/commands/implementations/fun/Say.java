package com.minus.commands.implementations.fun;

import com.minus.commands.Command;
import com.minus.commands.CommandInfo;
import com.minus.utils.ChatUtils;

@CommandInfo(names = {"say", "print"}, description = "print something out of your chat")
public class Say extends Command {
    @Override
    public void onCall(String[] args) {
        if (args.length > 1){
            String message = args[1];
            ChatUtils.addMessage(message);
        }
        else{
            ChatUtils.addMessage("Lacks of arguments >.<");
        }
    }
}

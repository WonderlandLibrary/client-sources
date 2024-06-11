package Hydro.command.impl;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;

public class Watermark implements CommandExecutor {

    @Override
    public void execute(EntityPlayerSP sender, List<String> args) {
        if(args.size() == 1){
            Client.instance.setName(args.get(0));
            ChatUtils.sendMessageToPlayer("Set Watermark to " + EnumChatFormatting.BLUE + args.get(0));
        }else{
            ChatUtils.sendMessageToPlayer(EnumChatFormatting.RED + getSyntax());
        }
    }

    private String getSyntax(){
        return ".Watermark (name)";
    }

}

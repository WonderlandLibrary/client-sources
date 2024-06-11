package Hydro.command.impl;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;

public class Cape implements CommandExecutor {

    @Override
    public void execute(EntityPlayerSP sender, List<String> args) {
        if(args.size() > 0) {
            String capeName = args.get(0);
            try {


                ChatUtils.sendMessageToPlayer("Cape was successfuly set!");

            }catch(Exception e) {
                ChatUtils.sendMessageToPlayer("Cape not found! Please check your spelling.");
            }

        }else{
            ChatUtils.sendMessageToPlayer(EnumChatFormatting.RED + ".Cape (name)");
        }
    }

}

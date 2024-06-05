package xyz.vergoclient.commands.impl;

import net.minecraft.client.Minecraft;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.commands.OnCommandInterface;
import xyz.vergoclient.util.main.ChatUtils;

public class GetItemDebug implements OnCommandInterface {

    @Override
    public void onCommand(String... args) {
        if(Vergo.isDev) {
            ChatUtils.addDevMessage(Minecraft.getMinecraft().thePlayer.getHeldItem());
            ChatUtils.addDevMessage(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem());
        } else {
            ChatUtils.addChatMessage("Command unrecognized. Please use .help for all valid commands.");
        }
    }

    @Override
    public String getName() {
        return "getitem";
    }

    @Override
    public String getUsage() {
        return ".getitem";
    }

    @Override
    public String getDescription() {
        return "get item";
    }

}

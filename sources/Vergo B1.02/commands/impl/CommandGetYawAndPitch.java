package xyz.vergoclient.commands.impl;

import net.minecraft.client.Minecraft;
import xyz.vergoclient.commands.OnCommandInterface;
import xyz.vergoclient.util.main.ChatUtils;

public class CommandGetYawAndPitch implements OnCommandInterface {

    @Override
    public void onCommand(String... args) {
        ChatUtils.addChatMessage("yaw: " + Minecraft.getMinecraft().thePlayer.rotationYaw);
        ChatUtils.addChatMessage("pitch: " + Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    @Override
    public String getName() {
        return "GetYawAndPitch";
    }

    @Override
    public String getUsage() {
        return ".getyawandpitch";
    }

    @Override
    public String getDescription() {
        return "Returns the current yaw and pitch of the player";
    }

}

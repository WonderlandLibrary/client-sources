package tk.rektsky.Commands.Commands;

import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import tk.rektsky.Commands.*;
import java.util.*;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help", "", "Show commands list");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.GREEN + "=============================="));
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.GREEN + " Commands List"));
        for (final Command cmd : CommandsManager.COMMANDS) {
            displayCommandInfomation(cmd);
        }
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.GREEN + "=============================="));
    }
    
    public static void displayCommandInfomation(final Command cmd) {
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.YELLOW + cmd.getName() + " " + cmd.getArgumentDescription() + "  -  " + ChatFormatting.GRAY + cmd.getDescription()));
    }
}

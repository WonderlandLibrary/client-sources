package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import tk.rektsky.Main.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import tk.rektsky.*;

public class NameCommand extends Command
{
    public NameCommand() {
        super("name", "", "Copy your name");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        this.mc.displayGuiScreen(new GuiChat(Auth.ign));
        Client.addClientChat(new ChatComponentText("Please copy your name to your clipboard!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
    }
}

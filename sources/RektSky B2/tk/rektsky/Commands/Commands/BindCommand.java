package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;
import net.minecraft.client.*;
import tk.rektsky.Guis.*;
import net.minecraft.client.gui.*;
import tk.rektsky.Module.*;

public class BindCommand extends Command
{
    public BindCommand() {
        super("bind", new String[] { "b" }, "<Module>", "Set the keybinding of the module");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        if (args.length != 1) {
            HelpCommand.displayCommandInfomation(this);
            return;
        }
        final Module module = ModulesManager.getModuleByName(args[0]);
        if (module == null) {
            Client.addClientChat(ChatFormatting.RED + "Invalid Module!");
            return;
        }
        Minecraft.getMinecraft().displayGuiScreen(new GuiKeybind(Minecraft.getMinecraft().currentScreen, module));
    }
}

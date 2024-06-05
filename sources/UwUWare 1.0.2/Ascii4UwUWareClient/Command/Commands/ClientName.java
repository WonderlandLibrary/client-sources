package Ascii4UwUWareClient.Command.Commands;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Command.Command;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.Display;

import java.util.Arrays;
import java.util.List;

public class ClientName extends Command {
    public ClientName() {
        super("clientname", new String[] { "clientname", "cn", "name", "client" }, "", "Change client name to whatever you want :D");
    }

    @Override
    public String execute(String[] args) {
        if (args.length > 0) {
            StringBuilder string = new StringBuilder();
            for (String arg : args) {
                String tempString = arg;
                tempString = tempString.replace('&', '§');
                string.append(tempString).append(" ");
            }
            Client.instance.name = string.toString();
            Display.setTitle(string.toString().trim());
            Helper.sendMessage("Changed client name to " + string.toString().trim());
        } else {
            Helper.sendMessage("Correct usage .clientname <name>");
        }
        return null;
    }
}

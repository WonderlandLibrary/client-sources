package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

public class Toggle extends Command {
    public Toggle() {
        super("t", new String[]{"toggle", "togl", "turnon", "enable"}, "", "Toggles a specified Module");
    }

    @Override
    public String execute(String[] args) {
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        } else if (args.length < 1) {
            Helper.sendMessage("Correct usage .t <module>");
        }
        boolean found = false;
        Module m = Client.getModuleManager().getAlias(args[0]);
        if (m != null) {
            m.setEnabled(!m.isEnabled());
            found = true;
            if (m.isEnabled()) {
                Helper.sendMessage(m.getName() + EnumChatFormatting.GRAY + " was"
                        + EnumChatFormatting.GREEN + " enabled");
            } else {
                Helper.sendMessage(m.getName() + EnumChatFormatting.GRAY + " was"
                        + EnumChatFormatting.RED + " disabled");
            }
        }
        if (!found) {
            Helper.sendMessage("Module name " + EnumChatFormatting.RED + args[0]
                    + EnumChatFormatting.GRAY + " is invalid");
        }
        return null;
    }
}

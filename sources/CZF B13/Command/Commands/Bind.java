package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("Bind", new String[]{"b"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 2) {
            Module m = Client.getModuleManager().getAlias(args[0]);
            if (m != null) {
                int k = Keyboard.getKeyIndex(args[1].toUpperCase());
                m.setKey(k);
                Object[] arrobject = new Object[2];
                arrobject[0] = m.getName();
                arrobject[1] = k == 0 ? "none" : args[1].toUpperCase();
                Helper.sendMessage(String.format("Bound %s to %s", arrobject));
            } else {
                Helper.sendMessage("Module name " + EnumChatFormatting.RED + args[0]
                        + EnumChatFormatting.GRAY + " is invalid");
            }
        } else {
            Helper.sendMessage("Correct usage .bind <module> <key>");
        }
        return null;
    }
}

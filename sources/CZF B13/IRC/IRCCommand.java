package gq.vapu.czfclient.IRC;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

public class IRCCommand extends Command {
    public static String username = null;

    public IRCCommand(){
        super("IRC",new String[]{"irc","i","chat"}, "", "irc");
    }

    @Override
    public String execute(String[] args) {
        if(args.length <= 1) {
            StringBuilder czf = new StringBuilder();
            for (String s : args)
                czf.append(" ").append(s);
            czf.deleteCharAt(0);
            IRC.sendMessage("MSG@"+ Helper.mc.thePlayer.getName() + "@" + EnumChatFormatting.WHITE + Client.name + "@" + czf);
        }
        return null;
    }
}

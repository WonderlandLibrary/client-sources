package gq.vapu.czfclient.Command.Commands;

//new ChatUtils.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.BLUE).build().displayClientSided();

import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;

import java.util.Arrays;

public class Echo extends Command {
    public Echo() {
        super("Echo", new String[]{"say"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 1) {
            StringBuilder czf = new StringBuilder();
            for (String s : args)
                czf.append(" ").append(s);
            czf.deleteCharAt(0);
            Helper.mc.thePlayer.sendChatMessage(czf.toString());
        } else {
            Helper.sendMessage("Correct usage .echo <Message>");
        }
        return null;
    }
}

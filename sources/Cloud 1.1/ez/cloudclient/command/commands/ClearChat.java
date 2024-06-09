package ez.cloudclient.command.commands;

import ez.cloudclient.command.Command;
import ez.cloudclient.util.MessageUtil;

public class ClearChat extends Command {

    public ClearChat() {
        super("ClearChat", "Clears all messages in chat", "clearchat", "cchat");
    }

    @Override
    public void call(String[] args) {
        if (mc.player == null) return;
        for (int i = 0; i < mc.ingameGUI.getChatGUI().getLineCount(); i++) {
            mc.ingameGUI.getChatGUI().deleteChatLine(i);
        }
        MessageUtil.sendMessage("Cleared chat", MessageUtil.Color.GREEN);
    }
}

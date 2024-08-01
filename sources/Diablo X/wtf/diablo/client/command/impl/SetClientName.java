package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.api.ClientInformation;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(
        name = "setname",
        description = "Sets the client's display name (watermark)",
        usage = "setname <name>",
        aliases = {"setclient", "cname", "name", "setclientname"}
)
public final class SetClientName extends AbstractCommand {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            this.sendUsage();
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();

        for (final String string : args) {
            stringBuilder.append(string).append(" ");
        }

        final ClientInformation clientInformation = Diablo.getInstance().getClientInformation();

        clientInformation.setDisplayName(stringBuilder.toString());

        ChatUtil.addChatMessage("Set the client's display name to " + clientInformation.getDisplayName());
    }
}

package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.config.Config;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

@Command(
        name = "config",
        description = "Configures the client",
        usage = "config <key> <value>"
)
public final class ConfigCommand extends AbstractCommand {

    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            this.sendUsage();
            return;
        }

        final String key = args[0];
        final String value = args[1];

        switch (key.toLowerCase()) {
            case "save":
                final Config config = new Config(value);
                ChatUtil.addChatMessage("Attempting to Save config.");

                try
                {
                    Diablo.getInstance().getConfigManager().saveConfig(config);
                    ChatUtil.addChatMessage("Saved config.");
                }
                catch (final Exception ignored)
                {
                    ChatUtil.addChatMessage("Failed to save config.");
                }
                break;
            case "load":
                Diablo.getInstance().getConfigManager().loadConfig(value);
                ChatUtil.addChatMessage("Loaded config.");
                break;
            default:
                ChatUtil.addChatMessage("Invalid key.");
                break;
        }
    }
}
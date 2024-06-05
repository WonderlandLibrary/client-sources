package me.vaziak.sensation.client.api.command;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.stream.Stream;

public class CommandHandler {
    private CommandManager commandManager;

    public CommandHandler() {
        Sensation.eventBus.register(this);
        EventSystem.hook(this);
        commandManager = Sensation.instance.commandManager;
    }

    @Collect
    public void onEvent(SendPacketEvent event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();

            String prefix = ".";

            if (packet.getMessage().startsWith(prefix)) {
                event.setCancelled(true);
                // Handle command

                // Might be better to substring, but replace first will replace the first period (prefix for commands) to work
                String[] args = packet.getMessage().replaceFirst(prefix, "").split("\\s+");

                if (args.length > 0) {
                    String commandName = args[0];

                    if (commandManager == null) {
                        commandManager = Sensation.instance.commandManager; // idk why this is required but
                    }

                    if (commandName != null
                            && commandName.length() > 0
                            && commandManager != null
                            && commandManager.getCommandList() != null
                            && commandManager.getCommandList().size() > 0) {
                        for (Command command : commandManager.getCommandList()) {
                            if (command.getName().equalsIgnoreCase(commandName)
                                    || Stream.of(command.getAliases()).anyMatch(s -> s.equalsIgnoreCase(commandName))) {
                                boolean successful = command.onCommand(args);

                                if (!successful) {
                                    ChatUtils.log("Syntax Error. Try: ." + commandName + " " + command.getUsage());
                                }
                                return;
                            }
                        }

                        ChatUtils.log("Unknown Command. For a list of commands do .help");
                    }
                }
            }
        }
    }
}
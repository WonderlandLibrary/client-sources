package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.play.client.C01PacketChatMessage;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.RenderChatEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.RenderUtils;

/**
 * @author Matthew
 */
public class Commands extends Mod implements Listener<SendPacketEvent> {
    private Listener renderChatListener;

    public Commands() {
        super("Commands", ModType.MISCELLANEOUS);

        renderChatListener = new Listener<RenderChatEvent>() {
            @Override
            public void onEventCalled(RenderChatEvent event) {
                if (event.getString().startsWith(XIV.getInstance().getCommandManager().getPrefix())) {
                    String message = event.getString();

                    String predictedCommand = "";
                    for (Command command : XIV.getInstance().getCommandManager().getContents()) {
                        if ((XIV.getInstance().getCommandManager().getPrefix() + command.getCmd()).toLowerCase().startsWith(message.split(" ")[0].toLowerCase())) {
                            predictedCommand = command.getCmd();
                        }

                        if (command.getAliases() != null) {
                            for (String alias : command.getAliases()) {
                                if ((XIV.getInstance().getCommandManager().getPrefix() + alias).toLowerCase().startsWith(message.split(" ")[0].toLowerCase())) {
                                    predictedCommand = alias;
                                }
                            }
                        }
                    }

                    predictedCommand = XIV.getInstance().getCommandManager().getPrefix() + predictedCommand;
                    if (!predictedCommand.equalsIgnoreCase("") && !message.contains(" ")) {
                        int y = RenderUtils.newScaledResolution().getScaledHeight() - 12;
                        mc.fontRendererObj.drawStringWithShadow(predictedCommand, 4, y, 0xFF808080);
                    }
                }
            }
        };
        setEnabled(true);
    }

    public void onEventCalled(SendPacketEvent event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
            event.setCancelled(XIV.getInstance().getCommandManager().parseCommand(packet.getMessage()));
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
        XIV.getInstance().getListenerManager().add(renderChatListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
        XIV.getInstance().getListenerManager().remove(renderChatListener);
    }
}

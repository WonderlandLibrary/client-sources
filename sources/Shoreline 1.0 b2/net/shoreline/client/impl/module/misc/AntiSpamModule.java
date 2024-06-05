package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AntiSpamModule extends ToggleModule
{
    //
    private final Map<UUID, String> messages = new HashMap<>();

    /**
     *
     */
    public AntiSpamModule()
    {
        super("AntiSpam", "Prevents players from spamming the game chat",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketInbound(PacketEvent.Inbound event)
    {
        if (mc.player == null)
        {
            return;
        }
        if (event.getPacket() instanceof ChatMessageS2CPacket packet)
        {
            final UUID sender = packet.sender();
            final String chatMessage = packet.body().content();
            String lastMessage = messages.get(sender);
            if (chatMessage.equalsIgnoreCase(lastMessage))
            {
                event.cancel();
            }
            else if (lastMessage != null)
            {
                messages.replace(sender, chatMessage);
            }
            else
            {
                messages.put(sender, chatMessage);
            }
        }
    }
}

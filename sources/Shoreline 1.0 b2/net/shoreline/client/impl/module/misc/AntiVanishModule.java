package net.shoreline.client.impl.module.misc;

import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.gui.hud.ChatMessageEvent;
import net.shoreline.client.util.chat.ChatUtil;
import net.shoreline.client.util.math.timer.CacheTimer;
import net.shoreline.client.util.math.timer.Timer;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AntiVanishModule extends ToggleModule
{
    //
    private final Timer vanishTimer = new CacheTimer();
    //
    private Map<UUID, String> playerCache = new HashMap<>();
    private final Set<String> messageCache = new HashSet<>();

    /**
     *
     */
    public AntiVanishModule()
    {
        super("AntiVanish", "Notifies user when a player uses /vanish",
                ModuleCategory.MISCELLANEOUS);
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        messageCache.clear();
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onChatMessage(ChatMessageEvent event)
    {
        // This only works if the server doesnt have a custom join/leave
        // message plugin
        String message = event.getText().getString();
        if (message.contains("left"))
        {
            messageCache.add(message);
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        if (!vanishTimer.passed(1000))
        {
            return;
        }
        final Map<UUID, String> players = playerCache;
        playerCache = mc.getNetworkHandler().getPlayerList().stream()
                .collect(Collectors.toMap(e -> e.getProfile().getId(),
                        e -> e.getProfile().getName()));
        for (UUID uuid : players.keySet())
        {
            if (playerCache.containsKey(uuid))
            {
                continue;
            }
            String name = players.get(uuid);
            if (messageCache.stream().noneMatch(s -> s.contains(name)))
            {
                ChatUtil.clientSendMessage("[AntiVanish] %s used /vanish!", name);
            }
        }
        messageCache.clear();
        vanishTimer.reset();
    }
}

package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.ScreenOpenEvent;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.imixin.IPlayerInteractEntityC2SPacket;
import net.shoreline.client.util.network.InteractType;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class AntiBotsModule extends ToggleModule
{
    //
    Config<Boolean> pingConfig = new BooleanConfig("Ping", "Checks the ping " +
            "of the bot", true);
    Config<Boolean> invisibleConfig = new BooleanConfig("Invisibles", "Checks" +
            " if the bot is invisible", true);
    Config<Boolean> nameConfig = new BooleanConfig("Name", "Checks the " +
            "username of the bot", true);
    Config<Boolean> uuidConfig = new BooleanConfig("UUID", "Checks the UUID " +
            "of the bot", true);
    //
    private final Set<PlayerEntity> botPlayers = new HashSet<>();

    /**
     *
     */
    public AntiBotsModule()
    {
        super("AntiBots", "Prevents player from interacting with bots",
                ModuleCategory.COMBAT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() != EventStage.POST || mc.player.isDead()
                || mc.player.isSpectator())
        {
            return;
        }
        //
        List<AbstractClientPlayerEntity> players = mc.world.getPlayers();
        players.remove(mc.player);
        if (uuidConfig.getValue())
        {
            for (int i = 0; i < players.size(); i++)
            {
                for (int j = i + 1; j < players.size(); j++)
                {
                    PlayerEntity player1 = players.get(i);
                    PlayerEntity player2 = players.get(j);
                    if (player1.getUuid() == player2.getUuid())
                    {
                        if (player1.getId() > player2.getId())
                        {
                            botPlayers.add(player1);
                            botPlayers.remove(player2);
                            break;
                        }
                        botPlayers.add(player2);
                        botPlayers.remove(player1);
                        break;
                    }
                }
            }
        }
        players.stream().filter(p -> checkInvisibility(p) || checkPing(p) || checkName(p))
                .forEach(p -> botPlayers.add(p));
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        if (event.getPacket() instanceof IPlayerInteractEntityC2SPacket packet
                && packet.getType() == InteractType.ATTACK
                && packet.getEntity() instanceof PlayerEntity attackPlayer
                && botPlayers.contains(attackPlayer))
        {
            event.cancel();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onDisconnect(DisconnectEvent event)
    {
        botPlayers.clear();
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onScreenOpen(ScreenOpenEvent event)
    {
        if (event.getScreen() instanceof DownloadingTerrainScreen)
        {
            botPlayers.clear();
        }
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean contains(PlayerEntity player)
    {
        return botPlayers.contains(player);
    }

    /**
     *
     * @param player
     * @return
     */
    private boolean checkInvisibility(PlayerEntity player)
    {
        return invisibleConfig.getValue() && player.isInvisible()
                && !player.hasStatusEffect(StatusEffects.INVISIBILITY);
    }

    /**
     *
     * @param player
     * @return
     */
    private boolean checkPing(PlayerEntity player)
    {
        return pingConfig.getValue() && mc.getNetworkHandler() != null
                && mc.getNetworkHandler().getPlayerListEntry(player.getUuid()) == null;
    }

    /**
     *
     * @param player
     * @return
     */
    private boolean checkName(PlayerEntity player)
    {
        return nameConfig.getValue() && player.getDisplayName().getString().equalsIgnoreCase(
                new StringBuilder().insert(0, player.getName()).append("§r").toString())
                && !mc.player.getDisplayName().getString().equalsIgnoreCase(
                        new StringBuilder().insert(0, mc.player.getName()).append("§r").toString());
    }
}

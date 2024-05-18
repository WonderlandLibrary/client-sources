package us.dev.direkt.command.internal.misc;

import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.util.math.BlockPos;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Foundry
 */
public class PluginDetection extends Command {

    private Timer packetTimer = new Timer(),
            latencyTimer = new Timer();
    private Set<String> detectedPlugins;
    private boolean doneDetection, isSneaky;
    private char currentLetter;

    public PluginDetection() {
        super(Direkt.getInstance().getCommandManager(), "plugindetection", "plugindiscovery", "plugins", "pldisc", "pl");
    }

    @Executes
    public String run() {
        detectedPlugins = new HashSet<>();
        doneDetection = isSneaky = false;
        currentLetter = 'a';
        latencyTimer.reset();

        Direkt.getInstance().getEventManager().register(this);
        Wrapper.sendPacket(new CPacketTabComplete("/", new BlockPos(0, 0, 0), false));
        return "Listening for a SPacketTabComplete for 20s!";
    }

    @Listener
    protected Link<EventGameTick> onGameTick = new Link<>(event -> {
        if (isSneaky) {
            if (packetTimer.hasReach(45 + (int) (Math.random() * 10))) {
                if (currentLetter <= 'z') {
                    Wrapper.sendPacket(new CPacketTabComplete("/" + currentLetter++, new BlockPos(0, 0, 0), false));
                    packetTimer.reset();
                } else if (doneDetection) {
                    if (detectedPlugins.size() > 0) {
                        Wrapper.addChatMessage("Plugins (" + detectedPlugins.size() + "): " + this.getPluginListString() + "\2478.");
                    } else {
                        Wrapper.addChatMessage("Plugins: none.");
                    }
                    Direkt.getInstance().getEventManager().unregister(this);
                }
            }
        } else if (doneDetection) {
            if (detectedPlugins.size() > 0) {
                Wrapper.addChatMessage("Plugins (" + detectedPlugins.size() + "): " + this.getPluginListString() + "\2478.");
                Direkt.getInstance().getEventManager().unregister(this);
            } else {
                doneDetection = false;
                isSneaky = true;
            }
        }
        if (this.latencyTimer.hasReach(20, TimeUnit.SECONDS)) {
            Direkt.getInstance().getEventManager().unregister(this);
            Wrapper.addChatMessage("Stopped listening for an SPacketTabComplete! Took too long (20)!");
        }
    });

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        final String[] matches = ((SPacketTabComplete) event.getPacket()).getMatches();
        for (int length = matches.length, i = 0; i < length; ++i) {
            final String[] pluginsArray = matches[i].split(":");
            final String pluginName = pluginsArray[0].substring(1);
            if (pluginsArray.length > 1 && (!pluginName.equals("minecraft") && !pluginName.equals("bukkit"))) {
                detectedPlugins.add(pluginName);
            }
        }
        doneDetection = !isSneaky || currentLetter == 'z' + 1;
        event.setCancelled(true);
    }, new PacketFilter<>(SPacketTabComplete.class));

    private String getPluginListString() {
        final StringBuilder sb = new StringBuilder(12 * detectedPlugins.size());
        for (String s : detectedPlugins) {
            sb.append(s).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}

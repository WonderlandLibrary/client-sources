package us.dev.direkt.module.internal.core.listeners;

import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.util.math.BlockPos;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.events.game.server.EventServerDisconnect;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.file.internal.files.WaypointsFile;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.Optional;

/**
 * @author Foundry
 */
@ModData(label = "Home Setting Listener", category = ModCategory.CORE)
public class HomeSetListener extends Module {

    private static BlockPos homePos;

    @Listener
    protected Link<EventServerDisconnect> onServerDisconnect = new Link<>(event -> {
        homePos = null;
    });

    @Listener
    protected Link<EventPostReceivePacket> onPostReceivePacket = new Link<>(event -> {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat) event.getPacket();
            if (packet.getType() == 1 && packet.getChatComponent().getUnformattedText().equals("Home set to current location.")) {
                homePos = new BlockPos(Math.floor(Wrapper.getPlayer().posX), Math.floor(Wrapper.getPlayer().posY), Math.floor(Wrapper.getPlayer().posZ));
                final String currentServer = !Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getMinecraft().getCurrentServerData().serverIP : "SINGLEPLAYER";
                final String currentWorld = !Wrapper.getMinecraft().isSingleplayer() ? Wrapper.getWorld().getSpawnPoint().toString() : Wrapper.getMinecraft().getIntegratedServer().getWorldName();

                Direkt.getInstance().getWaypointManager().getWaypoints().stream()
                        .filter(waypoint -> waypoint.getServer().equals(currentServer) && !waypoint.getWorld().contains(homePos.toString()))
                        .forEach(waypoint -> waypoint.setWorld(homePos.toString()));

                Wrapper.getWorld().setSpawnPoint(homePos);
                Direkt.getInstance().getFileManager().getFile(WaypointsFile.class).save();
            }
        } else if (event.getPacket() instanceof SPacketSpawnPosition) {
            homePos = ((SPacketSpawnPosition) event.getPacket()).getSpawnPos();
        }
    }, new PacketFilter<>(SPacketChat.class, SPacketSpawnPosition.class));

    public static Optional<BlockPos> findCurrentHome() {
        return Optional.ofNullable(homePos);
    }
}

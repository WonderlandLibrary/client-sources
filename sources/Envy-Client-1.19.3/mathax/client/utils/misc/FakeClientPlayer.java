package mathax.client.utils.misc;

import mathax.client.MatHax;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;

import static mathax.client.MatHax.mc;

public class FakeClientPlayer {
    private static ClientWorld world;
    private static PlayerEntity player;
    private static PlayerListEntry playerListEntry;

    private static String lastId;
    private static boolean needsNewEntry;

    public static void init() {
        MatHax.EVENT_BUS.subscribe(FakeClientPlayer.class);
    }

    public static PlayerEntity getPlayer() {
        String id = mc.getSession().getUuid();

        if (player == null || (!id.equals(lastId))) {
            if (world == null) {
                world = new ClientWorld(new ClientPlayNetworkHandler(mc, null, new ClientConnection(NetworkSide.CLIENTBOUND), mc.getCurrentServerEntry(), mc.getSession().getProfile(), null), new ClientWorld.Properties(Difficulty.NORMAL, false, false), world.getRegistryKey(), world.getDimensionEntry(), 1, 1, mc::getProfiler, null, false, 0);
            }

            player = new OtherClientPlayerEntity(world, mc.getSession().getProfile());

            lastId = id;
            needsNewEntry = true;
        }

        return player;
    }

    public static PlayerListEntry getPlayerListEntry() {
        if (playerListEntry == null || needsNewEntry) {
            playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            needsNewEntry = false;
        }

        return playerListEntry;
    }
}

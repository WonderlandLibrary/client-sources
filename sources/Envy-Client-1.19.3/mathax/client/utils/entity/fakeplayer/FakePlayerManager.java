package mathax.client.utils.entity.fakeplayer;

import java.util.ArrayList;
import java.util.List;

import static mathax.client.MatHax.mc;

public class FakePlayerManager {
    public static final List<FakePlayerEntity> fakePlayers = new ArrayList<>();

    public static void add(String name, float health, boolean copyInv) {
        FakePlayerEntity fakePlayer = new FakePlayerEntity(mc.player, name, health, copyInv);
        fakePlayer.spawn();

        fakePlayers.add(fakePlayer);
    }

    public static void clear() {
        if (fakePlayers.isEmpty()) return;
        fakePlayers.forEach(FakePlayerEntity::despawn);
        fakePlayers.clear();
    }

    public static List<FakePlayerEntity> getPlayers() {
        return fakePlayers;
    }

    public static int size() {
        return fakePlayers.size();
    }
}

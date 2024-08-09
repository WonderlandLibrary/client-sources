package xyz.mathax.mathaxclient.utils.entity.fakeplayer;

import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.MatHax;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class FakePlayerManager {
    private static final List<FakePlayerEntity> ENTITIES = new ArrayList<>();

    public static FakePlayerEntity get(String name) {
        for (FakePlayerEntity fakePlayer : ENTITIES) {
            if (fakePlayer.getEntityName().equals(name)) {
                return fakePlayer;
            }
        }

        return null;
    }

    public static void add(String name, float health, boolean copyInv) {
        if (!Utils.canUpdate()) {
            return;
        }

        for (FakePlayerEntity existingFakePlayer : ENTITIES) {
            if (!name.equals(existingFakePlayer.getEntityName())) {
                continue;
            }

            remove(existingFakePlayer);
        }

        FakePlayerEntity fakePlayer = new FakePlayerEntity(mc.player, name, health, copyInv);
        fakePlayer.spawn();

        ENTITIES.add(fakePlayer);
    }

    public static void remove(FakePlayerEntity fakePlayer) {
        ENTITIES.removeIf(fakePlayer1 -> {
            if (fakePlayer1.getEntityName().equals(fakePlayer.getEntityName())) {
                fakePlayer.despawn();
                return true;
            }

            return false;
        });
    }

    public static void clear() {
        if (ENTITIES.isEmpty()) {
            return;
        }

        ENTITIES.forEach(FakePlayerEntity::despawn);

        ENTITIES.clear();
    }

    public static void forEach(Consumer<FakePlayerEntity> action) {
        for (FakePlayerEntity fakePlayer : ENTITIES) {
            action.accept(fakePlayer);
        }
    }

    public static boolean empty() {
        return ENTITIES.size() == 0;
    }

    public static int count() {
        return ENTITIES.size();
    }

    public static Stream<FakePlayerEntity> stream() {
        return ENTITIES.stream();
    }

    public static boolean contains(FakePlayerEntity fakePlayer) {
        return ENTITIES.contains(fakePlayer);
    }
}

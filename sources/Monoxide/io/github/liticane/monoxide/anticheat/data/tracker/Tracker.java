package io.github.liticane.monoxide.anticheat.data.tracker;

import net.minecraft.network.Packet;
import io.github.liticane.monoxide.anticheat.data.PlayerData;

public abstract class Tracker {
    private final PlayerData data;

    public Tracker(PlayerData data) {
        this.data = data;
    }

    public abstract void handle(Packet<?> packet);

    public PlayerData getData() {
        return data;
    }
}
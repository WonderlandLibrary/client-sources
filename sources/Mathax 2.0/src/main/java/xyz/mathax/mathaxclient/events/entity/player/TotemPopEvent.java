package xyz.mathax.mathaxclient.events.entity.player;

import net.minecraft.entity.player.PlayerEntity;

public class TotemPopEvent {
    private static final TotemPopEvent INSTANCE = new TotemPopEvent();

    public PlayerEntity player;

    public static TotemPopEvent get(PlayerEntity player) {
        INSTANCE.player = player;
        return INSTANCE;
    }
}

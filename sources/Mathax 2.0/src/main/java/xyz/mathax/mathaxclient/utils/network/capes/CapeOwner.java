package xyz.mathax.mathaxclient.utils.network.capes;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CapeOwner {
    public final UUID uuid;

    public Cape cape;

    public CapeOwner(@NotNull UUID uuid, Cape cape) {
        this.uuid = uuid;
        this.cape = cape;
    }
}

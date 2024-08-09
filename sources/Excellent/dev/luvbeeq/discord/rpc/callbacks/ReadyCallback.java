package dev.luvbeeq.discord.rpc.callbacks;

import com.sun.jna.Callback;
import dev.luvbeeq.discord.rpc.utils.DiscordUser;

public interface ReadyCallback extends Callback {
    void apply(DiscordUser var1);
}
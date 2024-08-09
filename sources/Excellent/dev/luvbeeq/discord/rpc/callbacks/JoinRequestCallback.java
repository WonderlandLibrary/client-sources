package dev.luvbeeq.discord.rpc.callbacks;

import com.sun.jna.Callback;
import dev.luvbeeq.discord.rpc.utils.DiscordUser;

public interface JoinRequestCallback extends Callback {
    void apply(DiscordUser var1);
}
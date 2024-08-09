package dev.luvbeeq.discord.rpc.callbacks;

import com.sun.jna.Callback;

public interface DisconnectedCallback extends Callback {
    void apply(int var1, String var2);
}
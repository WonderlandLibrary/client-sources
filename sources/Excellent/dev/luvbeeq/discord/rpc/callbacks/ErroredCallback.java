package dev.luvbeeq.discord.rpc.callbacks;

import com.sun.jna.Callback;

public interface ErroredCallback extends Callback {
    void apply(int var1, String var2);
}
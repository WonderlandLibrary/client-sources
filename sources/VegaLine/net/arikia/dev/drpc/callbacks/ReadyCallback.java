/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;
import net.arikia.dev.drpc.DiscordUser;

public interface ReadyCallback
extends Callback {
    public void apply(DiscordUser var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 *  org.jetbrains.annotations.Nullable
 */
package net.dev.important.event;

import kotlin.Metadata;
import net.dev.important.event.Event;
import net.minecraft.client.multiplayer.WorldClient;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lnet/dev/important/event/WorldEvent;", "Lnet/dev/important/event/Event;", "worldClient", "Lnet/minecraft/client/multiplayer/WorldClient;", "(Lnet/minecraft/client/multiplayer/WorldClient;)V", "getWorldClient", "()Lnet/minecraft/client/multiplayer/WorldClient;", "LiquidBounce"})
public final class WorldEvent
extends Event {
    @Nullable
    private final WorldClient worldClient;

    public WorldEvent(@Nullable WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    @Nullable
    public final WorldClient getWorldClient() {
        return this.worldClient;
    }
}


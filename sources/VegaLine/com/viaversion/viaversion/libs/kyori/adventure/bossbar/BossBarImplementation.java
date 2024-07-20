/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBarImpl;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBarViewer;
import java.util.Collections;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface BossBarImplementation {
    @ApiStatus.Internal
    @NotNull
    public static <I extends BossBarImplementation> I get(@NotNull BossBar bar, @NotNull Class<I> type2) {
        return BossBarImpl.ImplementationAccessor.get(bar, type2);
    }

    @ApiStatus.Internal
    @NotNull
    default public Iterable<? extends BossBarViewer> viewers() {
        return Collections.emptyList();
    }

    @ApiStatus.Internal
    public static interface Provider {
        @ApiStatus.Internal
        @NotNull
        public BossBarImplementation create(@NotNull BossBar var1);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 */
package com.viaversion.viaversion.libs.kyori.adventure.bossbar;

import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBarImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface BossBarImplementation {
    @ApiStatus.Internal
    @NotNull
    public static <I extends BossBarImplementation> I get(@NotNull BossBar bossBar, @NotNull Class<I> clazz) {
        return BossBarImpl.ImplementationAccessor.get(bossBar, clazz);
    }

    @ApiStatus.Internal
    public static interface Provider {
        @ApiStatus.Internal
        @NotNull
        public BossBarImplementation create(@NotNull BossBar var1);
    }
}


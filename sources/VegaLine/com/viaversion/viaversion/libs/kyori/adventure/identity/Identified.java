/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;

public interface Identified {
    @NotNull
    public Identity identity();
}


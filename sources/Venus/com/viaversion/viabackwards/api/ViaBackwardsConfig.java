/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface ViaBackwardsConfig {
    public boolean addCustomEnchantsToLore();

    public boolean addTeamColorTo1_13Prefix();

    public boolean isFix1_13FacePlayer();

    public boolean fix1_13FormattedInventoryTitle();

    public boolean alwaysShowOriginalMobName();

    public boolean handlePingsAsInvAcknowledgements();

    public @Nullable String chatTypeFormat(String var1);
}


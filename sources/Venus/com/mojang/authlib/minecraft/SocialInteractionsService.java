/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import java.util.UUID;

public interface SocialInteractionsService {
    public boolean serversAllowed();

    public boolean realmsAllowed();

    public boolean chatAllowed();

    public boolean isBlockedPlayer(UUID var1);
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.UUID;

public class OfflineSocialInteractions
implements SocialInteractionsService {
    @Override
    public boolean serversAllowed() {
        return false;
    }

    @Override
    public boolean realmsAllowed() {
        return false;
    }

    @Override
    public boolean chatAllowed() {
        return false;
    }

    @Override
    public boolean isBlockedPlayer(UUID uUID) {
        return true;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.response;

import com.mojang.authlib.yggdrasil.response.Response;
import java.util.Set;
import java.util.UUID;

public class BlockListResponse
extends Response {
    private Set<UUID> blockedProfiles;

    public Set<UUID> getBlockedProfiles() {
        return this.blockedProfiles;
    }
}


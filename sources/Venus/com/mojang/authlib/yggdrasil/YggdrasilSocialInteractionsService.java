/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.mojang.authlib.Environment;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.response.BlockListResponse;
import com.mojang.authlib.yggdrasil.response.PrivilegesResponse;
import java.net.URL;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class YggdrasilSocialInteractionsService
implements SocialInteractionsService {
    private static final long BLOCKLIST_REQUEST_COOLDOWN_SECONDS = 120L;
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    private final URL routePrivileges;
    private final URL routeBlocklist;
    private final YggdrasilAuthenticationService authenticationService;
    private final String accessToken;
    private boolean serversAllowed;
    private boolean realmsAllowed;
    private boolean chatAllowed;
    @Nullable
    private Instant nextAcceptableBlockRequest;
    @Nullable
    private Set<UUID> blockList;

    public YggdrasilSocialInteractionsService(YggdrasilAuthenticationService yggdrasilAuthenticationService, String string, Environment environment) throws AuthenticationException {
        this.authenticationService = yggdrasilAuthenticationService;
        this.accessToken = string;
        this.routePrivileges = HttpAuthenticationService.constantURL(environment.getServicesHost() + "/privileges");
        this.routeBlocklist = HttpAuthenticationService.constantURL(environment.getServicesHost() + "/privacy/blocklist");
        this.checkPrivileges();
    }

    @Override
    public boolean serversAllowed() {
        return this.serversAllowed;
    }

    @Override
    public boolean realmsAllowed() {
        return this.realmsAllowed;
    }

    @Override
    public boolean chatAllowed() {
        return this.chatAllowed;
    }

    @Override
    public boolean isBlockedPlayer(UUID uUID) {
        if (uUID.equals(ZERO_UUID)) {
            return true;
        }
        if (this.blockList == null) {
            this.blockList = this.fetchBlockList();
            if (this.blockList == null) {
                return true;
            }
        }
        return this.blockList.contains(uUID);
    }

    @Nullable
    private Set<UUID> fetchBlockList() {
        if (this.nextAcceptableBlockRequest != null && this.nextAcceptableBlockRequest.isAfter(Instant.now())) {
            return null;
        }
        this.nextAcceptableBlockRequest = Instant.now().plusSeconds(120L);
        try {
            BlockListResponse blockListResponse = this.authenticationService.makeRequest(this.routeBlocklist, null, BlockListResponse.class, "Bearer " + this.accessToken);
            if (blockListResponse == null) {
                return null;
            }
            return blockListResponse.getBlockedProfiles();
        } catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    private void checkPrivileges() throws AuthenticationException {
        PrivilegesResponse privilegesResponse = this.authenticationService.makeRequest(this.routePrivileges, null, PrivilegesResponse.class, "Bearer " + this.accessToken);
        if (privilegesResponse == null) {
            throw new AuthenticationUnavailableException();
        }
        this.chatAllowed = privilegesResponse.getPrivileges().getOnlineChat().isEnabled();
        this.serversAllowed = privilegesResponse.getPrivileges().getMultiplayerServer().isEnabled();
        this.realmsAllowed = privilegesResponse.getPrivileges().getMultiplayerRealms().isEnabled();
    }
}


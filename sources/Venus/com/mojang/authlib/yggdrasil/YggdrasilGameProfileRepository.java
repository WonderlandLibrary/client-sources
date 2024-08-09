/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YggdrasilGameProfileRepository
implements GameProfileRepository {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String searchPageUrl;
    private static final int ENTRIES_PER_PAGE = 2;
    private static final int MAX_FAIL_COUNT = 3;
    private static final int DELAY_BETWEEN_PAGES = 100;
    private static final int DELAY_BETWEEN_FAILURES = 750;
    private final YggdrasilAuthenticationService authenticationService;

    public YggdrasilGameProfileRepository(YggdrasilAuthenticationService yggdrasilAuthenticationService, Environment environment) {
        this.authenticationService = yggdrasilAuthenticationService;
        this.searchPageUrl = environment.getAccountsHost() + "/profiles/";
    }

    @Override
    public void findProfilesByNames(String[] stringArray, Agent agent, ProfileLookupCallback profileLookupCallback) {
        HashSet<String> hashSet = Sets.newHashSet();
        for (String string : stringArray) {
            if (Strings.isNullOrEmpty(string)) continue;
            hashSet.add(string.toLowerCase());
        }
        boolean bl = false;
        for (List list : Iterables.partition(hashSet, 2)) {
            boolean bl2;
            int n = 0;
            do {
                bl2 = false;
                try {
                    ProfileSearchResultsResponse profileSearchResultsResponse = this.authenticationService.makeRequest(HttpAuthenticationService.constantURL(this.searchPageUrl + agent.getName().toLowerCase()), list, ProfileSearchResultsResponse.class);
                    n = 0;
                    LOGGER.debug("Page {} returned {} results, parsing", new Object[]{0, profileSearchResultsResponse.getProfiles().length});
                    HashSet hashSet2 = Sets.newHashSet(list);
                    for (GameProfile gameProfile : profileSearchResultsResponse.getProfiles()) {
                        LOGGER.debug("Successfully looked up profile {}", new Object[]{gameProfile});
                        hashSet2.remove(gameProfile.getName().toLowerCase());
                        profileLookupCallback.onProfileLookupSucceeded(gameProfile);
                    }
                    Object object = hashSet2.iterator();
                    while (object.hasNext()) {
                        String string = (String)object.next();
                        LOGGER.debug("Couldn't find profile {}", new Object[]{string});
                        profileLookupCallback.onProfileLookupFailed(new GameProfile(null, string), new ProfileNotFoundException("Server did not find the requested profile"));
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException interruptedException) {}
                } catch (AuthenticationException authenticationException) {
                    if (++n == 3) {
                        for (Object object : list) {
                            LOGGER.debug("Couldn't find profile {} because of a server error", new Object[]{object});
                            profileLookupCallback.onProfileLookupFailed(new GameProfile(null, (String)object), authenticationException);
                        }
                        continue;
                    }
                    try {
                        Thread.sleep(750L);
                    } catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    bl2 = true;
                }
            } while (bl2);
        }
    }
}


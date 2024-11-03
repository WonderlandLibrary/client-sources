package com.mojang.authlib.yggdrasil;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import java.util.List;
import java.util.Set;
import net.augustus.utils.Logger;

public class YggdrasilGameProfileRepository implements GameProfileRepository {
   private static final Logger LOGGER = new Logger();
   private static final String BASE_URL = "https://api.mojang.com/";
   private static final String SEARCH_PAGE_URL = "https://api.mojang.com/profiles/";
   private static final int ENTRIES_PER_PAGE = 2;
   private static final int MAX_FAIL_COUNT = 3;
   private static final int DELAY_BETWEEN_PAGES = 100;
   private static final int DELAY_BETWEEN_FAILURES = 750;
   private final YggdrasilAuthenticationService authenticationService;

   public YggdrasilGameProfileRepository(YggdrasilAuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
   }

   @Override
   public void findProfilesByNames(String[] names, Agent agent, ProfileLookupCallback callback) {
      Set<String> criteria = Sets.newHashSet();

      for(String name : names) {
         if (!Strings.isNullOrEmpty(name)) {
            criteria.add(name.toLowerCase());
         }
      }

      int page = 0;

      for(List<String> request : Iterables.partition(criteria, 2)) {
         int failCount = 0;

         boolean failed;
         do {
            failed = false;

            try {
               ProfileSearchResultsResponse response = this.authenticationService
                  .makeRequest(
                     HttpAuthenticationService.constantURL("https://api.mojang.com/profiles/" + agent.getName().toLowerCase()),
                     request,
                     ProfileSearchResultsResponse.class
                  );
               failCount = 0;
               LOGGER.debug("Page {} returned {} results, parsing", new Object[]{page, response.getProfiles().length});
               Set<String> missing = Sets.newHashSet(request);

               for(GameProfile profile : response.getProfiles()) {
                  LOGGER.debug("Successfully looked up profile {}", new Object[]{profile});
                  missing.remove(profile.getName().toLowerCase());
                  callback.onProfileLookupSucceeded(profile);
               }

               for(String name : missing) {
                  LOGGER.debug("Couldn't find profile {}", new Object[]{name});
                  callback.onProfileLookupFailed(new GameProfile(null, name), new ProfileNotFoundException("Server did not find the requested profile"));
               }

               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var17) {
               }
            } catch (AuthenticationException var18) {
               AuthenticationException e = var18;
               if (++failCount == 3) {
                  for(String name : request) {
                     LOGGER.debug("Couldn't find profile {} because of a server error", new Object[]{name});
                     callback.onProfileLookupFailed(new GameProfile(null, name), e);
                  }
               } else {
                  try {
                     Thread.sleep(750L);
                  } catch (InterruptedException var16) {
                  }

                  failed = true;
               }
            }
         } while(!failed);
      }
   }
}

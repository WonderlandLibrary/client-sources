package net;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

public class j1 {
   public static void a(ProfileLookupCallback var0, GameProfile var1) {
      var0.onProfileLookupSucceeded(var1);
   }
}

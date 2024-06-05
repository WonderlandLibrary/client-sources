package net.minecraft.server.management;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

final class PlayerProfileCache$2 implements ProfileLookupCallback {
   final GameProfile[] val$agameprofile;

   PlayerProfileCache$2(GameProfile[] var1) {
      this.val$agameprofile = var1;
   }

   public void onProfileLookupSucceeded(GameProfile var1) {
      this.val$agameprofile[0] = var1;
   }

   public void onProfileLookupFailed(GameProfile var1, Exception var2) {
      this.val$agameprofile[0] = null;
   }
}

package net.minecraft.server.management;

import com.mojang.authlib.GameProfile;
import java.util.Date;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.PlayerProfileCache$1;

class PlayerProfileCache$ProfileEntry {
   private final GameProfile gameProfile;
   private final Date expirationDate;
   final PlayerProfileCache this$0;

   private PlayerProfileCache$ProfileEntry(PlayerProfileCache var1, GameProfile var2, Date var3) {
      this.this$0 = var1;
      this.gameProfile = var2;
      this.expirationDate = var3;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public Date getExpirationDate() {
      return this.expirationDate;
   }

   PlayerProfileCache$ProfileEntry(PlayerProfileCache var1, GameProfile var2, Date var3, PlayerProfileCache$1 var4) {
      this(var1, var2, var3);
   }

   static Date access$200(PlayerProfileCache$ProfileEntry var0) {
      return var0.expirationDate;
   }
}

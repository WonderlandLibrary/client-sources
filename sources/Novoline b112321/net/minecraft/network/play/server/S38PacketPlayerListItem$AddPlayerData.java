package net.minecraft.network.play.server;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent$Serializer;
import net.minecraft.world.WorldSettings$GameType;

public class S38PacketPlayerListItem$AddPlayerData {
   private final int ping;
   private final WorldSettings$GameType gamemode;
   private final GameProfile profile;
   private final IChatComponent displayName;
   final S38PacketPlayerListItem this$0;

   public S38PacketPlayerListItem$AddPlayerData(S38PacketPlayerListItem var1, GameProfile var2, int var3, WorldSettings$GameType var4, IChatComponent var5) {
      this.this$0 = var1;
      this.profile = var2;
      this.ping = var3;
      this.gamemode = var4;
      this.displayName = var5;
   }

   public GameProfile getProfile() {
      return this.profile;
   }

   public int getPing() {
      return this.ping;
   }

   public WorldSettings$GameType getGameMode() {
      return this.gamemode;
   }

   public IChatComponent getDisplayName() {
      return this.displayName;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode).add("profile", this.profile).add("displayName", this.displayName == null?null:IChatComponent$Serializer.componentToJson(this.displayName)).toString();
   }
}

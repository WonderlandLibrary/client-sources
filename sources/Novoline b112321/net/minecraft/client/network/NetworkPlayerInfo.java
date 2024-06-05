package net.minecraft.client.network;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.aXg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo$1;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings$GameType;

public class NetworkPlayerInfo {
   private final GameProfile gameProfile;
   private WorldSettings$GameType gameType;
   private int responseTime;
   private boolean playerTexturesLoaded = false;
   private ResourceLocation locationSkin;
   private ResourceLocation locationCape;
   private String skinType;
   private IChatComponent displayName;
   private int g = 0;
   private int j = 0;
   private long i = 0L;
   private long c = 0L;
   private long h = 0L;

   public NetworkPlayerInfo(GameProfile var1) {
      this.gameProfile = var1;
   }

   public NetworkPlayerInfo(aXg var1) {
      this.gameProfile = var1.a();
      this.gameType = var1.d();
      this.responseTime = var1.c();
      this.displayName = var1.b();
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public WorldSettings$GameType getGameType() {
      return this.gameType;
   }

   protected void setGameType(WorldSettings$GameType var1) {
      this.gameType = var1;
   }

   public int getResponseTime() {
      return this.responseTime;
   }

   protected void setResponseTime(int var1) {
      this.responseTime = var1;
   }

   public boolean hasLocationSkin() {
      return this.locationSkin != null;
   }

   public String getSkinType() {
      return this.skinType == null?DefaultPlayerSkin.getSkinType(this.gameProfile.getId()):this.skinType;
   }

   public ResourceLocation getRawLocationSkin() {
      if(this.locationSkin == null) {
         this.loadPlayerTextures();
      }

      return this.locationSkin != null?this.locationSkin:DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId());
   }

   public ResourceLocation getLocationSkin() {
      if(this.locationSkin == null) {
         this.loadPlayerTextures();
      }

      return (ResourceLocation)Objects.firstNonNull(this.locationSkin, DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
   }

   public ResourceLocation getLocationCape() {
      if(this.locationCape == null) {
         this.loadPlayerTextures();
      }

      return this.locationCape;
   }

   public ScorePlayerTeam getPlayerTeam() {
      return Minecraft.getInstance().world.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
   }

   protected void loadPlayerTextures() {
      // $FF: Couldn't be decompiled
   }

   public IChatComponent getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(IChatComponent var1) {
      this.displayName = var1;
   }

   public int h() {
      return this.g;
   }

   public void b(int var1) {
      this.g = var1;
   }

   public int e() {
      return this.j;
   }

   public void c(int var1) {
      this.j = var1;
   }

   public long g() {
      return this.i;
   }

   public void c(long var1) {
      this.i = var1;
   }

   public long o() {
      return this.c;
   }

   public void a(long var1) {
      this.c = var1;
   }

   public long k() {
      return this.h;
   }

   public void b(long var1) {
      this.h = var1;
   }

   private void lambda$loadPlayerTextures$0(Type var1, ResourceLocation var2, MinecraftProfileTexture var3) {
      switch(NetworkPlayerInfo$1.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[var1.ordinal()]) {
      case 1:
         this.locationSkin = var2;
         this.skinType = var3.getMetadata("model");
         if(this.skinType == null) {
            this.skinType = "default";
         }
         break;
      case 2:
         this.locationCape = var2;
      }

   }
}

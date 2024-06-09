/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.client.resources.SkinManager;
/*     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkPlayerInfo
/*     */ {
/*     */   private final GameProfile gameProfile;
/*     */   private WorldSettings.GameType gameType;
/*     */   private int responseTime;
/*     */   private boolean playerTexturesLoaded = false;
/*     */   private ResourceLocation locationSkin;
/*     */   private ResourceLocation locationCape;
/*     */   private String skinType;
/*     */   private IChatComponent displayName;
/*  37 */   private int field_178873_i = 0;
/*  38 */   private int field_178870_j = 0;
/*  39 */   private long field_178871_k = 0L;
/*  40 */   private long field_178868_l = 0L;
/*  41 */   private long field_178869_m = 0L;
/*     */ 
/*     */   
/*     */   public NetworkPlayerInfo(GameProfile p_i46294_1_) {
/*  45 */     this.gameProfile = p_i46294_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData p_i46295_1_) {
/*  50 */     this.gameProfile = p_i46295_1_.getProfile();
/*  51 */     this.gameType = p_i46295_1_.getGameMode();
/*  52 */     this.responseTime = p_i46295_1_.getPing();
/*  53 */     this.displayName = p_i46295_1_.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfile() {
/*  61 */     return this.gameProfile;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/*  66 */     return this.gameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResponseTime() {
/*  71 */     return this.responseTime;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setGameType(WorldSettings.GameType p_178839_1_) {
/*  76 */     this.gameType = p_178839_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setResponseTime(int p_178838_1_) {
/*  81 */     this.responseTime = p_178838_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasLocationSkin() {
/*  86 */     return (this.locationSkin != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSkinType() {
/*  91 */     return (this.skinType == null) ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  96 */     if (this.locationSkin == null)
/*     */     {
/*  98 */       loadPlayerTextures();
/*     */     }
/*     */     
/* 101 */     return (ResourceLocation)Objects.firstNonNull(this.locationSkin, DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationCape() {
/* 106 */     if (this.locationCape == null)
/*     */     {
/* 108 */       loadPlayerTextures();
/*     */     }
/*     */     
/* 111 */     return this.locationCape;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getPlayerTeam() {
/* 116 */     return (Minecraft.getMinecraft()).theWorld.getScoreboard().getPlayersTeam(getGameProfile().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadPlayerTextures() {
/* 121 */     synchronized (this) {
/*     */       
/* 123 */       if (!this.playerTexturesLoaded) {
/*     */         
/* 125 */         this.playerTexturesLoaded = true;
/*     */         
/* 127 */         Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, new SkinManager.SkinAvailableCallback()
/*     */             {
/*     */               public void skinAvailable(MinecraftProfileTexture.Type p_180521_1_, ResourceLocation location, MinecraftProfileTexture profileTexture)
/*     */               {
/* 131 */                 switch (p_180521_1_) {
/*     */                   
/*     */                   case SKIN:
/* 134 */                     NetworkPlayerInfo.this.locationSkin = location;
/* 135 */                     NetworkPlayerInfo.this.skinType = profileTexture.getMetadata("model");
/*     */                     
/* 137 */                     if (NetworkPlayerInfo.this.skinType == null)
/*     */                     {
/* 139 */                       NetworkPlayerInfo.this.skinType = "default";
/*     */                     }
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case null:
/* 145 */                     NetworkPlayerInfo.this.locationCape = location;
/*     */                     break;
/*     */                 }  }
/* 148 */             },  true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayName(IChatComponent displayNameIn) {
/* 155 */     this.displayName = displayNameIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 160 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_178835_l() {
/* 165 */     return this.field_178873_i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178836_b(int p_178836_1_) {
/* 170 */     this.field_178873_i = p_178836_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_178860_m() {
/* 175 */     return this.field_178870_j;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178857_c(int p_178857_1_) {
/* 180 */     this.field_178870_j = p_178857_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long func_178847_n() {
/* 185 */     return this.field_178871_k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178846_a(long p_178846_1_) {
/* 190 */     this.field_178871_k = p_178846_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long func_178858_o() {
/* 195 */     return this.field_178868_l;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178844_b(long p_178844_1_) {
/* 200 */     this.field_178868_l = p_178844_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long func_178855_p() {
/* 205 */     return this.field_178869_m;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178843_c(long p_178843_1_) {
/* 210 */     this.field_178869_m = p_178843_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\network\NetworkPlayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
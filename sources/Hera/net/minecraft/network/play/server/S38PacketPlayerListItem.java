/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class S38PacketPlayerListItem implements Packet<INetHandlerPlayClient> {
/*     */   private Action action;
/*  19 */   private final List<AddPlayerData> players = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S38PacketPlayerListItem(Action actionIn, EntityPlayerMP... players) {
/*  27 */     this.action = actionIn; byte b; int i;
/*     */     EntityPlayerMP[] arrayOfEntityPlayerMP;
/*  29 */     for (i = (arrayOfEntityPlayerMP = players).length, b = 0; b < i; ) { EntityPlayerMP entityplayermp = arrayOfEntityPlayerMP[b];
/*     */       
/*  31 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public S38PacketPlayerListItem(Action actionIn, Iterable<EntityPlayerMP> players) {
/*  37 */     this.action = actionIn;
/*     */     
/*  39 */     for (EntityPlayerMP entityplayermp : players)
/*     */     {
/*  41 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  50 */     this.action = (Action)buf.readEnumValue(Action.class);
/*  51 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  53 */     for (int j = 0; j < i; j++) {
/*     */       int l, i1;
/*  55 */       GameProfile gameprofile = null;
/*  56 */       int k = 0;
/*  57 */       WorldSettings.GameType worldsettings$gametype = null;
/*  58 */       IChatComponent ichatcomponent = null;
/*     */       
/*  60 */       switch (this.action) {
/*     */         
/*     */         case null:
/*  63 */           gameprofile = new GameProfile(buf.readUuid(), buf.readStringFromBuffer(16));
/*  64 */           l = buf.readVarIntFromBuffer();
/*  65 */           i1 = 0;
/*     */           
/*  67 */           for (; i1 < l; i1++) {
/*     */             
/*  69 */             String s = buf.readStringFromBuffer(32767);
/*  70 */             String s1 = buf.readStringFromBuffer(32767);
/*     */             
/*  72 */             if (buf.readBoolean()) {
/*     */               
/*  74 */               gameprofile.getProperties().put(s, new Property(s, s1, buf.readStringFromBuffer(32767)));
/*     */             }
/*     */             else {
/*     */               
/*  78 */               gameprofile.getProperties().put(s, new Property(s, s1));
/*     */             } 
/*     */           } 
/*     */           
/*  82 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*  83 */           k = buf.readVarIntFromBuffer();
/*     */           
/*  85 */           if (buf.readBoolean())
/*     */           {
/*  87 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/*  93 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*  94 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*     */           break;
/*     */         
/*     */         case UPDATE_LATENCY:
/*  98 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*  99 */           k = buf.readVarIntFromBuffer();
/*     */           break;
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 103 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           
/* 105 */           if (buf.readBoolean())
/*     */           {
/* 107 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 113 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           break;
/*     */       } 
/* 116 */       this.players.add(new AddPlayerData(gameprofile, k, worldsettings$gametype, ichatcomponent));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 125 */     buf.writeEnumValue(this.action);
/* 126 */     buf.writeVarIntToBuffer(this.players.size());
/*     */     
/* 128 */     for (AddPlayerData s38packetplayerlistitem$addplayerdata : this.players) {
/*     */       
/* 130 */       switch (this.action) {
/*     */         
/*     */         case null:
/* 133 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 134 */           buf.writeString(s38packetplayerlistitem$addplayerdata.getProfile().getName());
/* 135 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getProfile().getProperties().size());
/*     */           
/* 137 */           for (Property property : s38packetplayerlistitem$addplayerdata.getProfile().getProperties().values()) {
/*     */             
/* 139 */             buf.writeString(property.getName());
/* 140 */             buf.writeString(property.getValue());
/*     */             
/* 142 */             if (property.hasSignature()) {
/*     */               
/* 144 */               buf.writeBoolean(true);
/* 145 */               buf.writeString(property.getSignature());
/*     */               
/*     */               continue;
/*     */             } 
/* 149 */             buf.writeBoolean(false);
/*     */           } 
/*     */ 
/*     */           
/* 153 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/* 154 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */           
/* 156 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 158 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 162 */           buf.writeBoolean(true);
/* 163 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/* 169 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 170 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/*     */ 
/*     */         
/*     */         case UPDATE_LATENCY:
/* 174 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 175 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */ 
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 179 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */           
/* 181 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 183 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 187 */           buf.writeBoolean(true);
/* 188 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 194 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 204 */     handler.handlePlayerListItem(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AddPlayerData> func_179767_a() {
/* 209 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public Action func_179768_b() {
/* 214 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 219 */     return Objects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
/*     */   }
/*     */   
/*     */   public S38PacketPlayerListItem() {}
/*     */   
/* 224 */   public enum Action { ADD_PLAYER,
/* 225 */     UPDATE_GAME_MODE,
/* 226 */     UPDATE_LATENCY,
/* 227 */     UPDATE_DISPLAY_NAME,
/* 228 */     REMOVE_PLAYER; }
/*     */ 
/*     */ 
/*     */   
/*     */   public class AddPlayerData
/*     */   {
/*     */     private final int ping;
/*     */     private final WorldSettings.GameType gamemode;
/*     */     private final GameProfile profile;
/*     */     private final IChatComponent displayName;
/*     */     
/*     */     public AddPlayerData(GameProfile profile, int pingIn, WorldSettings.GameType gamemodeIn, IChatComponent displayNameIn) {
/* 240 */       this.profile = profile;
/* 241 */       this.ping = pingIn;
/* 242 */       this.gamemode = gamemodeIn;
/* 243 */       this.displayName = displayNameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getProfile() {
/* 248 */       return this.profile;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPing() {
/* 253 */       return this.ping;
/*     */     }
/*     */ 
/*     */     
/*     */     public WorldSettings.GameType getGameMode() {
/* 258 */       return this.gamemode;
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent getDisplayName() {
/* 263 */       return this.displayName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 268 */       return Objects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode).add("profile", this.profile).add("displayName", (this.displayName == null) ? null : IChatComponent.Serializer.componentToJson(this.displayName)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S38PacketPlayerListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
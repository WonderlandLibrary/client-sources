/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class Session
/*    */ {
/*    */   private final String username;
/*    */   private final String playerID;
/*    */   private final String token;
/*    */   private final Type sessionType;
/*    */   
/*    */   public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
/* 18 */     this.username = usernameIn;
/* 19 */     this.playerID = playerIDIn;
/* 20 */     this.token = tokenIn;
/* 21 */     this.sessionType = Type.setSessionType(sessionTypeIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSessionID() {
/* 26 */     return "token:" + this.token + ":" + this.playerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPlayerID() {
/* 31 */     return this.playerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUsername() {
/* 36 */     return this.username;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getToken() {
/* 41 */     return this.token;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getProfile() {
/*    */     try {
/* 48 */       UUID uuid = UUIDTypeAdapter.fromString(getPlayerID());
/* 49 */       return new GameProfile(uuid, getUsername());
/*    */     }
/* 51 */     catch (IllegalArgumentException var2) {
/*    */       
/* 53 */       return new GameProfile(null, getUsername());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getSessionType() {
/* 62 */     return this.sessionType;
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 67 */     LEGACY("legacy"),
/* 68 */     MOJANG("mojang");
/*    */     
/* 70 */     private static final Map<String, Type> SESSION_TYPES = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String sessionType;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       Type[] arrayOfType;
/* 84 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type session$type = arrayOfType[b];
/*    */         
/* 86 */         SESSION_TYPES.put(session$type.sessionType, session$type);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     Type(String sessionTypeIn) {
/*    */       this.sessionType = sessionTypeIn;
/*    */     }
/*    */     
/*    */     public static Type setSessionType(String sessionTypeIn) {
/*    */       return SESSION_TYPES.get(sessionTypeIn.toLowerCase());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
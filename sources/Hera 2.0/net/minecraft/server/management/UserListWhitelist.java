/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListWhitelist
/*    */   extends UserList<GameProfile, UserListWhitelistEntry>
/*    */ {
/*    */   public UserListWhitelist(File p_i1132_1_) {
/* 11 */     super(p_i1132_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 16 */     return new UserListWhitelistEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 21 */     String[] astring = new String[getValues().size()];
/* 22 */     int i = 0;
/*    */     
/* 24 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values())
/*    */     {
/* 26 */       astring[i++] = userlistwhitelistentry.getValue().getName();
/*    */     }
/*    */     
/* 29 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 37 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile func_152706_a(String p_152706_1_) {
/* 42 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values()) {
/*    */       
/* 44 */       if (p_152706_1_.equalsIgnoreCase(userlistwhitelistentry.getValue().getName()))
/*    */       {
/* 46 */         return userlistwhitelistentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\management\UserListWhitelist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
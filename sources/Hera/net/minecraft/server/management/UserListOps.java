/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListOps
/*    */   extends UserList<GameProfile, UserListOpsEntry>
/*    */ {
/*    */   public UserListOps(File saveFile) {
/* 11 */     super(saveFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 16 */     return new UserListOpsEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 21 */     String[] astring = new String[getValues().size()];
/* 22 */     int i = 0;
/*    */     
/* 24 */     for (UserListOpsEntry userlistopsentry : getValues().values())
/*    */     {
/* 26 */       astring[i++] = userlistopsentry.getValue().getName();
/*    */     }
/*    */     
/* 29 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_183026_b(GameProfile p_183026_1_) {
/* 34 */     UserListOpsEntry userlistopsentry = getEntry(p_183026_1_);
/* 35 */     return (userlistopsentry != null) ? userlistopsentry.func_183024_b() : false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 43 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getGameProfileFromName(String username) {
/* 51 */     for (UserListOpsEntry userlistopsentry : getValues().values()) {
/*    */       
/* 53 */       if (username.equalsIgnoreCase(userlistopsentry.getValue().getName()))
/*    */       {
/* 55 */         return userlistopsentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\management\UserListOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
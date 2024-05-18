/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class DefaultPlayerSkin
/*    */ {
/*  9 */   private static final ResourceLocation TEXTURE_STEVE = new ResourceLocation("textures/entity/steve.png");
/*    */ 
/*    */   
/* 12 */   private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceLocation getDefaultSkinLegacy() {
/* 19 */     return TEXTURE_STEVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceLocation getDefaultSkin(UUID playerUUID) {
/* 27 */     return isSlimSkin(playerUUID) ? TEXTURE_ALEX : TEXTURE_STEVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSkinType(UUID playerUUID) {
/* 35 */     return isSlimSkin(playerUUID) ? "slim" : "default";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isSlimSkin(UUID playerUUID) {
/* 43 */     return ((playerUUID.hashCode() & 0x1) == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\DefaultPlayerSkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
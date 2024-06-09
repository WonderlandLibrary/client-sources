/*    */ package me.eagler.utils;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class PlayerUtils {
/*  9 */   public static Minecraft mc = Minecraft.getMinecraft();
/*    */ 
/*    */   
/*    */   public static void sendMessage(String message, boolean prefix) {
/* 13 */     String msg = message;
/*    */     
/* 15 */     if (prefix)
/*    */     {
/* 17 */       msg = String.valueOf(Client.instance.getPrefix()) + message;
/*    */     }
/*    */ 
/*    */     
/* 21 */     mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(msg));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean playeriswalking() {
/* 27 */     if (mc.gameSettings.keyBindForward.pressed)
/*    */     {
/* 29 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 33 */     if (mc.gameSettings.keyBindBack.pressed)
/*    */     {
/* 35 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 39 */     if (mc.gameSettings.keyBindLeft.pressed)
/*    */     {
/* 41 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 45 */     if (mc.gameSettings.keyBindRight.pressed)
/*    */     {
/* 47 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagle\\utils\PlayerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
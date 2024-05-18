/*    */ package me.eagler.command;
/*    */ 
/*    */ import me.eagler.file.ConfigLoader;
/*    */ import me.eagler.utils.PlayerUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public abstract class Command
/*    */ {
/*  9 */   public static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/* 11 */   public ConfigLoader configLoader = new ConfigLoader();
/*    */ 
/*    */   
/*    */   public abstract String getCommand();
/*    */   
/*    */   public abstract String getHelp();
/*    */   
/*    */   public abstract String getUsage();
/*    */   
/*    */   public abstract void onCommand(String paramString, String[] paramArrayOfString) throws Exception;
/*    */   
/*    */   public void sendUsage() {
/* 23 */     PlayerUtils.sendMessage("§c" + getUsage(), true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {
/* 29 */     PlayerUtils.sendMessage(message, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
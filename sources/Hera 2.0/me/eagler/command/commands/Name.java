/*    */ package me.eagler.command.commands;
/*    */ 
/*    */ import me.eagler.command.Command;
/*    */ import me.eagler.gui.GuiChangeName;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Name
/*    */   extends Command
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "name";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHelp() {
/* 20 */     return "Changes your name";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsage() {
/* 27 */     return ".name / .name NAME";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCommand(String message, String[] args) throws Exception {
/*    */     try {
/* 36 */       if (args.length == 1)
/*    */       {
/* 38 */         String prename = args[0];
/*    */         
/* 40 */         mc.displayGuiScreen((GuiScreen)new GuiChangeName(null, true, prename));
/*    */       }
/* 42 */       else if (args.length == 0)
/*    */       {
/* 44 */         mc.displayGuiScreen((GuiScreen)new GuiChangeName(null, true));
/*    */       }
/*    */       else
/*    */       {
/* 48 */         sendUsage();
/*    */       }
/*    */     
/*    */     }
/* 52 */     catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\commands\Name.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
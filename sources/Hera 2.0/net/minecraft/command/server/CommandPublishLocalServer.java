/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPublishLocalServer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "publish";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 24 */     return "commands.publish.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 32 */     String s = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
/*    */     
/* 34 */     if (s != null) {
/*    */       
/* 36 */       notifyOperators(sender, (ICommand)this, "commands.publish.started", new Object[] { s });
/*    */     }
/*    */     else {
/*    */       
/* 40 */       notifyOperators(sender, (ICommand)this, "commands.publish.failed", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandPublishLocalServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
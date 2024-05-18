/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandStop
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "stop";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 23 */     return "commands.stop.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 31 */     if ((MinecraftServer.getServer()).worldServers != null)
/*    */     {
/* 33 */       notifyOperators(sender, (ICommand)this, "commands.stop.start", new Object[0]);
/*    */     }
/*    */     
/* 36 */     MinecraftServer.getServer().initiateShutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandStop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
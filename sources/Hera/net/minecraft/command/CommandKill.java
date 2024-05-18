/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandKill
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "kill";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 32 */     return "commands.kill.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 40 */     if (args.length == 0) {
/*    */       
/* 42 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/* 43 */       entityPlayerMP.onKillCommand();
/* 44 */       notifyOperators(sender, this, "commands.kill.successful", new Object[] { entityPlayerMP.getDisplayName() });
/*    */     }
/*    */     else {
/*    */       
/* 48 */       Entity entity = func_175768_b(sender, args[0]);
/* 49 */       entity.onKillCommand();
/* 50 */       notifyOperators(sender, this, "commands.kill.successful", new Object[] { entity.getDisplayName() });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 59 */     return (index == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 64 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\CommandKill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
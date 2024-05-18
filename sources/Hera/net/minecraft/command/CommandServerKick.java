/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandServerKick
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "kick";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 23 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 31 */     return "commands.kick.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 39 */     if (args.length > 0 && args[0].length() > 1) {
/*    */       
/* 41 */       EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/* 42 */       String s = "Kicked by an operator.";
/* 43 */       boolean flag = false;
/*    */       
/* 45 */       if (entityplayermp == null)
/*    */       {
/* 47 */         throw new PlayerNotFoundException();
/*    */       }
/*    */ 
/*    */       
/* 51 */       if (args.length >= 2) {
/*    */         
/* 53 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/* 54 */         flag = true;
/*    */       } 
/*    */       
/* 57 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer(s);
/*    */       
/* 59 */       if (flag)
/*    */       {
/* 61 */         notifyOperators(sender, this, "commands.kick.success.reason", new Object[] { entityplayermp.getName(), s });
/*    */       }
/*    */       else
/*    */       {
/* 65 */         notifyOperators(sender, this, "commands.kick.success", new Object[] { entityplayermp.getName() });
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 71 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 77 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\CommandServerKick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
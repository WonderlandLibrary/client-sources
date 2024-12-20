/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.UserListBansEntry;
/*    */ import net.minecraft.server.management.UserListEntry;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanPlayer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "ban";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 30 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 38 */     return "commands.ban.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 46 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 54 */     if (args.length >= 1 && args[0].length() > 0) {
/*    */       
/* 56 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 57 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 59 */       if (gameprofile == null)
/*    */       {
/* 61 */         throw new CommandException("commands.ban.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 65 */       String s = null;
/*    */       
/* 67 */       if (args.length >= 2)
/*    */       {
/* 69 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/*    */       }
/*    */       
/* 72 */       UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, null, sender.getName(), null, s);
/* 73 */       minecraftserver.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/* 74 */       EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().getPlayerByUsername(args[0]);
/*    */       
/* 76 */       if (entityplayermp != null)
/*    */       {
/* 78 */         entityplayermp.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
/*    */       }
/*    */       
/* 81 */       notifyOperators(sender, (ICommand)this, "commands.ban.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 86 */       throw new WrongUsageException("commands.ban.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 92 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandBanPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandEmote
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 21 */     return "me";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 37 */     return "commands.me.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length <= 0)
/*    */     {
/* 47 */       throw new WrongUsageException("commands.me.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 51 */     IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 0, !(sender instanceof net.minecraft.entity.player.EntityPlayer));
/* 52 */     MinecraftServer.getServer().getConfigurationManager().sendChatMsg((IChatComponent)new ChatComponentTranslation("chat.type.emote", new Object[] { sender.getDisplayName(), ichatcomponent }));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 58 */     return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandEmote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
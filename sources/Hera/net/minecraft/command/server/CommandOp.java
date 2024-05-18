/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 20 */     return "op";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 28 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 36 */     return "commands.op.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 44 */     if (args.length == 1 && args[0].length() > 0) {
/*    */       
/* 46 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 47 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 49 */       if (gameprofile == null)
/*    */       {
/* 51 */         throw new CommandException("commands.op.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 55 */       minecraftserver.getConfigurationManager().addOp(gameprofile);
/* 56 */       notifyOperators(sender, (ICommand)this, "commands.op.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 61 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 67 */     if (args.length == 1) {
/*    */       
/* 69 */       String s = args[args.length - 1];
/* 70 */       List<String> list = Lists.newArrayList(); byte b; int i;
/*    */       GameProfile[] arrayOfGameProfile;
/* 72 */       for (i = (arrayOfGameProfile = MinecraftServer.getServer().getGameProfiles()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*    */         
/* 74 */         if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName()))
/*    */         {
/* 76 */           list.add(gameprofile.getName());
/*    */         }
/*    */         b++; }
/*    */       
/* 80 */       return list;
/*    */     } 
/*    */ 
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
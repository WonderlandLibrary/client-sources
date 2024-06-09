/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "save-all";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 27 */     return "commands.save.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 35 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 36 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.start", new Object[0]));
/*    */     
/* 38 */     if (minecraftserver.getConfigurationManager() != null)
/*    */     {
/* 40 */       minecraftserver.getConfigurationManager().saveAllPlayerData();
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 45 */       for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/*    */         
/* 47 */         if (minecraftserver.worldServers[i] != null) {
/*    */           
/* 49 */           WorldServer worldserver = minecraftserver.worldServers[i];
/* 50 */           boolean flag = worldserver.disableLevelSaving;
/* 51 */           worldserver.disableLevelSaving = false;
/* 52 */           worldserver.saveAllChunks(true, null);
/* 53 */           worldserver.disableLevelSaving = flag;
/*    */         } 
/*    */       } 
/*    */       
/* 57 */       if (args.length > 0 && "flush".equals(args[0]))
/*    */       {
/* 59 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
/*    */         
/* 61 */         for (int j = 0; j < minecraftserver.worldServers.length; j++) {
/*    */           
/* 63 */           if (minecraftserver.worldServers[j] != null) {
/*    */             
/* 65 */             WorldServer worldserver1 = minecraftserver.worldServers[j];
/* 66 */             boolean flag1 = worldserver1.disableLevelSaving;
/* 67 */             worldserver1.disableLevelSaving = false;
/* 68 */             worldserver1.saveChunkData();
/* 69 */             worldserver1.disableLevelSaving = flag1;
/*    */           } 
/*    */         } 
/*    */         
/* 73 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
/*    */       }
/*    */     
/* 76 */     } catch (MinecraftException minecraftexception) {
/*    */       
/* 78 */       notifyOperators(sender, (ICommand)this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 82 */     notifyOperators(sender, (ICommand)this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\server\CommandSaveAll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
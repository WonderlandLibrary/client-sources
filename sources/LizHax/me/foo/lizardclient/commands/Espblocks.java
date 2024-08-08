/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class Espblocks
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 11 */     return "espblocks";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 16 */     return "Add, remove, or see the list of blocks for xray and esp";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 21 */     return ".espblocks add <block> | .espblocks remove <blocks> | .espblocks list";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 26 */     if (args[0].equalsIgnoreCase("add")) {
/* 27 */       Client.espblocks.add(Block.getBlockFromName(args[1].toLowerCase()));
/* 28 */       Client.addChatMessage("Added §b" + Block.getBlockFromName(args[1].toLowerCase()).getLocalizedName() + " to ESP blocks list.");
/* 29 */     } else if (args[0].equalsIgnoreCase("remove")) {
/* 30 */       Client.espblocks.remove(Block.getBlockFromName(args[1].toLowerCase()));
/* 31 */       Client.addChatMessage("Removed §b" + Block.getBlockFromName(args[1].toLowerCase()).getLocalizedName() + " from ESP blocks list.");
/* 32 */     } else if (args[0].equalsIgnoreCase("list")) {
/* 33 */       for (Block b : Client.espblocks)
/* 34 */         Client.addChatMessage(b.getLocalizedName()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\commands\Espblocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
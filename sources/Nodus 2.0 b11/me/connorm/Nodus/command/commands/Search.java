/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.command.NodusCommand;
/*  6:   */ import me.connorm.Nodus.file.NodusFileManager;
/*  7:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ 
/* 10:   */ public class Search
/* 11:   */   extends NodusCommand
/* 12:   */ {
/* 13:   */   public Search()
/* 14:   */   {
/* 15:12 */     super("search");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:20 */       if (commandArgs[0].equalsIgnoreCase("add"))
/* 23:   */       {
/* 24:22 */         Block searchBlock = Block.getBlockById(Integer.parseInt(commandArgs[1]));
/* 25:23 */         if (Nodus.theNodus.moduleManager.searchModule.isSearchBlock(searchBlock.getLocalizedName()))
/* 26:   */         {
/* 27:25 */           Nodus.theNodus.addChatMessage("Block " + searchBlock.getLocalizedName() + " is already in the search.");
/* 28:   */         }
/* 29:   */         else
/* 30:   */         {
/* 31:28 */           Nodus.theNodus.moduleManager.searchModule.searchBlocks.add(searchBlock.getLocalizedName());
/* 32:29 */           Nodus.theNodus.addChatMessage("Added block " + searchBlock.getLocalizedName() + " to the search");
/* 33:30 */           Nodus.theNodus.fileManager.saveSearch();
/* 34:   */         }
/* 35:   */       }
/* 36:33 */       if (commandArgs[0].equalsIgnoreCase("del"))
/* 37:   */       {
/* 38:35 */         Block searchBlock = Block.getBlockById(Integer.parseInt(commandArgs[1]));
/* 39:36 */         if (!Nodus.theNodus.moduleManager.searchModule.isSearchBlock(searchBlock.getLocalizedName()))
/* 40:   */         {
/* 41:38 */           Nodus.theNodus.addChatMessage("Block " + searchBlock.getLocalizedName() + " is not in the search.");
/* 42:   */         }
/* 43:   */         else
/* 44:   */         {
/* 45:41 */           Nodus.theNodus.moduleManager.searchModule.searchBlocks.remove(searchBlock);
/* 46:42 */           Nodus.theNodus.addChatMessage("Removed block " + searchBlock.getLocalizedName() + " from the search");
/* 47:43 */           Nodus.theNodus.fileManager.saveSearch();
/* 48:   */         }
/* 49:   */       }
/* 50:   */     }
/* 51:   */     catch (Exception commandSyntaxException)
/* 52:   */     {
/* 53:48 */       commandSyntaxException.printStackTrace();
/* 54:49 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String getDescription()
/* 59:   */   {
/* 60:56 */     return "Adds blocks to the search.";
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String getSyntax()
/* 64:   */   {
/* 65:62 */     return "add/del <id>";
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getConsoleDisplay()
/* 69:   */   {
/* 70:68 */     return "search";
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Search
 * JD-Core Version:    0.7.0.1
 */
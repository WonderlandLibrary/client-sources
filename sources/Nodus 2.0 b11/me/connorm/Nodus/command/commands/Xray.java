/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.command.NodusCommand;
/*  6:   */ import me.connorm.Nodus.file.NodusFileManager;
/*  7:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  8:   */ import me.connorm.Nodus.module.modules.utils.XrayUtils;
/*  9:   */ import net.minecraft.block.Block;
/* 10:   */ import net.minecraft.client.Minecraft;
/* 11:   */ import net.minecraft.client.renderer.RenderGlobal;
/* 12:   */ 
/* 13:   */ public class Xray
/* 14:   */   extends NodusCommand
/* 15:   */ {
/* 16:   */   public Xray()
/* 17:   */   {
/* 18:12 */     super("xray");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 22:   */   {
/* 23:   */     try
/* 24:   */     {
/* 25:20 */       if (commandArgs[0].equalsIgnoreCase("add"))
/* 26:   */       {
/* 27:22 */         Block xrayBlock = Block.getBlockById(Integer.parseInt(commandArgs[1]));
/* 28:23 */         if (Nodus.theNodus.moduleManager.xrayModule.isXrayBlock(xrayBlock.getLocalizedName()))
/* 29:   */         {
/* 30:25 */           Nodus.theNodus.addChatMessage("Block " + xrayBlock.getLocalizedName() + " is already in the xray.");
/* 31:   */         }
/* 32:   */         else
/* 33:   */         {
/* 34:28 */           Nodus.theNodus.moduleManager.xrayModule.xrayBlocks.add(xrayBlock.getLocalizedName());
/* 35:29 */           Nodus.theNodus.addChatMessage("Added block " + xrayBlock.getLocalizedName() + " to the xray");
/* 36:30 */           Nodus.theNodus.fileManager.saveXray();
/* 37:31 */           if (XrayUtils.xrayEnabled) {
/* 38:33 */             Nodus.theNodus.getMinecraft().renderGlobal.loadRenderers();
/* 39:   */           }
/* 40:   */         }
/* 41:   */       }
/* 42:37 */       if (commandArgs[0].equalsIgnoreCase("del"))
/* 43:   */       {
/* 44:39 */         Block xrayBlock = Block.getBlockById(Integer.parseInt(commandArgs[1]));
/* 45:40 */         if (!Nodus.theNodus.moduleManager.xrayModule.isXrayBlock(xrayBlock.getLocalizedName()))
/* 46:   */         {
/* 47:42 */           Nodus.theNodus.addChatMessage("Block " + xrayBlock.getLocalizedName() + " is not in the xray.");
/* 48:   */         }
/* 49:   */         else
/* 50:   */         {
/* 51:45 */           Nodus.theNodus.moduleManager.xrayModule.xrayBlocks.remove(xrayBlock);
/* 52:46 */           Nodus.theNodus.addChatMessage("Removed block " + xrayBlock.getLocalizedName() + " from the xray");
/* 53:47 */           Nodus.theNodus.fileManager.saveXray();
/* 54:48 */           if (XrayUtils.xrayEnabled) {
/* 55:50 */             Nodus.theNodus.getMinecraft().renderGlobal.loadRenderers();
/* 56:   */           }
/* 57:   */         }
/* 58:   */       }
/* 59:   */     }
/* 60:   */     catch (Exception commandSyntaxException)
/* 61:   */     {
/* 62:56 */       commandSyntaxException.printStackTrace();
/* 63:57 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String getDescription()
/* 68:   */   {
/* 69:64 */     return "Adds blocks to the xray.";
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String getSyntax()
/* 73:   */   {
/* 74:70 */     return "add/del <id>";
/* 75:   */   }
/* 76:   */   
/* 77:   */   public String getConsoleDisplay()
/* 78:   */   {
/* 79:76 */     return "xray";
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Xray
 * JD-Core Version:    0.7.0.1
 */
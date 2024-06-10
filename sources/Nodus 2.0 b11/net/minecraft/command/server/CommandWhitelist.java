/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Set;
/*   6:    */ import net.minecraft.command.CommandBase;
/*   7:    */ import net.minecraft.command.ICommandSender;
/*   8:    */ import net.minecraft.command.WrongUsageException;
/*   9:    */ import net.minecraft.server.MinecraftServer;
/*  10:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  11:    */ import net.minecraft.util.ChatComponentText;
/*  12:    */ import net.minecraft.util.ChatComponentTranslation;
/*  13:    */ 
/*  14:    */ public class CommandWhitelist
/*  15:    */   extends CommandBase
/*  16:    */ {
/*  17:    */   private static final String __OBFID = "CL_00001186";
/*  18:    */   
/*  19:    */   public String getCommandName()
/*  20:    */   {
/*  21: 19 */     return "whitelist";
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getRequiredPermissionLevel()
/*  25:    */   {
/*  26: 27 */     return 3;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  30:    */   {
/*  31: 32 */     return "commands.whitelist.usage";
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  35:    */   {
/*  36: 37 */     if (par2ArrayOfStr.length >= 1)
/*  37:    */     {
/*  38: 39 */       if (par2ArrayOfStr[0].equals("on"))
/*  39:    */       {
/*  40: 41 */         MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
/*  41: 42 */         notifyAdmins(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
/*  42: 43 */         return;
/*  43:    */       }
/*  44: 46 */       if (par2ArrayOfStr[0].equals("off"))
/*  45:    */       {
/*  46: 48 */         MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
/*  47: 49 */         notifyAdmins(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
/*  48: 50 */         return;
/*  49:    */       }
/*  50: 53 */       if (par2ArrayOfStr[0].equals("list"))
/*  51:    */       {
/*  52: 55 */         par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.whitelist.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().size()), Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat().length) }));
/*  53: 56 */         Set var3 = MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers();
/*  54: 57 */         par1ICommandSender.addChatMessage(new ChatComponentText(joinNiceString(var3.toArray(new String[var3.size()]))));
/*  55: 58 */         return;
/*  56:    */       }
/*  57: 61 */       if (par2ArrayOfStr[0].equals("add"))
/*  58:    */       {
/*  59: 63 */         if (par2ArrayOfStr.length < 2) {
/*  60: 65 */           throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
/*  61:    */         }
/*  62: 68 */         MinecraftServer.getServer().getConfigurationManager().addToWhiteList(par2ArrayOfStr[1]);
/*  63: 69 */         notifyAdmins(par1ICommandSender, "commands.whitelist.add.success", new Object[] { par2ArrayOfStr[1] });
/*  64: 70 */         return;
/*  65:    */       }
/*  66: 73 */       if (par2ArrayOfStr[0].equals("remove"))
/*  67:    */       {
/*  68: 75 */         if (par2ArrayOfStr.length < 2) {
/*  69: 77 */           throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
/*  70:    */         }
/*  71: 80 */         MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(par2ArrayOfStr[1]);
/*  72: 81 */         notifyAdmins(par1ICommandSender, "commands.whitelist.remove.success", new Object[] { par2ArrayOfStr[1] });
/*  73: 82 */         return;
/*  74:    */       }
/*  75: 85 */       if (par2ArrayOfStr[0].equals("reload"))
/*  76:    */       {
/*  77: 87 */         MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
/*  78: 88 */         notifyAdmins(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
/*  79: 89 */         return;
/*  80:    */       }
/*  81:    */     }
/*  82: 93 */     throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  86:    */   {
/*  87:101 */     if (par2ArrayOfStr.length == 1) {
/*  88:103 */       return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*  89:    */     }
/*  90:107 */     if (par2ArrayOfStr.length == 2)
/*  91:    */     {
/*  92:109 */       if (par2ArrayOfStr[0].equals("add"))
/*  93:    */       {
/*  94:111 */         String[] var3 = MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat();
/*  95:112 */         ArrayList var4 = new ArrayList();
/*  96:113 */         String var5 = par2ArrayOfStr[(par2ArrayOfStr.length - 1)];
/*  97:114 */         String[] var6 = var3;
/*  98:115 */         int var7 = var3.length;
/*  99:117 */         for (int var8 = 0; var8 < var7; var8++)
/* 100:    */         {
/* 101:119 */           String var9 = var6[var8];
/* 102:121 */           if ((doesStringStartWith(var5, var9)) && (!MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().contains(var9))) {
/* 103:123 */             var4.add(var9);
/* 104:    */           }
/* 105:    */         }
/* 106:127 */         return var4;
/* 107:    */       }
/* 108:130 */       if (par2ArrayOfStr[0].equals("remove")) {
/* 109:132 */         return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers());
/* 110:    */       }
/* 111:    */     }
/* 112:136 */     return null;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandWhitelist
 * JD-Core Version:    0.7.0.1
 */
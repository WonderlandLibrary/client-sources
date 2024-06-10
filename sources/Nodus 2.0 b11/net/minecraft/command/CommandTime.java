/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.world.WorldServer;
/*  6:   */ 
/*  7:   */ public class CommandTime
/*  8:   */   extends CommandBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00001183";
/* 11:   */   
/* 12:   */   public String getCommandName()
/* 13:   */   {
/* 14:13 */     return "time";
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getRequiredPermissionLevel()
/* 18:   */   {
/* 19:21 */     return 2;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 23:   */   {
/* 24:26 */     return "commands.time.usage";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 28:   */   {
/* 29:31 */     if (par2ArrayOfStr.length > 1)
/* 30:   */     {
/* 31:35 */       if (par2ArrayOfStr[0].equals("set"))
/* 32:   */       {
/* 33:   */         int var3;
/* 34:   */         int var3;
/* 35:37 */         if (par2ArrayOfStr[1].equals("day"))
/* 36:   */         {
/* 37:39 */           var3 = 1000;
/* 38:   */         }
/* 39:   */         else
/* 40:   */         {
/* 41:   */           int var3;
/* 42:41 */           if (par2ArrayOfStr[1].equals("night")) {
/* 43:43 */             var3 = 13000;
/* 44:   */           } else {
/* 45:47 */             var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
/* 46:   */           }
/* 47:   */         }
/* 48:50 */         setTime(par1ICommandSender, var3);
/* 49:51 */         notifyAdmins(par1ICommandSender, "commands.time.set", new Object[] { Integer.valueOf(var3) });
/* 50:52 */         return;
/* 51:   */       }
/* 52:55 */       if (par2ArrayOfStr[0].equals("add"))
/* 53:   */       {
/* 54:57 */         int var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
/* 55:58 */         addTime(par1ICommandSender, var3);
/* 56:59 */         notifyAdmins(par1ICommandSender, "commands.time.added", new Object[] { Integer.valueOf(var3) });
/* 57:60 */         return;
/* 58:   */       }
/* 59:   */     }
/* 60:64 */     throw new WrongUsageException("commands.time.usage", new Object[0]);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 64:   */   {
/* 65:72 */     return (par2ArrayOfStr.length == 2) && (par2ArrayOfStr[0].equals("set")) ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "day", "night" }) : par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "set", "add" }) : null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   protected void setTime(ICommandSender par1ICommandSender, int par2)
/* 69:   */   {
/* 70:80 */     for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; var3++) {
/* 71:82 */       MinecraftServer.getServer().worldServers[var3].setWorldTime(par2);
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   protected void addTime(ICommandSender par1ICommandSender, int par2)
/* 76:   */   {
/* 77:91 */     for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; var3++)
/* 78:   */     {
/* 79:93 */       WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
/* 80:94 */       var4.setWorldTime(var4.getWorldTime() + par2);
/* 81:   */     }
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandTime
 * JD-Core Version:    0.7.0.1
 */
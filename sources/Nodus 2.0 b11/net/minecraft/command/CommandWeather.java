/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.world.WorldServer;
/*  6:   */ import net.minecraft.world.storage.WorldInfo;
/*  7:   */ 
/*  8:   */ public class CommandWeather
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001185";
/* 12:   */   
/* 13:   */   public String getCommandName()
/* 14:   */   {
/* 15:15 */     return "weather";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getRequiredPermissionLevel()
/* 19:   */   {
/* 20:23 */     return 2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 24:   */   {
/* 25:28 */     return "commands.weather.usage";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 29:   */   {
/* 30:33 */     if ((par2ArrayOfStr.length >= 1) && (par2ArrayOfStr.length <= 2))
/* 31:   */     {
/* 32:35 */       int var3 = (300 + new Random().nextInt(600)) * 20;
/* 33:37 */       if (par2ArrayOfStr.length >= 2) {
/* 34:39 */         var3 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
/* 35:   */       }
/* 36:42 */       WorldServer var4 = net.minecraft.server.MinecraftServer.getServer().worldServers[0];
/* 37:43 */       WorldInfo var5 = var4.getWorldInfo();
/* 38:45 */       if ("clear".equalsIgnoreCase(par2ArrayOfStr[0]))
/* 39:   */       {
/* 40:47 */         var5.setRainTime(0);
/* 41:48 */         var5.setThunderTime(0);
/* 42:49 */         var5.setRaining(false);
/* 43:50 */         var5.setThundering(false);
/* 44:51 */         notifyAdmins(par1ICommandSender, "commands.weather.clear", new Object[0]);
/* 45:   */       }
/* 46:53 */       else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0]))
/* 47:   */       {
/* 48:55 */         var5.setRainTime(var3);
/* 49:56 */         var5.setRaining(true);
/* 50:57 */         var5.setThundering(false);
/* 51:58 */         notifyAdmins(par1ICommandSender, "commands.weather.rain", new Object[0]);
/* 52:   */       }
/* 53:   */       else
/* 54:   */       {
/* 55:62 */         if (!"thunder".equalsIgnoreCase(par2ArrayOfStr[0])) {
/* 56:64 */           throw new WrongUsageException("commands.weather.usage", new Object[0]);
/* 57:   */         }
/* 58:67 */         var5.setRainTime(var3);
/* 59:68 */         var5.setThunderTime(var3);
/* 60:69 */         var5.setRaining(true);
/* 61:70 */         var5.setThundering(true);
/* 62:71 */         notifyAdmins(par1ICommandSender, "commands.weather.thunder", new Object[0]);
/* 63:   */       }
/* 64:   */     }
/* 65:   */     else
/* 66:   */     {
/* 67:76 */       throw new WrongUsageException("commands.weather.usage", new Object[0]);
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 72:   */   {
/* 73:85 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "clear", "rain", "thunder" }) : null;
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandWeather
 * JD-Core Version:    0.7.0.1
 */
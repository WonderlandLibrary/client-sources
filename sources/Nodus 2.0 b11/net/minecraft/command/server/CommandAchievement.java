/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.command.CommandBase;
/*   8:    */ import net.minecraft.command.CommandException;
/*   9:    */ import net.minecraft.command.ICommandSender;
/*  10:    */ import net.minecraft.command.WrongUsageException;
/*  11:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  12:    */ import net.minecraft.server.MinecraftServer;
/*  13:    */ import net.minecraft.stats.Achievement;
/*  14:    */ import net.minecraft.stats.AchievementList;
/*  15:    */ import net.minecraft.stats.StatBase;
/*  16:    */ import net.minecraft.stats.StatList;
/*  17:    */ import net.minecraft.stats.StatisticsFile;
/*  18:    */ 
/*  19:    */ public class CommandAchievement
/*  20:    */   extends CommandBase
/*  21:    */ {
/*  22:    */   private static final String __OBFID = "CL_00000113";
/*  23:    */   
/*  24:    */   public String getCommandName()
/*  25:    */   {
/*  26: 24 */     return "achievement";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getRequiredPermissionLevel()
/*  30:    */   {
/*  31: 32 */     return 2;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  35:    */   {
/*  36: 37 */     return "commands.achievement.usage";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  40:    */   {
/*  41: 42 */     if (par2ArrayOfStr.length >= 2)
/*  42:    */     {
/*  43: 44 */       StatBase var3 = StatList.func_151177_a(par2ArrayOfStr[1]);
/*  44: 46 */       if ((var3 == null) && (!par2ArrayOfStr[1].equals("*"))) {
/*  45: 48 */         throw new CommandException("commands.achievement.unknownAchievement", new Object[] { par2ArrayOfStr[1] });
/*  46:    */       }
/*  47:    */       EntityPlayerMP var4;
/*  48:    */       EntityPlayerMP var4;
/*  49: 53 */       if (par2ArrayOfStr.length >= 3) {
/*  50: 55 */         var4 = getPlayer(par1ICommandSender, par2ArrayOfStr[2]);
/*  51:    */       } else {
/*  52: 59 */         var4 = getCommandSenderAsPlayer(par1ICommandSender);
/*  53:    */       }
/*  54: 62 */       if (par2ArrayOfStr[0].equalsIgnoreCase("give"))
/*  55:    */       {
/*  56: 64 */         if (var3 == null)
/*  57:    */         {
/*  58: 66 */           Iterator var5 = AchievementList.achievementList.iterator();
/*  59: 68 */           while (var5.hasNext())
/*  60:    */           {
/*  61: 70 */             Achievement var6 = (Achievement)var5.next();
/*  62: 71 */             var4.triggerAchievement(var6);
/*  63:    */           }
/*  64: 74 */           notifyAdmins(par1ICommandSender, "commands.achievement.give.success.all", new Object[] { var4.getCommandSenderName() });
/*  65:    */         }
/*  66:    */         else
/*  67:    */         {
/*  68: 78 */           if ((var3 instanceof Achievement))
/*  69:    */           {
/*  70: 80 */             Achievement var9 = (Achievement)var3;
/*  71: 83 */             for (ArrayList var10 = Lists.newArrayList(); (var9.parentAchievement != null) && (!var4.func_147099_x().hasAchievementUnlocked(var9.parentAchievement)); var9 = var9.parentAchievement) {
/*  72: 85 */               var10.add(var9.parentAchievement);
/*  73:    */             }
/*  74: 88 */             Iterator var7 = Lists.reverse(var10).iterator();
/*  75: 90 */             while (var7.hasNext())
/*  76:    */             {
/*  77: 92 */               Achievement var8 = (Achievement)var7.next();
/*  78: 93 */               var4.triggerAchievement(var8);
/*  79:    */             }
/*  80:    */           }
/*  81: 97 */           var4.triggerAchievement(var3);
/*  82: 98 */           notifyAdmins(par1ICommandSender, "commands.achievement.give.success.one", new Object[] { var4.getCommandSenderName(), var3.func_150955_j() });
/*  83:    */         }
/*  84:101 */         return;
/*  85:    */       }
/*  86:    */     }
/*  87:105 */     throw new WrongUsageException("commands.achievement.usage", new Object[0]);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  91:    */   {
/*  92:113 */     if (par2ArrayOfStr.length == 1) {
/*  93:115 */       return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "give" });
/*  94:    */     }
/*  95:117 */     if (par2ArrayOfStr.length != 2) {
/*  96:119 */       return par2ArrayOfStr.length == 3 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
/*  97:    */     }
/*  98:123 */     ArrayList var3 = Lists.newArrayList();
/*  99:124 */     Iterator var4 = StatList.allStats.iterator();
/* 100:126 */     while (var4.hasNext())
/* 101:    */     {
/* 102:128 */       StatBase var5 = (StatBase)var4.next();
/* 103:129 */       var3.add(var5.statId);
/* 104:    */     }
/* 105:132 */     return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, var3);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 109:    */   {
/* 110:141 */     return par2 == 2;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandAchievement
 * JD-Core Version:    0.7.0.1
 */
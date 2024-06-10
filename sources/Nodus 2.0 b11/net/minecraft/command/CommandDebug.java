/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.text.SimpleDateFormat;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.profiler.Profiler;
/*   9:    */ import net.minecraft.profiler.Profiler.Result;
/*  10:    */ import net.minecraft.server.MinecraftServer;
/*  11:    */ import org.apache.logging.log4j.LogManager;
/*  12:    */ import org.apache.logging.log4j.Logger;
/*  13:    */ 
/*  14:    */ public class CommandDebug
/*  15:    */   extends CommandBase
/*  16:    */ {
/*  17: 15 */   private static final Logger logger = ;
/*  18:    */   private long field_147206_b;
/*  19:    */   private int field_147207_c;
/*  20:    */   private static final String __OBFID = "CL_00000270";
/*  21:    */   
/*  22:    */   public String getCommandName()
/*  23:    */   {
/*  24: 22 */     return "debug";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getRequiredPermissionLevel()
/*  28:    */   {
/*  29: 30 */     return 3;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  33:    */   {
/*  34: 35 */     return "commands.debug.usage";
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  38:    */   {
/*  39: 40 */     if (par2ArrayOfStr.length == 1)
/*  40:    */     {
/*  41: 42 */       if (par2ArrayOfStr[0].equals("start"))
/*  42:    */       {
/*  43: 44 */         notifyAdmins(par1ICommandSender, "commands.debug.start", new Object[0]);
/*  44: 45 */         MinecraftServer.getServer().enableProfiling();
/*  45: 46 */         this.field_147206_b = MinecraftServer.getSystemTimeMillis();
/*  46: 47 */         this.field_147207_c = MinecraftServer.getServer().getTickCounter();
/*  47: 48 */         return;
/*  48:    */       }
/*  49: 51 */       if (par2ArrayOfStr[0].equals("stop"))
/*  50:    */       {
/*  51: 53 */         if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
/*  52: 55 */           throw new CommandException("commands.debug.notStarted", new Object[0]);
/*  53:    */         }
/*  54: 58 */         long var3 = MinecraftServer.getSystemTimeMillis();
/*  55: 59 */         int var5 = MinecraftServer.getServer().getTickCounter();
/*  56: 60 */         long var6 = var3 - this.field_147206_b;
/*  57: 61 */         int var8 = var5 - this.field_147207_c;
/*  58: 62 */         func_147205_a(var6, var8);
/*  59: 63 */         MinecraftServer.getServer().theProfiler.profilingEnabled = false;
/*  60: 64 */         notifyAdmins(par1ICommandSender, "commands.debug.stop", new Object[] { Float.valueOf((float)var6 / 1000.0F), Integer.valueOf(var8) });
/*  61: 65 */         return;
/*  62:    */       }
/*  63:    */     }
/*  64: 69 */     throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void func_147205_a(long p_147205_1_, int p_147205_3_)
/*  68:    */   {
/*  69: 74 */     File var4 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
/*  70: 75 */     var4.getParentFile().mkdirs();
/*  71:    */     try
/*  72:    */     {
/*  73: 79 */       FileWriter var5 = new FileWriter(var4);
/*  74: 80 */       var5.write(func_147204_b(p_147205_1_, p_147205_3_));
/*  75: 81 */       var5.close();
/*  76:    */     }
/*  77:    */     catch (Throwable var6)
/*  78:    */     {
/*  79: 85 */       logger.error("Could not save profiler results to " + var4, var6);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String func_147204_b(long p_147204_1_, int p_147204_3_)
/*  84:    */   {
/*  85: 91 */     StringBuilder var4 = new StringBuilder();
/*  86: 92 */     var4.append("---- Minecraft Profiler Results ----\n");
/*  87: 93 */     var4.append("// ");
/*  88: 94 */     var4.append(func_147203_d());
/*  89: 95 */     var4.append("\n\n");
/*  90: 96 */     var4.append("Time span: ").append(p_147204_1_).append(" ms\n");
/*  91: 97 */     var4.append("Tick span: ").append(p_147204_3_).append(" ticks\n");
/*  92: 98 */     var4.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(p_147204_3_ / ((float)p_147204_1_ / 1000.0F)) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/*  93: 99 */     var4.append("--- BEGIN PROFILE DUMP ---\n\n");
/*  94:100 */     func_147202_a(0, "root", var4);
/*  95:101 */     var4.append("--- END PROFILE DUMP ---\n\n");
/*  96:102 */     return var4.toString();
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void func_147202_a(int p_147202_1_, String p_147202_2_, StringBuilder p_147202_3_)
/* 100:    */   {
/* 101:107 */     List var4 = MinecraftServer.getServer().theProfiler.getProfilingData(p_147202_2_);
/* 102:109 */     if ((var4 != null) && (var4.size() >= 3)) {
/* 103:111 */       for (int var5 = 1; var5 < var4.size(); var5++)
/* 104:    */       {
/* 105:113 */         Profiler.Result var6 = (Profiler.Result)var4.get(var5);
/* 106:114 */         p_147202_3_.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_147202_1_) }));
/* 107:116 */         for (int var7 = 0; var7 < p_147202_1_; var7++) {
/* 108:118 */           p_147202_3_.append(" ");
/* 109:    */         }
/* 110:121 */         p_147202_3_.append(var6.field_76331_c);
/* 111:122 */         p_147202_3_.append(" - ");
/* 112:123 */         p_147202_3_.append(String.format("%.2f", new Object[] { Double.valueOf(var6.field_76332_a) }));
/* 113:124 */         p_147202_3_.append("%/");
/* 114:125 */         p_147202_3_.append(String.format("%.2f", new Object[] { Double.valueOf(var6.field_76330_b) }));
/* 115:126 */         p_147202_3_.append("%\n");
/* 116:128 */         if (!var6.field_76331_c.equals("unspecified")) {
/* 117:    */           try
/* 118:    */           {
/* 119:132 */             func_147202_a(p_147202_1_ + 1, p_147202_2_ + "." + var6.field_76331_c, p_147202_3_);
/* 120:    */           }
/* 121:    */           catch (Exception var8)
/* 122:    */           {
/* 123:136 */             p_147202_3_.append("[[ EXCEPTION " + var8 + " ]]");
/* 124:    */           }
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static String func_147203_d()
/* 131:    */   {
/* 132:145 */     String[] var0 = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/* 133:    */     try
/* 134:    */     {
/* 135:149 */       return var0[((int)(java.lang.System.nanoTime() % var0.length))];
/* 136:    */     }
/* 137:    */     catch (Throwable var2) {}
/* 138:153 */     return "Witty comment unavailable :(";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 142:    */   {
/* 143:162 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "start", "stop" }) : null;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandDebug
 * JD-Core Version:    0.7.0.1
 */
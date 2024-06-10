/*   1:    */ package net.minecraft.profiler;
/*   2:    */ 
/*   3:    */ import java.lang.management.ManagementFactory;
/*   4:    */ import java.lang.management.RuntimeMXBean;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.LinkedHashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.Timer;
/*  15:    */ import java.util.TimerTask;
/*  16:    */ import java.util.UUID;
/*  17:    */ import net.minecraft.util.HttpUtil;
/*  18:    */ 
/*  19:    */ public class PlayerUsageSnooper
/*  20:    */ {
/*  21: 21 */   private Map dataMap = new HashMap();
/*  22: 22 */   private final String uniqueID = UUID.randomUUID().toString();
/*  23:    */   private final URL serverUrl;
/*  24:    */   private final IPlayerUsage playerStatsCollector;
/*  25: 29 */   private final Timer threadTrigger = new Timer("Snooper Timer", true);
/*  26: 30 */   private final Object syncLock = new Object();
/*  27:    */   private final long minecraftStartTimeMilis;
/*  28:    */   private boolean isRunning;
/*  29:    */   private int selfCounter;
/*  30:    */   private static final String __OBFID = "CL_00001515";
/*  31:    */   
/*  32:    */   public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage, long par3)
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 42 */       this.serverUrl = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
/*  37:    */     }
/*  38:    */     catch (MalformedURLException var6)
/*  39:    */     {
/*  40: 46 */       throw new IllegalArgumentException();
/*  41:    */     }
/*  42: 49 */     this.playerStatsCollector = par2IPlayerUsage;
/*  43: 50 */     this.minecraftStartTimeMilis = par3;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void startSnooper()
/*  47:    */   {
/*  48: 58 */     if (!this.isRunning)
/*  49:    */     {
/*  50: 60 */       this.isRunning = true;
/*  51: 61 */       addBaseDataToSnooper();
/*  52: 62 */       this.threadTrigger.schedule(new TimerTask()
/*  53:    */       {
/*  54:    */         private static final String __OBFID = "CL_00001516";
/*  55:    */         
/*  56:    */         public void run()
/*  57:    */         {
/*  58: 67 */           if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled())
/*  59:    */           {
/*  60: 71 */             synchronized (PlayerUsageSnooper.this.syncLock)
/*  61:    */             {
/*  62: 73 */               HashMap var1 = new HashMap(PlayerUsageSnooper.this.dataMap);
/*  63: 74 */               var1.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.getSelfCounterFor(PlayerUsageSnooper.this)));
/*  64:    */             }
/*  65:    */             HashMap var1;
/*  66: 77 */             HttpUtil.func_151226_a(PlayerUsageSnooper.this.serverUrl, var1, true);
/*  67:    */           }
/*  68:    */         }
/*  69: 80 */       }, 0L, 900000L);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void addBaseDataToSnooper()
/*  74:    */   {
/*  75: 86 */     addJvmArgsToSnooper();
/*  76: 87 */     addData("snooper_token", this.uniqueID);
/*  77: 88 */     addData("os_name", System.getProperty("os.name"));
/*  78: 89 */     addData("os_version", System.getProperty("os.version"));
/*  79: 90 */     addData("os_architecture", System.getProperty("os.arch"));
/*  80: 91 */     addData("java_version", System.getProperty("java.version"));
/*  81: 92 */     addData("version", "1.7.2");
/*  82: 93 */     this.playerStatsCollector.addServerTypeToSnooper(this);
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void addJvmArgsToSnooper()
/*  86:    */   {
/*  87: 98 */     RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
/*  88: 99 */     List var2 = var1.getInputArguments();
/*  89:100 */     int var3 = 0;
/*  90:101 */     Iterator var4 = var2.iterator();
/*  91:103 */     while (var4.hasNext())
/*  92:    */     {
/*  93:105 */       String var5 = (String)var4.next();
/*  94:107 */       if (var5.startsWith("-X")) {
/*  95:109 */         addData("jvm_arg[" + var3++ + "]", var5);
/*  96:    */       }
/*  97:    */     }
/*  98:113 */     addData("jvm_args", Integer.valueOf(var3));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void addMemoryStatsToSnooper()
/* 102:    */   {
/* 103:118 */     addData("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
/* 104:119 */     addData("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
/* 105:120 */     addData("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
/* 106:121 */     addData("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/* 107:122 */     this.playerStatsCollector.addServerStatsToSnooper(this);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addData(String par1Str, Object par2Obj)
/* 111:    */   {
/* 112:130 */     Object var3 = this.syncLock;
/* 113:132 */     synchronized (this.syncLock)
/* 114:    */     {
/* 115:134 */       this.dataMap.put(par1Str, par2Obj);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Map getCurrentStats()
/* 120:    */   {
/* 121:140 */     LinkedHashMap var1 = new LinkedHashMap();
/* 122:141 */     Object var2 = this.syncLock;
/* 123:143 */     synchronized (this.syncLock)
/* 124:    */     {
/* 125:145 */       addMemoryStatsToSnooper();
/* 126:146 */       Iterator var3 = this.dataMap.entrySet().iterator();
/* 127:148 */       while (var3.hasNext())
/* 128:    */       {
/* 129:150 */         Map.Entry var4 = (Map.Entry)var3.next();
/* 130:151 */         var1.put(var4.getKey(), var4.getValue().toString());
/* 131:    */       }
/* 132:154 */       return var1;
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isSnooperRunning()
/* 137:    */   {
/* 138:160 */     return this.isRunning;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void stopSnooper()
/* 142:    */   {
/* 143:165 */     this.threadTrigger.cancel();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getUniqueID()
/* 147:    */   {
/* 148:170 */     return this.uniqueID;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public long getMinecraftStartTimeMillis()
/* 152:    */   {
/* 153:178 */     return this.minecraftStartTimeMilis;
/* 154:    */   }
/* 155:    */   
/* 156:    */   static int getSelfCounterFor(PlayerUsageSnooper par0PlayerUsageSnooper)
/* 157:    */   {
/* 158:186 */     return par0PlayerUsageSnooper.selfCounter++;
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.profiler.PlayerUsageSnooper
 * JD-Core Version:    0.7.0.1
 */
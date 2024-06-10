/*   1:    */ package net.minecraft.server.management;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.text.SimpleDateFormat;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.apache.logging.log4j.LogManager;
/*  13:    */ import org.apache.logging.log4j.Logger;
/*  14:    */ 
/*  15:    */ public class BanList
/*  16:    */ {
/*  17: 16 */   private static final Logger logger = ;
/*  18: 17 */   private final LowerStringMap theBanList = new LowerStringMap();
/*  19:    */   private final File fileName;
/*  20: 21 */   private boolean listActive = true;
/*  21:    */   private static final String __OBFID = "CL_00001396";
/*  22:    */   
/*  23:    */   public BanList(File par1File)
/*  24:    */   {
/*  25: 26 */     this.fileName = par1File;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean isListActive()
/*  29:    */   {
/*  30: 31 */     return this.listActive;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setListActive(boolean par1)
/*  34:    */   {
/*  35: 36 */     this.listActive = par1;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Map getBannedList()
/*  39:    */   {
/*  40: 44 */     removeExpiredBans();
/*  41: 45 */     return this.theBanList;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isBanned(String par1Str)
/*  45:    */   {
/*  46: 50 */     if (!isListActive()) {
/*  47: 52 */       return false;
/*  48:    */     }
/*  49: 56 */     removeExpiredBans();
/*  50: 57 */     return this.theBanList.containsKey(par1Str);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void put(BanEntry par1BanEntry)
/*  54:    */   {
/*  55: 63 */     this.theBanList.put(par1BanEntry.getBannedUsername(), par1BanEntry);
/*  56: 64 */     saveToFileWithHeader();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void remove(String par1Str)
/*  60:    */   {
/*  61: 69 */     this.theBanList.remove(par1Str);
/*  62: 70 */     saveToFileWithHeader();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void removeExpiredBans()
/*  66:    */   {
/*  67: 75 */     Iterator var1 = this.theBanList.values().iterator();
/*  68: 77 */     while (var1.hasNext())
/*  69:    */     {
/*  70: 79 */       BanEntry var2 = (BanEntry)var1.next();
/*  71: 81 */       if (var2.hasBanExpired()) {
/*  72: 83 */         var1.remove();
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void saveToFileWithHeader()
/*  78:    */   {
/*  79: 90 */     saveToFile(true);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void saveToFile(boolean par1)
/*  83:    */   {
/*  84: 98 */     removeExpiredBans();
/*  85:    */     try
/*  86:    */     {
/*  87:102 */       PrintWriter var2 = new PrintWriter(new FileWriter(this.fileName, false));
/*  88:104 */       if (par1)
/*  89:    */       {
/*  90:106 */         var2.println("# Updated " + new SimpleDateFormat().format(new Date()) + " by Minecraft " + "1.7.2");
/*  91:107 */         var2.println("# victim name | ban date | banned by | banned until | reason");
/*  92:108 */         var2.println();
/*  93:    */       }
/*  94:111 */       Iterator var3 = this.theBanList.values().iterator();
/*  95:113 */       while (var3.hasNext())
/*  96:    */       {
/*  97:115 */         BanEntry var4 = (BanEntry)var3.next();
/*  98:116 */         var2.println(var4.buildBanString());
/*  99:    */       }
/* 100:119 */       var2.close();
/* 101:    */     }
/* 102:    */     catch (IOException var5)
/* 103:    */     {
/* 104:123 */       logger.error("Could not save ban list", var5);
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.BanList
 * JD-Core Version:    0.7.0.1
 */
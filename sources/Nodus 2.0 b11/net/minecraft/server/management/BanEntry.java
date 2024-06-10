/*  1:   */ package net.minecraft.server.management;
/*  2:   */ 
/*  3:   */ import java.text.SimpleDateFormat;
/*  4:   */ import java.util.Date;
/*  5:   */ import org.apache.logging.log4j.LogManager;
/*  6:   */ import org.apache.logging.log4j.Logger;
/*  7:   */ 
/*  8:   */ public class BanEntry
/*  9:   */ {
/* 10:10 */   private static final Logger logger = ;
/* 11:11 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/* 12:   */   private final String username;
/* 13:13 */   private Date banStartDate = new Date();
/* 14:14 */   private String bannedBy = "(Unknown)";
/* 15:   */   private Date banEndDate;
/* 16:16 */   private String reason = "Banned by an operator.";
/* 17:   */   private static final String __OBFID = "CL_00001395";
/* 18:   */   
/* 19:   */   public BanEntry(String par1Str)
/* 20:   */   {
/* 21:21 */     this.username = par1Str;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getBannedUsername()
/* 25:   */   {
/* 26:26 */     return this.username;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Date getBanStartDate()
/* 30:   */   {
/* 31:31 */     return this.banStartDate;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getBannedBy()
/* 35:   */   {
/* 36:36 */     return this.bannedBy;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setBannedBy(String par1Str)
/* 40:   */   {
/* 41:41 */     this.bannedBy = par1Str;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Date getBanEndDate()
/* 45:   */   {
/* 46:46 */     return this.banEndDate;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean hasBanExpired()
/* 50:   */   {
/* 51:51 */     return this.banEndDate == null ? false : this.banEndDate.before(new Date());
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String getBanReason()
/* 55:   */   {
/* 56:56 */     return this.reason;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setBanReason(String par1Str)
/* 60:   */   {
/* 61:61 */     this.reason = par1Str;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String buildBanString()
/* 65:   */   {
/* 66:66 */     StringBuilder var1 = new StringBuilder();
/* 67:67 */     var1.append(getBannedUsername());
/* 68:68 */     var1.append("|");
/* 69:69 */     var1.append(dateFormat.format(getBanStartDate()));
/* 70:70 */     var1.append("|");
/* 71:71 */     var1.append(getBannedBy());
/* 72:72 */     var1.append("|");
/* 73:73 */     var1.append(getBanEndDate() == null ? "Forever" : dateFormat.format(getBanEndDate()));
/* 74:74 */     var1.append("|");
/* 75:75 */     var1.append(getBanReason());
/* 76:76 */     return var1.toString();
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.BanEntry
 * JD-Core Version:    0.7.0.1
 */
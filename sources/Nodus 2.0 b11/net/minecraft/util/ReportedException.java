/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.crash.CrashReport;
/*  4:   */ 
/*  5:   */ public class ReportedException
/*  6:   */   extends RuntimeException
/*  7:   */ {
/*  8:   */   private final CrashReport theReportedExceptionCrashReport;
/*  9:   */   private static final String __OBFID = "CL_00001579";
/* 10:   */   
/* 11:   */   public ReportedException(CrashReport par1CrashReport)
/* 12:   */   {
/* 13:13 */     this.theReportedExceptionCrashReport = par1CrashReport;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public CrashReport getCrashReport()
/* 17:   */   {
/* 18:21 */     return this.theReportedExceptionCrashReport;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Throwable getCause()
/* 22:   */   {
/* 23:26 */     return this.theReportedExceptionCrashReport.getCrashCause();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getMessage()
/* 27:   */   {
/* 28:31 */     return this.theReportedExceptionCrashReport.getDescription();
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ReportedException
 * JD-Core Version:    0.7.0.1
 */
/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.crash.CrashReport;
/*    */ 
/*    */ 
/*    */ public class ReportedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final CrashReport theReportedExceptionCrashReport;
/*    */   
/*    */   public ReportedException(CrashReport report) {
/* 12 */     this.theReportedExceptionCrashReport = report;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CrashReport getCrashReport() {
/* 20 */     return this.theReportedExceptionCrashReport;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 25 */     return this.theReportedExceptionCrashReport.getCrashCause();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 30 */     return this.theReportedExceptionCrashReport.getDescription();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\ReportedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
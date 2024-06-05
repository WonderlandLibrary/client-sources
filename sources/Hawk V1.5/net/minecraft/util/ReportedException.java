package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException extends RuntimeException {
   private final CrashReport theReportedExceptionCrashReport;
   private static final String __OBFID = "CL_00001579";

   public Throwable getCause() {
      return this.theReportedExceptionCrashReport.getCrashCause();
   }

   public ReportedException(CrashReport var1) {
      this.theReportedExceptionCrashReport = var1;
   }

   public CrashReport getCrashReport() {
      return this.theReportedExceptionCrashReport;
   }

   public String getMessage() {
      return this.theReportedExceptionCrashReport.getDescription();
   }
}

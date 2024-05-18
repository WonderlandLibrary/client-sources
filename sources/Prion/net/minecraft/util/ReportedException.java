package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException
  extends RuntimeException
{
  private final CrashReport theReportedExceptionCrashReport;
  private static final String __OBFID = "CL_00001579";
  
  public ReportedException(CrashReport p_i1356_1_)
  {
    theReportedExceptionCrashReport = p_i1356_1_;
  }
  



  public CrashReport getCrashReport()
  {
    return theReportedExceptionCrashReport;
  }
  
  public Throwable getCause()
  {
    return theReportedExceptionCrashReport.getCrashCause();
  }
  
  public String getMessage()
  {
    return theReportedExceptionCrashReport.getDescription();
  }
}

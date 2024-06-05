package net.minecraft.util;

import net.minecraft.crash.*;

public class ReportedException extends RuntimeException
{
    private final CrashReport theReportedExceptionCrashReport;
    
    public ReportedException(final CrashReport p_i1356_1_) {
        this.theReportedExceptionCrashReport = p_i1356_1_;
    }
    
    public CrashReport getCrashReport() {
        return this.theReportedExceptionCrashReport;
    }
    
    @Override
    public Throwable getCause() {
        return this.theReportedExceptionCrashReport.getCrashCause();
    }
    
    @Override
    public String getMessage() {
        return this.theReportedExceptionCrashReport.getDescription();
    }
}

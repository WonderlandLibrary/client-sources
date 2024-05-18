package net.minecraft.src;

public class ReportedException extends RuntimeException
{
    private final CrashReport theReportedExceptionCrashReport;
    
    public ReportedException(final CrashReport par1CrashReport) {
        this.theReportedExceptionCrashReport = par1CrashReport;
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

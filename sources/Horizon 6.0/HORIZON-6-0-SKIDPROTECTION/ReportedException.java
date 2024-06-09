package HORIZON-6-0-SKIDPROTECTION;

public class ReportedException extends RuntimeException
{
    private final CrashReport HorizonCode_Horizon_È;
    private static final String Â = "CL_00001579";
    
    public ReportedException(final CrashReport p_i1356_1_) {
        this.HorizonCode_Horizon_È = p_i1356_1_;
    }
    
    public CrashReport HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public Throwable getCause() {
        return this.HorizonCode_Horizon_È.Â();
    }
    
    @Override
    public String getMessage() {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
}

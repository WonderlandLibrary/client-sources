package HORIZON-6-0-SKIDPROTECTION;

public final class ThreadQuickExitException extends RuntimeException
{
    public static final ThreadQuickExitException HorizonCode_Horizon_È;
    private static final String Â = "CL_00002274";
    
    static {
        HorizonCode_Horizon_È = new ThreadQuickExitException();
    }
    
    private ThreadQuickExitException() {
        this.setStackTrace(new StackTraceElement[0]);
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        this.setStackTrace(new StackTraceElement[0]);
        return this;
    }
}

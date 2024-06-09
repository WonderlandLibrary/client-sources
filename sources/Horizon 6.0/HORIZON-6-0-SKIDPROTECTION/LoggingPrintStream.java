package HORIZON-6-0-SKIDPROTECTION;

import java.io.OutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.PrintStream;

public class LoggingPrintStream extends PrintStream
{
    private static final Logger HorizonCode_Horizon_È;
    private final String Â;
    private static final String Ý = "CL_00002275";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public LoggingPrintStream(final String p_i45927_1_, final OutputStream p_i45927_2_) {
        super(p_i45927_2_);
        this.Â = p_i45927_1_;
    }
    
    @Override
    public void println(final String p_println_1_) {
        this.HorizonCode_Horizon_È(p_println_1_);
    }
    
    @Override
    public void println(final Object p_println_1_) {
        this.HorizonCode_Horizon_È(String.valueOf(p_println_1_));
    }
    
    private void HorizonCode_Horizon_È(final String p_179882_1_) {
        final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
        final StackTraceElement var3 = var2[Math.min(3, var2.length)];
        LoggingPrintStream.HorizonCode_Horizon_È.info("[{}]@.({}:{}): {}", new Object[] { this.Â, var3.getFileName(), var3.getLineNumber(), p_179882_1_ });
    }
}

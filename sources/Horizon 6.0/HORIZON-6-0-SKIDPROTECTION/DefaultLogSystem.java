package HORIZON-6-0-SKIDPROTECTION;

import java.util.Date;
import java.io.PrintStream;

public class DefaultLogSystem implements LogSystem
{
    public static PrintStream HorizonCode_Horizon_È;
    
    static {
        DefaultLogSystem.HorizonCode_Horizon_È = System.out;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String message, final Throwable e) {
        this.HorizonCode_Horizon_È(message);
        this.HorizonCode_Horizon_È(e);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Throwable e) {
        DefaultLogSystem.HorizonCode_Horizon_È.println(new Date() + " ERROR:" + e.getMessage());
        e.printStackTrace(DefaultLogSystem.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String message) {
        DefaultLogSystem.HorizonCode_Horizon_È.println(new Date() + " ERROR:" + message);
    }
    
    @Override
    public void Â(final String message) {
        DefaultLogSystem.HorizonCode_Horizon_È.println(new Date() + " WARN:" + message);
    }
    
    @Override
    public void Ý(final String message) {
        DefaultLogSystem.HorizonCode_Horizon_È.println(new Date() + " INFO:" + message);
    }
    
    @Override
    public void Ø­áŒŠá(final String message) {
        DefaultLogSystem.HorizonCode_Horizon_È.println(new Date() + " DEBUG:" + message);
    }
    
    @Override
    public void Â(final String message, final Throwable e) {
        this.Â(message);
        e.printStackTrace(DefaultLogSystem.HorizonCode_Horizon_È);
    }
}

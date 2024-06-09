package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Method;

public class MethodData
{
    public final Object HorizonCode_Horizon_È;
    public final Method Â;
    public final byte Ý;
    
    MethodData(final Object source, final Method target, final byte priority) {
        this.HorizonCode_Horizon_È = source;
        this.Â = target;
        this.Ý = priority;
    }
}

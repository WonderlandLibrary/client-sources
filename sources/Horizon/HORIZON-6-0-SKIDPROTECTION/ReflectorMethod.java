package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Method;

public class ReflectorMethod
{
    private ReflectorClass HorizonCode_Horizon_È;
    private String Â;
    private Class[] Ý;
    private boolean Ø­áŒŠá;
    private Method Âµá€;
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName) {
        this(reflectorClass, targetMethodName, null);
    }
    
    public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName, final Class[] targetMethodParameterTypes) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = null;
        this.Ø­áŒŠá = false;
        this.Âµá€ = null;
        this.HorizonCode_Horizon_È = reflectorClass;
        this.Â = targetMethodName;
        this.Ý = targetMethodParameterTypes;
        final Method m = this.HorizonCode_Horizon_È();
    }
    
    public Method HorizonCode_Horizon_È() {
        if (this.Ø­áŒŠá) {
            return this.Âµá€;
        }
        this.Ø­áŒŠá = true;
        final Class cls = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (cls == null) {
            return null;
        }
        final Method[] ms = cls.getDeclaredMethods();
        for (int i = 0; i < ms.length; ++i) {
            final Method m = ms[i];
            if (m.getName().equals(this.Â)) {
                if (this.Ý != null) {
                    final Class[] types = m.getParameterTypes();
                    if (!Reflector.HorizonCode_Horizon_È(this.Ý, types)) {
                        continue;
                    }
                }
                this.Âµá€ = m;
                if (!this.Âµá€.isAccessible()) {
                    this.Âµá€.setAccessible(true);
                }
                return this.Âµá€;
            }
        }
        Config.Ø­áŒŠá("(Reflector) Method not pesent: " + cls.getName() + "." + this.Â);
        return null;
    }
    
    public boolean Â() {
        return this.Ø­áŒŠá ? (this.Âµá€ != null) : (this.HorizonCode_Horizon_È() != null);
    }
    
    public Class Ý() {
        final Method tm = this.HorizonCode_Horizon_È();
        return (tm == null) ? null : tm.getReturnType();
    }
    
    public void Ø­áŒŠá() {
        this.Ø­áŒŠá = true;
        this.Âµá€ = null;
    }
}

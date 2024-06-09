package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Constructor;

public class ReflectorConstructor
{
    private ReflectorClass HorizonCode_Horizon_È;
    private Class[] Â;
    private boolean Ý;
    private Constructor Ø­áŒŠá;
    
    public ReflectorConstructor(final ReflectorClass reflectorClass, final Class[] parameterTypes) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = false;
        this.Ø­áŒŠá = null;
        this.HorizonCode_Horizon_È = reflectorClass;
        this.Â = parameterTypes;
        final Constructor c = this.HorizonCode_Horizon_È();
    }
    
    public Constructor HorizonCode_Horizon_È() {
        if (this.Ý) {
            return this.Ø­áŒŠá;
        }
        this.Ý = true;
        final Class cls = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (cls == null) {
            return null;
        }
        this.Ø­áŒŠá = HorizonCode_Horizon_È(cls, this.Â);
        if (this.Ø­áŒŠá == null) {
            Config.HorizonCode_Horizon_È("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.HorizonCode_Horizon_È(this.Â));
        }
        if (this.Ø­áŒŠá != null && !this.Ø­áŒŠá.isAccessible()) {
            this.Ø­áŒŠá.setAccessible(true);
        }
        return this.Ø­áŒŠá;
    }
    
    private static Constructor HorizonCode_Horizon_È(final Class cls, final Class[] paramTypes) {
        final Constructor[] cs = cls.getDeclaredConstructors();
        for (int i = 0; i < cs.length; ++i) {
            final Constructor c = cs[i];
            final Class[] types = c.getParameterTypes();
            if (Reflector.HorizonCode_Horizon_È(paramTypes, types)) {
                return c;
            }
        }
        return null;
    }
    
    public boolean Â() {
        return this.Ý ? (this.Ø­áŒŠá != null) : (this.HorizonCode_Horizon_È() != null);
    }
    
    public void Ý() {
        this.Ý = true;
        this.Ø­áŒŠá = null;
    }
}

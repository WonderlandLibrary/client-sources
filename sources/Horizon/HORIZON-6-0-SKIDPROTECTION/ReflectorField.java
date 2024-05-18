package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Field;

public class ReflectorField
{
    private ReflectorClass HorizonCode_Horizon_È;
    private String Â;
    private boolean Ý;
    private Field Ø­áŒŠá;
    
    public ReflectorField(final ReflectorClass reflectorClass, final String targetFieldName) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = false;
        this.Ø­áŒŠá = null;
        this.HorizonCode_Horizon_È = reflectorClass;
        this.Â = targetFieldName;
        final Field f = this.HorizonCode_Horizon_È();
    }
    
    public Field HorizonCode_Horizon_È() {
        if (this.Ý) {
            return this.Ø­áŒŠá;
        }
        this.Ý = true;
        final Class cls = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (cls == null) {
            return null;
        }
        try {
            this.Ø­áŒŠá = cls.getDeclaredField(this.Â);
            if (!this.Ø­áŒŠá.isAccessible()) {
                this.Ø­áŒŠá.setAccessible(true);
            }
        }
        catch (SecurityException var3) {
            var3.printStackTrace();
        }
        catch (NoSuchFieldException var4) {
            Config.Ø­áŒŠá("(Reflector) Field not present: " + cls.getName() + "." + this.Â);
        }
        return this.Ø­áŒŠá;
    }
    
    public Object Â() {
        return Reflector.HorizonCode_Horizon_È((Object)null, this);
    }
    
    public void HorizonCode_Horizon_È(final Object value) {
        Reflector.HorizonCode_Horizon_È(null, this, value);
    }
    
    public boolean Ý() {
        return this.Ý ? (this.Ø­áŒŠá != null) : (this.HorizonCode_Horizon_È() != null);
    }
}

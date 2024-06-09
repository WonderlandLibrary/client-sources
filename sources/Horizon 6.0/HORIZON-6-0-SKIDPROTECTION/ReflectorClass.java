package HORIZON-6-0-SKIDPROTECTION;

public class ReflectorClass
{
    private String HorizonCode_Horizon_È;
    private boolean Â;
    private Class Ý;
    
    public ReflectorClass(final String targetClassName) {
        this.HorizonCode_Horizon_È = null;
        this.Â = false;
        this.Ý = null;
        this.HorizonCode_Horizon_È = targetClassName;
        final Class cls = this.HorizonCode_Horizon_È();
    }
    
    public ReflectorClass(final Class targetClass) {
        this.HorizonCode_Horizon_È = null;
        this.Â = false;
        this.Ý = null;
        this.Ý = targetClass;
        this.HorizonCode_Horizon_È = targetClass.getName();
        this.Â = true;
    }
    
    public Class HorizonCode_Horizon_È() {
        if (this.Â) {
            return this.Ý;
        }
        this.Â = true;
        try {
            this.Ý = Class.forName(this.HorizonCode_Horizon_È);
        }
        catch (ClassNotFoundException var4) {
            Config.Ø­áŒŠá("(Reflector) Class not present: " + this.HorizonCode_Horizon_È);
        }
        catch (Throwable var3) {
            var3.printStackTrace();
        }
        return this.Ý;
    }
    
    public boolean Â() {
        return this.HorizonCode_Horizon_È() != null;
    }
    
    public String Ý() {
        return this.HorizonCode_Horizon_È;
    }
}

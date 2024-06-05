package optfine;

public class ReflectorClass
{
    private String targetClassName;
    private boolean checked;
    private Class targetClass;
    
    public ReflectorClass(final String p_i55_1_) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClassName = p_i55_1_;
        final Class oclass = this.getTargetClass();
    }
    
    public ReflectorClass(final Class p_i56_1_) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClass = p_i56_1_;
        this.targetClassName = p_i56_1_.getName();
        this.checked = true;
    }
    
    public Class getTargetClass() {
        if (this.checked) {
            return this.targetClass;
        }
        this.checked = true;
        try {
            this.targetClass = Class.forName(this.targetClassName);
        }
        catch (ClassNotFoundException var2) {
            Config.log("(Reflector) Class not present: " + this.targetClassName);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return this.targetClass;
    }
    
    public boolean exists() {
        return this.getTargetClass() != null;
    }
    
    public String getTargetClassName() {
        return this.targetClassName;
    }
}

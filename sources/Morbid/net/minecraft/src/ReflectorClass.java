package net.minecraft.src;

public class ReflectorClass
{
    private String targetClassName;
    private boolean checked;
    private Class targetClass;
    
    public ReflectorClass(final String var1) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClassName = var1;
        final Class var2 = this.getTargetClass();
    }
    
    public ReflectorClass(final Class var1) {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClass = var1;
        this.targetClassName = var1.getName();
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
        catch (ClassNotFoundException var4) {
            Config.log("(Reflector) Class not present: " + this.targetClassName);
        }
        catch (Throwable var3) {
            var3.printStackTrace();
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

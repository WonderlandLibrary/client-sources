package net.minecraft.src;

import java.lang.reflect.*;

public class ReflectorMethod
{
    private ReflectorClass reflectorClass;
    private String targetMethodName;
    private Class[] targetMethodParameterTypes;
    private boolean checked;
    private Method targetMethod;
    
    public ReflectorMethod(final ReflectorClass var1, final String var2) {
        this(var1, var2, null);
    }
    
    public ReflectorMethod(final ReflectorClass var1, final String var2, final Class[] var3) {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = var1;
        this.targetMethodName = var2;
        this.targetMethodParameterTypes = var3;
        final Method var4 = this.getTargetMethod();
    }
    
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        final Class var1 = this.reflectorClass.getTargetClass();
        if (var1 == null) {
            return null;
        }
        final Method[] var2 = var1.getMethods();
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final Method var4 = var2[var3];
            if (var4.getName().equals(this.targetMethodName)) {
                if (this.targetMethodParameterTypes != null) {
                    final Class[] var5 = var4.getParameterTypes();
                    if (!Reflector.matchesTypes(this.targetMethodParameterTypes, var5)) {
                        continue;
                    }
                }
                return this.targetMethod = var4;
            }
        }
        Config.log("(Reflector) Method not pesent: " + var1.getName() + "." + this.targetMethodName);
        return null;
    }
    
    public boolean exists() {
        return this.getTargetMethod() != null;
    }
    
    public Class getReturnType() {
        final Method var1 = this.getTargetMethod();
        return (var1 == null) ? null : var1.getReturnType();
    }
    
    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}

package optfine;

import java.lang.reflect.*;

public class ReflectorMethod
{
    private ReflectorClass reflectorClass;
    private String targetMethodName;
    private Class[] targetMethodParameterTypes;
    private boolean checked;
    private Method targetMethod;
    
    public ReflectorMethod(final ReflectorClass p_i59_1_, final String p_i59_2_) {
        this(p_i59_1_, p_i59_2_, null);
    }
    
    public ReflectorMethod(final ReflectorClass p_i60_1_, final String p_i60_2_, final Class[] p_i60_3_) {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = p_i60_1_;
        this.targetMethodName = p_i60_2_;
        this.targetMethodParameterTypes = p_i60_3_;
        final Method method = this.getTargetMethod();
    }
    
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        final Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        final Method[] amethod = oclass.getDeclaredMethods();
        for (int i = 0; i < amethod.length; ++i) {
            final Method method = amethod[i];
            if (method.getName().equals(this.targetMethodName)) {
                if (this.targetMethodParameterTypes != null) {
                    final Class[] aclass = method.getParameterTypes();
                    if (!Reflector.matchesTypes(this.targetMethodParameterTypes, aclass)) {
                        continue;
                    }
                }
                this.targetMethod = method;
                if (!this.targetMethod.isAccessible()) {
                    this.targetMethod.setAccessible(true);
                }
                return this.targetMethod;
            }
        }
        Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
        return null;
    }
    
    public boolean exists() {
        return this.checked ? (this.targetMethod != null) : (this.getTargetMethod() != null);
    }
    
    public Class getReturnType() {
        final Method method = this.getTargetMethod();
        return (method == null) ? null : method.getReturnType();
    }
    
    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}

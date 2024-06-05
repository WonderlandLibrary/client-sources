/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.lang.reflect.Method;
import optfine.Config;
import optfine.Reflector;
import optfine.ReflectorClass;

public class ReflectorMethod {
    private ReflectorClass reflectorClass = null;
    private String targetMethodName = null;
    private Class[] targetMethodParameterTypes = null;
    private boolean checked = false;
    private Method targetMethod = null;

    public ReflectorMethod(ReflectorClass p_i59_1_, String p_i59_2_) {
        this(p_i59_1_, p_i59_2_, null);
    }

    public ReflectorMethod(ReflectorClass p_i60_1_, String p_i60_2_, Class[] p_i60_3_) {
        this.reflectorClass = p_i60_1_;
        this.targetMethodName = p_i60_2_;
        this.targetMethodParameterTypes = p_i60_3_;
        Method method = this.getTargetMethod();
    }

    public Method getTargetMethod() {
        Method method;
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        Method[] amethod = oclass.getDeclaredMethods();
        int i = 0;
        while (true) {
            Class[] aclass;
            if (i >= amethod.length) {
                Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
                return null;
            }
            method = amethod[i];
            if (method.getName().equals(this.targetMethodName) && (this.targetMethodParameterTypes == null || Reflector.matchesTypes(this.targetMethodParameterTypes, aclass = method.getParameterTypes()))) break;
            ++i;
        }
        this.targetMethod = method;
        if (this.targetMethod.isAccessible()) return this.targetMethod;
        this.targetMethod.setAccessible(true);
        return this.targetMethod;
    }

    public boolean exists() {
        if (this.checked) {
            if (this.targetMethod == null) return false;
            return true;
        }
        if (this.getTargetMethod() == null) return false;
        return true;
    }

    public Class getReturnType() {
        Method method = this.getTargetMethod();
        if (method == null) {
            return null;
        }
        Class<?> clazz = method.getReturnType();
        return clazz;
    }

    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}


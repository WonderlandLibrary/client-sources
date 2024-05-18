/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.lang.reflect.Method;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;

public class ReflectorMethod {
    private ReflectorClass reflectorClass = null;
    private String targetMethodName = null;
    private Class[] targetMethodParameterTypes = null;
    private boolean checked = false;
    private Method targetMethod = null;

    public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
        this(reflectorClass, targetMethodName, null);
    }

    public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
        this.reflectorClass = reflectorClass;
        this.targetMethodName = targetMethodName;
        this.targetMethodParameterTypes = targetMethodParameterTypes;
        Method m = this.getTargetMethod();
    }

    public Method getTargetMethod() {
        Method m;
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        Class cls = this.reflectorClass.getTargetClass();
        if (cls == null) {
            return null;
        }
        Method[] ms = cls.getDeclaredMethods();
        int i = 0;
        do {
            Class[] types;
            if (i >= ms.length) {
                Config.log("(Reflector) Method not pesent: " + cls.getName() + "." + this.targetMethodName);
                return null;
            }
            m = ms[i];
            if (m.getName().equals(this.targetMethodName) && (this.targetMethodParameterTypes == null || Reflector.matchesTypes(this.targetMethodParameterTypes, types = m.getParameterTypes()))) break;
            ++i;
        } while (true);
        this.targetMethod = m;
        if (!this.targetMethod.isAccessible()) {
            this.targetMethod.setAccessible(true);
        }
        return this.targetMethod;
    }

    public boolean exists() {
        return this.checked ? this.targetMethod != null : this.getTargetMethod() != null;
    }

    public Class getReturnType() {
        Method tm = this.getTargetMethod();
        return tm == null ? null : tm.getReturnType();
    }

    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}


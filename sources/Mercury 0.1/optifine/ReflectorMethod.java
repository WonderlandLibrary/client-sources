/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.lang.reflect.Method;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorClass;

public class ReflectorMethod {
    private ReflectorClass reflectorClass = null;
    private String targetMethodName = null;
    private Class[] targetMethodParameterTypes = null;
    private boolean checked = false;
    private Method targetMethod = null;

    public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
        this(reflectorClass, targetMethodName, null, false);
    }

    public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
        this(reflectorClass, targetMethodName, targetMethodParameterTypes, false);
    }

    public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes, boolean lazyResolve) {
        this.reflectorClass = reflectorClass;
        this.targetMethodName = targetMethodName;
        this.targetMethodParameterTypes = targetMethodParameterTypes;
        if (!lazyResolve) {
            Method method = this.getTargetMethod();
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        Class cls = this.reflectorClass.getTargetClass();
        if (cls == null) {
            return null;
        }
        try {
            if (this.targetMethodParameterTypes == null) {
                Method[] e2 = Reflector.getMethods(cls, this.targetMethodName);
                if (e2.length <= 0) {
                    Config.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
                    return null;
                }
                if (e2.length > 1) {
                    Config.warn("(Reflector) More than one method found: " + cls.getName() + "." + this.targetMethodName);
                    for (int i2 = 0; i2 < e2.length; ++i2) {
                        Method m2 = e2[i2];
                        Config.warn("(Reflector)  - " + m2);
                    }
                    return null;
                }
                this.targetMethod = e2[0];
            } else {
                this.targetMethod = Reflector.getMethod(cls, this.targetMethodName, this.targetMethodParameterTypes);
            }
            if (this.targetMethod == null) {
                Config.log("(Reflector) Method not present: " + cls.getName() + "." + this.targetMethodName);
                return null;
            }
            this.targetMethod.setAccessible(true);
            return this.targetMethod;
        }
        catch (Throwable var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public boolean exists() {
        return this.checked ? this.targetMethod != null : this.getTargetMethod() != null;
    }

    public Class getReturnType() {
        Method tm2 = this.getTargetMethod();
        return tm2 == null ? null : tm2.getReturnType();
    }

    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }
}


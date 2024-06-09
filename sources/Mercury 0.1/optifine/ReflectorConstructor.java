/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.lang.reflect.Constructor;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorClass;

public class ReflectorConstructor {
    private ReflectorClass reflectorClass = null;
    private Class[] parameterTypes = null;
    private boolean checked = false;
    private Constructor targetConstructor = null;

    public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
        this.reflectorClass = reflectorClass;
        this.parameterTypes = parameterTypes;
        Constructor c2 = this.getTargetConstructor();
    }

    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = true;
        Class cls = this.reflectorClass.getTargetClass();
        if (cls == null) {
            return null;
        }
        try {
            this.targetConstructor = ReflectorConstructor.findConstructor(cls, this.parameterTypes);
            if (this.targetConstructor == null) {
                Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
            }
            if (this.targetConstructor != null) {
                this.targetConstructor.setAccessible(true);
            }
        }
        catch (Throwable var3) {
            var3.printStackTrace();
        }
        return this.targetConstructor;
    }

    private static Constructor findConstructor(Class cls, Class[] paramTypes) {
        Constructor<?>[] cs2 = cls.getDeclaredConstructors();
        for (int i2 = 0; i2 < cs2.length; ++i2) {
            Constructor<?> c2 = cs2[i2];
            Class[] types = c2.getParameterTypes();
            if (!Reflector.matchesTypes(paramTypes, types)) continue;
            return c2;
        }
        return null;
    }

    public boolean exists() {
        return this.checked ? this.targetConstructor != null : this.getTargetConstructor() != null;
    }

    public void deactivate() {
        this.checked = true;
        this.targetConstructor = null;
    }
}


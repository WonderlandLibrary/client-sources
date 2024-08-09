/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Constructor;
import net.optifine.Log;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.util.ArrayUtils;

public class ReflectorConstructor
implements IResolvable {
    private ReflectorClass reflectorClass = null;
    private Class[] parameterTypes = null;
    private boolean checked = false;
    private Constructor targetConstructor = null;

    public ReflectorConstructor(ReflectorClass reflectorClass, Class[] classArray) {
        this.reflectorClass = reflectorClass;
        this.parameterTypes = classArray;
        ReflectorResolver.register(this);
    }

    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = true;
        Class clazz = this.reflectorClass.getTargetClass();
        if (clazz == null) {
            return null;
        }
        try {
            this.targetConstructor = ReflectorConstructor.findConstructor(clazz, this.parameterTypes);
            if (this.targetConstructor == null) {
                Log.dbg("(Reflector) Constructor not present: " + clazz.getName() + ", params: " + ArrayUtils.arrayToString(this.parameterTypes));
            }
            if (this.targetConstructor != null) {
                this.targetConstructor.setAccessible(false);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return this.targetConstructor;
    }

    private static Constructor findConstructor(Class clazz, Class[] classArray) {
        Constructor<?>[] constructorArray = clazz.getDeclaredConstructors();
        for (int i = 0; i < constructorArray.length; ++i) {
            Constructor<?> constructor = constructorArray[i];
            Class[] classArray2 = constructor.getParameterTypes();
            if (!Reflector.matchesTypes(classArray, classArray2)) continue;
            return constructor;
        }
        return null;
    }

    public boolean exists() {
        if (this.checked) {
            return this.targetConstructor != null;
        }
        return this.getTargetConstructor() != null;
    }

    public void deactivate() {
        this.checked = true;
        this.targetConstructor = null;
    }

    public Object newInstance(Object ... objectArray) {
        return Reflector.newInstance(this, objectArray);
    }

    @Override
    public void resolve() {
        Constructor constructor = this.getTargetConstructor();
    }
}


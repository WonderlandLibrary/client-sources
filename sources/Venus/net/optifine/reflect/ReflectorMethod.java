/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.optifine.Log;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;

public class ReflectorMethod
implements IResolvable {
    private ReflectorClass reflectorClass = null;
    private String targetMethodName = null;
    private Class[] targetMethodParameterTypes = null;
    private boolean checked = false;
    private Method targetMethod = null;

    public ReflectorMethod(ReflectorClass reflectorClass, String string) {
        this(reflectorClass, string, null);
    }

    public ReflectorMethod(ReflectorClass reflectorClass, String string, Class[] classArray) {
        this.reflectorClass = reflectorClass;
        this.targetMethodName = string;
        this.targetMethodParameterTypes = classArray;
        ReflectorResolver.register(this);
    }

    public Method getTargetMethod() {
        if (this.checked) {
            return this.targetMethod;
        }
        this.checked = true;
        Class clazz = this.reflectorClass.getTargetClass();
        if (clazz == null) {
            return null;
        }
        try {
            if (this.targetMethodParameterTypes == null) {
                Method[] methodArray = ReflectorMethod.getMethods(clazz, this.targetMethodName);
                if (methodArray.length <= 0) {
                    Log.log("(Reflector) Method not present: " + clazz.getName() + "." + this.targetMethodName);
                    return null;
                }
                if (methodArray.length > 1) {
                    Log.warn("(Reflector) More than one method found: " + clazz.getName() + "." + this.targetMethodName);
                    for (int i = 0; i < methodArray.length; ++i) {
                        Method method = methodArray[i];
                        Log.warn("(Reflector)  - " + method);
                    }
                    return null;
                }
                this.targetMethod = methodArray[0];
            } else {
                this.targetMethod = ReflectorMethod.getMethod(clazz, this.targetMethodName, this.targetMethodParameterTypes);
            }
            if (this.targetMethod == null) {
                Log.log("(Reflector) Method not present: " + clazz.getName() + "." + this.targetMethodName);
                return null;
            }
            this.targetMethod.setAccessible(false);
            return this.targetMethod;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public boolean exists() {
        if (this.checked) {
            return this.targetMethod != null;
        }
        return this.getTargetMethod() != null;
    }

    public Class getReturnType() {
        Method method = this.getTargetMethod();
        return method == null ? null : method.getReturnType();
    }

    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }

    public Object call(Object ... objectArray) {
        return Reflector.call(this, objectArray);
    }

    public boolean callBoolean(Object ... objectArray) {
        return Reflector.callBoolean(this, objectArray);
    }

    public int callInt(Object ... objectArray) {
        return Reflector.callInt(this, objectArray);
    }

    public float callFloat(Object ... objectArray) {
        return Reflector.callFloat(this, objectArray);
    }

    public double callDouble(Object ... objectArray) {
        return Reflector.callDouble(this, objectArray);
    }

    public String callString(Object ... objectArray) {
        return Reflector.callString(this, objectArray);
    }

    public Object call(Object object) {
        return Reflector.call(this, object);
    }

    public boolean callBoolean(Object object) {
        return Reflector.callBoolean(this, object);
    }

    public int callInt(Object object) {
        return Reflector.callInt(this, object);
    }

    public float callFloat(Object object) {
        return Reflector.callFloat(this, object);
    }

    public double callDouble(Object object) {
        return Reflector.callDouble(this, object);
    }

    public String callString1(Object object) {
        return Reflector.callString(this, object);
    }

    public void callVoid(Object ... objectArray) {
        Reflector.callVoid(this, objectArray);
    }

    public static Method getMethod(Class clazz, String string, Class[] classArray) {
        Method[] methodArray = clazz.getDeclaredMethods();
        for (int i = 0; i < methodArray.length; ++i) {
            Class[] classArray2;
            Method method = methodArray[i];
            if (!method.getName().equals(string) || !Reflector.matchesTypes(classArray, classArray2 = method.getParameterTypes())) continue;
            return method;
        }
        return null;
    }

    public static Method[] getMethods(Class clazz, String string) {
        ArrayList<Method> arrayList = new ArrayList<Method>();
        Method[] methodArray = clazz.getDeclaredMethods();
        for (int i = 0; i < methodArray.length; ++i) {
            Method method = methodArray[i];
            if (!method.getName().equals(string)) continue;
            arrayList.add(method);
        }
        return arrayList.toArray(new Method[arrayList.size()]);
    }

    @Override
    public void resolve() {
        Method method = this.getTargetMethod();
    }
}


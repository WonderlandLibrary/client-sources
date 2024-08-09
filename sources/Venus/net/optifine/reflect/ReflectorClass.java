/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import net.optifine.Log;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.ReflectorConstructor;
import net.optifine.reflect.ReflectorField;
import net.optifine.reflect.ReflectorMethod;
import net.optifine.reflect.ReflectorResolver;

public class ReflectorClass
implements IResolvable {
    private String targetClassName = null;
    private boolean checked = false;
    private Class targetClass = null;

    public ReflectorClass(String string) {
        this.targetClassName = string;
        ReflectorResolver.register(this);
    }

    public ReflectorClass(Class clazz) {
        this.targetClass = clazz;
        this.targetClassName = clazz.getName();
        this.checked = true;
    }

    public Class getTargetClass() {
        if (this.checked) {
            return this.targetClass;
        }
        this.checked = true;
        try {
            this.targetClass = Class.forName(this.targetClassName);
        } catch (ClassNotFoundException classNotFoundException) {
            Log.log("(Reflector) Class not present: " + this.targetClassName);
        } catch (Throwable throwable) {
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

    public boolean isInstance(Object object) {
        return this.getTargetClass() == null ? false : this.getTargetClass().isInstance(object);
    }

    public ReflectorField makeField(String string) {
        return new ReflectorField(this, string);
    }

    public ReflectorMethod makeMethod(String string) {
        return new ReflectorMethod(this, string);
    }

    public ReflectorMethod makeMethod(String string, Class[] classArray) {
        return new ReflectorMethod(this, string, classArray);
    }

    public ReflectorConstructor makeConstructor(Class[] classArray) {
        return new ReflectorConstructor(this, classArray);
    }

    @Override
    public void resolve() {
        Class clazz = this.getTargetClass();
    }
}


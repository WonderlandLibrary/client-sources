/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.Log;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.ReflectorClass;

public class FieldLocatorName
implements IFieldLocator {
    private ReflectorClass reflectorClass = null;
    private String targetFieldName = null;

    public FieldLocatorName(ReflectorClass reflectorClass, String string) {
        this.reflectorClass = reflectorClass;
        this.targetFieldName = string;
    }

    @Override
    public Field getField() {
        Class clazz = this.reflectorClass.getTargetClass();
        if (clazz == null) {
            return null;
        }
        try {
            Field field = this.getDeclaredField(clazz, this.targetFieldName);
            field.setAccessible(false);
            return field;
        } catch (NoSuchFieldException noSuchFieldException) {
            Log.log("(Reflector) Field not present: " + clazz.getName() + "." + this.targetFieldName);
            return null;
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    private Field getDeclaredField(Class clazz, String string) throws NoSuchFieldException {
        Field[] fieldArray = clazz.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; ++i) {
            Field field = fieldArray[i];
            if (!field.getName().equals(string)) continue;
            return field;
        }
        if (clazz == Object.class) {
            throw new NoSuchFieldException(string);
        }
        return this.getDeclaredField(clazz.getSuperclass(), string);
    }
}


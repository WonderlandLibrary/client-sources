/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.Log;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.ReflectorClass;

public class FieldLocatorType
implements IFieldLocator {
    private ReflectorClass reflectorClass = null;
    private Class targetFieldType = null;
    private int targetFieldIndex;

    public FieldLocatorType(ReflectorClass reflectorClass, Class clazz) {
        this(reflectorClass, clazz, 0);
    }

    public FieldLocatorType(ReflectorClass reflectorClass, Class clazz, int n) {
        this.reflectorClass = reflectorClass;
        this.targetFieldType = clazz;
        this.targetFieldIndex = n;
    }

    @Override
    public Field getField() {
        Class clazz = this.reflectorClass.getTargetClass();
        if (clazz == null) {
            return null;
        }
        try {
            Field[] fieldArray = clazz.getDeclaredFields();
            int n = 0;
            for (int i = 0; i < fieldArray.length; ++i) {
                Field field = fieldArray[i];
                if (field.getType() != this.targetFieldType) continue;
                if (n == this.targetFieldIndex) {
                    field.setAccessible(false);
                    return field;
                }
                ++n;
            }
            Log.log("(Reflector) Field not present: " + clazz.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
            return null;
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}


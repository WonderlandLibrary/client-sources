/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.reflect.FieldLocatorFixed;
import net.optifine.reflect.FieldLocatorName;
import net.optifine.reflect.FieldLocatorType;
import net.optifine.reflect.IFieldLocator;
import net.optifine.reflect.IResolvable;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorResolver;

public class ReflectorField
implements IResolvable {
    private IFieldLocator fieldLocator = null;
    private boolean checked = false;
    private Field targetField = null;

    public ReflectorField(ReflectorClass reflectorClass, String string) {
        this(new FieldLocatorName(reflectorClass, string));
    }

    public ReflectorField(ReflectorClass reflectorClass, Class clazz) {
        this(reflectorClass, clazz, 0);
    }

    public ReflectorField(ReflectorClass reflectorClass, Class clazz, int n) {
        this(new FieldLocatorType(reflectorClass, clazz, n));
    }

    public ReflectorField(Field field) {
        this(new FieldLocatorFixed(field));
    }

    public ReflectorField(IFieldLocator iFieldLocator) {
        this.fieldLocator = iFieldLocator;
        ReflectorResolver.register(this);
    }

    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = true;
        this.targetField = this.fieldLocator.getField();
        if (this.targetField != null) {
            this.targetField.setAccessible(false);
        }
        return this.targetField;
    }

    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }

    public Object getValue(Object object) {
        return Reflector.getFieldValue(object, this);
    }

    public void setValue(Object object) {
        Reflector.setFieldValue(null, this, object);
    }

    public void setValue(Object object, Object object2) {
        Reflector.setFieldValue(object, this, object2);
    }

    public boolean exists() {
        return this.getTargetField() != null;
    }

    @Override
    public void resolve() {
        Field field = this.getTargetField();
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import java.text.Format;
import java.util.Objects;

public class ConstrainedFieldPosition {
    private ConstraintType fConstraint;
    private Class<?> fClassConstraint;
    private Format.Field fField;
    private Object fValue;
    private int fStart;
    private int fLimit;
    private long fContext;
    static final boolean $assertionsDisabled = !ConstrainedFieldPosition.class.desiredAssertionStatus();

    public ConstrainedFieldPosition() {
        this.reset();
    }

    public void reset() {
        this.fConstraint = ConstraintType.NONE;
        this.fClassConstraint = Object.class;
        this.fField = null;
        this.fValue = null;
        this.fStart = 0;
        this.fLimit = 0;
        this.fContext = 0L;
    }

    public void constrainField(Format.Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Cannot constrain on null field");
        }
        this.fConstraint = ConstraintType.FIELD;
        this.fClassConstraint = Object.class;
        this.fField = field;
        this.fValue = null;
    }

    public void constrainClass(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Cannot constrain on null field class");
        }
        this.fConstraint = ConstraintType.CLASS;
        this.fClassConstraint = clazz;
        this.fField = null;
        this.fValue = null;
    }

    @Deprecated
    public void constrainFieldAndValue(Format.Field field, Object object) {
        this.fConstraint = ConstraintType.VALUE;
        this.fClassConstraint = Object.class;
        this.fField = field;
        this.fValue = object;
    }

    public Format.Field getField() {
        return this.fField;
    }

    public int getStart() {
        return this.fStart;
    }

    public int getLimit() {
        return this.fLimit;
    }

    public Object getFieldValue() {
        return this.fValue;
    }

    public long getInt64IterationContext() {
        return this.fContext;
    }

    public void setInt64IterationContext(long l) {
        this.fContext = l;
    }

    public void setState(Format.Field field, Object object, int n, int n2) {
        if (!$assertionsDisabled && !this.matchesField(field, object)) {
            throw new AssertionError();
        }
        this.fField = field;
        this.fValue = object;
        this.fStart = n;
        this.fLimit = n2;
    }

    public boolean matchesField(Format.Field field, Object object) {
        if (field == null) {
            throw new IllegalArgumentException("field must not be null");
        }
        switch (1.$SwitchMap$com$ibm$icu$text$ConstrainedFieldPosition$ConstraintType[this.fConstraint.ordinal()]) {
            case 1: {
                return false;
            }
            case 2: {
                return this.fClassConstraint.isAssignableFrom(field.getClass());
            }
            case 3: {
                return this.fField == field;
            }
            case 4: {
                return this.fField == field && Objects.equals(this.fValue, object);
            }
        }
        throw new AssertionError();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CFPos[");
        stringBuilder.append(this.fStart);
        stringBuilder.append('-');
        stringBuilder.append(this.fLimit);
        stringBuilder.append(' ');
        stringBuilder.append(this.fField);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static enum ConstraintType {
        NONE,
        CLASS,
        FIELD,
        VALUE;

    }
}


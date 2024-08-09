/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.util.Collection;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RecursiveToStringStyle
extends ToStringStyle {
    private static final long serialVersionUID = 1L;

    @Override
    public void appendDetail(StringBuffer stringBuffer, String string, Object object) {
        if (!ClassUtils.isPrimitiveWrapper(object.getClass()) && !String.class.equals(object.getClass()) && this.accept(object.getClass())) {
            stringBuffer.append(ReflectionToStringBuilder.toString(object, this));
        } else {
            super.appendDetail(stringBuffer, string, object);
        }
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, Collection<?> collection) {
        this.appendClassName(stringBuffer, collection);
        this.appendIdentityHashCode(stringBuffer, collection);
        this.appendDetail(stringBuffer, string, collection.toArray());
    }

    protected boolean accept(Class<?> clazz) {
        return false;
    }
}


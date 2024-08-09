/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.yaml.snakeyaml.introspector.Property;

public abstract class GenericProperty
extends Property {
    private final Type genType;
    private boolean actualClassesChecked;
    private Class<?>[] actualClasses;

    public GenericProperty(String string, Class<?> clazz, Type type) {
        super(string, clazz);
        this.genType = type;
        this.actualClassesChecked = type == null;
    }

    @Override
    public Class<?>[] getActualTypeArguments() {
        if (!this.actualClassesChecked) {
            Class clazz;
            if (this.genType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)this.genType;
                Type[] typeArray = parameterizedType.getActualTypeArguments();
                if (typeArray.length > 0) {
                    this.actualClasses = new Class[typeArray.length];
                    for (int i = 0; i < typeArray.length; ++i) {
                        if (typeArray[i] instanceof Class) {
                            this.actualClasses[i] = (Class)typeArray[i];
                            continue;
                        }
                        if (typeArray[i] instanceof ParameterizedType) {
                            this.actualClasses[i] = (Class)((ParameterizedType)typeArray[i]).getRawType();
                            continue;
                        }
                        if (typeArray[i] instanceof GenericArrayType) {
                            Type type = ((GenericArrayType)typeArray[i]).getGenericComponentType();
                            if (type instanceof Class) {
                                this.actualClasses[i] = Array.newInstance((Class)type, 0).getClass();
                                continue;
                            }
                            this.actualClasses = null;
                        } else {
                            this.actualClasses = null;
                        }
                        break;
                    }
                }
            } else if (this.genType instanceof GenericArrayType) {
                Type type = ((GenericArrayType)this.genType).getGenericComponentType();
                if (type instanceof Class) {
                    this.actualClasses = new Class[]{(Class)type};
                }
            } else if (this.genType instanceof Class && (clazz = (Class)this.genType).isArray()) {
                this.actualClasses = new Class[1];
                this.actualClasses[0] = this.getType().getComponentType();
            }
            this.actualClassesChecked = true;
        }
        return this.actualClasses;
    }
}


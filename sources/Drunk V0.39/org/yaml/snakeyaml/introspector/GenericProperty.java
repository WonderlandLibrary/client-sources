/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.introspector;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.yaml.snakeyaml.introspector.Property;

public abstract class GenericProperty
extends Property {
    private Type genType;
    private boolean actualClassesChecked;
    private Class<?>[] actualClasses;

    public GenericProperty(String name, Class<?> aClass, Type aType) {
        super(name, aClass);
        this.genType = aType;
        this.actualClassesChecked = aType == null;
    }

    @Override
    public Class<?>[] getActualTypeArguments() {
        block6: {
            Type[] actualTypeArguments;
            block7: {
                Class classType;
                block8: {
                    block5: {
                        if (this.actualClassesChecked) return this.actualClasses;
                        if (!(this.genType instanceof ParameterizedType)) break block5;
                        ParameterizedType parameterizedType = (ParameterizedType)this.genType;
                        actualTypeArguments = parameterizedType.getActualTypeArguments();
                        if (actualTypeArguments.length <= 0) break block6;
                        this.actualClasses = new Class[actualTypeArguments.length];
                        break block7;
                    }
                    if (!(this.genType instanceof GenericArrayType)) break block8;
                    Type componentType = ((GenericArrayType)this.genType).getGenericComponentType();
                    if (!(componentType instanceof Class)) break block6;
                    this.actualClasses = new Class[]{(Class)componentType};
                    break block6;
                }
                if (!(this.genType instanceof Class) || !(classType = (Class)this.genType).isArray()) break block6;
                this.actualClasses = new Class[1];
                this.actualClasses[0] = this.getType().getComponentType();
                break block6;
            }
            for (int i = 0; i < actualTypeArguments.length; ++i) {
                if (actualTypeArguments[i] instanceof Class) {
                    this.actualClasses[i] = (Class)actualTypeArguments[i];
                    continue;
                }
                if (actualTypeArguments[i] instanceof ParameterizedType) {
                    this.actualClasses[i] = (Class)((ParameterizedType)actualTypeArguments[i]).getRawType();
                    continue;
                }
                if (actualTypeArguments[i] instanceof GenericArrayType) {
                    Type componentType = ((GenericArrayType)actualTypeArguments[i]).getGenericComponentType();
                    if (componentType instanceof Class) {
                        this.actualClasses[i] = Array.newInstance((Class)componentType, 0).getClass();
                        continue;
                    }
                    this.actualClasses = null;
                    break;
                }
                this.actualClasses = null;
                break;
            }
        }
        this.actualClassesChecked = true;
        return this.actualClasses;
    }
}


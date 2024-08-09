/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
abstract class TypeVisitor {
    private final Set<Type> visited = Sets.newHashSet();

    TypeVisitor() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void visit(Type ... typeArray) {
        for (Type type : typeArray) {
            if (type == null || !this.visited.add(type)) continue;
            boolean bl = false;
            try {
                if (type instanceof TypeVariable) {
                    this.visitTypeVariable((TypeVariable)type);
                } else if (type instanceof WildcardType) {
                    this.visitWildcardType((WildcardType)type);
                } else if (type instanceof ParameterizedType) {
                    this.visitParameterizedType((ParameterizedType)type);
                } else if (type instanceof Class) {
                    this.visitClass((Class)type);
                } else if (type instanceof GenericArrayType) {
                    this.visitGenericArrayType((GenericArrayType)type);
                } else {
                    throw new AssertionError((Object)("Unknown type: " + type));
                }
                bl = true;
            } finally {
                if (!bl) {
                    this.visited.remove(type);
                }
            }
        }
    }

    void visitClass(Class<?> clazz) {
    }

    void visitGenericArrayType(GenericArrayType genericArrayType) {
    }

    void visitParameterizedType(ParameterizedType parameterizedType) {
    }

    void visitTypeVariable(TypeVariable<?> typeVariable) {
    }

    void visitWildcardType(WildcardType wildcardType) {
    }
}


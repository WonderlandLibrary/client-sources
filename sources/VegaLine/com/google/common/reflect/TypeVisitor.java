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
    public final void visit(Type ... types) {
        for (Type type2 : types) {
            if (type2 == null || !this.visited.add(type2)) continue;
            boolean succeeded = false;
            try {
                if (type2 instanceof TypeVariable) {
                    this.visitTypeVariable((TypeVariable)type2);
                } else if (type2 instanceof WildcardType) {
                    this.visitWildcardType((WildcardType)type2);
                } else if (type2 instanceof ParameterizedType) {
                    this.visitParameterizedType((ParameterizedType)type2);
                } else if (type2 instanceof Class) {
                    this.visitClass((Class)type2);
                } else if (type2 instanceof GenericArrayType) {
                    this.visitGenericArrayType((GenericArrayType)type2);
                } else {
                    throw new AssertionError((Object)("Unknown type: " + type2));
                }
                succeeded = true;
            } finally {
                if (!succeeded) {
                    this.visited.remove(type2);
                }
            }
        }
    }

    void visitClass(Class<?> t) {
    }

    void visitGenericArrayType(GenericArrayType t) {
    }

    void visitParameterizedType(ParameterizedType t) {
    }

    void visitTypeVariable(TypeVariable<?> t) {
    }

    void visitWildcardType(WildcardType t) {
    }
}


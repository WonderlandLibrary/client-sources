package com.google.gson.internal;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable
{
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;
    private static final long serialVersionUID = 0L;
    
    public ParameterizedTypeImpl(final Type ownerType, final Type rawType, final Type... typeArguments) {
        if (rawType instanceof Class) {
            final Class<?> rawTypeAsClass = (Class<?>)rawType;
            final boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
            $Gson$Preconditions.checkArgument(ownerType != null || isStaticOrTopLevelClass);
        }
        this.ownerType = ((ownerType == null) ? null : $Gson$Types.canonicalize(ownerType));
        this.rawType = $Gson$Types.canonicalize(rawType);
        this.typeArguments = typeArguments.clone();
        for (int t = 0, length = this.typeArguments.length; t < length; ++t) {
            $Gson$Preconditions.checkNotNull(this.typeArguments[t]);
            $Gson$Types.checkNotPrimitive(this.typeArguments[t]);
            this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
        }
    }
    
    @Override
    public Type[] getActualTypeArguments() {
        return this.typeArguments.clone();
    }
    
    @Override
    public Type getRawType() {
        return this.rawType;
    }
    
    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof ParameterizedType && $Gson$Types.equals(this, (Type)other);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
    }
    
    @Override
    public String toString() {
        final int length = this.typeArguments.length;
        if (length == 0) {
            return $Gson$Types.typeToString(this.rawType);
        }
        final StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
        stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
        for (int i = 1; i < length; ++i) {
            stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
        }
        return stringBuilder.append(">").toString();
    }
}

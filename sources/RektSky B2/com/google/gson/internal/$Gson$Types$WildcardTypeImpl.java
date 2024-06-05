package com.google.gson.internal;

import java.io.*;
import java.lang.reflect.*;

private static final class WildcardTypeImpl implements WildcardType, Serializable
{
    private final Type upperBound;
    private final Type lowerBound;
    private static final long serialVersionUID = 0L;
    
    public WildcardTypeImpl(final Type[] upperBounds, final Type[] lowerBounds) {
        $Gson$Preconditions.checkArgument(lowerBounds.length <= 1);
        $Gson$Preconditions.checkArgument(upperBounds.length == 1);
        if (lowerBounds.length == 1) {
            $Gson$Preconditions.checkNotNull(lowerBounds[0]);
            $Gson$Types.checkNotPrimitive(lowerBounds[0]);
            $Gson$Preconditions.checkArgument(upperBounds[0] == Object.class);
            this.lowerBound = $Gson$Types.canonicalize(lowerBounds[0]);
            this.upperBound = Object.class;
        }
        else {
            $Gson$Preconditions.checkNotNull(upperBounds[0]);
            $Gson$Types.checkNotPrimitive(upperBounds[0]);
            this.lowerBound = null;
            this.upperBound = $Gson$Types.canonicalize(upperBounds[0]);
        }
    }
    
    @Override
    public Type[] getUpperBounds() {
        return new Type[] { this.upperBound };
    }
    
    @Override
    public Type[] getLowerBounds() {
        return (this.lowerBound != null) ? new Type[] { this.lowerBound } : $Gson$Types.EMPTY_TYPE_ARRAY;
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof WildcardType && $Gson$Types.equals(this, (Type)other);
    }
    
    @Override
    public int hashCode() {
        return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound.hashCode();
    }
    
    @Override
    public String toString() {
        if (this.lowerBound != null) {
            return "? super " + $Gson$Types.typeToString(this.lowerBound);
        }
        if (this.upperBound == Object.class) {
            return "?";
        }
        return "? extends " + $Gson$Types.typeToString(this.upperBound);
    }
}

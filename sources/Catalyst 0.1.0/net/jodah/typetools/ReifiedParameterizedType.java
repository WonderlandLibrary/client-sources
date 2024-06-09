// 
// Decompiled by Procyon v0.5.36
// 

package net.jodah.typetools;

import java.util.Arrays;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

class ReifiedParameterizedType implements ParameterizedType
{
    private final ParameterizedType original;
    private final Type[] reifiedTypeArguments;
    
    ReifiedParameterizedType(final ParameterizedType original) {
        this.original = original;
        this.reifiedTypeArguments = new Type[original.getActualTypeArguments().length];
    }
    
    @Override
    public Type[] getActualTypeArguments() {
        return this.reifiedTypeArguments;
    }
    
    @Override
    public Type getRawType() {
        return this.original.getRawType();
    }
    
    @Override
    public Type getOwnerType() {
        return this.original.getOwnerType();
    }
    
    void setReifiedTypeArguments(final Type[] reifiedTypeArguments) {
        System.arraycopy(reifiedTypeArguments, 0, this.reifiedTypeArguments, 0, this.reifiedTypeArguments.length);
    }
    
    @Override
    public String toString() {
        final Type ownerType = this.getOwnerType();
        final Type rawType = this.getRawType();
        final Type[] actualTypeArguments = this.getActualTypeArguments();
        final StringBuilder sb = new StringBuilder();
        if (ownerType != null) {
            if (ownerType instanceof Class) {
                sb.append(((Class)ownerType).getName());
            }
            else {
                sb.append(ownerType.toString());
            }
            sb.append(".");
            if (ownerType instanceof ParameterizedType) {
                sb.append(rawType.getTypeName().replace(((ParameterizedType)ownerType).getRawType().getTypeName() + "$", ""));
            }
            else {
                sb.append(rawType.getTypeName());
            }
        }
        else {
            sb.append(rawType.getTypeName());
        }
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            sb.append("<");
            boolean first = true;
            for (final Type t : actualTypeArguments) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append((t == null) ? "null" : t.getTypeName());
                first = false;
            }
            sb.append(">");
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ReifiedParameterizedType that = (ReifiedParameterizedType)o;
        return this.original.equals(that.original) && Arrays.equals(this.reifiedTypeArguments, that.reifiedTypeArguments);
    }
    
    @Override
    public int hashCode() {
        int result = this.original.hashCode();
        result = 31 * result + Arrays.hashCode(this.reifiedTypeArguments);
        return result;
    }
}

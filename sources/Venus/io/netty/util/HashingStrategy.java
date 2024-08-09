/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

public interface HashingStrategy<T> {
    public static final HashingStrategy JAVA_HASHER = new HashingStrategy(){

        public int hashCode(Object object) {
            return object != null ? object.hashCode() : 0;
        }

        public boolean equals(Object object, Object object2) {
            return object == object2 || object != null && object.equals(object2);
        }
    };

    public int hashCode(T var1);

    public boolean equals(T var1, T var2);
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.ExtraObjectsMethodsForWeb;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible
public final class Objects
extends ExtraObjectsMethodsForWeb {
    private Objects() {
    }

    public static boolean equal(@Nullable Object object, @Nullable Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static int hashCode(@Nullable Object ... objectArray) {
        return Arrays.hashCode(objectArray);
    }
}


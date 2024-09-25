/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;
import us.myles.viaversion.libs.gson.internal.reflect.ReflectionAccessor;

final class PreJava9ReflectionAccessor
extends ReflectionAccessor {
    PreJava9ReflectionAccessor() {
    }

    @Override
    public void makeAccessible(AccessibleObject ao) {
        ao.setAccessible(true);
    }
}


/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import us.myles.viaversion.libs.gson.FieldAttributes;

public interface ExclusionStrategy {
    public boolean shouldSkipField(FieldAttributes var1);

    public boolean shouldSkipClass(Class<?> var1);
}


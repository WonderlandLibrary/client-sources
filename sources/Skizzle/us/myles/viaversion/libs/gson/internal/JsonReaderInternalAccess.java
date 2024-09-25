/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson.internal;

import java.io.IOException;
import us.myles.viaversion.libs.gson.stream.JsonReader;

public abstract class JsonReaderInternalAccess {
    public static JsonReaderInternalAccess INSTANCE;

    public abstract void promoteNameToValue(JsonReader var1) throws IOException;
}


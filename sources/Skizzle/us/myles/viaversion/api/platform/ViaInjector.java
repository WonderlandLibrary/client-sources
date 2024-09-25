/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.platform;

import us.myles.viaversion.libs.gson.JsonObject;

public interface ViaInjector {
    public void inject() throws Exception;

    public void uninject() throws Exception;

    public int getServerProtocolVersion() throws Exception;

    public String getEncoderName();

    public String getDecoderName();

    public JsonObject getDump();
}


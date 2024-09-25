/*
 * Decompiled with CFR 0.150.
 */
package com.github.creeper123123321.viafabric.util;

import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ProtocolUtils {
    public static String getProtocolName(int id) {
        if (!ProtocolVersion.isRegistered(id)) {
            return Integer.toString(id);
        }
        return ProtocolVersion.getProtocol(id).getName();
    }
}


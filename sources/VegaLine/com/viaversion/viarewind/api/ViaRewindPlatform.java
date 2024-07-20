/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.api;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.Protocol1_7_2_5To1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.logging.Logger;

public interface ViaRewindPlatform {
    public static final String VERSION = "3.0.0";
    public static final String IMPL_VERSION = "git-viarewind-common-3.0.0:c164228";

    default public void init(ViaRewindConfig config) {
        ViaRewind.init(this, config);
        Via.getManager().getSubPlatforms().add(IMPL_VERSION);
        ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol((Protocol)new Protocol1_8To1_9(), ProtocolVersion.v1_8, ProtocolVersion.v1_9);
        protocolManager.registerProtocol((Protocol)new Protocol1_7_6_10To1_8(), ProtocolVersion.v1_7_6, ProtocolVersion.v1_8);
        protocolManager.registerProtocol((Protocol)new Protocol1_7_2_5To1_7_6_10(), ProtocolVersion.v1_7_1, ProtocolVersion.v1_7_6);
    }

    public Logger getLogger();
}


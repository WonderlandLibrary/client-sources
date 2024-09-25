/*
 * Decompiled with CFR 0.150.
 */
package com.github.creeper123123321.viafabric.providers;

import com.github.creeper123123321.viafabric.ViaFabric;
import com.github.creeper123123321.viafabric.platform.VRClientSideUserConnection;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.base.VersionProvider;

public class VRVersionProvider
extends VersionProvider {
    @Override
    public int getServerProtocol(UserConnection connection) throws Exception {
        if (connection instanceof VRClientSideUserConnection) {
            return ViaFabric.clientSideVersion;
        }
        return super.getServerProtocol(connection);
    }
}


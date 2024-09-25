/*
 * Decompiled with CFR 0.150.
 */
package com.github.creeper123123321.viafabric.platform;

import com.github.creeper123123321.viafabric.platform.VRClientSideUserConnection;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;

public class VRConnectionManager
extends ViaConnectionManager {
    @Override
    public boolean isFrontEnd(UserConnection connection) {
        return !(connection instanceof VRClientSideUserConnection);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import dev.intave.vialoadingbase.ViaLoadingBase;

public class VLBBaseVersionProvider
extends BaseVersionProvider {
    @Override
    public int getClosestServerProtocol(UserConnection connection) throws Exception {
        if (connection.isClientSide()) {
            return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
        }
        return super.getClosestServerProtocol(connection);
    }
}


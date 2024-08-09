/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import via.ViaLoadingBase;

public class ViaBaseVersionProvider
extends BaseVersionProvider {
    @Override
    public int getClosestServerProtocol(UserConnection userConnection) throws Exception {
        if (userConnection.isClientSide()) {
            return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
        }
        return super.getClosestServerProtocol(userConnection);
    }
}


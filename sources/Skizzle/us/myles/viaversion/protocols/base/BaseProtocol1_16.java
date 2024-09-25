/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.base;

import java.util.UUID;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.base.BaseProtocol1_7;

public class BaseProtocol1_16
extends BaseProtocol1_7 {
    @Override
    protected UUID passthroughLoginUUID(PacketWrapper wrapper) throws Exception {
        return wrapper.passthrough(Type.UUID_INT_ARRAY);
    }
}


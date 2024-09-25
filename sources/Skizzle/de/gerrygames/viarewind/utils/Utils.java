/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.utils;

import java.util.UUID;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

public class Utils {
    public static UUID getUUID(UserConnection user) {
        return user.get(ProtocolInfo.class).getUuid();
    }
}


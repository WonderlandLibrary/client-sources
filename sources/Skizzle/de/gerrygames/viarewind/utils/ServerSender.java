/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.utils;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.protocol.Protocol;

public interface ServerSender {
    public void sendToServer(PacketWrapper var1, Class<? extends Protocol> var2, boolean var3, boolean var4) throws Exception;
}


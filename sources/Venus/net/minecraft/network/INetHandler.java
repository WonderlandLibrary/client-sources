/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;

public interface INetHandler {
    public void onDisconnect(ITextComponent var1);

    public NetworkManager getNetworkManager();
}


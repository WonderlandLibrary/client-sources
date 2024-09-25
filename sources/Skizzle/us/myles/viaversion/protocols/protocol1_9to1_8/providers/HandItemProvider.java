/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.providers;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.platform.providers.Provider;

public class HandItemProvider
implements Provider {
    public Item getHandItem(UserConnection info) {
        return new Item(0, 0, 0, null);
    }
}


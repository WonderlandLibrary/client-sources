/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import digital.rbq.utils.entity.ICheck;

public final class AliveCheck
implements ICheck {
    @Override
    public boolean validate(Entity entity) {
        return entity.isEntityAlive() || Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("mineplex");
    }
}


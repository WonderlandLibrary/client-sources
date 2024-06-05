/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import digital.rbq.utils.entity.ICheck;

public final class WallCheck
implements ICheck {
    @Override
    public boolean validate(Entity entity) {
        return Minecraft.getMinecraft().thePlayer.canEntityBeSeen(entity);
    }
}


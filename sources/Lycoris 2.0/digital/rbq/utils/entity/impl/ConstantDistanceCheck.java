/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import digital.rbq.utils.entity.ICheck;

public final class ConstantDistanceCheck
implements ICheck {
    private final float distance;

    public ConstantDistanceCheck(float distance) {
        this.distance = distance;
    }

    @Override
    public boolean validate(Entity entity) {
        return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= this.distance;
    }
}


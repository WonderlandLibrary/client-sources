/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.utils.entity.ICheck;

public final class DistanceCheck
implements ICheck {
    private final DoubleOption distance;

    public DistanceCheck(DoubleOption distance) {
        this.distance = distance;
    }

    @Override
    public boolean validate(Entity entity) {
        return (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= (Double)this.distance.getValue();
    }
}


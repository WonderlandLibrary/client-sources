/*
 * Decompiled with CFR 0.150.
 */
package me.superskidder.lune.utils.entity.impl;

import me.superskidder.lune.utils.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public final class VoidCheck
implements ICheck {
    @Override
    public boolean validate(Entity entity) {
        return this.isBlockUnder(entity);
    }

    private boolean isBlockUnder(Entity entity) {
        int offset = 0;
        while ((double)offset < entity.posY + (double)entity.getEyeHeight()) {
            AxisAlignedBB boundingBox = entity.getEntityBoundingBox().offset(0.0, -offset, 0.0);
            if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(entity, boundingBox).isEmpty()) {
                return true;
            }
            offset += 2;
        }
        return false;
    }
}


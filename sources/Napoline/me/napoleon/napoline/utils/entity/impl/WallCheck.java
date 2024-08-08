/*
 * Decompiled with CFR 0.150.
 */
package me.napoleon.napoline.utils.entity.impl;

import me.napoleon.napoline.utils.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class WallCheck
implements ICheck {
    @Override
    public boolean validate(Entity entity) {
        return Minecraft.getMinecraft().thePlayer.canEntityBeSeen(entity);
    }
}


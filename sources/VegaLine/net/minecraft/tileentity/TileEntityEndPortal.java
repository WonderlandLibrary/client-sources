/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityEndPortal
extends TileEntity {
    public boolean shouldRenderFace(EnumFacing p_184313_1_) {
        return p_184313_1_ == EnumFacing.UP;
    }
}


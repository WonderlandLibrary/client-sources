/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class EndPortalTileEntity
extends TileEntity {
    public EndPortalTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public EndPortalTileEntity() {
        this(TileEntityType.END_PORTAL);
    }

    public boolean shouldRenderFace(Direction direction) {
        return direction == Direction.UP;
    }
}


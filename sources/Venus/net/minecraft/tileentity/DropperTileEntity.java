/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DropperTileEntity
extends DispenserTileEntity {
    public DropperTileEntity() {
        super(TileEntityType.DROPPER);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.dropper");
    }
}


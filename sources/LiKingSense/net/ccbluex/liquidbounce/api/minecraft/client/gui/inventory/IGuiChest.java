/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IIInventory;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiChest;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiContainer;", "inventoryRows", "", "getInventoryRows", "()I", "lowerChestInventory", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IIInventory;", "getLowerChestInventory", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IIInventory;", "LiKingSense"})
public interface IGuiChest
extends IGuiContainer {
    public int getInventoryRows();

    @Nullable
    public IIInventory getLowerChestInventory();
}


package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GenericContainerScreenHandler.class)
public interface GenericContainerScreenHandlerAccessor {
    @Accessor
    Inventory getInventory();
}

package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;

class RenderItem$5 implements ItemMeshDefinition {
    @Override
    public ModelResourceLocation getModelLocation(final ItemStack p_178113_1_) {
        return ItemPotion.isSplash(p_178113_1_.getMetadata()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
    }
}
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.model.*;

class RenderItem$8 implements ItemMeshDefinition {
    @Override
    public ModelResourceLocation getModelLocation(final ItemStack p_178113_1_) {
        return new ModelResourceLocation("enchanted_book", "inventory");
    }
}
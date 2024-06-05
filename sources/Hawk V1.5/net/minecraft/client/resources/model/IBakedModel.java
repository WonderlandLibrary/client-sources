package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public interface IBakedModel {
   TextureAtlasSprite getTexture();

   List func_177550_a();

   boolean isAmbientOcclusionEnabled();

   boolean isGui3d();

   ItemCameraTransforms getItemCameraTransforms();

   List func_177551_a(EnumFacing var1);

   boolean isBuiltInRenderer();
}

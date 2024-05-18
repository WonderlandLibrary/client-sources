package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public interface IBakedModel {
  List<BakedQuad> getFaceQuads(EnumFacing paramEnumFacing);
  
  List<BakedQuad> getGeneralQuads();
  
  boolean isAmbientOcclusion();
  
  boolean isGui3d();
  
  boolean isBuiltInRenderer();
  
  TextureAtlasSprite getParticleTexture();
  
  ItemCameraTransforms getItemCameraTransforms();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\model\IBakedModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
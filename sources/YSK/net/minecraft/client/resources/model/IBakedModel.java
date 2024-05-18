package net.minecraft.client.resources.model;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;

public interface IBakedModel
{
    List<BakedQuad> getGeneralQuads();
    
    List<BakedQuad> getFaceQuads(final EnumFacing p0);
    
    ItemCameraTransforms getItemCameraTransforms();
    
    boolean isAmbientOcclusion();
    
    boolean isGui3d();
    
    boolean isBuiltInRenderer();
    
    TextureAtlasSprite getParticleTexture();
}

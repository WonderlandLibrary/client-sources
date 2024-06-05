package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;

public interface IBakedModel
{
    List func_177551_a(final EnumFacing p0);
    
    List func_177550_a();
    
    boolean isGui3d();
    
    boolean isAmbientOcclusionEnabled();
    
    boolean isBuiltInRenderer();
    
    TextureAtlasSprite getTexture();
    
    ItemCameraTransforms getItemCameraTransforms();
}

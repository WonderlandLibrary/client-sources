package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BuiltInModel implements IBakedModel
{
    private ItemCameraTransforms field_177557_a;
    
    public BuiltInModel(final ItemCameraTransforms p_i46086_1_) {
        this.field_177557_a = p_i46086_1_;
    }
    
    @Override
    public List func_177551_a(final EnumFacing p_177551_1_) {
        return null;
    }
    
    @Override
    public List func_177550_a() {
        return null;
    }
    
    @Override
    public boolean isGui3d() {
        return false;
    }
    
    @Override
    public boolean isAmbientOcclusionEnabled() {
        return true;
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }
    
    @Override
    public TextureAtlasSprite getTexture() {
        return null;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.field_177557_a;
    }
}

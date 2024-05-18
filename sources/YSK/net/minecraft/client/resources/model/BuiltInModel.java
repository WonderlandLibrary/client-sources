package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;

public class BuiltInModel implements IBakedModel
{
    private ItemCameraTransforms cameraTransforms;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return "".length() != 0;
    }
    
    @Override
    public List<BakedQuad> getFaceQuads(final EnumFacing enumFacing) {
        return null;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return " ".length() != 0;
    }
    
    public BuiltInModel(final ItemCameraTransforms cameraTransforms) {
        this.cameraTransforms = cameraTransforms;
    }
    
    @Override
    public boolean isGui3d() {
        return " ".length() != 0;
    }
    
    @Override
    public List<BakedQuad> getGeneralQuads() {
        return null;
    }
}

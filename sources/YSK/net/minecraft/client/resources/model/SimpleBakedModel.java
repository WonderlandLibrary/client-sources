package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;

public class SimpleBakedModel implements IBakedModel
{
    protected final ItemCameraTransforms cameraTransforms;
    protected final List<BakedQuad> generalQuads;
    protected final List<List<BakedQuad>> faceQuads;
    protected final TextureAtlasSprite texture;
    protected final boolean ambientOcclusion;
    protected final boolean gui3d;
    
    @Override
    public boolean isBuiltInRenderer() {
        return "".length() != 0;
    }
    
    @Override
    public List<BakedQuad> getGeneralQuads() {
        return this.generalQuads;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }
    
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public SimpleBakedModel(final List<BakedQuad> generalQuads, final List<List<BakedQuad>> faceQuads, final boolean ambientOcclusion, final boolean gui3d, final TextureAtlasSprite texture, final ItemCameraTransforms cameraTransforms) {
        this.generalQuads = generalQuads;
        this.faceQuads = faceQuads;
        this.ambientOcclusion = ambientOcclusion;
        this.gui3d = gui3d;
        this.texture = texture;
        this.cameraTransforms = cameraTransforms;
    }
    
    @Override
    public boolean isGui3d() {
        return this.gui3d;
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }
    
    @Override
    public List<BakedQuad> getFaceQuads(final EnumFacing enumFacing) {
        return this.faceQuads.get(enumFacing.ordinal());
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }
    
    public static class Builder
    {
        private ItemCameraTransforms builderCameraTransforms;
        private final List<List<BakedQuad>> builderFaceQuads;
        private boolean builderGui3d;
        private final List<BakedQuad> builderGeneralQuads;
        private static final String[] I;
        private final boolean builderAmbientOcclusion;
        private TextureAtlasSprite builderTexture;
        
        public Builder addGeneralQuad(final BakedQuad bakedQuad) {
            this.builderGeneralQuads.add(bakedQuad);
            return this;
        }
        
        public Builder addFaceQuad(final EnumFacing enumFacing, final BakedQuad bakedQuad) {
            this.builderFaceQuads.get(enumFacing.ordinal()).add(bakedQuad);
            return this;
        }
        
        private Builder(final boolean builderAmbientOcclusion, final boolean builderGui3d, final ItemCameraTransforms builderCameraTransforms) {
            this.builderGeneralQuads = (List<BakedQuad>)Lists.newArrayList();
            this.builderFaceQuads = (List<List<BakedQuad>>)Lists.newArrayListWithCapacity(0xD ^ 0xB);
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < length) {
                final EnumFacing enumFacing = values[i];
                this.builderFaceQuads.add(Lists.newArrayList());
                ++i;
            }
            this.builderAmbientOcclusion = builderAmbientOcclusion;
            this.builderGui3d = builderGui3d;
            this.builderCameraTransforms = builderCameraTransforms;
        }
        
        private void addGeneralBreakingFours(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            final Iterator<BakedQuad> iterator = bakedModel.getGeneralQuads().iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.addGeneralQuad(new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
        
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public IBakedModel makeBakedModel() {
            if (this.builderTexture == null) {
                throw new RuntimeException(Builder.I["".length()]);
            }
            return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
        }
        
        static {
            I();
        }
        
        public Builder setTexture(final TextureAtlasSprite builderTexture) {
            this.builderTexture = builderTexture;
            return this;
        }
        
        public Builder(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            this(bakedModel.isAmbientOcclusion(), bakedModel.isGui3d(), bakedModel.getItemCameraTransforms());
            this.builderTexture = bakedModel.getParticleTexture();
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
            while (i < length) {
                this.addFaceBreakingFours(bakedModel, textureAtlasSprite, values[i]);
                ++i;
            }
            this.addGeneralBreakingFours(bakedModel, textureAtlasSprite);
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0000\u0018\u0003+\"#\u0016P(*?\u0005\u0019;'(P", "MqpXK");
        }
        
        public Builder(final ModelBlock modelBlock) {
            this(modelBlock.isAmbientOcclusion(), modelBlock.isGui3d(), modelBlock.func_181682_g());
        }
        
        private void addFaceBreakingFours(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing) {
            final Iterator<BakedQuad> iterator = bakedModel.getFaceQuads(enumFacing).iterator();
            "".length();
            if (0 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.addFaceQuad(enumFacing, new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
    }
}

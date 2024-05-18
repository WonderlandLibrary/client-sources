package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class WeightedBakedModel implements IBakedModel
{
    private final int totalWeight;
    private final IBakedModel baseModel;
    private final List<MyWeighedRandomItem> models;
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.baseModel.getParticleTexture();
    }
    
    public WeightedBakedModel(final List<MyWeighedRandomItem> models) {
        this.models = models;
        this.totalWeight = WeightedRandom.getTotalWeight(models);
        this.baseModel = models.get("".length()).model;
    }
    
    public IBakedModel getAlternativeModel(final long n) {
        return WeightedRandom.getRandomItem(this.models, Math.abs((int)n >> (0x94 ^ 0x84)) % this.totalWeight).model;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.baseModel.getItemCameraTransforms();
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return this.baseModel.isBuiltInRenderer();
    }
    
    @Override
    public boolean isGui3d() {
        return this.baseModel.isGui3d();
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<BakedQuad> getGeneralQuads() {
        return this.baseModel.getGeneralQuads();
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return this.baseModel.isAmbientOcclusion();
    }
    
    @Override
    public List<BakedQuad> getFaceQuads(final EnumFacing enumFacing) {
        return this.baseModel.getFaceQuads(enumFacing);
    }
    
    public static class Builder
    {
        private List<MyWeighedRandomItem> listItems;
        
        public Builder add(final IBakedModel bakedModel, final int n) {
            this.listItems.add(new MyWeighedRandomItem(bakedModel, n));
            return this;
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public IBakedModel first() {
            return this.listItems.get("".length()).model;
        }
        
        public Builder() {
            this.listItems = (List<MyWeighedRandomItem>)Lists.newArrayList();
        }
        
        public WeightedBakedModel build() {
            Collections.sort(this.listItems);
            return new WeightedBakedModel(this.listItems);
        }
    }
    
    static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable<MyWeighedRandomItem>
    {
        protected final IBakedModel model;
        private static final String[] I;
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0018\u000e;/\u001e2\u001f\t.%4\u0019\b%\u001a\u001c\u0003\t'\f\"\u0012\u0005-\u001f!J", "UwlJw");
            MyWeighedRandomItem.I[" ".length()] = I("OP)\u0002-\u0006\u001cy", "cpDmI");
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
                if (1 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public MyWeighedRandomItem(final IBakedModel model, final int n) {
            super(n);
            this.model = model;
        }
        
        @Override
        public String toString() {
            return MyWeighedRandomItem.I["".length()] + this.itemWeight + MyWeighedRandomItem.I[" ".length()] + this.model + (char)(0x6C ^ 0x11);
        }
        
        static {
            I();
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((MyWeighedRandomItem)o);
        }
        
        protected int getCountQuads() {
            int size = this.model.getGeneralQuads().size();
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < length) {
                size += this.model.getFaceQuads(values[i]).size();
                ++i;
            }
            return size;
        }
        
        @Override
        public int compareTo(final MyWeighedRandomItem myWeighedRandomItem) {
            return ComparisonChain.start().compare(myWeighedRandomItem.itemWeight, this.itemWeight).compare(this.getCountQuads(), myWeighedRandomItem.getCountQuads()).result();
        }
    }
}

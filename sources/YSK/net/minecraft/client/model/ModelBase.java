package net.minecraft.client.model;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;

public abstract class ModelBase
{
    public int textureWidth;
    public List<ModelRenderer> boxList;
    private Map<String, TextureOffset> modelTextureMap;
    public int textureHeight;
    public boolean isRiding;
    public boolean isChild;
    public float swingProgress;
    
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
    }
    
    protected void setTextureOffset(final String s, final int n, final int n2) {
        this.modelTextureMap.put(s, new TextureOffset(n, n2));
    }
    
    public ModelRenderer getRandomModelBox(final Random random) {
        return this.boxList.get(random.nextInt(this.boxList.size()));
    }
    
    public TextureOffset getTextureOffset(final String s) {
        return this.modelTextureMap.get(s);
    }
    
    public ModelBase() {
        this.isChild = (" ".length() != 0);
        this.boxList = (List<ModelRenderer>)Lists.newArrayList();
        this.modelTextureMap = (Map<String, TextureOffset>)Maps.newHashMap();
        this.textureWidth = (0xFD ^ 0xBD);
        this.textureHeight = (0x5F ^ 0x7F);
    }
    
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setModelAttributes(final ModelBase modelBase) {
        this.swingProgress = modelBase.swingProgress;
        this.isRiding = modelBase.isRiding;
        this.isChild = modelBase.isChild;
    }
    
    public static void copyModelAngles(final ModelRenderer modelRenderer, final ModelRenderer modelRenderer2) {
        modelRenderer2.rotateAngleX = modelRenderer.rotateAngleX;
        modelRenderer2.rotateAngleY = modelRenderer.rotateAngleY;
        modelRenderer2.rotateAngleZ = modelRenderer.rotateAngleZ;
        modelRenderer2.rotationPointX = modelRenderer.rotationPointX;
        modelRenderer2.rotationPointY = modelRenderer.rotationPointY;
        modelRenderer2.rotationPointZ = modelRenderer.rotationPointZ;
    }
    
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
    }
}

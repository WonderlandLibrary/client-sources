package net.minecraft.src;

import java.util.*;

public abstract class ModelBase
{
    public float onGround;
    public boolean isRiding;
    public List boxList;
    public boolean isChild;
    private Map modelTextureMap;
    public int textureWidth;
    public int textureHeight;
    
    public ModelBase() {
        this.isRiding = false;
        this.boxList = new ArrayList();
        this.isChild = true;
        this.modelTextureMap = new HashMap();
        this.textureWidth = 64;
        this.textureHeight = 32;
    }
    
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
    }
    
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
    }
    
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
    }
    
    public ModelRenderer getRandomModelBox(final Random par1Random) {
        return this.boxList.get(par1Random.nextInt(this.boxList.size()));
    }
    
    protected void setTextureOffset(final String par1Str, final int par2, final int par3) {
        this.modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
    }
    
    public TextureOffset getTextureOffset(final String par1Str) {
        return this.modelTextureMap.get(par1Str);
    }
}

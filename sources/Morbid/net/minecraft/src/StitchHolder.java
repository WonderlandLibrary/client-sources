package net.minecraft.src;

public class StitchHolder implements Comparable
{
    private final Texture theTexture;
    private final int width;
    private final int height;
    private boolean rotated;
    private float scaleFactor;
    
    public StitchHolder(final Texture par1Texture) {
        this.scaleFactor = 1.0f;
        this.theTexture = par1Texture;
        this.width = par1Texture.getWidth();
        this.height = par1Texture.getHeight();
        this.rotated = (this.ceil16(this.height) > this.ceil16(this.width));
    }
    
    public Texture func_98150_a() {
        return this.theTexture;
    }
    
    public int getWidth() {
        return this.rotated ? this.ceil16((int)(this.height * this.scaleFactor)) : this.ceil16((int)(this.width * this.scaleFactor));
    }
    
    public int getHeight() {
        return this.rotated ? this.ceil16((int)(this.width * this.scaleFactor)) : this.ceil16((int)(this.height * this.scaleFactor));
    }
    
    public void rotate() {
        this.rotated = !this.rotated;
    }
    
    public boolean isRotated() {
        return this.rotated;
    }
    
    private int ceil16(final int par1) {
        final int var2 = TextureUtils.ceilPowerOfTwo(par1);
        return (var2 < 16) ? 16 : var2;
    }
    
    public void setNewDimension(final int par1) {
        if (this.width > par1 && this.height > par1) {
            this.scaleFactor = par1 / Math.min(this.width, this.height);
        }
    }
    
    @Override
    public String toString() {
        return "TextureHolder{width=" + this.width + ", height=" + this.height + '}';
    }
    
    public int compareToStitchHolder(final StitchHolder par1StitchHolder) {
        int var2;
        if (this.getHeight() == par1StitchHolder.getHeight()) {
            if (this.getWidth() == par1StitchHolder.getWidth()) {
                if (this.theTexture.getTextureName() == null) {
                    return (par1StitchHolder.theTexture.getTextureName() == null) ? 0 : -1;
                }
                return this.theTexture.getTextureName().compareTo(par1StitchHolder.theTexture.getTextureName());
            }
            else {
                var2 = ((this.getWidth() < par1StitchHolder.getWidth()) ? 1 : -1);
            }
        }
        else {
            var2 = ((this.getHeight() < par1StitchHolder.getHeight()) ? 1 : -1);
        }
        return var2;
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareToStitchHolder((StitchHolder)par1Obj);
    }
}

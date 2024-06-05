package net.minecraft.client.renderer.texture;

public static class Holder implements Comparable
{
    private final TextureAtlasSprite theTexture;
    private final int width;
    private final int height;
    private final int mipmapLevelHolder;
    private boolean rotated;
    private float scaleFactor;
    
    public Holder(final TextureAtlasSprite p_i45094_1_, final int p_i45094_2_) {
        this.scaleFactor = 1.0f;
        this.theTexture = p_i45094_1_;
        this.width = p_i45094_1_.getIconWidth();
        this.height = p_i45094_1_.getIconHeight();
        this.mipmapLevelHolder = p_i45094_2_;
        this.rotated = (Stitcher.access$000(this.height, p_i45094_2_) > Stitcher.access$000(this.width, p_i45094_2_));
    }
    
    public TextureAtlasSprite getAtlasSprite() {
        return this.theTexture;
    }
    
    public int getWidth() {
        return this.rotated ? Stitcher.access$000((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$000((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
    }
    
    public int getHeight() {
        return this.rotated ? Stitcher.access$000((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$000((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
    }
    
    public void rotate() {
        this.rotated = !this.rotated;
    }
    
    public boolean isRotated() {
        return this.rotated;
    }
    
    public void setNewDimension(final int p_94196_1_) {
        if (this.width > p_94196_1_ && this.height > p_94196_1_) {
            this.scaleFactor = p_94196_1_ / (float)Math.min(this.width, this.height);
        }
    }
    
    @Override
    public String toString() {
        return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.theTexture.getIconName() + '}';
    }
    
    public int compareTo(final Holder p_compareTo_1_) {
        int var2;
        if (this.getHeight() == p_compareTo_1_.getHeight()) {
            if (this.getWidth() == p_compareTo_1_.getWidth()) {
                if (this.theTexture.getIconName() == null) {
                    return (p_compareTo_1_.theTexture.getIconName() == null) ? 0 : -1;
                }
                return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
            }
            else {
                var2 = ((this.getWidth() < p_compareTo_1_.getWidth()) ? 1 : -1);
            }
        }
        else {
            var2 = ((this.getHeight() < p_compareTo_1_.getHeight()) ? 1 : -1);
        }
        return var2;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((Holder)p_compareTo_1_);
    }
}

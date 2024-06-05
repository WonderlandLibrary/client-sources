package ClassSub;

final class Class229 implements Class236.Class2
{
    final float val$scaleX;
    final float val$scaleY;
    final Class220 val$image;
    
    
    Class229(final float val$scaleX, final float val$scaleY, final Class220 val$image) {
        this.val$scaleX = val$scaleX;
        this.val$scaleY = val$scaleY;
        this.val$image = val$image;
    }
    
    @Override
    public float[] preRenderPoint(final Class186 class186, float n, float n2) {
        n -= class186.getMinX();
        n2 -= class186.getMinY();
        n /= class186.getMaxX() - class186.getMinX();
        n2 /= class186.getMaxY() - class186.getMinY();
        Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
        return null;
    }
}

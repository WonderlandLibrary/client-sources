package ClassSub;

final class Class3 implements Class236.Class2
{
    final float val$scaleX;
    final float val$scaleY;
    final Class220 val$image;
    
    
    Class3(final float val$scaleX, final float val$scaleY, final Class220 val$image) {
        this.val$scaleX = val$scaleX;
        this.val$scaleY = val$scaleY;
        this.val$image = val$image;
    }
    
    @Override
    public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
        Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
        return null;
    }
}

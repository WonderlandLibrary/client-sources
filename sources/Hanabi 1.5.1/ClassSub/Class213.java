package ClassSub;

final class Class213 implements Class236.Class2
{
    final Class173 val$fill;
    final float[] val$center;
    final float val$scaleX;
    final float val$scaleY;
    final Class220 val$image;
    
    
    Class213(final Class173 val$fill, final float[] val$center, final float val$scaleX, final float val$scaleY, final Class220 val$image) {
        this.val$fill = val$fill;
        this.val$center = val$center;
        this.val$scaleX = val$scaleX;
        this.val$scaleY = val$scaleY;
        this.val$image = val$image;
    }
    
    @Override
    public float[] preRenderPoint(final Class186 class186, float n, float n2) {
        this.val$fill.colorAt(class186, n - this.val$center[0], n2 - this.val$center[1]).bind();
        final Class224 offset = this.val$fill.getOffsetAt(class186, n, n2);
        n += offset.x;
        n2 += offset.y;
        Class236.access$000().glTexCoord2f(this.val$image.getTextureOffsetX() + this.val$image.getTextureWidth() * (n * this.val$scaleX), this.val$image.getTextureOffsetY() + this.val$image.getTextureHeight() * (n2 * this.val$scaleY));
        return new float[] { offset.x + n, offset.y + n2 };
    }
}

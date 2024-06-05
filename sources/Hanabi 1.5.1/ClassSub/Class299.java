package ClassSub;

final class Class299 implements Class236.Class2
{
    final Class316 val$gen;
    
    
    Class299(final Class316 val$gen) {
        this.val$gen = val$gen;
    }
    
    @Override
    public float[] preRenderPoint(final Class186 class186, final float n, final float n2) {
        final Class224 coord = this.val$gen.getCoordFor(n, n2);
        Class236.access$000().glTexCoord2f(coord.x, coord.y);
        return new float[] { n, n2 };
    }
}

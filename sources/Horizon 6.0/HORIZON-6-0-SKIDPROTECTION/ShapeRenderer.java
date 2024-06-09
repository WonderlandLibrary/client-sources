package HORIZON-6-0-SKIDPROTECTION;

public final class ShapeRenderer
{
    private static SGL HorizonCode_Horizon_È;
    private static LineStripRenderer Â;
    
    static {
        ShapeRenderer.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        ShapeRenderer.Â = Renderer.Â();
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape) {
        final Texture t = TextureImpl.Å();
        TextureImpl.£à();
        final float[] points = shape.ŠÄ();
        ShapeRenderer.Â.Â();
        for (int i = 0; i < points.length; i += 2) {
            ShapeRenderer.Â.HorizonCode_Horizon_È(points[i], points[i + 1]);
        }
        if (shape.à()) {
            ShapeRenderer.Â.HorizonCode_Horizon_È(points[0], points[1]);
        }
        ShapeRenderer.Â.HorizonCode_Horizon_È();
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape, final ShapeFill fill) {
        final float[] points = shape.ŠÄ();
        final Texture t = TextureImpl.Å();
        TextureImpl.£à();
        final float[] center = shape.Ý();
        ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3);
        for (int i = 0; i < points.length; i += 2) {
            fill.HorizonCode_Horizon_È(shape, points[i], points[i + 1]).HorizonCode_Horizon_È();
            final Vector2f offset = fill.Â(shape, points[i], points[i + 1]);
            ShapeRenderer.HorizonCode_Horizon_È.Â(points[i] + offset.HorizonCode_Horizon_È, points[i + 1] + offset.Â);
        }
        if (shape.à()) {
            fill.HorizonCode_Horizon_È(shape, points[0], points[1]).HorizonCode_Horizon_È();
            final Vector2f offset2 = fill.Â(shape, points[0], points[1]);
            ShapeRenderer.HorizonCode_Horizon_È.Â(points[0] + offset2.HorizonCode_Horizon_È, points[1] + offset2.Â);
        }
        ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static boolean Â(final Shape shape) {
        return shape.Ê() != null && shape.Ê().Ý() != 0;
    }
    
    public static final void Ý(final Shape shape) {
        if (!Â(shape)) {
            return;
        }
        final Texture t = TextureImpl.Å();
        TextureImpl.£à();
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, final float x, final float y) {
                return null;
            }
        });
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    private static final void HorizonCode_Horizon_È(final Shape shape, final HorizonCode_Horizon_È callback) {
        final Triangulator tris = shape.Ê();
        ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(4);
        for (int i = 0; i < tris.Ý(); ++i) {
            for (int p = 0; p < 3; ++p) {
                final float[] pt = tris.HorizonCode_Horizon_È(i, p);
                final float[] np = callback.HorizonCode_Horizon_È(shape, pt[0], pt[1]);
                if (np == null) {
                    ShapeRenderer.HorizonCode_Horizon_È.Â(pt[0], pt[1]);
                }
                else {
                    ShapeRenderer.HorizonCode_Horizon_È.Â(np[0], np[1]);
                }
            }
        }
        ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape, final Image image) {
        HorizonCode_Horizon_È(shape, image, 0.01f, 0.01f);
    }
    
    public static final void Â(final Shape shape, final Image image) {
        Â(shape, image, 1.0f, 1.0f);
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape, final Image image, final float scaleX, final float scaleY) {
        if (!Â(shape)) {
            return;
        }
        final Texture t = TextureImpl.Å();
        image.áŒŠÆ().Ý();
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, final float x, final float y) {
                float tx = x * scaleX;
                float ty = y * scaleY;
                tx = image.Ø­à() + image.Æ() * tx;
                ty = image.µÕ() + image.Šáƒ() * ty;
                ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tx, ty);
                return null;
            }
        });
        final float[] points = shape.ŠÄ();
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static final void Â(final Shape shape, final Image image, final float scaleX, final float scaleY) {
        if (!Â(shape)) {
            return;
        }
        final float[] points = shape.ŠÄ();
        final Texture t = TextureImpl.Å();
        image.áŒŠÆ().Ý();
        final float minX = shape.£á();
        final float minY = shape.Å();
        final float maxX = shape.µÕ() - minX;
        final float maxY = shape.Æ() - minY;
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, float x, float y) {
                x -= shape.Šáƒ();
                y -= shape.Ï­Ðƒà();
                x /= shape.µÕ() - shape.Šáƒ();
                y /= shape.Æ() - shape.Ï­Ðƒà();
                float tx = x * scaleX;
                float ty = y * scaleY;
                tx = image.Ø­à() + image.Æ() * tx;
                ty = image.µÕ() + image.Šáƒ() * ty;
                ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tx, ty);
                return null;
            }
        });
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static final void Â(final Shape shape, final ShapeFill fill) {
        if (!Â(shape)) {
            return;
        }
        final Texture t = TextureImpl.Å();
        TextureImpl.£à();
        final float[] center = shape.Ý();
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, final float x, final float y) {
                fill.HorizonCode_Horizon_È(shape, x, y).HorizonCode_Horizon_È();
                final Vector2f offset = fill.Â(shape, x, y);
                return new float[] { offset.HorizonCode_Horizon_È + x, offset.Â + y };
            }
        });
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape, final Image image, final float scaleX, final float scaleY, final ShapeFill fill) {
        if (!Â(shape)) {
            return;
        }
        final Texture t = TextureImpl.Å();
        image.áŒŠÆ().Ý();
        final float[] center = shape.Ý();
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, float x, float y) {
                fill.HorizonCode_Horizon_È(shape, x - center[0], y - center[1]).HorizonCode_Horizon_È();
                final Vector2f offset = fill.Â(shape, x, y);
                x += offset.HorizonCode_Horizon_È;
                y += offset.Â;
                float tx = x * scaleX;
                float ty = y * scaleY;
                tx = image.Ø­à() + image.Æ() * tx;
                ty = image.µÕ() + image.Šáƒ() * ty;
                ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tx, ty);
                return new float[] { offset.HorizonCode_Horizon_È + x, offset.Â + y };
            }
        });
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    public static final void HorizonCode_Horizon_È(final Shape shape, final Image image, final TexCoordGenerator gen) {
        final Texture t = TextureImpl.Å();
        image.áŒŠÆ().Ý();
        final float[] center = shape.Ý();
        HorizonCode_Horizon_È(shape, new HorizonCode_Horizon_È() {
            @Override
            public float[] HorizonCode_Horizon_È(final Shape shape, final float x, final float y) {
                final Vector2f tex = gen.HorizonCode_Horizon_È(x, y);
                ShapeRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tex.HorizonCode_Horizon_È, tex.Â);
                return new float[] { x, y };
            }
        });
        if (t == null) {
            TextureImpl.£à();
        }
        else {
            t.Ý();
        }
    }
    
    private interface HorizonCode_Horizon_È
    {
        float[] HorizonCode_Horizon_È(final Shape p0, final float p1, final float p2);
    }
}

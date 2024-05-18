package org.newdawn.slick.geom;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;





public final class ShapeRenderer
{
  private static SGL GL = ;
  
  private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
  


  public ShapeRenderer() {}
  

  public static final void draw(Shape shape)
  {
    Texture t = TextureImpl.getLastBind();
    TextureImpl.bindNone();
    
    float[] points = shape.getPoints();
    
    LSR.start();
    for (int i = 0; i < points.length; i += 2) {
      LSR.vertex(points[i], points[(i + 1)]);
    }
    
    if (shape.closed()) {
      LSR.vertex(points[0], points[1]);
    }
    
    LSR.end();
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  






  public static final void draw(Shape shape, ShapeFill fill)
  {
    float[] points = shape.getPoints();
    
    Texture t = TextureImpl.getLastBind();
    TextureImpl.bindNone();
    
    float[] center = shape.getCenter();
    GL.glBegin(3);
    for (int i = 0; i < points.length; i += 2) {
      fill.colorAt(shape, points[i], points[(i + 1)]).bind();
      Vector2f offset = fill.getOffsetAt(shape, points[i], points[(i + 1)]);
      GL.glVertex2f(points[i] + x, points[(i + 1)] + y);
    }
    
    if (shape.closed()) {
      fill.colorAt(shape, points[0], points[1]).bind();
      Vector2f offset = fill.getOffsetAt(shape, points[0], points[1]);
      GL.glVertex2f(points[0] + x, points[1] + y);
    }
    GL.glEnd();
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  





  public static boolean validFill(Shape shape)
  {
    if (shape.getTriangles() == null) {
      return false;
    }
    return shape.getTriangles().getTriangleCount() != 0;
  }
  





  public static final void fill(Shape shape)
  {
    if (!validFill(shape)) {
      return;
    }
    
    Texture t = TextureImpl.getLastBind();
    TextureImpl.bindNone();
    
    fill(shape, new PointCallback()
    {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        return null;
      }
    });
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  






  private static final void fill(Shape shape, PointCallback callback)
  {
    Triangulator tris = shape.getTriangles();
    
    GL.glBegin(4);
    for (int i = 0; i < tris.getTriangleCount(); i++) {
      for (int p = 0; p < 3; p++) {
        float[] pt = tris.getTrianglePoint(i, p);
        float[] np = callback.preRenderPoint(shape, pt[0], pt[1]);
        
        if (np == null) {
          GL.glVertex2f(pt[0], pt[1]);
        } else {
          GL.glVertex2f(np[0], np[1]);
        }
      }
    }
    GL.glEnd();
  }
  






  public static final void texture(Shape shape, Image image)
  {
    texture(shape, image, 0.01F, 0.01F);
  }
  







  public static final void textureFit(Shape shape, Image image)
  {
    textureFit(shape, image, 1.0F, 1.0F);
  }
  








  public static final void texture(Shape shape, final Image image, float scaleX, final float scaleY)
  {
    if (!validFill(shape)) {
      return;
    }
    
    Texture t = TextureImpl.getLastBind();
    image.getTexture().bind();
    
    fill(shape, new PointCallback() {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        float tx = x * val$scaleX;
        float ty = y * scaleY;
        
        tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
        ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
        
        ShapeRenderer.GL.glTexCoord2f(tx, ty);
        return null;
      }
      
    });
    float[] points = shape.getPoints();
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  









  public static final void textureFit(Shape shape, final Image image, float scaleX, final float scaleY)
  {
    if (!validFill(shape)) {
      return;
    }
    
    float[] points = shape.getPoints();
    
    Texture t = TextureImpl.getLastBind();
    image.getTexture().bind();
    
    float minX = shape.getX();
    float minY = shape.getY();
    float maxX = shape.getMaxX() - minX;
    float maxY = shape.getMaxY() - minY;
    
    fill(shape, new PointCallback() {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        x -= shape.getMinX();
        y -= shape.getMinY();
        
        x /= (shape.getMaxX() - shape.getMinX());
        y /= (shape.getMaxY() - shape.getMinY());
        
        float tx = x * val$scaleX;
        float ty = y * scaleY;
        
        tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
        ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
        
        ShapeRenderer.GL.glTexCoord2f(tx, ty);
        return null;
      }
    });
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  






  public static final void fill(Shape shape, ShapeFill fill)
  {
    if (!validFill(shape)) {
      return;
    }
    
    Texture t = TextureImpl.getLastBind();
    TextureImpl.bindNone();
    
    float[] center = shape.getCenter();
    fill(shape, new PointCallback() {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        colorAt(shape, x, y).bind();
        Vector2f offset = getOffsetAt(shape, x, y);
        
        return new float[] { x + x, y + y };
      }
    });
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  










  public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY, ShapeFill fill)
  {
    if (!validFill(shape)) {
      return;
    }
    
    Texture t = TextureImpl.getLastBind();
    image.getTexture().bind();
    
    final float[] center = shape.getCenter();
    fill(shape, new PointCallback() {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        colorAt(shape, x - center[0], y - center[1]).bind();
        Vector2f offset = getOffsetAt(shape, x, y);
        
        x += x;
        y += y;
        
        float tx = x * scaleX;
        float ty = y * scaleY;
        
        tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
        ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
        
        ShapeRenderer.GL.glTexCoord2f(tx, ty);
        
        return new float[] { x + x, y + y };
      }
    });
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  






  public static final void texture(Shape shape, Image image, TexCoordGenerator gen)
  {
    Texture t = TextureImpl.getLastBind();
    
    image.getTexture().bind();
    
    float[] center = shape.getCenter();
    fill(shape, new PointCallback() {
      public float[] preRenderPoint(Shape shape, float x, float y) {
        Vector2f tex = getCoordFor(x, y);
        ShapeRenderer.GL.glTexCoord2f(x, y);
        
        return new float[] { x, y };
      }
    });
    
    if (t == null) {
      TextureImpl.bindNone();
    } else {
      t.bind();
    }
  }
  
  private static abstract interface PointCallback
  {
    public abstract float[] preRenderPoint(Shape paramShape, float paramFloat1, float paramFloat2);
  }
}

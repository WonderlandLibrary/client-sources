package org.newdawn.slick;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract interface ShapeFill
{
  public abstract Color colorAt(Shape paramShape, float paramFloat1, float paramFloat2);
  
  public abstract Vector2f getOffsetAt(Shape paramShape, float paramFloat1, float paramFloat2);
}

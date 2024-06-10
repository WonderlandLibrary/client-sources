package org.newdawn.slick;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract interface ShapeFill
{
  public abstract Color colorAt(Shape paramShape, float paramFloat1, float paramFloat2);
  
  public abstract Vector2f getOffsetAt(Shape paramShape, float paramFloat1, float paramFloat2);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.ShapeFill
 * JD-Core Version:    0.7.0.1
 */
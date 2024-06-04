package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;








































public final class SharedDrawable
  extends DrawableGL
{
  public SharedDrawable(Drawable drawable)
    throws LWJGLException
  {
    context = ((ContextGL)((DrawableLWJGL)drawable).createSharedContext());
  }
  
  public ContextGL createSharedContext() {
    throw new UnsupportedOperationException();
  }
}

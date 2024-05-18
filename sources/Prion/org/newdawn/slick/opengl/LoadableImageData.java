package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract interface LoadableImageData
  extends ImageData
{
  public abstract void configureEdging(boolean paramBoolean);
  
  public abstract ByteBuffer loadImage(InputStream paramInputStream)
    throws IOException;
  
  public abstract ByteBuffer loadImage(InputStream paramInputStream, boolean paramBoolean, int[] paramArrayOfInt)
    throws IOException;
  
  public abstract ByteBuffer loadImage(InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfInt)
    throws IOException;
}

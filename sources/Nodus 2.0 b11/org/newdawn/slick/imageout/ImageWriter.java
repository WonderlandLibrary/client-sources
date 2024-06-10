package org.newdawn.slick.imageout;

import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Image;

public abstract interface ImageWriter
{
  public abstract void saveImage(Image paramImage, String paramString, OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException;
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.imageout.ImageWriter
 * JD-Core Version:    0.7.0.1
 */
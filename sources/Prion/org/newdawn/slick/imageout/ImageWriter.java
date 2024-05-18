package org.newdawn.slick.imageout;

import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Image;

public abstract interface ImageWriter
{
  public abstract void saveImage(Image paramImage, String paramString, OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException;
}

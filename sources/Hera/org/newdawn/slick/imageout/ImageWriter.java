package org.newdawn.slick.imageout;

import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Image;

public interface ImageWriter {
  void saveImage(Image paramImage, String paramString, OutputStream paramOutputStream, boolean paramBoolean) throws IOException;
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\imageout\ImageWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
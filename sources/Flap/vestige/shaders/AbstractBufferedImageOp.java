package vestige.shaders;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.Hashtable;

public abstract class AbstractBufferedImageOp implements BufferedImageOp, Cloneable {
   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
      if (dstCM == null) {
         dstCM = src.getColorModel();
      }

      return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), (Hashtable)null);
   }

   public Rectangle2D getBounds2D(BufferedImage src) {
      return new Rectangle(0, 0, src.getWidth(), src.getHeight());
   }

   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
      return null;
   }

   public RenderingHints getRenderingHints() {
      return null;
   }

   public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
      int type = image.getType();
      return type != 2 && type != 1 ? image.getRGB(x, y, width, height, pixels, 0, width) : (int[])image.getRaster().getDataElements(x, y, width, height, pixels);
   }

   public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
      int type = image.getType();
      if (type != 2 && type != 1) {
         image.setRGB(x, y, width, height, pixels, 0, width);
      } else {
         image.getRaster().setDataElements(x, y, width, height, pixels);
      }

   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }
}

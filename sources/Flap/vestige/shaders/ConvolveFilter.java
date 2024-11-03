package vestige.shaders;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import org.jetbrains.annotations.NotNull;

public class ConvolveFilter extends AbstractBufferedImageOp {
   public static int CLAMP_EDGES = 1;
   public static int WRAP_EDGES = 2;
   protected Kernel kernel;
   protected boolean alpha;
   protected boolean premultiplyAlpha;
   private final int edgeAction;

   public ConvolveFilter() {
      this(new float[9]);
   }

   public ConvolveFilter(float[] matrix) {
      this(new Kernel(3, 3, matrix));
   }

   public ConvolveFilter(Kernel kernel) {
      this.alpha = true;
      this.premultiplyAlpha = true;
      this.edgeAction = CLAMP_EDGES;
      this.kernel = kernel;
   }

   public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      if (kernel.getHeight() == 1) {
         convolveH(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      } else if (kernel.getWidth() == 1) {
         convolveV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      } else {
         convolveHV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      }

   }

   public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int rows = kernel.getHeight();
      int cols = kernel.getWidth();
      int rows2 = rows / 2;
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;

            int row;
            int ioffset;
            int iy;
            int moffset;
            for(row = -rows2; row <= rows2; ++row) {
               iy = y + row;
               if (0 <= iy && iy < height) {
                  ioffset = iy * width;
               } else if (edgeAction == CLAMP_EDGES) {
                  ioffset = y * width;
               } else {
                  if (edgeAction != WRAP_EDGES) {
                     continue;
                  }

                  ioffset = (iy + height) % height * width;
               }

               moffset = cols * (row + rows2) + cols2;

               for(int col = -cols2; col <= cols2; ++col) {
                  float f = matrix[moffset + col];
                  if (f != 0.0F) {
                     int ix = x + col;
                     if (0 > ix || ix >= width) {
                        if (edgeAction == CLAMP_EDGES) {
                           ix = x;
                        } else {
                           if (edgeAction != WRAP_EDGES) {
                              continue;
                           }

                           ix = (x + width) % width;
                        }
                     }

                     int rgb = inPixels[ioffset + ix];
                     a += f * (float)(rgb >> 24 & 255);
                     r += f * (float)(rgb >> 16 & 255);
                     g += f * (float)(rgb >> 8 & 255);
                     b += f * (float)(rgb & 255);
                  }
               }
            }

            row = alpha ? clamp((int)((double)a + 0.5D)) : 255;
            ioffset = clamp((int)((double)r + 0.5D));
            iy = clamp((int)((double)g + 0.5D));
            moffset = clamp((int)((double)b + 0.5D));
            outPixels[index++] = row << 24 | ioffset << 16 | iy << 8 | moffset;
         }
      }

   }

   public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int cols = kernel.getWidth();
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
         int ioffset = y * width;

         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;

            int col;
            int ix;
            int rgb;
            for(col = -cols2; col <= cols2; ++col) {
               float f = matrix[cols2 + col];
               if (f != 0.0F) {
                  ix = x + col;
                  if (ix < 0) {
                     if (edgeAction == CLAMP_EDGES) {
                        ix = 0;
                     } else if (edgeAction == WRAP_EDGES) {
                        ix = (x + width) % width;
                     }
                  } else if (ix >= width) {
                     if (edgeAction == CLAMP_EDGES) {
                        ix = width - 1;
                     } else if (edgeAction == WRAP_EDGES) {
                        ix = (x + width) % width;
                     }
                  }

                  rgb = inPixels[ioffset + ix];
                  a += f * (float)(rgb >> 24 & 255);
                  r += f * (float)(rgb >> 16 & 255);
                  g += f * (float)(rgb >> 8 & 255);
                  b += f * (float)(rgb & 255);
               }
            }

            col = alpha ? clamp((int)((double)a + 0.5D)) : 255;
            int ir = clamp((int)((double)r + 0.5D));
            ix = clamp((int)((double)g + 0.5D));
            rgb = clamp((int)((double)b + 0.5D));
            outPixels[index++] = col << 24 | ir << 16 | ix << 8 | rgb;
         }
      }

   }

   public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int rows = kernel.getHeight();
      int rows2 = rows / 2;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;

            int row;
            int ioffset;
            int iy;
            for(row = -rows2; row <= rows2; ++row) {
               iy = y + row;
               if (iy < 0) {
                  if (edgeAction == CLAMP_EDGES) {
                     ioffset = 0;
                  } else if (edgeAction == WRAP_EDGES) {
                     ioffset = (y + height) % height * width;
                  } else {
                     ioffset = iy * width;
                  }
               } else if (iy >= height) {
                  if (edgeAction == CLAMP_EDGES) {
                     ioffset = (height - 1) * width;
                  } else if (edgeAction == WRAP_EDGES) {
                     ioffset = (y + height) % height * width;
                  } else {
                     ioffset = iy * width;
                  }
               } else {
                  ioffset = iy * width;
               }

               float f = matrix[row + rows2];
               if (f != 0.0F) {
                  int rgb = inPixels[ioffset + x];
                  a += f * (float)(rgb >> 24 & 255);
                  r += f * (float)(rgb >> 16 & 255);
                  g += f * (float)(rgb >> 8 & 255);
                  b += f * (float)(rgb & 255);
               }
            }

            row = alpha ? clamp((int)((double)a + 0.5D)) : 255;
            ioffset = clamp((int)((double)r + 0.5D));
            iy = clamp((int)((double)g + 0.5D));
            int ib = clamp((int)((double)b + 0.5D));
            outPixels[index++] = row << 24 | ioffset << 16 | iy << 8 | ib;
         }
      }

   }

   public static int clamp(int c) {
      return c < 0 ? 0 : Math.min(c, 255);
   }

   public BufferedImage filter(@NotNull BufferedImage src, BufferedImage dst) {
      if (src == null) {
         $$$reportNull$$$0(0);
      }

      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = this.createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      this.getRGB(src, 0, 0, width, height, inPixels);
      if (this.premultiplyAlpha) {
         ImageMath.premultiply(inPixels, 0, inPixels.length);
      }

      convolve(this.kernel, inPixels, outPixels, width, height, this.alpha, this.edgeAction);
      if (this.premultiplyAlpha) {
         ImageMath.unpremultiply(outPixels, 0, outPixels.length);
      }

      this.setRGB(dst, 0, 0, width, height, outPixels);
      return dst;
   }

   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
      return super.createCompatibleDestImage(src, dstCM);
   }

   public Rectangle2D getBounds2D(BufferedImage src) {
      return super.getBounds2D(src);
   }

   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
      return super.getPoint2D(srcPt, dstPt);
   }

   public RenderingHints getRenderingHints() {
      return super.getRenderingHints();
   }

   public String toString() {
      return "Blur/Convolve...";
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "src", "vestige/shaders/ConvolveFilter", "filter"));
   }
}

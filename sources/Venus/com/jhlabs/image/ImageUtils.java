/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public abstract class ImageUtils {
    private static BufferedImage backgroundImage = null;

    public static BufferedImage createImage(ImageProducer imageProducer) {
        PixelGrabber pixelGrabber = new PixelGrabber(imageProducer, 0, 0, -1, -1, null, 0, 0);
        try {
            pixelGrabber.grabPixels();
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException("Image fetch interrupted");
        }
        if ((pixelGrabber.status() & 0x80) != 0) {
            throw new RuntimeException("Image fetch aborted");
        }
        if ((pixelGrabber.status() & 0x40) != 0) {
            throw new RuntimeException("Image fetch error");
        }
        BufferedImage bufferedImage = new BufferedImage(pixelGrabber.getWidth(), pixelGrabber.getHeight(), 2);
        bufferedImage.setRGB(0, 0, pixelGrabber.getWidth(), pixelGrabber.getHeight(), (int[])pixelGrabber.getPixels(), 0, pixelGrabber.getWidth());
        return bufferedImage;
    }

    public static BufferedImage convertImageToARGB(Image image) {
        if (image instanceof BufferedImage && ((BufferedImage)image).getType() == 2) {
            return (BufferedImage)image;
        }
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

    public static BufferedImage getSubimage(BufferedImage bufferedImage, int n, int n2, int n3, int n4) {
        BufferedImage bufferedImage2 = new BufferedImage(n3, n4, 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawRenderedImage(bufferedImage, AffineTransform.getTranslateInstance(-n, -n2));
        graphics2D.dispose();
        return bufferedImage2;
    }

    public static BufferedImage cloneImage(BufferedImage bufferedImage) {
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.drawRenderedImage(bufferedImage, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public static void paintCheckedBackground(Component component, Graphics graphics, int n, int n2, int n3, int n4) {
        int n5;
        Object object;
        if (backgroundImage == null) {
            backgroundImage = new BufferedImage(64, 64, 2);
            object = backgroundImage.createGraphics();
            for (int i = 0; i < 64; i += 8) {
                for (n5 = 0; n5 < 64; n5 += 8) {
                    ((Graphics)object).setColor(((n5 ^ i) & 8) != 0 ? Color.lightGray : Color.white);
                    ((Graphics)object).fillRect(n5, i, 8, 8);
                }
            }
            ((Graphics)object).dispose();
        }
        if (backgroundImage != null) {
            object = graphics.getClip();
            Rectangle rectangle = graphics.getClipBounds();
            if (rectangle == null) {
                rectangle = new Rectangle(component.getSize());
            }
            rectangle = rectangle.intersection(new Rectangle(n, n2, n3, n4));
            graphics.setClip(rectangle);
            n5 = backgroundImage.getWidth();
            int n6 = backgroundImage.getHeight();
            if (n5 != -1 && n6 != -1) {
                int n7 = rectangle.x / n5 * n5;
                int n8 = rectangle.y / n6 * n6;
                int n9 = (rectangle.x + rectangle.width + n5 - 1) / n5 * n5;
                int n10 = (rectangle.y + rectangle.height + n6 - 1) / n6 * n6;
                for (n2 = n8; n2 < n10; n2 += n6) {
                    for (n = n7; n < n9; n += n5) {
                        graphics.drawImage(backgroundImage, n, n2, component);
                    }
                }
            }
            graphics.setClip((Shape)object);
        }
    }

    public static Rectangle getSelectedBounds(BufferedImage bufferedImage) {
        int n;
        int n2;
        int n3 = bufferedImage.getWidth();
        int n4 = bufferedImage.getHeight();
        int n5 = 0;
        int n6 = 0;
        int n7 = n3;
        int n8 = n4;
        boolean bl = false;
        int[] nArray = null;
        for (n2 = n4 - 1; n2 >= 0; --n2) {
            nArray = ImageUtils.getRGB(bufferedImage, 0, n2, n3, 1, nArray);
            for (n = 0; n < n7; ++n) {
                if ((nArray[n] & 0xFF000000) == 0) continue;
                n7 = n;
                n6 = n2;
                bl = true;
                break;
            }
            for (n = n3 - 1; n >= n5; --n) {
                if ((nArray[n] & 0xFF000000) == 0) continue;
                n5 = n;
                n6 = n2;
                bl = true;
                break;
            }
            if (bl) break;
        }
        nArray = null;
        block3: for (n = 0; n < n2; ++n) {
            int n9;
            nArray = ImageUtils.getRGB(bufferedImage, 0, n, n3, 1, nArray);
            for (n9 = 0; n9 < n7; ++n9) {
                if ((nArray[n9] & 0xFF000000) == 0) continue;
                n7 = n9;
                if (n < n8) {
                    n8 = n;
                }
                bl = true;
                break;
            }
            for (n9 = n3 - 1; n9 >= n5; --n9) {
                if ((nArray[n9] & 0xFF000000) == 0) continue;
                n5 = n9;
                if (n < n8) {
                    n8 = n;
                }
                bl = true;
                continue block3;
            }
        }
        if (bl) {
            return new Rectangle(n7, n8, n5 - n7 + 1, n6 - n8 + 1);
        }
        return null;
    }

    public static void composeThroughMask(Raster raster, WritableRaster writableRaster, Raster raster2) {
        int n = raster.getMinX();
        int n2 = raster.getMinY();
        int n3 = raster.getWidth();
        int n4 = raster.getHeight();
        int[] nArray = null;
        int[] nArray2 = null;
        int[] nArray3 = null;
        for (int i = 0; i < n4; ++i) {
            nArray = raster.getPixels(n, n2, n3, 1, nArray);
            nArray2 = raster2.getPixels(n, n2, n3, 1, nArray2);
            nArray3 = writableRaster.getPixels(n, n2, n3, 1, nArray3);
            int n5 = n;
            for (int j = 0; j < n3; ++j) {
                int n6 = nArray[n5];
                int n7 = nArray3[n5];
                int n8 = nArray[n5 + 1];
                int n9 = nArray3[n5 + 1];
                int n10 = nArray[n5 + 2];
                int n11 = nArray3[n5 + 2];
                int n12 = nArray[n5 + 3];
                int n13 = nArray3[n5 + 3];
                float f = (float)nArray2[n5 + 3] / 255.0f;
                float f2 = 1.0f - f;
                nArray3[n5] = (int)(f * (float)n6 + f2 * (float)n7);
                nArray3[n5 + 1] = (int)(f * (float)n8 + f2 * (float)n9);
                nArray3[n5 + 2] = (int)(f * (float)n10 + f2 * (float)n11);
                nArray3[n5 + 3] = (int)(f * (float)n12 + f2 * (float)n13);
                n5 += 4;
            }
            writableRaster.setPixels(n, n2, n3, 1, nArray3);
            ++n2;
        }
    }

    public static int[] getRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            return (int[])bufferedImage.getRaster().getDataElements(n, n2, n3, n4, nArray);
        }
        return bufferedImage.getRGB(n, n2, n3, n4, nArray, 0, n3);
    }

    public static void setRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            bufferedImage.getRaster().setDataElements(n, n2, n3, n4, nArray);
        } else {
            bufferedImage.setRGB(n, n2, n3, n4, nArray, 0, n3);
        }
    }
}


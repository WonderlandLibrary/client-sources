package a.a.a;

import java.awt.image.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class a extends c
{
    public static int a;
    public static int b;
    private Kernel e;
    protected boolean c;
    protected boolean d;
    private int f;
    
    static {
        a.a.a.a.a = 1;
        a.a.a.a.b = 2;
    }
    
    public a() {
        this(new float[9]);
    }
    
    private a(final float[] array) {
        this(new Kernel(3, 3, array));
    }
    
    private a(final Kernel e) {
        this.e = null;
        this.c = true;
        this.d = true;
        this.f = a.a.a.a.a;
        this.e = e;
    }
    
    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage compatibleDestImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        if (compatibleDestImage == null) {
            compatibleDestImage = this.createCompatibleDestImage(bufferedImage, null);
        }
        final int[] array = new int[width * height];
        final int[] array2 = new int[width * height];
        final BufferedImage bufferedImage2 = bufferedImage;
        final int n = width;
        final int n2 = height;
        final int[] array3 = array;
        final int n3 = n2;
        final int n4 = n;
        bufferedImage = bufferedImage2;
        final int type;
        if ((type = bufferedImage2.getType()) == 2 || type == 1) {
            final int[] array4 = (int[])bufferedImage.getRaster().getDataElements(0, 0, n4, n3, array3);
        }
        else {
            bufferedImage.getRGB(0, 0, n4, n3, array3, 0, n4);
        }
        if (this.d) {
            final int[] array5 = array;
            final int length = array.length;
            final int[] array6 = array5;
            for (int n5 = length, i = 0; i < n5; ++i) {
                final int n7;
                final int n6 = (n7 = array6[i]) >>> 24;
                final int n8 = n7 >> 16 & 0xFF;
                final int n9 = n7 >> 8 & 0xFF;
                final int n10 = n7 & 0xFF;
                final float n11 = n6 * 0.003921569f;
                array6[i] = (n6 << 24 | (int)(n8 * n11) << 16 | (int)(n9 * n11) << 8 | (int)(n10 * n11));
            }
        }
        final Kernel e = this.e;
        final int[] array7 = array;
        final int[] array8 = array2;
        final int n12 = width;
        final int n13 = height;
        final boolean c = this.c;
        final int f = this.f;
        final boolean b = c;
        final int n14 = n13;
        final int n15 = n12;
        final int[] array9 = array8;
        final int[] array10 = array7;
        final Kernel kernel = e;
        if (e.getHeight() == 1) {
            final Kernel kernel2 = kernel;
            final int[] array11 = array10;
            final int[] array12 = array9;
            final int n16 = n15;
            final int n17 = n14;
            final boolean b2 = b;
            final int n18 = f;
            final boolean b3 = b2;
            final int n19 = n17;
            final int n20 = n16;
            final int[] array13 = array12;
            final int[] array14 = array11;
            final Kernel kernel3 = kernel2;
            int n21 = 0;
            final float[] kernelData = kernel3.getKernelData(null);
            final int n22 = kernel3.getWidth() / 2;
            for (int j = 0; j < n19; ++j) {
                final int n23 = j * n20;
                for (int k = 0; k < n20; ++k) {
                    float n24 = 0.0f;
                    float n25 = 0.0f;
                    float n26 = 0.0f;
                    float n27 = 0.0f;
                    for (int l = -n22; l <= n22; ++l) {
                        final float n28;
                        if ((n28 = kernelData[n22 + l]) != 0.0f) {
                            int n29;
                            if ((n29 = k + l) < 0) {
                                if (n18 == a.a.a.a.a) {
                                    n29 = 0;
                                }
                                else if (n18 == a.a.a.a.b) {
                                    n29 = (k + n20) % n20;
                                }
                            }
                            else if (n29 >= n20) {
                                if (n18 == a.a.a.a.a) {
                                    n29 = n20 - 1;
                                }
                                else if (n18 == a.a.a.a.b) {
                                    n29 = (k + n20) % n20;
                                }
                            }
                            final int n30 = array14[n23 + n29];
                            n27 += n28 * (n30 >>> 24);
                            n24 += n28 * (n30 >> 16 & 0xFF);
                            n25 += n28 * (n30 >> 8 & 0xFF);
                            n26 += n28 * (n30 & 0xFF);
                        }
                    }
                    array13[n21++] = ((b3 ? a.a.a.e.a((int)(n27 + 0.5)) : 255) << 24 | a.a.a.e.a((int)(n24 + 0.5)) << 16 | a.a.a.e.a((int)(n25 + 0.5)) << 8 | a.a.a.e.a((int)(n26 + 0.5)));
                }
            }
        }
        else if (kernel.getWidth() == 1) {
            final Kernel kernel4 = kernel;
            final int[] array15 = array10;
            final int[] array16 = array9;
            final int n31 = n15;
            final int n32 = n14;
            final boolean b4 = b;
            final int n33 = f;
            final boolean b5 = b4;
            final int n34 = n32;
            final int n35 = n31;
            final int[] array17 = array16;
            final int[] array18 = array15;
            final Kernel kernel5 = kernel4;
            int n36 = 0;
            final float[] kernelData2 = kernel5.getKernelData(null);
            final int n37 = kernel5.getHeight() / 2;
            for (int n38 = 0; n38 < n34; ++n38) {
                for (int n39 = 0; n39 < n35; ++n39) {
                    float n40 = 0.0f;
                    float n41 = 0.0f;
                    float n42 = 0.0f;
                    float n43 = 0.0f;
                    for (int n44 = -n37; n44 <= n37; ++n44) {
                        final int n45;
                        int n46;
                        if ((n45 = n38 + n44) < 0) {
                            if (n33 == a.a.a.a.a) {
                                n46 = 0;
                            }
                            else if (n33 == a.a.a.a.b) {
                                n46 = (n38 + n34) % n34 * n35;
                            }
                            else {
                                n46 = n45 * n35;
                            }
                        }
                        else if (n45 >= n34) {
                            if (n33 == a.a.a.a.a) {
                                n46 = (n34 - 1) * n35;
                            }
                            else if (n33 == a.a.a.a.b) {
                                n46 = (n38 + n34) % n34 * n35;
                            }
                            else {
                                n46 = n45 * n35;
                            }
                        }
                        else {
                            n46 = n45 * n35;
                        }
                        final float n47;
                        if ((n47 = kernelData2[n44 + n37]) != 0.0f) {
                            final int n48 = array18[n46 + n39];
                            n43 += n47 * (n48 >>> 24);
                            n40 += n47 * (n48 >> 16 & 0xFF);
                            n41 += n47 * (n48 >> 8 & 0xFF);
                            n42 += n47 * (n48 & 0xFF);
                        }
                    }
                    array17[n36++] = ((b5 ? a.a.a.e.a((int)(n43 + 0.5)) : 255) << 24 | a.a.a.e.a((int)(n40 + 0.5)) << 16 | a.a.a.e.a((int)(n41 + 0.5)) << 8 | a.a.a.e.a((int)(n42 + 0.5)));
                }
            }
        }
        else {
            final Kernel kernel6 = kernel;
            final int[] array19 = array10;
            final int[] array20 = array9;
            final int n49 = n15;
            final int n50 = n14;
            final boolean b6 = b;
            final int n51 = f;
            final boolean b7 = b6;
            final int n52 = n50;
            final int n53 = n49;
            final int[] array21 = array20;
            final int[] array22 = array19;
            final Kernel kernel7 = kernel6;
            int n54 = 0;
            final float[] kernelData3 = kernel7.getKernelData(null);
            final int height2 = kernel7.getHeight();
            final int width2 = kernel7.getWidth();
            final int n55 = height2 / 2;
            final int n56 = width2 / 2;
            for (int n57 = 0; n57 < n52; ++n57) {
                for (int n58 = 0; n58 < n53; ++n58) {
                    float n59 = 0.0f;
                    float n60 = 0.0f;
                    float n61 = 0.0f;
                    float n62 = 0.0f;
                    for (int n63 = -n55; n63 <= n55; ++n63) {
                        final int n64;
                        int n65;
                        if ((n64 = n57 + n63) >= 0 && n64 < n52) {
                            n65 = n64 * n53;
                        }
                        else if (n51 == a.a.a.a.a) {
                            n65 = n57 * n53;
                        }
                        else {
                            if (n51 != a.a.a.a.b) {
                                continue;
                            }
                            n65 = (n64 + n52) % n52 * n53;
                        }
                        final int n66 = width2 * (n63 + n55) + n56;
                        for (int n67 = -n56; n67 <= n56; ++n67) {
                            final float n68;
                            if ((n68 = kernelData3[n66 + n67]) != 0.0f) {
                                int n69;
                                if ((n69 = n58 + n67) < 0 || n69 >= n53) {
                                    if (n51 == a.a.a.a.a) {
                                        n69 = n58;
                                    }
                                    else {
                                        if (n51 != a.a.a.a.b) {
                                            continue;
                                        }
                                        n69 = (n58 + n53) % n53;
                                    }
                                }
                                final int n70 = array22[n65 + n69];
                                n62 += n68 * (n70 >>> 24);
                                n59 += n68 * (n70 >> 16 & 0xFF);
                                n60 += n68 * (n70 >> 8 & 0xFF);
                                n61 += n68 * (n70 & 0xFF);
                            }
                        }
                    }
                    array21[n54++] = ((b7 ? a.a.a.e.a((int)(n62 + 0.5)) : 255) << 24 | a.a.a.e.a((int)(n59 + 0.5)) << 16 | a.a.a.e.a((int)(n60 + 0.5)) << 8 | a.a.a.e.a((int)(n61 + 0.5)));
                }
            }
        }
        if (this.d) {
            final int[] array23 = array2;
            final int length2 = array2.length;
            final int[] array24 = array23;
            for (int n71 = length2, n72 = 0; n72 < n71; ++n72) {
                final int n74;
                final int n73 = (n74 = array24[n72]) >>> 24;
                final int n75 = n74 >> 16 & 0xFF;
                final int n76 = n74 >> 8 & 0xFF;
                final int n77 = n74 & 0xFF;
                if (n73 != 0 && n73 != 255) {
                    final float n78 = 255.0f / n73;
                    int n79 = (int)(n75 * n78);
                    int n80 = (int)(n76 * n78);
                    int n81 = (int)(n77 * n78);
                    if (n79 > 255) {
                        n79 = 255;
                    }
                    if (n80 > 255) {
                        n80 = 255;
                    }
                    if (n81 > 255) {
                        n81 = 255;
                    }
                    array24[n72] = (n73 << 24 | n79 << 16 | n80 << 8 | n81);
                }
            }
        }
        final BufferedImage bufferedImage3 = compatibleDestImage;
        final int n82 = width;
        final int n83 = height;
        final int[] array25 = array2;
        final int n84 = n83;
        final int n85 = n82;
        bufferedImage = bufferedImage3;
        final int type2;
        if ((type2 = bufferedImage3.getType()) == 2 || type2 == 1) {
            bufferedImage.getRaster().setDataElements(0, 0, n85, n84, array25);
        }
        else {
            bufferedImage.setRGB(0, 0, n85, n84, array25, 0, n85);
        }
        return compatibleDestImage;
    }
    
    @Override
    public BufferedImage createCompatibleDestImage(final BufferedImage bufferedImage, ColorModel colorModel) {
        if (colorModel == null) {
            colorModel = bufferedImage.getColorModel();
        }
        return new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(bufferedImage.getWidth(), bufferedImage.getHeight()), colorModel.isAlphaPremultiplied(), null);
    }
    
    @Override
    public Rectangle2D getBounds2D(final BufferedImage bufferedImage) {
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
    
    @Override
    public Point2D getPoint2D(final Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Double();
        }
        point2D2.setLocation(point2D.getX(), point2D.getY());
        return point2D2;
    }
    
    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
    
    @Override
    public String toString() {
        return "Blur/Convolve...";
    }
}

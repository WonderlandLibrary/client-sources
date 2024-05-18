package optfine;

import java.nio.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class Mipmaps
{
    private IntBuffer[] mipmapBuffers;
    private final boolean direct;
    private final int[] data;
    private static final String[] I;
    private final int width;
    private final String iconName;
    private int[][] mipmapDatas;
    private Dimension[] mipmapDimensions;
    private final int height;
    
    public static void allocateMipmapTextures(final int n, final int n2, final String s) {
        final Dimension[] mipmapDimensions = makeMipmapDimensions(n, n2, s);
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < mipmapDimensions.length) {
            final Dimension dimension = mipmapDimensions[i];
            GL11.glTexImage2D(1726 + 2064 - 451 + 214, i + " ".length(), 4943 + 3378 - 6137 + 4224, dimension.width, dimension.height, "".length(), 5609 + 1984 + 7906 + 17494, 25677 + 32667 - 30875 + 6170, (IntBuffer)null);
            ++i;
        }
    }
    
    static {
        I();
    }
    
    public static int[][] generateMipMapData(final int[] array, final int n, final int n2, final Dimension[] array2) {
        int[] array3 = array;
        int n3 = n;
        int n4 = " ".length();
        final int[][] array4 = new int[array2.length][];
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < array2.length) {
            final Dimension dimension = array2[i];
            final int width = dimension.width;
            final int height = dimension.height;
            final int[] array5 = new int[width * height];
            array4[i] = array5;
            final int n5 = i + " ".length();
            if (n4 != 0) {
                int j = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (j < width) {
                    int k = "".length();
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                    while (k < height) {
                        array5[j + k * width] = alphaBlend(array3[j * "  ".length() + "".length() + (k * "  ".length() + "".length()) * n3], array3[j * "  ".length() + " ".length() + (k * "  ".length() + "".length()) * n3], array3[j * "  ".length() + " ".length() + (k * "  ".length() + " ".length()) * n3], array3[j * "  ".length() + "".length() + (k * "  ".length() + " ".length()) * n3]);
                        ++k;
                    }
                    ++j;
                }
            }
            array3 = array5;
            n3 = width;
            if (width <= " ".length() || height <= " ".length()) {
                n4 = "".length();
            }
            ++i;
        }
        return array4;
    }
    
    private static int alphaBlend(int n, int n2) {
        int length = (n & -(8943454 + 3549590 - 6991764 + 11275936)) >> (0x2E ^ 0x36) & 29 + 155 + 44 + 27;
        int length2 = (n2 & -(15279071 + 14315785 - 16287244 + 3469604)) >> (0x3D ^ 0x25) & 97 + 86 - 113 + 185;
        int n3 = (length + length2) / "  ".length();
        if (length == 0 && length2 == 0) {
            length = " ".length();
            length2 = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            if (length == 0) {
                n = n2;
                n3 /= "  ".length();
            }
            if (length2 == 0) {
                n2 = n;
                n3 /= "  ".length();
            }
        }
        return n3 << (0x14 ^ 0xC) | ((n >> (0x14 ^ 0x4) & 50 + 63 - 29 + 171) * length + (n2 >> (0xA7 ^ 0xB7) & 180 + 30 - 118 + 163) * length2) / (length + length2) << (0x6B ^ 0x7B) | ((n >> (0x4F ^ 0x47) & 118 + 144 - 8 + 1) * length + (n2 >> (0x18 ^ 0x10) & 92 + 4 - 45 + 204) * length2) / (length + length2) << (0x93 ^ 0x9B) | ((n & 6 + 137 - 113 + 225) * length + (n2 & 149 + 154 - 87 + 39) * length2) / (length + length2);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0002\u001c(\u0007\u0012?\u0006x\u0004\u001c;U(\u0005\u0000<\u001c:\u0006\u0016o](\u0005\u0004*\u0007x\u0005\u0015oGx\u000e\u001a\"\u00106\u0019\u001a \u001b+J\u001d*\u0010<\u000f\u0017fYx\u001e\u00167\u0001-\u0018\u0016uU", "OuXjs");
        Mipmaps.I[" ".length()] = I("MF\u0002\b*[F", "affaG");
        Mipmaps.I["  ".length()] = I("\u000e", "vrbFK");
    }
    
    private int averageColor(final int n, final int n2) {
        return (((n & -(16172324 + 3749008 - 17580660 + 14436544)) >> (0x4D ^ 0x55) & 27 + 254 - 73 + 47) + ((n2 & -(14267291 + 2476131 - 12046559 + 12080353)) >> (0x37 ^ 0x2F) & 215 + 64 - 236 + 212) >> " ".length() << (0x6F ^ 0x77)) + ((n & 116806 + 25327 + 8561511 + 8007778) + (n2 & 6447198 + 7884836 - 4693004 + 7072392) >> " ".length());
    }
    
    public Mipmaps(final String iconName, final int width, final int height, final int[] data, final boolean direct) {
        this.iconName = iconName;
        this.width = width;
        this.height = height;
        this.data = data;
        this.direct = direct;
        this.mipmapDimensions = makeMipmapDimensions(width, height, iconName);
        this.mipmapDatas = generateMipMapData(data, width, height, this.mipmapDimensions);
        if (direct) {
            this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
        }
    }
    
    public static int alphaBlend(final int n, final int n2, final int n3, final int n4) {
        return alphaBlend(alphaBlend(n, n2), alphaBlend(n3, n4));
    }
    
    public static IntBuffer[] makeMipmapBuffers(final Dimension[] array, final int[][] array2) {
        if (array == null) {
            return null;
        }
        final IntBuffer[] array3 = new IntBuffer[array.length];
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < array.length) {
            final Dimension dimension = array[i];
            final IntBuffer directIntBuffer = GLAllocation.createDirectIntBuffer(dimension.width * dimension.height);
            final int[] array4 = array2[i];
            directIntBuffer.clear();
            directIntBuffer.put(array4);
            directIntBuffer.clear();
            array3[i] = directIntBuffer;
            ++i;
        }
        return array3;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static Dimension[] makeMipmapDimensions(final int n, final int n2, final String s) {
        final int ceilPowerOfTwo = TextureUtils.ceilPowerOfTwo(n);
        final int ceilPowerOfTwo2 = TextureUtils.ceilPowerOfTwo(n2);
        if (ceilPowerOfTwo == n && ceilPowerOfTwo2 == n2) {
            final ArrayList<Dimension> list = new ArrayList<Dimension>();
            int length = ceilPowerOfTwo;
            int length2 = ceilPowerOfTwo2;
            do {
                length /= "  ".length();
                length2 /= "  ".length();
                if (length <= 0 && length2 <= 0) {
                    return list.toArray(new Dimension[list.size()]);
                }
                if (length <= 0) {
                    length = " ".length();
                }
                if (length2 <= 0) {
                    length2 = " ".length();
                }
                list.add(new Dimension(length, length2));
                "".length();
            } while (1 > -1);
            throw null;
        }
        Config.warn(Mipmaps.I["".length()] + s + Mipmaps.I[" ".length()] + n + Mipmaps.I["  ".length()] + n2);
        return new Dimension["".length()];
    }
}

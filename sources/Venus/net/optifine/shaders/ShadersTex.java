/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import net.optifine.util.TextureUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ShadersTex {
    public static final int initialBufferSize = 0x100000;
    public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(0x400000);
    public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
    public static int[] intArray = new int[0x100000];
    public static final int defBaseTexColor = 0;
    public static final int defNormTexColor = -8421377;
    public static final int defSpecTexColor = 0;
    public static Map<Integer, MultiTexID> multiTexMap = new HashMap<Integer, MultiTexID>();

    public static IntBuffer getIntBuffer(int n) {
        if (intBuffer.capacity() < n) {
            int n2 = ShadersTex.roundUpPOT(n);
            byteBuffer = BufferUtils.createByteBuffer(n2 * 4);
            intBuffer = byteBuffer.asIntBuffer();
        }
        return intBuffer;
    }

    public static int[] getIntArray(int n) {
        if (intArray == null) {
            intArray = new int[0x100000];
        }
        if (intArray.length < n) {
            intArray = new int[ShadersTex.roundUpPOT(n)];
        }
        return intArray;
    }

    public static int roundUpPOT(int n) {
        int n2 = n - 1;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return n2 + 1;
    }

    public static int log2(int n) {
        int n2 = 0;
        if ((n & 0xFFFF0000) != 0) {
            n2 += 16;
            n >>= 16;
        }
        if ((n & 0xFF00) != 0) {
            n2 += 8;
            n >>= 8;
        }
        if ((n & 0xF0) != 0) {
            n2 += 4;
            n >>= 4;
        }
        if ((n & 6) != 0) {
            n2 += 2;
            n >>= 2;
        }
        if ((n & 2) != 0) {
            ++n2;
        }
        return n2;
    }

    public static IntBuffer fillIntBuffer(int n, int n2) {
        int[] nArray = ShadersTex.getIntArray(n);
        IntBuffer intBuffer = ShadersTex.getIntBuffer(n);
        Arrays.fill(intArray, 0, n, n2);
        ShadersTex.intBuffer.put(intArray, 0, n);
        return ShadersTex.intBuffer;
    }

    public static int[] createAIntImage(int n) {
        int[] nArray = new int[n * 3];
        Arrays.fill(nArray, 0, n, 0);
        Arrays.fill(nArray, n, n * 2, -8421377);
        Arrays.fill(nArray, n * 2, n * 3, 0);
        return nArray;
    }

    public static int[] createAIntImage(int n, int n2) {
        int[] nArray = new int[n * 3];
        Arrays.fill(nArray, 0, n, n2);
        Arrays.fill(nArray, n, n * 2, -8421377);
        Arrays.fill(nArray, n * 2, n * 3, 0);
        return nArray;
    }

    public static MultiTexID getMultiTexID(Texture texture) {
        MultiTexID multiTexID = texture.multiTex;
        if (multiTexID == null) {
            int n = texture.getGlTextureId();
            multiTexID = multiTexMap.get(n);
            if (multiTexID == null) {
                multiTexID = new MultiTexID(n, GL11.glGenTextures(), GL11.glGenTextures());
                multiTexMap.put(n, multiTexID);
            }
            texture.multiTex = multiTexID;
        }
        return multiTexID;
    }

    public static void deleteTextures(Texture texture, int n) {
        MultiTexID multiTexID = texture.multiTex;
        if (multiTexID != null) {
            texture.multiTex = null;
            multiTexMap.remove(multiTexID.base);
            GlStateManager.deleteTexture(multiTexID.norm);
            GlStateManager.deleteTexture(multiTexID.spec);
            if (multiTexID.base != n) {
                SMCLog.warning("Error : MultiTexID.base mismatch: " + multiTexID.base + ", texid: " + n);
                GlStateManager.deleteTexture(multiTexID.base);
            }
        }
    }

    public static void bindNSTextures(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            int n3;
            if (Shaders.configNormalMap) {
                GlStateManager.activeTexture(33985);
                GlStateManager.bindTexture(n);
                if (!bl) {
                    n3 = bl3 ? 9984 : 9728;
                    GlStateManager.texParameter(3553, 10241, n3);
                    GlStateManager.texParameter(3553, 10240, 9728);
                }
            }
            if (Shaders.configSpecularMap) {
                GlStateManager.activeTexture(33987);
                GlStateManager.bindTexture(n2);
                if (!bl2) {
                    n3 = bl3 ? 9984 : 9728;
                    GlStateManager.texParameter(3553, 10241, n3);
                    GlStateManager.texParameter(3553, 10240, 9728);
                }
            }
            GlStateManager.activeTexture(33984);
        }
    }

    public static void bindNSTextures(MultiTexID multiTexID) {
        ShadersTex.bindNSTextures(multiTexID.norm, multiTexID.spec, true, true, false);
    }

    public static void bindTextures(int n, int n2, int n3) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            GlStateManager.activeTexture(33985);
            GlStateManager.bindTexture(n2);
            GlStateManager.activeTexture(33987);
            GlStateManager.bindTexture(n3);
            GlStateManager.activeTexture(33984);
        }
        GlStateManager.bindTexture(n);
    }

    public static void bindTextures(MultiTexID multiTexID, boolean bl, boolean bl2, boolean bl3) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            int n;
            if (Shaders.configNormalMap) {
                GlStateManager.activeTexture(33985);
                GlStateManager.bindTexture(multiTexID.norm);
                if (!bl) {
                    n = bl3 ? 9984 : 9728;
                    GlStateManager.texParameter(3553, 10241, n);
                    GlStateManager.texParameter(3553, 10240, 9728);
                }
            }
            if (Shaders.configSpecularMap) {
                GlStateManager.activeTexture(33987);
                GlStateManager.bindTexture(multiTexID.spec);
                if (!bl2) {
                    n = bl3 ? 9984 : 9728;
                    GlStateManager.texParameter(3553, 10241, n);
                    GlStateManager.texParameter(3553, 10240, 9728);
                }
            }
            GlStateManager.activeTexture(33984);
        }
        GlStateManager.bindTexture(multiTexID.base);
    }

    public static void bindTexture(Texture texture) {
        int n = texture.getGlTextureId();
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = false;
        if (texture instanceof AtlasTexture) {
            AtlasTexture atlasTexture = (AtlasTexture)texture;
            bl = atlasTexture.isNormalBlend();
            bl2 = atlasTexture.isSpecularBlend();
            bl3 = atlasTexture.isMipmaps();
        }
        ShadersTex.bindTextures(texture.getMultiTexID(), bl, bl2, bl3);
        if (GlStateManager.getActiveTextureUnit() == 33984) {
            int n2 = Shaders.atlasSizeX;
            int n3 = Shaders.atlasSizeY;
            if (texture instanceof AtlasTexture) {
                Shaders.atlasSizeX = ((AtlasTexture)texture).atlasWidth;
                Shaders.atlasSizeY = ((AtlasTexture)texture).atlasHeight;
            } else {
                Shaders.atlasSizeX = 0;
                Shaders.atlasSizeY = 0;
            }
            if (Shaders.atlasSizeX != n2 || Shaders.atlasSizeY != n3) {
                boolean bl4 = RenderUtils.setFlushRenderBuffers(false);
                Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
                RenderUtils.setFlushRenderBuffers(bl4);
            }
        }
    }

    public static void bindTextures(int n) {
        MultiTexID multiTexID = multiTexMap.get(n);
        ShadersTex.bindTextures(multiTexID, true, true, false);
    }

    public static void initDynamicTextureNS(DynamicTexture dynamicTexture) {
        MultiTexID multiTexID = dynamicTexture.getMultiTexID();
        NativeImage nativeImage = dynamicTexture.getTextureData();
        int n = nativeImage.getWidth();
        int n2 = nativeImage.getHeight();
        NativeImage nativeImage2 = ShadersTex.makeImageColor(n, n2, -8421377);
        TextureUtil.prepareImage(multiTexID.norm, n, n2);
        nativeImage2.uploadTextureSub(0, 0, 0, 0, 0, n, n2, false, false, false, false);
        NativeImage nativeImage3 = ShadersTex.makeImageColor(n, n2, 0);
        TextureUtil.prepareImage(multiTexID.spec, n, n2);
        nativeImage3.uploadTextureSub(0, 0, 0, 0, 0, n, n2, false, false, false, false);
        GlStateManager.bindTexture(multiTexID.base);
    }

    public static void updateDynTexSubImage1(int[] nArray, int n, int n2, int n3, int n4, int n5) {
        int n6 = n * n2;
        IntBuffer intBuffer = ShadersTex.getIntBuffer(n6);
        intBuffer.clear();
        int n7 = n5 * n6;
        if (nArray.length >= n7 + n6) {
            intBuffer.put(nArray, n7, n6).position(0).limit(n6);
            TextureUtils.resetDataUnpacking();
            GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
            intBuffer.clear();
        }
    }

    public static Texture createDefaultTexture() {
        DynamicTexture dynamicTexture = new DynamicTexture(1, 1, true);
        dynamicTexture.getTextureData().setPixelRGBA(0, 0, -1);
        dynamicTexture.updateDynamicTexture();
        return dynamicTexture;
    }

    public static void allocateTextureMapNS(int n, int n2, int n3, AtlasTexture atlasTexture) {
        MultiTexID multiTexID = ShadersTex.getMultiTexID(atlasTexture);
        if (Shaders.configNormalMap) {
            SMCLog.info("Allocate texture map normal: " + n2 + "x" + n3 + ", mipmaps: " + n);
            TextureUtil.prepareImage(multiTexID.norm, n, n2, n3);
        }
        if (Shaders.configSpecularMap) {
            SMCLog.info("Allocate texture map specular: " + n2 + "x" + n3 + ", mipmaps: " + n);
            TextureUtil.prepareImage(multiTexID.spec, n, n2, n3);
        }
        GlStateManager.bindTexture(multiTexID.base);
    }

    private static NativeImage[] generateMipmaps(NativeImage nativeImage, int n) {
        if (n < 0) {
            n = 0;
        }
        NativeImage[] nativeImageArray = new NativeImage[n + 1];
        nativeImageArray[0] = nativeImage;
        if (n > 0) {
            for (int i = 1; i <= n; ++i) {
                NativeImage nativeImage2 = nativeImageArray[i - 1];
                NativeImage nativeImage3 = new NativeImage(nativeImage2.getWidth() >> 1, nativeImage2.getHeight() >> 1, false);
                int n2 = nativeImage3.getWidth();
                int n3 = nativeImage3.getHeight();
                for (int j = 0; j < n2; ++j) {
                    for (int k = 0; k < n3; ++k) {
                        nativeImage3.setPixelRGBA(j, k, ShadersTex.blend4Simple(nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 1), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 1)));
                    }
                }
                nativeImageArray[i] = nativeImage3;
            }
        }
        return nativeImageArray;
    }

    public static BufferedImage readImage(ResourceLocation resourceLocation) {
        try {
            if (!Config.hasResource(resourceLocation)) {
                return null;
            }
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return null;
            }
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            inputStream.close();
            return bufferedImage;
        } catch (IOException iOException) {
            return null;
        }
    }

    public static int[][] genMipmapsSimple(int n, int n2, int[][] nArray) {
        for (int i = 1; i <= n; ++i) {
            if (nArray[i] != null) continue;
            int n3 = n2 >> i;
            int n4 = n3 * 2;
            int[] nArray2 = nArray[i - 1];
            nArray[i] = new int[n3 * n3];
            int[] nArray3 = nArray[i];
            for (int j = 0; j < n3; ++j) {
                for (int k = 0; k < n3; ++k) {
                    int n5 = j * 2 * n4 + k * 2;
                    nArray3[j * n3 + k] = ShadersTex.blend4Simple(nArray2[n5], nArray2[n5 + 1], nArray2[n5 + n4], nArray2[n5 + n4 + 1]);
                }
            }
        }
        return nArray;
    }

    public static void uploadTexSub1(int[][] nArray, int n, int n2, int n3, int n4, int n5) {
        TextureUtils.resetDataUnpacking();
        int n6 = n * n2;
        IntBuffer intBuffer = ShadersTex.getIntBuffer(n6);
        int n7 = nArray.length;
        int n8 = 0;
        int n9 = n;
        int n10 = n2;
        int n11 = n3;
        int n12 = n4;
        while (n9 > 0 && n10 > 0 && n8 < n7) {
            int n13 = n9 * n10;
            int[] nArray2 = nArray[n8];
            intBuffer.clear();
            if (nArray2.length >= n13 * (n5 + 1)) {
                intBuffer.put(nArray2, n13 * n5, n13).position(0).limit(n13);
                GL11.glTexSubImage2D(3553, n8, n11, n12, n9, n10, 32993, 33639, intBuffer);
            }
            n9 >>= 1;
            n10 >>= 1;
            n11 >>= 1;
            n12 >>= 1;
            ++n8;
        }
        intBuffer.clear();
    }

    public static int blend4Alpha(int n, int n2, int n3, int n4) {
        int n5;
        int n6 = n >>> 24 & 0xFF;
        int n7 = n2 >>> 24 & 0xFF;
        int n8 = n3 >>> 24 & 0xFF;
        int n9 = n4 >>> 24 & 0xFF;
        int n10 = n6 + n7 + n8 + n9;
        int n11 = (n10 + 2) / 4;
        if (n10 != 0) {
            n5 = n10;
        } else {
            n5 = 4;
            n6 = 1;
            n7 = 1;
            n8 = 1;
            n9 = 1;
        }
        int n12 = (n5 + 1) / 2;
        return n11 << 24 | ((n >>> 16 & 0xFF) * n6 + (n2 >>> 16 & 0xFF) * n7 + (n3 >>> 16 & 0xFF) * n8 + (n4 >>> 16 & 0xFF) * n9 + n12) / n5 << 16 | ((n >>> 8 & 0xFF) * n6 + (n2 >>> 8 & 0xFF) * n7 + (n3 >>> 8 & 0xFF) * n8 + (n4 >>> 8 & 0xFF) * n9 + n12) / n5 << 8 | ((n >>> 0 & 0xFF) * n6 + (n2 >>> 0 & 0xFF) * n7 + (n3 >>> 0 & 0xFF) * n8 + (n4 >>> 0 & 0xFF) * n9 + n12) / n5 << 0;
    }

    public static int blend4Simple(int n, int n2, int n3, int n4) {
        return ((n >>> 24 & 0xFF) + (n2 >>> 24 & 0xFF) + (n3 >>> 24 & 0xFF) + (n4 >>> 24 & 0xFF) + 2) / 4 << 24 | ((n >>> 16 & 0xFF) + (n2 >>> 16 & 0xFF) + (n3 >>> 16 & 0xFF) + (n4 >>> 16 & 0xFF) + 2) / 4 << 16 | ((n >>> 8 & 0xFF) + (n2 >>> 8 & 0xFF) + (n3 >>> 8 & 0xFF) + (n4 >>> 8 & 0xFF) + 2) / 4 << 8 | ((n >>> 0 & 0xFF) + (n2 >>> 0 & 0xFF) + (n3 >>> 0 & 0xFF) + (n4 >>> 0 & 0xFF) + 2) / 4 << 0;
    }

    public static void genMipmapAlpha(int[] nArray, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        Math.min(n2, n3);
        int n7 = n;
        int n8 = n2;
        int n9 = n3;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        while (n8 > 1 && n9 > 1) {
            n10 = n7 + n8 * n9;
            n11 = n8 / 2;
            n12 = n9 / 2;
            for (n6 = 0; n6 < n12; ++n6) {
                n5 = n10 + n6 * n11;
                n4 = n7 + n6 * 2 * n8;
                for (int i = 0; i < n11; ++i) {
                    nArray[n5 + i] = ShadersTex.blend4Alpha(nArray[n4 + i * 2], nArray[n4 + i * 2 + 1], nArray[n4 + n8 + i * 2], nArray[n4 + n8 + i * 2 + 1]);
                }
            }
            ++n13;
            n8 = n11;
            n9 = n12;
            n7 = n10;
        }
        while (n13 > 0) {
            n8 = n2 >> --n13;
            n9 = n3 >> n13;
            n6 = n7 = n10 - n8 * n9;
            for (n5 = 0; n5 < n9; ++n5) {
                for (n4 = 0; n4 < n8; ++n4) {
                    if (nArray[n6] == 0) {
                        nArray[n6] = nArray[n10 + n5 / 2 * n11 + n4 / 2] & 0xFFFFFF;
                    }
                    ++n6;
                }
            }
            n10 = n7;
            n11 = n8;
        }
    }

    public static void genMipmapSimple(int[] nArray, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        Math.min(n2, n3);
        int n7 = n;
        int n8 = n2;
        int n9 = n3;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        while (n8 > 1 && n9 > 1) {
            n10 = n7 + n8 * n9;
            n11 = n8 / 2;
            n12 = n9 / 2;
            for (n6 = 0; n6 < n12; ++n6) {
                n5 = n10 + n6 * n11;
                n4 = n7 + n6 * 2 * n8;
                for (int i = 0; i < n11; ++i) {
                    nArray[n5 + i] = ShadersTex.blend4Simple(nArray[n4 + i * 2], nArray[n4 + i * 2 + 1], nArray[n4 + n8 + i * 2], nArray[n4 + n8 + i * 2 + 1]);
                }
            }
            ++n13;
            n8 = n11;
            n9 = n12;
            n7 = n10;
        }
        while (n13 > 0) {
            n8 = n2 >> --n13;
            n9 = n3 >> n13;
            n6 = n7 = n10 - n8 * n9;
            for (n5 = 0; n5 < n9; ++n5) {
                for (n4 = 0; n4 < n8; ++n4) {
                    if (nArray[n6] == 0) {
                        nArray[n6] = nArray[n10 + n5 / 2 * n11 + n4 / 2] & 0xFFFFFF;
                    }
                    ++n6;
                }
            }
            n10 = n7;
            n11 = n8;
        }
    }

    public static boolean isSemiTransparent(int[] nArray, int n, int n2) {
        int n3 = n * n2;
        if (nArray[0] >>> 24 == 255 && nArray[n3 - 1] == 0) {
            return false;
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = nArray[i] >>> 24;
            if (n4 == 0 || n4 == 255) continue;
            return false;
        }
        return true;
    }

    public static void updateSubTex1(int[] nArray, int n, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        int n9 = n4;
        while (n6 > 0 && n7 > 0) {
            GL11.glCopyTexSubImage2D(3553, n5, n8, n9, 0, 0, n6, n7);
            ++n5;
            n6 /= 2;
            n7 /= 2;
            n8 /= 2;
            n9 /= 2;
        }
    }

    public static void updateSubImage(MultiTexID multiTexID, int[] nArray, int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        int n5 = n * n2;
        IntBuffer intBuffer = ShadersTex.getIntBuffer(n5);
        TextureUtils.resetDataUnpacking();
        intBuffer.clear();
        intBuffer.put(nArray, 0, n5);
        intBuffer.position(0).limit(n5);
        GlStateManager.bindTexture(multiTexID.base);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        if (nArray.length == n5 * 3) {
            intBuffer.clear();
            intBuffer.put(nArray, n5, n5).position(0);
            intBuffer.position(0).limit(n5);
        }
        GlStateManager.bindTexture(multiTexID.norm);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        if (nArray.length == n5 * 3) {
            intBuffer.clear();
            intBuffer.put(nArray, n5 * 2, n5);
            intBuffer.position(0).limit(n5);
        }
        GlStateManager.bindTexture(multiTexID.spec);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        GlStateManager.activeTexture(33984);
    }

    public static ResourceLocation getNSMapLocation(ResourceLocation resourceLocation, String string) {
        if (resourceLocation == null) {
            return null;
        }
        String string2 = resourceLocation.getPath();
        String[] stringArray = string2.split(".png");
        String string3 = stringArray[0];
        return new ResourceLocation(resourceLocation.getNamespace(), string3 + "_" + string + ".png");
    }

    private static NativeImage loadNSMapImage(IResourceManager iResourceManager, ResourceLocation resourceLocation, int n, int n2, int n3) {
        NativeImage nativeImage = ShadersTex.loadNSMapFile(iResourceManager, resourceLocation, n, n2);
        if (nativeImage == null) {
            nativeImage = new NativeImage(n, n2, false);
            int n4 = TextureUtils.toAbgr(n3);
            nativeImage.fillAreaRGBA(0, 0, n, n2, n4);
        }
        return nativeImage;
    }

    private static NativeImage makeImageColor(int n, int n2, int n3) {
        NativeImage nativeImage = new NativeImage(n, n2, false);
        int n4 = TextureUtils.toAbgr(n3);
        nativeImage.fillRGBA(n4);
        return nativeImage;
    }

    private static NativeImage loadNSMapFile(IResourceManager iResourceManager, ResourceLocation resourceLocation, int n, int n2) {
        if (resourceLocation == null) {
            return null;
        }
        try {
            IResource iResource = iResourceManager.getResource(resourceLocation);
            NativeImage nativeImage = NativeImage.read(iResource.getInputStream());
            if (nativeImage == null) {
                return null;
            }
            if (nativeImage.getWidth() == n && nativeImage.getHeight() == n2) {
                return nativeImage;
            }
            nativeImage.close();
            return null;
        } catch (IOException iOException) {
            return null;
        }
    }

    public static void loadSimpleTextureNS(int n, NativeImage nativeImage, boolean bl, boolean bl2, IResourceManager iResourceManager, ResourceLocation resourceLocation, MultiTexID multiTexID) {
        int n2 = nativeImage.getWidth();
        int n3 = nativeImage.getHeight();
        ResourceLocation resourceLocation2 = ShadersTex.getNSMapLocation(resourceLocation, "n");
        NativeImage nativeImage2 = ShadersTex.loadNSMapImage(iResourceManager, resourceLocation2, n2, n3, -8421377);
        TextureUtil.prepareImage(multiTexID.norm, 0, n2, n3);
        nativeImage2.uploadTextureSub(0, 0, 0, 0, 0, n2, n3, bl, bl2, false, false);
        ResourceLocation resourceLocation3 = ShadersTex.getNSMapLocation(resourceLocation, "s");
        NativeImage nativeImage3 = ShadersTex.loadNSMapImage(iResourceManager, resourceLocation3, n2, n3, 0);
        TextureUtil.prepareImage(multiTexID.spec, 0, n2, n3);
        nativeImage3.uploadTextureSub(0, 0, 0, 0, 0, n2, n3, bl, bl2, false, false);
        GlStateManager.bindTexture(multiTexID.base);
    }

    public static void mergeImage(int[] nArray, int n, int n2, int n3) {
    }

    public static int blendColor(int n, int n2, int n3) {
        int n4 = 255 - n3;
        return ((n >>> 24 & 0xFF) * n3 + (n2 >>> 24 & 0xFF) * n4) / 255 << 24 | ((n >>> 16 & 0xFF) * n3 + (n2 >>> 16 & 0xFF) * n4) / 255 << 16 | ((n >>> 8 & 0xFF) * n3 + (n2 >>> 8 & 0xFF) * n4) / 255 << 8 | ((n >>> 0 & 0xFF) * n3 + (n2 >>> 0 & 0xFF) * n4) / 255 << 0;
    }

    public static void updateTextureMinMagFilter() {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        Texture texture = textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        if (texture != null) {
            MultiTexID multiTexID = texture.getMultiTexID();
            GlStateManager.bindTexture(multiTexID.base);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GlStateManager.bindTexture(multiTexID.norm);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GlStateManager.bindTexture(multiTexID.spec);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GlStateManager.bindTexture(0);
        }
    }

    public static int[][] getFrameTexData(int[][] nArray, int n, int n2, int n3) {
        int n4 = nArray.length;
        int[][] nArrayArray = new int[n4][];
        for (int i = 0; i < n4; ++i) {
            int[] nArray2 = nArray[i];
            if (nArray2 == null) continue;
            int n5 = (n >> i) * (n2 >> i);
            int[] nArray3 = new int[n5 * 3];
            nArrayArray[i] = nArray3;
            int n6 = nArray2.length / 3;
            int n7 = n5 * n3;
            int n8 = 0;
            System.arraycopy(nArray2, n7, nArray3, n8, n5);
            System.arraycopy(nArray2, n7 += n6, nArray3, n8 += n5, n5);
            System.arraycopy(nArray2, n7 += n6, nArray3, n8 += n5, n5);
        }
        return nArrayArray;
    }

    public static int[][] prepareAF(TextureAtlasSprite textureAtlasSprite, int[][] nArray, int n, int n2) {
        boolean bl = true;
        return nArray;
    }

    public static void fixTransparentColor(TextureAtlasSprite textureAtlasSprite, int[] nArray) {
    }
}


package net.minecraft.client.renderer.texture;

import java.awt.image.*;
import net.minecraft.client.resources.*;
import java.io.*;

public class DynamicTexture extends AbstractTexture
{
    private final int width;
    private final int[] dynamicTextureData;
    private final int height;
    
    public int[] getTextureData() {
        return this.dynamicTextureData;
    }
    
    public DynamicTexture(final BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB("".length(), "".length(), bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, "".length(), bufferedImage.getWidth());
        this.updateDynamicTexture();
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public DynamicTexture(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.dynamicTextureData = new int[width * height];
        TextureUtil.allocateTexture(this.getGlTextureId(), width, height);
    }
    
    public void updateDynamicTexture() {
        TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
    }
}

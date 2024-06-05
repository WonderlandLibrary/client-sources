package net.minecraft.src;

import java.nio.*;
import java.awt.image.*;
import java.util.logging.*;
import org.lwjgl.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import java.io.*;
import net.minecraft.client.*;
import java.util.*;
import javax.imageio.*;

public class RenderEngine
{
    private HashMap textureMap;
    private HashMap textureContentsMap;
    private IntHashMap textureNameToImageMap;
    private IntBuffer imageData;
    private Map urlToImageDataMap;
    private GameSettings options;
    public TexturePackList texturePack;
    private BufferedImage missingTextureImage;
    public final TextureMap textureMapBlocks;
    public final TextureMap textureMapItems;
    public int boundTexture;
    public static Logger log;
    private boolean initialized;
    
    static {
        RenderEngine.log = Logger.getAnonymousLogger();
    }
    
    public RenderEngine(final TexturePackList par1TexturePackList, final GameSettings par2GameSettings) {
        this.textureMap = new HashMap();
        this.textureContentsMap = new HashMap();
        this.textureNameToImageMap = new IntHashMap();
        this.imageData = GLAllocation.createDirectIntBuffer(4194304);
        this.urlToImageDataMap = new HashMap();
        this.missingTextureImage = new BufferedImage(64, 64, 2);
        this.initialized = false;
        if (Config.isMultiTexture()) {
            final int var3 = Config.getAntialiasingLevel();
            Config.dbg("FSAA Samples: " + var3);
            try {
                Display.destroy();
                Display.create(new PixelFormat().withDepthBits(24).withSamples(var3));
            }
            catch (LWJGLException var4) {
                Config.dbg("Error setting FSAA: " + var3 + "x");
                var4.printStackTrace();
                try {
                    Display.create(new PixelFormat().withDepthBits(24));
                }
                catch (LWJGLException var5) {
                    var5.printStackTrace();
                    try {
                        Display.create();
                    }
                    catch (LWJGLException var6) {
                        var6.printStackTrace();
                    }
                }
            }
        }
        this.texturePack = par1TexturePackList;
        this.options = par2GameSettings;
        final Graphics var7 = this.missingTextureImage.getGraphics();
        var7.setColor(Color.WHITE);
        var7.fillRect(0, 0, 64, 64);
        var7.setColor(Color.BLACK);
        int var8 = 10;
        int var9 = 0;
        while (var8 < 64) {
            final String var10 = (var9++ % 2 == 0) ? "missing" : "texture";
            var7.drawString(var10, 1, var8);
            var8 += var7.getFont().getSize();
            if (var9 % 2 == 0) {
                var8 += 5;
            }
        }
        var7.dispose();
        this.textureMapBlocks = new TextureMap(0, "terrain", "textures/blocks/", this.missingTextureImage);
        this.textureMapItems = new TextureMap(1, "items", "textures/items/", this.missingTextureImage);
    }
    
    public int[] getTextureContents(final String par1Str) {
        final ITexturePack var2 = this.texturePack.getSelectedTexturePack();
        final int[] var3 = this.textureContentsMap.get(par1Str);
        if (var3 != null) {
            return var3;
        }
        try {
            final InputStream var4 = var2.getResourceAsStream(par1Str);
            int[] var5;
            if (var4 == null) {
                var5 = this.getImageContentsAndAllocate(this.missingTextureImage);
            }
            else {
                var5 = this.getImageContentsAndAllocate(this.readTextureImage(var4));
            }
            this.textureContentsMap.put(par1Str, var5);
            return var5;
        }
        catch (IOException var6) {
            var6.printStackTrace();
            final int[] var5 = this.getImageContentsAndAllocate(this.missingTextureImage);
            this.textureContentsMap.put(par1Str, var5);
            return var5;
        }
    }
    
    private int[] getImageContentsAndAllocate(final BufferedImage par1BufferedImage) {
        return this.getImageContents(par1BufferedImage, new int[par1BufferedImage.getWidth() * par1BufferedImage.getHeight()]);
    }
    
    private int[] getImageContents(final BufferedImage par1BufferedImage, final int[] par2ArrayOfInteger) {
        final int var3 = par1BufferedImage.getWidth();
        final int var4 = par1BufferedImage.getHeight();
        par1BufferedImage.getRGB(0, 0, var3, var4, par2ArrayOfInteger, 0, var3);
        return par2ArrayOfInteger;
    }
    
    public void bindTexture(final String par1Str) {
        GL11.glBindTexture(3553, this.getTexture(par1Str));
    }
    
    public void bindTexture(final int par1) {
        if (par1 != this.boundTexture) {
            GL11.glBindTexture(3553, par1);
            this.boundTexture = par1;
        }
    }
    
    public void resetBoundTexture() {
        this.boundTexture = -1;
    }
    
    public int getTexture(String par1Str) {
        if (Config.isRandomMobs()) {
            par1Str = RandomMobs.getTexture(par1Str);
        }
        if (par1Str.equals("/terrain.png")) {
            this.textureMapBlocks.getTexture().bindTexture(0);
            return this.textureMapBlocks.getTexture().getGlTextureId();
        }
        if (par1Str.equals("/gui/items.png")) {
            this.textureMapItems.getTexture().bindTexture(0);
            return this.textureMapItems.getTexture().getGlTextureId();
        }
        final Integer var2 = this.textureMap.get(par1Str);
        if (var2 != null) {
            return var2;
        }
        final String var3 = par1Str;
        try {
            Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoadPre, par1Str);
            final int var4 = GLAllocation.generateTextureNames();
            final boolean var5 = par1Str.startsWith("%blur%");
            if (var5) {
                par1Str = par1Str.substring(6);
            }
            final boolean var6 = par1Str.startsWith("%clamp%");
            if (var6) {
                par1Str = par1Str.substring(7);
            }
            final InputStream var7 = this.texturePack.getSelectedTexturePack().getResourceAsStream(par1Str);
            if (var7 == null) {
                this.setupTextureExt(this.missingTextureImage, var4, var5, var6);
            }
            else {
                this.setupTextureExt(this.readTextureImage(var7), var4, var5, var6);
            }
            this.textureMap.put(var3, var4);
            Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoad, par1Str, this.texturePack.getSelectedTexturePack());
            return var4;
        }
        catch (Exception var8) {
            var8.printStackTrace();
            final int var9 = GLAllocation.generateTextureNames();
            this.setupTexture(this.missingTextureImage, var9);
            this.textureMap.put(par1Str, var9);
            return var9;
        }
    }
    
    public int allocateAndSetupTexture(final BufferedImage par1BufferedImage) {
        final int var2 = GLAllocation.generateTextureNames();
        this.setupTexture(par1BufferedImage, var2);
        this.textureNameToImageMap.addKey(var2, par1BufferedImage);
        return var2;
    }
    
    public void setupTexture(final BufferedImage par1BufferedImage, final int par2) {
        this.setupTextureExt(par1BufferedImage, par2, false, false);
    }
    
    public void setupTextureExt(final BufferedImage par1BufferedImage, final int par2, final boolean par3, final boolean par4) {
        this.bindTexture(par2);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        if (par3) {
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        if (par4) {
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
        }
        else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
        final int var5 = par1BufferedImage.getWidth();
        final int var6 = par1BufferedImage.getHeight();
        int[] var7 = new int[var5 * var6];
        par1BufferedImage.getRGB(0, 0, var5, var6, var7, 0, var5);
        if (this.options != null && this.options.anaglyph) {
            var7 = this.colorToAnaglyph(var7);
        }
        this.fixTransparency(var7);
        this.checkImageDataSize(var7.length);
        this.imageData.clear();
        this.imageData.put(var7);
        this.imageData.position(0).limit(var7.length);
        GL11.glTexImage2D(3553, 0, 6408, var5, var6, 0, 32993, 33639, this.imageData);
    }
    
    private int[] colorToAnaglyph(final int[] par1ArrayOfInteger) {
        final int[] var2 = new int[par1ArrayOfInteger.length];
        for (int var3 = 0; var3 < par1ArrayOfInteger.length; ++var3) {
            final int var4 = par1ArrayOfInteger[var3] >> 24 & 0xFF;
            final int var5 = par1ArrayOfInteger[var3] >> 16 & 0xFF;
            final int var6 = par1ArrayOfInteger[var3] >> 8 & 0xFF;
            final int var7 = par1ArrayOfInteger[var3] & 0xFF;
            final int var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
            final int var9 = (var5 * 30 + var6 * 70) / 100;
            final int var10 = (var5 * 30 + var7 * 70) / 100;
            var2[var3] = (var4 << 24 | var8 << 16 | var9 << 8 | var10);
        }
        return var2;
    }
    
    public void createTextureFromBytes(int[] par1ArrayOfInteger, final int par2, final int par3, final int par4) {
        this.bindTexture(par4);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        if (this.options != null && this.options.anaglyph) {
            par1ArrayOfInteger = this.colorToAnaglyph(par1ArrayOfInteger);
        }
        this.checkImageDataSize(par1ArrayOfInteger.length);
        this.imageData.clear();
        this.imageData.put(par1ArrayOfInteger);
        this.imageData.position(0).limit(par1ArrayOfInteger.length);
        GL11.glTexSubImage2D(3553, 0, 0, 0, par2, par3, 32993, 33639, this.imageData);
    }
    
    public void deleteTexture(final int par1) {
        this.textureNameToImageMap.removeObject(par1);
        GL11.glDeleteTextures(par1);
    }
    
    public int getTextureForDownloadableImage(final String par1Str, final String par2Str) {
        final ThreadDownloadImageData var3 = this.urlToImageDataMap.get(par1Str);
        if (var3 != null && var3.image != null && !var3.textureSetupComplete) {
            if (var3.textureName < 0) {
                var3.textureName = this.allocateAndSetupTexture(var3.image);
            }
            else {
                this.setupTexture(var3.image, var3.textureName);
            }
            var3.textureSetupComplete = true;
        }
        return (var3 != null && var3.textureName >= 0) ? var3.textureName : ((par2Str == null) ? -1 : this.getTexture(par2Str));
    }
    
    public boolean hasImageData(final String par1Str) {
        return this.urlToImageDataMap.containsKey(par1Str);
    }
    
    public ThreadDownloadImageData obtainImageData(final String par1Str, final IImageBuffer par2IImageBuffer) {
        if (par1Str != null && par1Str.length() > 0 && Character.isDigit(par1Str.charAt(0))) {
            return null;
        }
        final ThreadDownloadImageData var3 = this.urlToImageDataMap.get(par1Str);
        if (var3 == null) {
            this.urlToImageDataMap.put(par1Str, new ThreadDownloadImageData(par1Str, par2IImageBuffer));
        }
        else {
            final ThreadDownloadImageData threadDownloadImageData = var3;
            ++threadDownloadImageData.referenceCount;
        }
        return var3;
    }
    
    public void releaseImageData(final String par1Str) {
        final ThreadDownloadImageData var2 = this.urlToImageDataMap.get(par1Str);
        if (var2 != null) {
            final ThreadDownloadImageData threadDownloadImageData = var2;
            --threadDownloadImageData.referenceCount;
            if (var2.referenceCount == 0) {
                if (var2.textureName >= 0) {
                    this.deleteTexture(var2.textureName);
                }
                this.urlToImageDataMap.remove(par1Str);
            }
        }
    }
    
    public void updateDynamicTextures() {
        this.checkInitialized();
        this.textureMapBlocks.updateAnimations();
        this.textureMapItems.updateAnimations();
        TextureAnimations.updateCustomAnimations();
    }
    
    public void refreshTextures() {
        Config.dbg("*** Reloading textures ***");
        Config.log("Texture pack: \"" + this.texturePack.getSelectedTexturePack().getTexturePackFileName() + "\"");
        CustomSky.reset();
        TextureAnimations.reset();
        WrUpdates.finishCurrentUpdate();
        final ITexturePack var1 = this.texturePack.getSelectedTexturePack();
        this.refreshTextureMaps();
        for (final int var3 : this.textureNameToImageMap.getKeySet()) {
            final BufferedImage var4 = (BufferedImage)this.textureNameToImageMap.lookup(var3);
            this.setupTexture(var4, var3);
        }
        for (final ThreadDownloadImageData var5 : this.urlToImageDataMap.values()) {
            var5.textureSetupComplete = false;
        }
        for (String var6 : this.textureMap.keySet()) {
            try {
                final int var7 = this.textureMap.get(var6);
                final boolean var8 = var6.startsWith("%blur%");
                if (var8) {
                    var6 = var6.substring(6);
                }
                final boolean var9 = var6.startsWith("%clamp%");
                if (var9) {
                    var6 = var6.substring(7);
                }
                final BufferedImage var10 = this.readTextureImage(var1.getResourceAsStream(var6));
                this.setupTextureExt(var10, var7, var8, var9);
            }
            catch (FileNotFoundException ex) {}
            catch (Exception var11) {
                if ("input == null!".equals(var11.getMessage())) {
                    continue;
                }
                var11.printStackTrace();
            }
        }
        for (final String var6 : this.textureContentsMap.keySet()) {
            try {
                final BufferedImage var4 = this.readTextureImage(var1.getResourceAsStream(var6));
                this.getImageContents(var4, this.textureContentsMap.get(var6));
            }
            catch (FileNotFoundException ex2) {}
            catch (Exception var12) {
                if ("input == null!".equals(var12.getMessage())) {
                    continue;
                }
                var12.printStackTrace();
            }
        }
        Minecraft.getMinecraft().fontRenderer.readFontData();
        Minecraft.getMinecraft().standardGalacticFontRenderer.readFontData();
        TextureAnimations.update(this);
        CustomColorizer.update(this);
        CustomSky.update(this);
        RandomMobs.resetTextures();
        Config.updateTexturePackClouds();
        this.updateDynamicTextures();
    }
    
    private BufferedImage readTextureImage(final InputStream par1InputStream) throws IOException {
        final BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
    public void refreshTextureMaps() {
        this.textureMapBlocks.refreshTextures();
        this.textureMapItems.refreshTextures();
        TextureUtils.update(this);
        NaturalTextures.update(this);
        ConnectedTextures.update(this);
    }
    
    public Icon getMissingIcon(final int par1) {
        switch (par1) {
            case 0: {
                return this.textureMapBlocks.getMissingIcon();
            }
            default: {
                return this.textureMapItems.getMissingIcon();
            }
        }
    }
    
    protected BufferedImage readTextureImage(final String var1) throws IOException {
        final InputStream var2 = this.texturePack.getSelectedTexturePack().getResourceAsStream(var1);
        if (var2 == null) {
            return null;
        }
        final BufferedImage var3 = ImageIO.read(var2);
        var2.close();
        return var3;
    }
    
    public TexturePackList getTexturePack() {
        return this.texturePack;
    }
    
    public void checkInitialized() {
        if (!this.initialized) {
            final Minecraft var1 = Config.getMinecraft();
            if (var1 != null) {
                this.initialized = true;
                Config.log("Texture pack: \"" + this.texturePack.getSelectedTexturePack().getTexturePackFileName() + "\"");
                CustomColorizer.update(this);
                CustomSky.update(this);
                TextureAnimations.update(this);
                Config.updateTexturePackClouds();
            }
        }
    }
    
    public void checkImageDataSize(int var1) {
        if (this.imageData == null || this.imageData.capacity() < var1) {
            var1 = TextureUtils.ceilPowerOfTwo(var1);
            this.imageData = GLAllocation.createDirectIntBuffer(var1);
        }
    }
    
    private void fixTransparency(final int[] var1) {
        for (int var2 = 0; var2 < var1.length; ++var2) {
            final int var3 = var1[var2] >> 24 & 0xFF;
            if (var3 == 0) {
                var1[var2] = 0;
            }
        }
    }
    
    public void refreshBlockTextures() {
        Config.dbg("*** Reloading block textures ***");
        WrUpdates.finishCurrentUpdate();
        this.textureMapBlocks.refreshTextures();
        TextureUtils.update(this);
        NaturalTextures.update(this);
        ConnectedTextures.update(this);
        this.updateDynamicTextures();
    }
}

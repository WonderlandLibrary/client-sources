package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class TextureManager
{
    private static TextureManager instance;
    private int nextTextureID;
    private final HashMap texturesMap;
    private final HashMap mapNameToId;
    
    public TextureManager() {
        this.nextTextureID = 0;
        this.texturesMap = new HashMap();
        this.mapNameToId = new HashMap();
    }
    
    public static void init() {
        TextureManager.instance = new TextureManager();
    }
    
    public static TextureManager instance() {
        return TextureManager.instance;
    }
    
    public int getNextTextureId() {
        return this.nextTextureID++;
    }
    
    public void registerTexture(final String par1Str, final Texture par2Texture) {
        this.mapNameToId.put(par1Str, par2Texture.getTextureId());
        if (!this.texturesMap.containsKey(par2Texture.getTextureId())) {
            this.texturesMap.put(par2Texture.getTextureId(), par2Texture);
        }
    }
    
    public void registerTexture(final Texture par1Texture) {
        if (this.texturesMap.containsValue(par1Texture)) {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.registerTexture called, but this texture has already been registered. ignoring.");
        }
        else {
            this.texturesMap.put(par1Texture.getTextureId(), par1Texture);
        }
    }
    
    public Stitcher createStitcher(final String par1Str) {
        final int var2 = Minecraft.getGLMaximumTextureSize();
        return new Stitcher(par1Str, var2, var2, true);
    }
    
    public List createTexture(final String par1Str) {
        return this.createNewTexture(this.getBasename(par1Str), par1Str, null);
    }
    
    public List createNewTexture(final String var1, final String var2, final TextureStitched var3) {
        final ArrayList var4 = new ArrayList();
        final ITexturePack var5 = Minecraft.getMinecraft().texturePackList.getSelectedTexturePack();
        try {
            BufferedImage var6 = null;
            FileNotFoundException var7 = null;
            try {
                var6 = ImageIO.read(var5.getResourceAsStream("/" + var2));
            }
            catch (FileNotFoundException var8) {
                var7 = var8;
            }
            if (var3 != null && var3.loadTexture(this, var5, var1, var2, var6, var4)) {
                return var4;
            }
            if (var7 != null) {
                throw var7;
            }
            final int var9 = var6.getHeight();
            final int var10 = var6.getWidth();
            final int var11 = var6.getWidth();
            final int var12 = var6.getHeight();
            final boolean var13 = var12 > var11 && var12 / var11 * var11 == var12;
            if (!var13 && !this.hasAnimationTxt(var2, var5)) {
                if (var10 == var9) {
                    var4.add(this.makeTexture(var1, 2, var10, var9, 10496, 6408, 9728, 9728, false, var6));
                }
                else {
                    Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture: Skipping " + var2 + " because of broken aspect ratio and not animation");
                }
            }
            else {
                final int var14 = var10;
                final int var15 = var10;
                for (int var16 = var9 / var10, var17 = 0; var17 < var16; ++var17) {
                    final Texture var18 = this.makeTexture(var1, 2, var14, var15, 10496, 6408, 9728, 9728, false, var6.getSubimage(0, var15 * var17, var14, var15));
                    var4.add(var18);
                }
            }
            return var4;
        }
        catch (FileNotFoundException var19) {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture called for file " + var2 + ", but that file does not exist. Ignoring.");
        }
        catch (IOException var20) {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture encountered an IOException when trying to read file " + var2 + ". Ignoring.");
        }
        return var4;
    }
    
    private String getBasename(final String par1Str) {
        if (!par1Str.startsWith("ctm/") && !par1Str.startsWith("mods/")) {
            final File var2 = new File(par1Str);
            return var2.getName().substring(0, var2.getName().lastIndexOf(46));
        }
        return par1Str.substring(0, par1Str.lastIndexOf(46));
    }
    
    private boolean hasAnimationTxt(final String par1Str, final ITexturePack par2ITexturePack) {
        final String var3 = "/" + par1Str.substring(0, par1Str.lastIndexOf(46)) + ".txt";
        final boolean var4 = par2ITexturePack.func_98138_b("/" + par1Str, false);
        return Minecraft.getMinecraft().texturePackList.getSelectedTexturePack().func_98138_b(var3, !var4);
    }
    
    public Texture makeTexture(final String par1Str, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final boolean par9, final BufferedImage par10BufferedImage) {
        final Texture var11 = new Texture(par1Str, par2, par3, par4, par5, par6, par7, par8, par10BufferedImage);
        this.registerTexture(var11);
        return var11;
    }
    
    public Texture createEmptyTexture(final String par1Str, final int par2, final int par3, final int par4, final int par5) {
        return this.makeTexture(par1Str, par2, par3, par4, 10496, par5, 9728, 9728, false, null);
    }
}

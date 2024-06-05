package net.minecraft.src;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import org.lwjgl.opengl.*;

public abstract class TexturePackImplementation implements ITexturePack
{
    private final String texturePackID;
    private final String texturePackFileName;
    protected final File texturePackFile;
    protected String firstDescriptionLine;
    protected String secondDescriptionLine;
    private final ITexturePack field_98141_g;
    protected BufferedImage thumbnailImage;
    private int thumbnailTextureName;
    
    protected TexturePackImplementation(final String par1, final File par2File, final String par3Str, final ITexturePack par4ITexturePack) {
        this.thumbnailTextureName = -1;
        this.texturePackID = par1;
        this.texturePackFileName = par3Str;
        this.texturePackFile = par2File;
        this.field_98141_g = par4ITexturePack;
        this.loadThumbnailImage();
        this.loadDescription();
    }
    
    private static String trimStringToGUIWidth(String par0Str) {
        if (par0Str != null && par0Str.length() > 34) {
            par0Str = par0Str.substring(0, 34);
        }
        return par0Str;
    }
    
    private void loadThumbnailImage() {
        InputStream var1 = null;
        try {
            var1 = this.func_98137_a("/pack.png", false);
            this.thumbnailImage = ImageIO.read(var1);
        }
        catch (IOException ex) {}
        finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            }
            catch (IOException ex2) {}
        }
        try {
            if (var1 != null) {
                var1.close();
            }
        }
        catch (IOException ex3) {}
    }
    
    protected void loadDescription() {
        InputStream var1 = null;
        BufferedReader var2 = null;
        try {
            var1 = this.func_98139_b("/pack.txt");
            var2 = new BufferedReader(new InputStreamReader(var1));
            this.firstDescriptionLine = trimStringToGUIWidth(var2.readLine());
            this.secondDescriptionLine = trimStringToGUIWidth(var2.readLine());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (var2 != null) {
                    var2.close();
                }
                if (var1 != null) {
                    var1.close();
                }
            }
            catch (IOException ex2) {}
        }
        try {
            if (var2 != null) {
                var2.close();
            }
            if (var1 != null) {
                var1.close();
            }
        }
        catch (IOException ex3) {}
    }
    
    @Override
    public InputStream func_98137_a(final String par1Str, final boolean par2) throws IOException {
        try {
            return this.func_98139_b(par1Str);
        }
        catch (IOException var4) {
            if (this.field_98141_g != null && par2) {
                return this.field_98141_g.func_98137_a(par1Str, true);
            }
            throw var4;
        }
    }
    
    @Override
    public InputStream getResourceAsStream(final String par1Str) throws IOException {
        return this.func_98137_a(par1Str, true);
    }
    
    protected abstract InputStream func_98139_b(final String p0) throws IOException;
    
    @Override
    public void deleteTexturePack(final RenderEngine par1RenderEngine) {
        if (this.thumbnailImage != null && this.thumbnailTextureName != -1) {
            par1RenderEngine.deleteTexture(this.thumbnailTextureName);
        }
    }
    
    @Override
    public void bindThumbnailTexture(final RenderEngine par1RenderEngine) {
        if (this.thumbnailImage != null) {
            if (this.thumbnailTextureName == -1) {
                this.thumbnailTextureName = par1RenderEngine.allocateAndSetupTexture(this.thumbnailImage);
            }
            GL11.glBindTexture(3553, this.thumbnailTextureName);
            par1RenderEngine.resetBoundTexture();
        }
        else {
            par1RenderEngine.bindTexture("/gui/unknown_pack.png");
        }
    }
    
    @Override
    public boolean func_98138_b(final String par1Str, final boolean par2) {
        final boolean var3 = this.func_98140_c(par1Str);
        return (!var3 && par2 && this.field_98141_g != null) ? this.field_98141_g.func_98138_b(par1Str, par2) : var3;
    }
    
    public abstract boolean func_98140_c(final String p0);
    
    @Override
    public String getTexturePackID() {
        return this.texturePackID;
    }
    
    @Override
    public String getTexturePackFileName() {
        return this.texturePackFileName;
    }
    
    @Override
    public String getFirstDescriptionLine() {
        return this.firstDescriptionLine;
    }
    
    @Override
    public String getSecondDescriptionLine() {
        return this.secondDescriptionLine;
    }
}

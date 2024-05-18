package net.minecraft.src;

import java.io.*;
import java.util.zip.*;
import java.util.*;

public class TexturePackCustom extends TexturePackImplementation
{
    private ZipFile texturePackZipFile;
    
    public TexturePackCustom(final String par1Str, final File par2File, final ITexturePack par3ITexturePack) {
        super(par1Str, par2File, par2File.getName(), par3ITexturePack);
    }
    
    @Override
    public void deleteTexturePack(final RenderEngine par1RenderEngine) {
        super.deleteTexturePack(par1RenderEngine);
        try {
            if (this.texturePackZipFile != null) {
                this.texturePackZipFile.close();
            }
        }
        catch (IOException ex) {}
        this.texturePackZipFile = null;
    }
    
    @Override
    protected InputStream func_98139_b(final String par1Str) throws IOException {
        this.openTexturePackFile();
        final ZipEntry var2 = this.texturePackZipFile.getEntry(par1Str.substring(1));
        if (var2 == null) {
            throw new FileNotFoundException(par1Str);
        }
        return this.texturePackZipFile.getInputStream(var2);
    }
    
    @Override
    public boolean func_98140_c(final String par1Str) {
        try {
            this.openTexturePackFile();
            return this.texturePackZipFile.getEntry(par1Str.substring(1)) != null;
        }
        catch (Exception var3) {
            return false;
        }
    }
    
    private void openTexturePackFile() throws IOException, ZipException {
        if (this.texturePackZipFile == null) {
            this.texturePackZipFile = new ZipFile(this.texturePackFile);
        }
    }
    
    @Override
    public boolean isCompatible() {
        try {
            this.openTexturePackFile();
            final Enumeration var1 = this.texturePackZipFile.entries();
            while (var1.hasMoreElements()) {
                final ZipEntry var2 = var1.nextElement();
                if (var2.getName().startsWith("textures/")) {
                    return true;
                }
            }
        }
        catch (Exception ex) {}
        final boolean var3 = this.func_98140_c("terrain.png") || this.func_98140_c("gui/items.png");
        return !var3;
    }
}

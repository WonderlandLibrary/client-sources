package net.minecraft.src;

import java.io.*;

public class TexturePackFolder extends TexturePackImplementation
{
    public TexturePackFolder(final String par1, final File par2, final ITexturePack par3ITexturePack) {
        super(par1, par2, par2.getName(), par3ITexturePack);
    }
    
    @Override
    protected InputStream func_98139_b(final String par1Str) throws IOException {
        final File var2 = new File(this.texturePackFile, par1Str.substring(1));
        if (!var2.exists()) {
            throw new FileNotFoundException(par1Str);
        }
        return new BufferedInputStream(new FileInputStream(var2));
    }
    
    @Override
    public boolean func_98140_c(final String par1Str) {
        final File var2 = new File(this.texturePackFile, par1Str);
        return var2.exists() && var2.isFile();
    }
    
    @Override
    public boolean isCompatible() {
        final File var1 = new File(this.texturePackFile, "textures/");
        final boolean var2 = var1.exists() && var1.isDirectory();
        final boolean var3 = this.func_98140_c("terrain.png") || this.func_98140_c("gui/items.png");
        return var2 || !var3;
    }
}

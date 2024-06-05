package net.minecraft.src;

import java.io.*;

public class TexturePackDefault extends TexturePackImplementation
{
    public TexturePackDefault() {
        super("default", null, "Default", null);
    }
    
    @Override
    protected void loadDescription() {
        this.firstDescriptionLine = "The default look of Minecraft";
    }
    
    @Override
    public boolean func_98140_c(final String par1Str) {
        return TexturePackDefault.class.getResourceAsStream(par1Str) != null;
    }
    
    @Override
    public boolean isCompatible() {
        return true;
    }
    
    @Override
    protected InputStream func_98139_b(final String par1Str) throws IOException {
        final InputStream var2 = TexturePackDefault.class.getResourceAsStream(par1Str);
        if (var2 == null) {
            throw new FileNotFoundException(par1Str);
        }
        return var2;
    }
}

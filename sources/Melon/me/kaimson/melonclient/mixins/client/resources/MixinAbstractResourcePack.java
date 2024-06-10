package me.kaimson.melonclient.mixins.client.resources;

import me.kaimson.melonclient.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ bmx.class })
public abstract class MixinAbstractResourcePack
{
    @Overwrite
    public BufferedImage a() throws IOException {
        final BufferedImage image = bml.a(this.a("pack.png"));
        Client.info("Scaling resource pack icon from " + image.getWidth() + " to 64", new Object[0]);
        final BufferedImage scaledImage = new BufferedImage(64, 64, 2);
        final Graphics graphics = scaledImage.getGraphics();
        graphics.drawImage(image, 0, 0, 64, 64, null);
        graphics.dispose();
        return scaledImage;
    }
    
    @Shadow
    protected abstract InputStream a(final String p0);
}

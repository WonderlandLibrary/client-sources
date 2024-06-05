package net.minecraft.client.renderer.texture;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.resources.*;
import java.awt.image.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class SimpleTexture extends AbstractTexture
{
    private static final Logger logger;
    protected final ResourceLocation textureLocation;
    
    public SimpleTexture(final ResourceLocation textureResourceLocation) {
        this.textureLocation = textureResourceLocation;
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        InputStream inputstream = null;
        try {
            final IResource iresource = resourceManager.getResource(this.textureLocation);
            inputstream = iresource.getInputStream();
            final BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
            boolean flag = false;
            boolean flag2 = false;
            if (iresource.hasMetadata()) {
                try {
                    final TextureMetadataSection texturemetadatasection = iresource.getMetadata("texture");
                    if (texturemetadatasection != null) {
                        flag = texturemetadatasection.getTextureBlur();
                        flag2 = texturemetadatasection.getTextureClamp();
                    }
                }
                catch (RuntimeException runtimeexception) {
                    SimpleTexture.logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
                }
            }
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag2);
        }
        finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}

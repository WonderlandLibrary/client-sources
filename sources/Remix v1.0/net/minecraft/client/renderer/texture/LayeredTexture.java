package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class LayeredTexture extends AbstractTexture
{
    private static final Logger logger;
    public final List layeredTextureNames;
    
    public LayeredTexture(final String... p_i1274_1_) {
        this.layeredTextureNames = Lists.newArrayList((Object[])p_i1274_1_);
    }
    
    @Override
    public void loadTexture(final IResourceManager p_110551_1_) throws IOException {
        this.deleteGlTexture();
        BufferedImage var2 = null;
        try {
            for (final String var4 : this.layeredTextureNames) {
                if (var4 != null) {
                    final InputStream var5 = p_110551_1_.getResource(new ResourceLocation(var4)).getInputStream();
                    final BufferedImage var6 = TextureUtil.func_177053_a(var5);
                    if (var2 == null) {
                        var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
                    }
                    var2.getGraphics().drawImage(var6, 0, 0, null);
                }
            }
        }
        catch (IOException var7) {
            LayeredTexture.logger.error("Couldn't load layered image", (Throwable)var7);
            return;
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), var2);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}

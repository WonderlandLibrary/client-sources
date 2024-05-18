package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture
{
    private static final Logger à;
    protected final ResourceLocation_1975012498 Ó;
    private static final String Ø = "CL_00001052";
    
    static {
        à = LogManager.getLogger();
    }
    
    public SimpleTexture(final ResourceLocation_1975012498 p_i1275_1_) {
        this.Ó = p_i1275_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
        this.Âµá€();
        InputStream var2 = null;
        try {
            final IResource var3 = p_110551_1_.HorizonCode_Horizon_È(this.Ó);
            var2 = var3.Â();
            final BufferedImage var4 = TextureUtil.HorizonCode_Horizon_È(var2);
            boolean var5 = false;
            boolean var6 = false;
            if (var3.Ý()) {
                try {
                    final TextureMetadataSection var7 = (TextureMetadataSection)var3.HorizonCode_Horizon_È("texture");
                    if (var7 != null) {
                        var5 = var7.HorizonCode_Horizon_È();
                        var6 = var7.Â();
                    }
                }
                catch (RuntimeException var8) {
                    SimpleTexture.à.warn("Failed reading metadata of: " + this.Ó, (Throwable)var8);
                }
            }
            TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), var4, var5, var6);
        }
        finally {
            if (var2 != null) {
                var2.close();
            }
        }
        if (var2 != null) {
            var2.close();
        }
    }
}

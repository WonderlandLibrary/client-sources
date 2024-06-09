package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.util.Iterator;
import java.io.IOException;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture
{
    private static final Logger à;
    public final List Ó;
    private static final String Ø = "CL_00001051";
    
    static {
        à = LogManager.getLogger();
    }
    
    public LayeredTexture(final String... p_i1274_1_) {
        this.Ó = Lists.newArrayList((Object[])p_i1274_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
        this.Âµá€();
        BufferedImage var2 = null;
        try {
            for (final String var4 : this.Ó) {
                if (var4 != null) {
                    final InputStream var5 = p_110551_1_.HorizonCode_Horizon_È(new ResourceLocation_1975012498(var4)).Â();
                    final BufferedImage var6 = TextureUtil.HorizonCode_Horizon_È(var5);
                    if (var2 == null) {
                        var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
                    }
                    var2.getGraphics().drawImage(var6, 0, 0, null);
                }
            }
        }
        catch (IOException var7) {
            LayeredTexture.à.error("Couldn't load layered image", (Throwable)var7);
            return;
        }
        TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), var2);
    }
}

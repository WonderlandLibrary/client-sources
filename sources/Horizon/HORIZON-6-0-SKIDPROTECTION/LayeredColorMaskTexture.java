package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture
{
    private static final Logger Ó;
    private final ResourceLocation_1975012498 à;
    private final List Ø;
    private final List áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00002404";
    
    static {
        Ó = LogManager.getLogger();
    }
    
    public LayeredColorMaskTexture(final ResourceLocation_1975012498 p_i46101_1_, final List p_i46101_2_, final List p_i46101_3_) {
        this.à = p_i46101_1_;
        this.Ø = p_i46101_2_;
        this.áŒŠÆ = p_i46101_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
        this.Âµá€();
        BufferedImage var5;
        try {
            final BufferedImage var3 = TextureUtil.HorizonCode_Horizon_È(p_110551_1_.HorizonCode_Horizon_È(this.à).Â());
            int var4 = var3.getType();
            if (var4 == 0) {
                var4 = 6;
            }
            var5 = new BufferedImage(var3.getWidth(), var3.getHeight(), var4);
            final Graphics var6 = var5.getGraphics();
            var6.drawImage(var3, 0, 0, null);
            for (int var7 = 0; var7 < this.Ø.size(); ++var7) {
                if (var7 >= this.áŒŠÆ.size()) {
                    break;
                }
                final String var8 = this.Ø.get(var7);
                final MapColor var9 = this.áŒŠÆ.get(var7).Âµá€();
                if (var8 != null) {
                    final InputStream var10 = p_110551_1_.HorizonCode_Horizon_È(new ResourceLocation_1975012498(var8)).Â();
                    final BufferedImage var11 = TextureUtil.HorizonCode_Horizon_È(var10);
                    if (var11.getWidth() == var5.getWidth() && var11.getHeight() == var5.getHeight() && var11.getType() == 6) {
                        for (int var12 = 0; var12 < var11.getHeight(); ++var12) {
                            for (int var13 = 0; var13 < var11.getWidth(); ++var13) {
                                final int var14 = var11.getRGB(var13, var12);
                                if ((var14 & 0xFF000000) != 0x0) {
                                    final int var15 = (var14 & 0xFF0000) << 8 & 0xFF000000;
                                    final int var16 = var3.getRGB(var13, var12);
                                    final int var17 = MathHelper.Ø­áŒŠá(var16, var9.à¢) & 0xFFFFFF;
                                    var11.setRGB(var13, var12, var15 | var17);
                                }
                            }
                        }
                        var5.getGraphics().drawImage(var11, 0, 0, null);
                    }
                }
            }
        }
        catch (IOException var18) {
            LayeredColorMaskTexture.Ó.error("Couldn't load layered image", (Throwable)var18);
            return;
        }
        TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), var5);
    }
}

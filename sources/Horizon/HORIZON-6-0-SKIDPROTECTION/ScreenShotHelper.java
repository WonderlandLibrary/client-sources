package HORIZON-6-0-SKIDPROTECTION;

import java.util.Date;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import java.nio.IntBuffer;
import java.text.DateFormat;
import org.apache.logging.log4j.Logger;

public class ScreenShotHelper
{
    private static final Logger HorizonCode_Horizon_È;
    private static final DateFormat Â;
    private static IntBuffer Ý;
    private static int[] Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000656";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final File p_148260_0_, final int p_148260_1_, final int p_148260_2_, final Framebuffer p_148260_3_) {
        return HorizonCode_Horizon_È(p_148260_0_, null, p_148260_1_, p_148260_2_, p_148260_3_);
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final File p_148259_0_, final String p_148259_1_, int p_148259_2_, int p_148259_3_, final Framebuffer p_148259_4_) {
        try {
            final File var5 = new File(p_148259_0_, "screenshots");
            var5.mkdir();
            if (OpenGlHelper.áŒŠÆ()) {
                p_148259_2_ = p_148259_4_.HorizonCode_Horizon_È;
                p_148259_3_ = p_148259_4_.Â;
            }
            final int var6 = p_148259_2_ * p_148259_3_;
            if (ScreenShotHelper.Ý == null || ScreenShotHelper.Ý.capacity() < var6) {
                ScreenShotHelper.Ý = BufferUtils.createIntBuffer(var6);
                ScreenShotHelper.Ø­áŒŠá = new int[var6];
            }
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            ScreenShotHelper.Ý.clear();
            if (OpenGlHelper.áŒŠÆ()) {
                GlStateManager.áŒŠÆ(p_148259_4_.à);
                GL11.glGetTexImage(3553, 0, 32993, 33639, ScreenShotHelper.Ý);
            }
            else {
                GL11.glReadPixels(0, 0, p_148259_2_, p_148259_3_, 32993, 33639, ScreenShotHelper.Ý);
            }
            ScreenShotHelper.Ý.get(ScreenShotHelper.Ø­áŒŠá);
            TextureUtil.HorizonCode_Horizon_È(ScreenShotHelper.Ø­áŒŠá, p_148259_2_, p_148259_3_);
            BufferedImage var7 = null;
            if (OpenGlHelper.áŒŠÆ()) {
                var7 = new BufferedImage(p_148259_4_.Ý, p_148259_4_.Ø­áŒŠá, 1);
                int var9;
                for (int var8 = var9 = p_148259_4_.Â - p_148259_4_.Ø­áŒŠá; var9 < p_148259_4_.Â; ++var9) {
                    for (int var10 = 0; var10 < p_148259_4_.Ý; ++var10) {
                        var7.setRGB(var10, var9 - var8, ScreenShotHelper.Ø­áŒŠá[var9 * p_148259_4_.HorizonCode_Horizon_È + var10]);
                    }
                }
            }
            else {
                var7 = new BufferedImage(p_148259_2_, p_148259_3_, 1);
                var7.setRGB(0, 0, p_148259_2_, p_148259_3_, ScreenShotHelper.Ø­áŒŠá, 0, p_148259_2_);
            }
            File var11;
            if (p_148259_1_ == null) {
                var11 = HorizonCode_Horizon_È(var5);
            }
            else {
                var11 = new File(var5, p_148259_1_);
            }
            ImageIO.write(var7, "png", var11);
            final ChatComponentText var12 = new ChatComponentText(var11.getName());
            var12.à().HorizonCode_Horizon_È(new ClickEvent(ClickEvent.HorizonCode_Horizon_È.Â, var11.getAbsolutePath()));
            var12.à().Ø­áŒŠá(Boolean.valueOf(true));
            return new ChatComponentTranslation("screenshot.success", new Object[] { var12 });
        }
        catch (Exception var13) {
            ScreenShotHelper.HorizonCode_Horizon_È.warn("Couldn't save screenshot", (Throwable)var13);
            return new ChatComponentTranslation("screenshot.failure", new Object[] { var13.getMessage() });
        }
    }
    
    private static File HorizonCode_Horizon_È(final File p_74290_0_) {
        final String var2 = ScreenShotHelper.Â.format(new Date()).toString();
        int var3 = 1;
        File var4;
        while (true) {
            var4 = new File(p_74290_0_, String.valueOf(var2) + ((var3 == 1) ? "" : ("_" + var3)) + ".png");
            if (!var4.exists()) {
                break;
            }
            ++var3;
        }
        return var4;
    }
}

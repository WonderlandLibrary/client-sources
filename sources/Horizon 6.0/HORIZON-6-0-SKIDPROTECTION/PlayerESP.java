package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = -1671646, Â = "Stalk Players.", HorizonCode_Horizon_È = "PlayerESP")
public class PlayerESP extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventRender3D ev) {
        for (final Object o : Minecraft.áŒŠà().áŒŠÆ.Â) {
            if (o instanceof EntityPlayer) {
                final EntityPlayer ep = (EntityPlayer)o;
                final double d = ep.áˆºáˆºÈ + (ep.ŒÏ - ep.áˆºáˆºÈ) * this.Â.Ø.Ý;
                final double d2 = ep.ÇŽá€ + (ep.Çªà¢ - ep.ÇŽá€) * this.Â.Ø.Ý;
                final double d3 = ep.Ï + (ep.Ê - ep.Ï) * this.Â.Ø.Ý;
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                if (ModuleManager.HorizonCode_Horizon_È(HitboxExtend.class).áˆºÑ¢Õ()) {
                    final double n = d;
                    this.Â.ÇªÓ();
                    final double d4 = n - RenderManager.HorizonCode_Horizon_È;
                    final double n2 = d2;
                    this.Â.ÇªÓ();
                    final double d5 = n2 - RenderManager.Â - Horizon.à¢.áˆºÑ¢Õ;
                    final double n3 = d3;
                    this.Â.ÇªÓ();
                    this.HorizonCode_Horizon_È(d4, d5, n3 - RenderManager.Ý, (EntityLivingBase)ep, ep.£ÂµÄ - 0.1 + Horizon.à¢.áˆºÑ¢Õ, ep.áŒŠ - 0.12 + Horizon.à¢.áˆºÑ¢Õ);
                }
                else {
                    final double n4 = d;
                    this.Â.ÇªÓ();
                    final double d6 = n4 - RenderManager.HorizonCode_Horizon_È;
                    final double n5 = d2;
                    this.Â.ÇªÓ();
                    final double d7 = n5 - RenderManager.Â;
                    final double n6 = d3;
                    this.Â.ÇªÓ();
                    this.HorizonCode_Horizon_È(d6, d7, n6 - RenderManager.Ý, (EntityLivingBase)ep, ep.£ÂµÄ - 0.1, ep.áŒŠ - 0.12);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final double d, final double d1, final double d2, final EntityLivingBase ep, final double e, final double f) {
        if (!(ep instanceof EntityPlayerSP) && !ep.ˆáŠ && !ep.Ý(Minecraft.áŒŠà().á)) {
            if (FriendManager.HorizonCode_Horizon_È.containsKey(ep.v_())) {
                GL11.glPushMatrix();
                GLUtil.HorizonCode_Horizon_È(3042, true);
                GLUtil.HorizonCode_Horizon_È(3553, false);
                GLUtil.HorizonCode_Horizon_È(2896, false);
                GLUtil.HorizonCode_Horizon_È(2929, false);
                GL11.glDepthMask(false);
                GL11.glLineWidth(1.8f);
                GL11.glBlendFunc(770, 771);
                GLUtil.HorizonCode_Horizon_È(2848, true);
                int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                color = ColorUtil.HorizonCode_Horizon_È(color, 0.2);
                OGLManager.Ø­áŒŠá(color);
                RenderHelper_1118140819.Â(new AxisAlignedBB(d - f, d1 + e - 1.75, d2 - f, d + f, d1 + e + 0.3, d2 + f));
                color = ColorUtil.HorizonCode_Horizon_È(color, 0.6);
                OGLManager.Ø­áŒŠá(color);
                RenderHelper_1118140819.Ø­áŒŠá(new AxisAlignedBB(d - f, d1 + e - 1.75, d2 - f, d + f, d1 + e + 0.3, d2 + f));
                GL11.glDepthMask(true);
                GLUtil.HorizonCode_Horizon_È();
                GL11.glPopMatrix();
            }
            else {
                GL11.glPushMatrix();
                GLUtil.HorizonCode_Horizon_È(3042, true);
                GLUtil.HorizonCode_Horizon_È(3553, false);
                GLUtil.HorizonCode_Horizon_È(2896, false);
                GLUtil.HorizonCode_Horizon_È(2929, false);
                GL11.glDepthMask(false);
                GL11.glLineWidth(1.8f);
                GL11.glBlendFunc(770, 771);
                GLUtil.HorizonCode_Horizon_È(2848, true);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
                RenderHelper_1118140819.Â(new AxisAlignedBB(d - f, d1 + e - 1.75, d2 - f, d + f, d1 + e + 0.3, d2 + f));
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
                RenderHelper_1118140819.Ø­áŒŠá(new AxisAlignedBB(d - f, d1 + e - 1.75, d2 - f, d + f, d1 + e + 0.3, d2 + f));
                GL11.glDepthMask(true);
                GLUtil.HorizonCode_Horizon_È();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final double d, final double d1, final double d2, final EntityPlayer ep, final double e, final double f) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.8f);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.8f);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        HorizonCode_Horizon_È(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
        this.Â(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    private void Â(final AxisAlignedBB par1AxisAlignedBB) {
        final Tessellator var2 = Tessellator.HorizonCode_Horizon_È;
        var2.Ý().HorizonCode_Horizon_È(3);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ý);
        var2.Ý().Ø­áŒŠá();
        var2.Ý().HorizonCode_Horizon_È(3);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ý);
        var2.Ý().Ø­áŒŠá();
        var2.Ý().HorizonCode_Horizon_È(1);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ý);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.Ø­áŒŠá, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Â, par1AxisAlignedBB.Ó);
        var2.Ý().Â(par1AxisAlignedBB.HorizonCode_Horizon_È, par1AxisAlignedBB.Âµá€, par1AxisAlignedBB.Ó);
        var2.Ý().Ø­áŒŠá();
    }
    
    public static void HorizonCode_Horizon_È(final AxisAlignedBB axisalignedbb) {
        final Tessellator var3 = Tessellator.HorizonCode_Horizon_È;
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Ø­áŒŠá();
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Ø­áŒŠá();
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Ø­áŒŠá();
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Ø­áŒŠá();
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Ø­áŒŠá();
        var3.Ý().Â();
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        var3.Ý().Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        var3.Ý().Ø­áŒŠá();
    }
}

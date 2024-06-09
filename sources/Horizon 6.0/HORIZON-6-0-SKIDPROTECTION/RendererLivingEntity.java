package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.nio.FloatBuffer;
import org.apache.logging.log4j.Logger;

public abstract class RendererLivingEntity extends Render
{
    private static final Logger HorizonCode_Horizon_È;
    private static final DynamicTexture Âµá€;
    protected ModelBase Ó;
    protected FloatBuffer à;
    protected List Ø;
    public static boolean áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001012";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Âµá€ = new DynamicTexture(16, 16);
        RendererLivingEntity.áŒŠÆ = false;
        final int[] var0 = RendererLivingEntity.Âµá€.Ý();
        for (int var2 = 0; var2 < 256; ++var2) {
            var0[var2] = -1;
        }
        RendererLivingEntity.Âµá€.Â();
    }
    
    public RendererLivingEntity(final RenderManager p_i46156_1_, final ModelBase p_i46156_2_, final float p_i46156_3_) {
        super(p_i46156_1_);
        this.à = GLAllocation.Âµá€(4);
        this.Ø = Lists.newArrayList();
        this.Ó = p_i46156_2_;
        this.Ý = p_i46156_3_;
    }
    
    protected boolean HorizonCode_Horizon_È(final LayerRenderer p_177094_1_) {
        return this.Ø.add(p_177094_1_);
    }
    
    protected boolean Â(final LayerRenderer p_177089_1_) {
        return this.Ø.remove(p_177089_1_);
    }
    
    public ModelBase Â() {
        return this.Ó;
    }
    
    protected float HorizonCode_Horizon_È(final float p_77034_1_, final float p_77034_2_, final float p_77034_3_) {
        float var4;
        for (var4 = p_77034_2_ - p_77034_1_; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return p_77034_1_ + p_77034_3_ * var4;
    }
    
    public void u_() {
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.£à();
        this.Ó.Âµá€ = this.Ø­áŒŠá(p_76986_1_, p_76986_9_);
        this.Ó.Ó = p_76986_1_.áˆºÇŽØ();
        this.Ó.à = p_76986_1_.h_();
        try {
            float var10 = this.HorizonCode_Horizon_È(p_76986_1_.£ÇªÓ, p_76986_1_.¥É, p_76986_9_);
            final float var11 = this.HorizonCode_Horizon_È(p_76986_1_.Š, p_76986_1_.ÂµÕ, p_76986_9_);
            float var12 = var11 - var10;
            if (p_76986_1_.áˆºÇŽØ() && p_76986_1_.Æ instanceof EntityLivingBase) {
                final EntityLivingBase var13 = (EntityLivingBase)p_76986_1_.Æ;
                var10 = this.HorizonCode_Horizon_È(var13.£ÇªÓ, var13.¥É, p_76986_9_);
                var12 = var11 - var10;
                float var14 = MathHelper.à(var12);
                if (var14 < -85.0f) {
                    var14 = -85.0f;
                }
                if (var14 >= 85.0f) {
                    var14 = 85.0f;
                }
                var10 = var11 - var14;
                if (var14 * var14 > 2500.0f) {
                    var10 += var14 * 0.2f;
                }
            }
            final float var15 = p_76986_1_.Õ + (p_76986_1_.áƒ - p_76986_1_.Õ) * p_76986_9_;
            this.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
            float var14 = this.Â(p_76986_1_, p_76986_9_);
            this.HorizonCode_Horizon_È(p_76986_1_, var14, var10, p_76986_9_);
            GlStateManager.ŠÄ();
            GlStateManager.HorizonCode_Horizon_È(-1.0f, -1.0f, 1.0f);
            this.HorizonCode_Horizon_È(p_76986_1_, p_76986_9_);
            final float var16 = 0.0625f;
            GlStateManager.Â(0.0f, -1.5078125f, 0.0f);
            float var17 = p_76986_1_.Šà + (p_76986_1_.áŒŠá€ - p_76986_1_.Šà) * p_76986_9_;
            float var18 = p_76986_1_.¥Ï - p_76986_1_.áŒŠá€ * (1.0f - p_76986_9_);
            if (p_76986_1_.h_()) {
                var18 *= 3.0f;
            }
            if (var17 > 1.0f) {
                var17 = 1.0f;
            }
            GlStateManager.Ø­áŒŠá();
            this.Ó.HorizonCode_Horizon_È(p_76986_1_, var18, var17, p_76986_9_);
            this.Ó.HorizonCode_Horizon_È(var18, var17, var14, var12, var15, 0.0625f, p_76986_1_);
            if (RendererLivingEntity.áŒŠÆ) {
                final boolean var19 = this.Ý(p_76986_1_);
                this.HorizonCode_Horizon_È(p_76986_1_, var18, var17, var14, var12, var15, 0.0625f);
                if (var19) {
                    this.Ó();
                }
            }
            else {
                final boolean var19 = this.Ý(p_76986_1_, p_76986_9_);
                this.HorizonCode_Horizon_È(p_76986_1_, var18, var17, var14, var12, var15, 0.0625f);
                if (var19) {
                    this.à();
                }
                GlStateManager.HorizonCode_Horizon_È(true);
                if (!(p_76986_1_ instanceof EntityPlayer) || !((EntityPlayer)p_76986_1_).Ø­áŒŠá()) {
                    this.HorizonCode_Horizon_È(p_76986_1_, var18, var17, p_76986_9_, var14, var12, var15, 0.0625f);
                }
            }
            GlStateManager.Ñ¢á();
        }
        catch (Exception var20) {
            RendererLivingEntity.HorizonCode_Horizon_È.error("Couldn't render entity", (Throwable)var20);
        }
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.µÕ();
        GlStateManager.à(OpenGlHelper.£à);
        GlStateManager.Å();
        GlStateManager.Ê();
        if (!RendererLivingEntity.áŒŠÆ) {
            super.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }
    
    protected boolean Ý(final EntityLivingBase p_177088_1_) {
        int var2 = 16777215;
        if (p_177088_1_ instanceof EntityPlayer) {
            final ScorePlayerTeam var3 = (ScorePlayerTeam)p_177088_1_.Çªáˆºá();
            if (var3 != null) {
                final String var4 = FontRenderer.Ý(var3.Ø­áŒŠá());
                if (var4.length() >= 2) {
                    var2 = Render.Ý().Â(var4.charAt(1));
                }
            }
        }
        final float var5 = (var2 >> 16 & 0xFF) / 255.0f;
        final float var6 = (var2 >> 8 & 0xFF) / 255.0f;
        final float var7 = (var2 & 0xFF) / 255.0f;
        GlStateManager.Ó();
        GlStateManager.à(OpenGlHelper.£à);
        GlStateManager.Ý(var5, var6, var7, 1.0f);
        GlStateManager.Æ();
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.Æ();
        GlStateManager.à(OpenGlHelper.£à);
        return true;
    }
    
    protected void Ó() {
        GlStateManager.Âµá€();
        GlStateManager.à(OpenGlHelper.£à);
        GlStateManager.µÕ();
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.µÕ();
        GlStateManager.à(OpenGlHelper.£à);
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        final boolean var8 = !p_77036_1_.áŒŠÏ();
        final boolean var9 = !var8 && !p_77036_1_.Ý(Minecraft.áŒŠà().á);
        if (var8 || var9) {
            if (!this.Ý(p_77036_1_)) {
                return;
            }
            if (var9) {
                GlStateManager.Çªà¢();
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.HorizonCode_Horizon_È(false);
                GlStateManager.á();
                GlStateManager.Â(770, 771);
                GlStateManager.HorizonCode_Horizon_È(516, 0.003921569f);
            }
            this.Ó.HorizonCode_Horizon_È(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            if (var9) {
                GlStateManager.ÂµÈ();
                GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
                GlStateManager.Ê();
                GlStateManager.HorizonCode_Horizon_È(true);
            }
        }
    }
    
    protected boolean Ý(final EntityLivingBase p_177090_1_, final float p_177090_2_) {
        return this.HorizonCode_Horizon_È(p_177090_1_, p_177090_2_, true);
    }
    
    protected boolean HorizonCode_Horizon_È(final EntityLivingBase p_177092_1_, final float p_177092_2_, final boolean p_177092_3_) {
        final float var4 = p_177092_1_.Â(p_177092_2_);
        final int var5 = this.HorizonCode_Horizon_È(p_177092_1_, var4, p_177092_2_);
        final boolean var6 = (var5 >> 24 & 0xFF) > 0;
        final boolean var7 = p_177092_1_.µà > 0 || p_177092_1_.ÇªØ­ > 0;
        if (!var6 && !var7) {
            return false;
        }
        if (!var6 && !p_177092_3_) {
            return false;
        }
        GlStateManager.à(OpenGlHelper.£à);
        GlStateManager.µÕ();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, OpenGlHelper.£à);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.µÕ);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, OpenGlHelper.£à);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.µÕ();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, OpenGlHelper.Ø­à);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, OpenGlHelper.Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ñ¢á, OpenGlHelper.Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Ê, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        this.à.position(0);
        if (var7) {
            this.à.put(1.0f);
            this.à.put(0.0f);
            this.à.put(0.0f);
            this.à.put(0.3f);
        }
        else {
            final float var8 = (var5 >> 24 & 0xFF) / 255.0f;
            final float var9 = (var5 >> 16 & 0xFF) / 255.0f;
            final float var10 = (var5 >> 8 & 0xFF) / 255.0f;
            final float var11 = (var5 & 0xFF) / 255.0f;
            this.à.put(var9);
            this.à.put(var10);
            this.à.put(var11);
            this.à.put(1.0f - var8);
        }
        this.à.flip();
        GL11.glTexEnv(8960, 8705, this.à);
        GlStateManager.à(OpenGlHelper.ˆà);
        GlStateManager.µÕ();
        GlStateManager.áŒŠÆ(RendererLivingEntity.Âµá€.HorizonCode_Horizon_È());
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.µà);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        GlStateManager.à(OpenGlHelper.£à);
        return true;
    }
    
    protected void à() {
        GlStateManager.à(OpenGlHelper.£à);
        GlStateManager.µÕ();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, OpenGlHelper.£à);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.µÕ);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, OpenGlHelper.£à);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÕ, OpenGlHelper.µÕ);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.á€, 770);
        GlStateManager.à(OpenGlHelper.µà);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, 5890);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.à(OpenGlHelper.ˆà);
        GlStateManager.Æ();
        GlStateManager.áŒŠÆ(0);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.¥Æ);
        GL11.glTexEnvi(8960, OpenGlHelper.Ï­Ðƒà, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.ŒÏ, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.Çªà¢, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.áŒŠà, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.ŠÄ, OpenGlHelper.Šáƒ);
        GL11.glTexEnvi(8960, OpenGlHelper.ÇŽÉ, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.áƒ, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.ˆá, 5890);
        GlStateManager.à(OpenGlHelper.£à);
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        GlStateManager.Â((float)p_77039_2_, (float)p_77039_4_, (float)p_77039_6_);
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        GlStateManager.Â(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        if (p_77043_1_.ÇªØ­ > 0) {
            float var5 = (p_77043_1_.ÇªØ­ + p_77043_4_ - 1.0f) / 20.0f * 1.6f;
            var5 = MathHelper.Ý(var5);
            if (var5 > 1.0f) {
                var5 = 1.0f;
            }
            GlStateManager.Â(var5 * this.Â(p_77043_1_), 0.0f, 0.0f, 1.0f);
        }
        else {
            final String var6 = EnumChatFormatting.HorizonCode_Horizon_È(p_77043_1_.v_());
            if (var6 != null && (var6.equals("Dinnerbone") || var6.equals("Grumm")) && (!(p_77043_1_ instanceof EntityPlayer) || ((EntityPlayer)p_77043_1_).HorizonCode_Horizon_È(EnumPlayerModelParts.HorizonCode_Horizon_È))) {
                GlStateManager.Â(0.0f, p_77043_1_.£ÂµÄ + 0.1f, 0.0f);
                GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    protected float Ø­áŒŠá(final EntityLivingBase p_77040_1_, final float p_77040_2_) {
        return p_77040_1_.á(p_77040_2_);
    }
    
    protected float Â(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return p_77044_1_.Œ + p_77044_2_;
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_177093_1_, final float p_177093_2_, final float p_177093_3_, final float p_177093_4_, final float p_177093_5_, final float p_177093_6_, final float p_177093_7_, final float p_177093_8_) {
        for (final LayerRenderer var10 : this.Ø) {
            final boolean var11 = this.HorizonCode_Horizon_È(p_177093_1_, p_177093_4_, var10.Â());
            var10.HorizonCode_Horizon_È(p_177093_1_, p_177093_2_, p_177093_3_, p_177093_4_, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
            if (var11) {
                this.à();
            }
        }
    }
    
    protected float Â(final EntityLivingBase p_77037_1_) {
        return 90.0f;
    }
    
    protected int HorizonCode_Horizon_È(final EntityLivingBase p_77030_1_, final float p_77030_2_, final float p_77030_3_) {
        return 0;
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
    }
    
    public void Â(final EntityLivingBase p_77033_1_, final double p_77033_2_, final double p_77033_4_, final double p_77033_6_) {
        if (this.HorizonCode_Horizon_È(p_77033_1_)) {
            final double var8 = p_77033_1_.Âµá€(this.Â.Ó);
            final float var9 = p_77033_1_.Çªà¢() ? 32.0f : 64.0f;
            if (var8 < var9 * var9) {
                final String var10 = p_77033_1_.Ý().áŒŠÆ();
                final float var11 = 0.02666667f;
                GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
                this.HorizonCode_Horizon_È(p_77033_1_, p_77033_2_, p_77033_4_ - (p_77033_1_.h_() ? (p_77033_1_.£ÂµÄ / 2.0f) : 0.0), p_77033_6_, var10, 0.02666667f, var8);
            }
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final EntityLivingBase targetEntity) {
        final EntityPlayerSP var2 = Minecraft.áŒŠà().á;
        if (targetEntity instanceof EntityPlayer && targetEntity != var2) {
            final Team var3 = targetEntity.Çªáˆºá();
            final Team var4 = var2.Çªáˆºá();
            if (var3 != null) {
                final Team.HorizonCode_Horizon_È var5 = var3.Ø();
                switch (RendererLivingEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var5.ordinal()]) {
                    case 1: {
                        return true;
                    }
                    case 2: {
                        return false;
                    }
                    case 3: {
                        return var4 == null || var3.HorizonCode_Horizon_È(var4);
                    }
                    case 4: {
                        return var4 == null || !var3.HorizonCode_Horizon_È(var4);
                    }
                    default: {
                        return true;
                    }
                }
            }
        }
        return Minecraft.Æ() && targetEntity != this.Â.Ó && !targetEntity.Ý(var2) && targetEntity.µÕ == null;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_177086_1_) {
        RendererLivingEntity.áŒŠÆ = p_177086_1_;
    }
    
    @Override
    protected boolean Â(final Entity p_177070_1_) {
        return this.HorizonCode_Horizon_È((EntityLivingBase)p_177070_1_);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_, final double p_177067_6_) {
        this.Â((EntityLivingBase)p_177067_1_, p_177067_2_, p_177067_4_, p_177067_6_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002435";
        
        static {
            HorizonCode_Horizon_È = new int[Team.HorizonCode_Horizon_È.values().length];
            try {
                RendererLivingEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È[Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                RendererLivingEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È[Team.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                RendererLivingEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È[Team.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                RendererLivingEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È[Team.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}

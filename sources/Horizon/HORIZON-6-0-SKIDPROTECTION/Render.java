package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.Iterator;

public abstract class Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    protected final RenderManager Â;
    protected float Ý;
    protected float Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000992";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/misc/shadow.png");
    }
    
    protected Render(final RenderManager p_i46179_1_) {
        this.Ø­áŒŠá = 1.0f;
        this.Â = p_i46179_1_;
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_, final double p_177071_5_, final double p_177071_7_) {
        return p_177071_1_.Ø(p_177071_3_, p_177071_5_, p_177071_7_) && (p_177071_1_.ÇªÂµÕ || p_177071_2_.HorizonCode_Horizon_È(p_177071_1_.£É()));
    }
    
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
    }
    
    protected void HorizonCode_Horizon_È(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_, final double p_177067_6_) {
        if (this.Â(p_177067_1_)) {
            this.HorizonCode_Horizon_È(p_177067_1_, p_177067_1_.Ý().áŒŠÆ(), p_177067_2_, p_177067_4_, p_177067_6_, 64);
        }
    }
    
    protected boolean Â(final Entity p_177070_1_) {
        return p_177070_1_.¥Ï() && p_177070_1_.j_();
    }
    
    protected void HorizonCode_Horizon_È(final Entity p_177069_1_, final double p_177069_2_, final double p_177069_4_, final double p_177069_6_, final String p_177069_8_, final float p_177069_9_, final double p_177069_10_) {
        if (p_177069_1_ instanceof EntityOtherPlayerMP) {
            final EntityOtherPlayerMP mp = (EntityOtherPlayerMP)p_177069_1_;
            final EventNametagRender t = new EventNametagRender(mp, mp.v_(), p_177069_2_, p_177069_4_, p_177069_6_);
            t.Â();
        }
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        if (!ModuleManager.HorizonCode_Horizon_È(NameTags.class).áˆºÑ¢Õ()) {
            this.HorizonCode_Horizon_È(p_177069_1_, p_177069_1_.Ý().áŒŠÆ(), p_177069_2_, p_177069_4_, p_177069_6_, 64);
        }
    }
    
    protected abstract ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p0);
    
    protected boolean Ý(final Entity p_180548_1_) {
        final ResourceLocation_1975012498 var2 = this.HorizonCode_Horizon_È(p_180548_1_);
        if (var2 == null) {
            return false;
        }
        this.HorizonCode_Horizon_È(var2);
        return true;
    }
    
    public void HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110776_1_) {
        this.Â.Ø­áŒŠá.HorizonCode_Horizon_È(p_110776_1_);
    }
    
    private void HorizonCode_Horizon_È(final Entity p_76977_1_, final double p_76977_2_, final double p_76977_4_, final double p_76977_6_, final float p_76977_8_) {
        GlStateManager.Ó();
        final TextureMap var9 = Minecraft.áŒŠà().áŠ();
        final TextureAtlasSprite var10 = var9.HorizonCode_Horizon_È("minecraft:blocks/fire_layer_0");
        final TextureAtlasSprite var11 = var9.HorizonCode_Horizon_È("minecraft:blocks/fire_layer_1");
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_76977_2_, (float)p_76977_4_, (float)p_76977_6_);
        final float var12 = p_76977_1_.áŒŠ * 1.4f;
        GlStateManager.HorizonCode_Horizon_È(var12, var12, var12);
        final Tessellator var13 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var14 = var13.Ý();
        float var15 = 0.5f;
        final float var16 = 0.0f;
        float var17 = p_76977_1_.£ÂµÄ / var12;
        float var18 = (float)(p_76977_1_.Çªà¢ - p_76977_1_.£É().Â);
        GlStateManager.Â(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(0.0f, 0.0f, -0.3f + (int)var17 * 0.02f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        float var19 = 0.0f;
        int var20 = 0;
        var14.Â();
        while (var17 > 0.0f) {
            final TextureAtlasSprite var21 = (var20 % 2 == 0) ? var10 : var11;
            this.HorizonCode_Horizon_È(TextureMap.à);
            float var22 = var21.Âµá€();
            final float var23 = var21.à();
            float var24 = var21.Ó();
            final float var25 = var21.Ø();
            if (var20 / 2 % 2 == 0) {
                final float var26 = var24;
                var24 = var22;
                var22 = var26;
            }
            var14.HorizonCode_Horizon_È(var15 - var16, 0.0f - var18, var19, var24, var25);
            var14.HorizonCode_Horizon_È(-var15 - var16, 0.0f - var18, var19, var22, var25);
            var14.HorizonCode_Horizon_È(-var15 - var16, 1.4f - var18, var19, var22, var23);
            var14.HorizonCode_Horizon_È(var15 - var16, 1.4f - var18, var19, var24, var23);
            var17 -= 0.45f;
            var18 -= 0.45f;
            var15 *= 0.9f;
            var19 += 0.03f;
            ++var20;
        }
        var13.Â();
        GlStateManager.Ê();
        GlStateManager.Âµá€();
    }
    
    private void Ý(final Entity p_76975_1_, final double p_76975_2_, final double p_76975_4_, final double p_76975_6_, final float p_76975_8_, final float p_76975_9_) {
        GlStateManager.á();
        GlStateManager.Â(770, 771);
        this.Â.Ø­áŒŠá.HorizonCode_Horizon_È(Render.HorizonCode_Horizon_È);
        final World var10 = this.HorizonCode_Horizon_È();
        GlStateManager.HorizonCode_Horizon_È(false);
        float var11 = this.Ý;
        if (p_76975_1_ instanceof EntityLiving) {
            final EntityLiving var12 = (EntityLiving)p_76975_1_;
            var11 *= var12.£áƒ();
            if (var12.h_()) {
                var11 *= 0.5f;
            }
        }
        final double var13 = p_76975_1_.áˆºáˆºÈ + (p_76975_1_.ŒÏ - p_76975_1_.áˆºáˆºÈ) * p_76975_9_;
        final double var14 = p_76975_1_.ÇŽá€ + (p_76975_1_.Çªà¢ - p_76975_1_.ÇŽá€) * p_76975_9_;
        final double var15 = p_76975_1_.Ï + (p_76975_1_.Ê - p_76975_1_.Ï) * p_76975_9_;
        final int var16 = MathHelper.Ý(var13 - var11);
        final int var17 = MathHelper.Ý(var13 + var11);
        final int var18 = MathHelper.Ý(var14 - var11);
        final int var19 = MathHelper.Ý(var14);
        final int var20 = MathHelper.Ý(var15 - var11);
        final int var21 = MathHelper.Ý(var15 + var11);
        final double var22 = p_76975_2_ - var13;
        final double var23 = p_76975_4_ - var14;
        final double var24 = p_76975_6_ - var15;
        final Tessellator var25 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var26 = var25.Ý();
        var26.Â();
        for (final BlockPos var28 : BlockPos.Â(new BlockPos(var16, var18, var20), new BlockPos(var17, var19, var21))) {
            final Block var29 = var10.Â(var28.Âµá€()).Ý();
            if (var29.ÂµÈ() != -1 && var10.ˆÏ­(var28) > 3) {
                this.HorizonCode_Horizon_È(var29, p_76975_2_, p_76975_4_, p_76975_6_, var28, p_76975_8_, var11, var22, var23, var24);
            }
        }
        var25.Â();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
        GlStateManager.HorizonCode_Horizon_È(true);
    }
    
    private World HorizonCode_Horizon_È() {
        return this.Â.Âµá€;
    }
    
    private void HorizonCode_Horizon_È(final Block p_180549_1_, final double p_180549_2_, final double p_180549_4_, final double p_180549_6_, final BlockPos p_180549_8_, final float p_180549_9_, final float p_180549_10_, final double p_180549_11_, final double p_180549_13_, final double p_180549_15_) {
        if (p_180549_1_.áˆºÑ¢Õ()) {
            final Tessellator var17 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var18 = var17.Ý();
            double var19 = (p_180549_9_ - (p_180549_4_ - (p_180549_8_.Â() + p_180549_13_)) / 2.0) * 0.5 * this.HorizonCode_Horizon_È().£à(p_180549_8_);
            if (var19 >= 0.0) {
                if (var19 > 1.0) {
                    var19 = 1.0;
                }
                var18.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f, (float)var19);
                final double var20 = p_180549_8_.HorizonCode_Horizon_È() + p_180549_1_.ˆà() + p_180549_11_;
                final double var21 = p_180549_8_.HorizonCode_Horizon_È() + p_180549_1_.¥Æ() + p_180549_11_;
                final double var22 = p_180549_8_.Â() + p_180549_1_.Ø­à() + p_180549_13_ + 0.015625;
                final double var23 = p_180549_8_.Ý() + p_180549_1_.Æ() + p_180549_15_;
                final double var24 = p_180549_8_.Ý() + p_180549_1_.Šáƒ() + p_180549_15_;
                final float var25 = (float)((p_180549_2_ - var20) / 2.0 / p_180549_10_ + 0.5);
                final float var26 = (float)((p_180549_2_ - var21) / 2.0 / p_180549_10_ + 0.5);
                final float var27 = (float)((p_180549_6_ - var23) / 2.0 / p_180549_10_ + 0.5);
                final float var28 = (float)((p_180549_6_ - var24) / 2.0 / p_180549_10_ + 0.5);
                var18.HorizonCode_Horizon_È(var20, var22, var23, var25, var27);
                var18.HorizonCode_Horizon_È(var20, var22, var24, var25, var28);
                var18.HorizonCode_Horizon_È(var21, var22, var24, var26, var28);
                var18.HorizonCode_Horizon_È(var21, var22, var23, var26, var27);
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final AxisAlignedBB p_76978_0_, final double p_76978_1_, final double p_76978_3_, final double p_76978_5_) {
        GlStateManager.Æ();
        final Tessellator var7 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var8 = var7.Ý();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        var8.Â();
        var8.Ý(p_76978_1_, p_76978_3_, p_76978_5_);
        var8.Ý(0.0f, 0.0f, -1.0f);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Ý(0.0f, 0.0f, 1.0f);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Ý(0.0f, -1.0f, 0.0f);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Ý(0.0f, 1.0f, 0.0f);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Ý(-1.0f, 0.0f, 0.0f);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Â(p_76978_0_.HorizonCode_Horizon_È, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Ý(1.0f, 0.0f, 0.0f);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ý);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Âµá€, p_76978_0_.Ó);
        var8.Â(p_76978_0_.Ø­áŒŠá, p_76978_0_.Â, p_76978_0_.Ó);
        var8.Ý(0.0, 0.0, 0.0);
        var7.Â();
        GlStateManager.µÕ();
    }
    
    public void Â(final Entity p_76979_1_, final double p_76979_2_, final double p_76979_4_, final double p_76979_6_, final float p_76979_8_, final float p_76979_9_) {
        if (this.Â.áˆºÑ¢Õ != null) {
            if (this.Â.áˆºÑ¢Õ.Û && this.Ý > 0.0f && !p_76979_1_.áŒŠÏ() && this.Â.HorizonCode_Horizon_È()) {
                final double var10 = this.Â.Â(p_76979_1_.ŒÏ, p_76979_1_.Çªà¢, p_76979_1_.Ê);
                final float var11 = (float)((1.0 - var10 / 256.0) * this.Ø­áŒŠá);
                if (var11 > 0.0f) {
                    this.Ý(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, var11, p_76979_9_);
                }
            }
            if (p_76979_1_.ÇªØ­() && (!(p_76979_1_ instanceof EntityPlayer) || !((EntityPlayer)p_76979_1_).Ø­áŒŠá())) {
                this.HorizonCode_Horizon_È(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_9_);
            }
        }
    }
    
    public static FontRenderer Ý() {
        return Minecraft.áŒŠà().ÂµÈ.Ý();
    }
    
    protected void HorizonCode_Horizon_È(final Entity p_147906_1_, final String p_147906_2_, final double p_147906_3_, final double p_147906_5_, final double p_147906_7_, final int p_147906_9_) {
        final double var10 = p_147906_1_.Âµá€(this.Â.Ó);
        if (var10 <= p_147906_9_ * p_147906_9_) {
            final FontRenderer var11 = Ý();
            final float var12 = 1.6f;
            final float var13 = 0.016666668f * var12;
            GlStateManager.Çªà¢();
            GlStateManager.Â((float)p_147906_3_ + 0.0f, (float)p_147906_5_ + p_147906_1_.£ÂµÄ + 0.5f, (float)p_147906_7_);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
            GlStateManager.HorizonCode_Horizon_È(-var13, -var13, var13);
            GlStateManager.Ó();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.áŒŠÆ();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            final Tessellator var14 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var15 = var14.Ý();
            byte var16 = 0;
            if (p_147906_2_.equals("deadmau5")) {
                var16 = -10;
            }
            GlStateManager.Æ();
            var15.Â();
            final int var17 = var11.HorizonCode_Horizon_È(p_147906_2_) / 2;
            var15.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.25f);
            var15.Â(-var17 - 1, -1 + var16, 0.0);
            var15.Â(-var17 - 1, 8 + var16, 0.0);
            var15.Â(var17 + 1, 8 + var16, 0.0);
            var15.Â(var17 + 1, -1 + var16, 0.0);
            var14.Â();
            GlStateManager.µÕ();
            var11.HorizonCode_Horizon_È(p_147906_2_, -var11.HorizonCode_Horizon_È(p_147906_2_) / 2, var16, 553648127);
            GlStateManager.áˆºÑ¢Õ();
            GlStateManager.HorizonCode_Horizon_È(true);
            var11.HorizonCode_Horizon_È(p_147906_2_, -var11.HorizonCode_Horizon_È(p_147906_2_) / 2, var16, -1);
            GlStateManager.Âµá€();
            GlStateManager.ÂµÈ();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ê();
        }
    }
    
    public RenderManager Ø­áŒŠá() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_177069_1_, String tag, final double pX, double pY, final double pZ) {
        final FontRenderer var12 = Minecraft.áŒŠà().µà;
        String NameTag = "";
        int ping2 = 0;
        pY += 0.9;
        float var13 = Minecraft.áŒŠà().á.Ø­áŒŠá(p_177069_1_) / 3.0f;
        if (var13 < 1.6f) {
            var13 = 1.6f;
        }
        byte var14 = 0;
        if (tag.equals(Minecraft.áŒŠà().á)) {
            var14 = -10;
        }
        if (p_177069_1_ instanceof EntityLivingBase) {
            if (p_177069_1_ instanceof EntityPlayer) {
                if (!FriendManager.HorizonCode_Horizon_È.isEmpty() && FriendManager.HorizonCode_Horizon_È.containsKey(p_177069_1_.v_())) {
                    final String DisplayName = FriendManager.HorizonCode_Horizon_È.get(p_177069_1_.v_());
                    tag = "§a§o" + DisplayName;
                }
                final EntityPlayer pm = (EntityPlayer)p_177069_1_;
                final float hp = Math.round(pm.Ï­Ä() / 2.0f);
                String health = "";
                health = new StringBuilder().append(Math.round(pm.Ï­Ä() / 2.0f)).toString();
                try {
                    final int Ping = ping2 = Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pm.£áŒŠá()).Ý();
                    String pPing = String.valueOf(ping2) + "ms";
                    if (Ping < 0) {
                        pPing = "§cError";
                    }
                    if (Ping >= 200) {
                        pPing = "§c§lARAB";
                    }
                    if (hp >= 10.0f) {
                        NameTag = "§8◖ §2" + health + "§4❤ §7│ §a" + pPing + " §8◗";
                    }
                    else if (hp == 6.0f || hp == 7.0f || hp == 8.0f || hp == 9.0f || hp == 10.0f) {
                        NameTag = "§8◖ §a" + health + "§4❤ §7│ §a" + pPing + " §8◗";
                    }
                    else if (hp == 5.0f || hp == 4.0f || hp == 3.0f) {
                        NameTag = "§8◖ §6" + health + "§4❤ §7│ §a" + pPing + " §8◗";
                    }
                    else if (hp == 2.0f || hp == 1.0f || hp == 0.5) {
                        NameTag = "§8◖ §c" + health + "§4❤ §7│ §a" + pPing + " §8◗";
                    }
                    else if (hp == 0.0f) {
                        NameTag = "§8▶ §4Down! §8◀";
                    }
                    final FontRenderer µà = Minecraft.áŒŠà().µà;
                }
                catch (NullPointerException ex) {}
            }
            final float var15 = 1.6f;
            final float var16 = 0.016666668f * var13;
            final RenderManager renderManager = Minecraft.áŒŠà().ÇªÓ();
            final int color = 16776960;
            float scale = var13 * 1.5f;
            scale /= 100.0f;
            GlStateManager.Çªà¢();
            GL11.glTranslatef((float)pX, (float)pY + 1.5f, (float)pZ);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-scale, -scale, scale);
            GlStateManager.Ó();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.áŒŠÆ();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            final Tessellator var17 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var18 = var17.Ý();
            GlStateManager.Æ();
            var18.Â();
            final int var19 = var12.HorizonCode_Horizon_È(tag) / 2;
            RenderHelper_1118140819.HorizonCode_Horizon_È(-var12.HorizonCode_Horizon_È(tag) / 2 - 1, -14.0f, -var12.HorizonCode_Horizon_È(tag) / 2 + Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(StringUtils.HorizonCode_Horizon_È(tag)), -3.0f, 1.5f, -1879048192, 1342181135);
            if (p_177069_1_ instanceof EntityPlayer) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(-var12.HorizonCode_Horizon_È(NameTag) / 2 - 1, -2.0f, -var12.HorizonCode_Horizon_È(NameTag) / 2 + Minecraft.áŒŠà().µà.HorizonCode_Horizon_È(StringUtils.HorizonCode_Horizon_È(NameTag)), 9.0f, 1.5f, -1879048192, 1342181135);
            }
            var17.Â();
            GlStateManager.µÕ();
            var12.HorizonCode_Horizon_È(tag, -var12.HorizonCode_Horizon_È(tag) / 2, var14 - 12, 553648127);
            var12.HorizonCode_Horizon_È(NameTag, -var12.HorizonCode_Horizon_È(NameTag) / 2, var14, 553648127);
            GlStateManager.áˆºÑ¢Õ();
            GlStateManager.HorizonCode_Horizon_È(true);
            var12.HorizonCode_Horizon_È(tag, -var12.HorizonCode_Horizon_È(tag) / 2, var14 - 12, -1);
            var12.HorizonCode_Horizon_È(NameTag, -var12.HorizonCode_Horizon_È(NameTag) / 2, var14, -1);
            GlStateManager.Âµá€();
            GlStateManager.ÂµÈ();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ê();
        }
    }
}

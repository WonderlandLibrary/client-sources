package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public final class NewChat extends GuiNewChat
{
    private final NahrFont áŒŠÆ;
    private final Minecraft áˆºÑ¢Õ;
    private int ÂµÈ;
    
    public NewChat(final Minecraft mc) {
        super(mc);
        this.áŒŠÆ = new NahrFont("Arial", 18.0f);
        this.áˆºÑ¢Õ = mc;
        this.Ø­áŒŠá = 2;
        this.Âµá€ = 20;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_146230_1_) {
        if (this.áˆºÑ¢Õ.ŠÄ.Ñ¢à != EntityPlayer.HorizonCode_Horizon_È.Ý) {
            final int var2 = this.Â();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.HorizonCode_Horizon_È.size();
            final float var6 = this.áˆºÑ¢Õ.ŠÄ.Šà * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.Ó()) {
                    var3 = true;
                }
                final float var7 = this.áŒŠÆ();
                MathHelper.Ó(this.à() / var7);
                GL11.glPushMatrix();
                GL11.glScalef(var7, var7, 1.0f);
                for (int var8 = 0; var8 + this.Â < this.HorizonCode_Horizon_È.size() && var8 < var2; ++var8) {
                    final ChatLine width = this.HorizonCode_Horizon_È.get(var8 + this.Â);
                    if (width != null) {
                        final int var9 = p_146230_1_ - width.Â();
                        if (var9 < 200 || var3) {
                            double color = var9 / 200.0;
                            color = 1.0 - color;
                            color *= 10.0;
                            if (color < 0.0) {
                                color = 0.0;
                            }
                            if (color > 1.0) {
                                color = 1.0;
                            }
                            color *= color;
                            int var10 = (int)(255.0 * color);
                            if (var3) {
                                var10 = 255;
                            }
                            final int var11 = (int)(var10 * var6);
                            ++var4;
                        }
                    }
                }
                final float var12 = this.à();
                GL11.glTranslated((double)this.Ø­áŒŠá, (double)this.Âµá€, 0.0);
                this.ÂµÈ = -var2 * 9;
                GL11.glTranslated(0.0, -7.0, 0.0);
                final int var13 = this.Ø ? Integer.MIN_VALUE : 1610612736;
                final int bcolor = this.Ø ? 1610612736 : 1073741824;
                if (this.Ó()) {
                    final int var14 = -var2 * 9;
                    RenderHelper_1118140819.HorizonCode_Horizon_È(0.0f, var14 + 4, var12 + 3.0f, 9.0f, 2.0f, 0, -1879048192);
                }
                else if (var4 > 0) {
                    final int var14 = -var4 * 9;
                    RenderHelper_1118140819.HorizonCode_Horizon_È(0.0f, var14 + 4, var12 + 3.0f, 9.0f, 2.0f, 0, -1879048192);
                }
                if (var3) {
                    final int var8 = this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È;
                    GlStateManager.Â(-3.0f, 0.0f, 0.0f);
                    final int var15 = var5 * var8 + var5;
                    final int var9 = var4 * var8;
                    final int var16 = this.Â * var9 / var5 - 8;
                    final int var17 = var9 * var9 / var15;
                    final int var10 = (var16 > 0) ? 170 : 96;
                    Gui_1808253012.HorizonCode_Horizon_È(323, -175, 325, 8, -15132391);
                    if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, -1618884);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, -1618884);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, -13330213);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, -13330213);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, -13710223);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, -13710223);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, -21744);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, -21744);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, -3394561);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, -3394561);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                        final int color2 = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                        Gui_1808253012.HorizonCode_Horizon_È(323, -var16, 325, -var16 - var17 - 11, color2);
                        Gui_1808253012.HorizonCode_Horizon_È(325, -var16, 324, -var16 - var17 - 11, color2);
                    }
                }
                GL11.glLineWidth(1.0f);
                GL11.glTranslated(0.0, 5.0, 0.0);
                for (int var8 = 0; var8 + this.Â < this.HorizonCode_Horizon_È.size() && var8 < var2; ++var8) {
                    final ChatLine var18 = this.HorizonCode_Horizon_È.get(var8 + this.Â);
                    if (var18 != null) {
                        final int var9 = p_146230_1_ - var18.Â();
                        if (var9 < 200 || var3) {
                            double var19 = var9 / 200.0;
                            var19 = 1.0 - var19;
                            var19 *= 10.0;
                            if (var19 < 0.0) {
                                var19 = 0.0;
                            }
                            if (var19 > 1.0) {
                                var19 = 1.0;
                            }
                            var19 *= var19;
                            int var10 = (int)(255.0 * var19);
                            if (var3) {
                                var10 = 255;
                            }
                            var10 *= (int)var6;
                            ++var4;
                            final boolean var20 = true;
                            final int var21 = -var8 * 9;
                            String var22 = var18.HorizonCode_Horizon_È().áŒŠÆ();
                            var22 = var22.replaceAll("ä", "ae");
                            var22 = var22.replaceAll("ö", "oe");
                            var22 = var22.replaceAll("ü", "ue");
                            var22 = var22.replaceAll("ß", "ss");
                            var22 = var22.replaceAll("Ä", "AE");
                            var22 = var22.replaceAll("Ö", "OE");
                            var22 = var22.replaceAll("Ü", "UE");
                            var22 = var22.replaceAll("je", "yee");
                            if (var3) {
                                this.áŒŠÆ.HorizonCode_Horizon_È(var22, 5.0f, var21 - 12 + 2.0f, NahrFont.HorizonCode_Horizon_È.Ó, -1, -16777216);
                            }
                            else {
                                this.áŒŠÆ.HorizonCode_Horizon_È(var22, 2.0f, var21 - 12 + 2.0f, NahrFont.HorizonCode_Horizon_È.Ó, -1, -16777216);
                            }
                            GL11.glDisable(3008);
                        }
                    }
                }
                GL11.glTranslated((double)(-this.Ø­áŒŠá), (double)(-this.Âµá€), 0.0);
                if (var3) {
                    final int var8 = this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È;
                    GlStateManager.Â(-3.0f, 0.0f, 0.0f);
                    final int var15 = var5 * var8 + var5;
                    final int var9 = 2;
                    final int var16 = this.Â * var9 / var5;
                    final int var17 = var9 * var9 / var15;
                    if (var15 != var9) {
                        final int var10 = (var16 > 0) ? 170 : 96;
                        final int var23 = this.Ý ? 13382451 : 3355562;
                        Gui_1808253012.HorizonCode_Horizon_È(0, -var16, 2, -var16 - var17, var23 + (var10 << 24));
                        Gui_1808253012.HorizonCode_Horizon_È(2, -var16, 1, -var16 - var17, 13421772 + (var10 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }
    
    private boolean Â(final int par1, final int par2) {
        final int height = this.áˆºÑ¢Õ.Â().Â();
        return par1 >= this.Ø­áŒŠá && par2 >= this.Âµá€ + this.ÂµÈ - 20 + height - 48 && par1 <= this.Ø­áŒŠá + this.à() + 3 && par2 <= this.Âµá€ + this.ÂµÈ - 6 + height - 48;
    }
    
    public void HorizonCode_Horizon_È(final int par1, final int par2, final int par3) {
        if (this.Â(par1, par2) && par3 == 0) {
            this.Ø = true;
            this.Ó = this.Ø­áŒŠá - par1;
            this.à = this.Âµá€ - par2;
        }
    }
    
    public void Â(final int par1, final int par2, final int par3) {
        if (par3 == 0) {
            this.Ø = false;
        }
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2) {
        final int factor = this.áˆºÑ¢Õ.Â().Âµá€();
        GL11.glScissor((int)(x * factor), (int)((this.áˆºÑ¢Õ.Â().Â() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
}

package HORIZON-6-0-SKIDPROTECTION;

import java.util.Set;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.ArabicShaping;
import org.lwjgl.opengl.GL11;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class FontRenderer implements IResourceManagerReloadListener
{
    private static final ResourceLocation_1975012498[] Ø;
    private float[] áŒŠÆ;
    public int HorizonCode_Horizon_È;
    public Random Â;
    private byte[] áˆºÑ¢Õ;
    public int[] Ý;
    private ResourceLocation_1975012498 ÂµÈ;
    private final TextureManager á;
    private float ˆÏ­;
    private float £á;
    private boolean Å;
    private boolean £à;
    private float µà;
    private float ˆà;
    private float ¥Æ;
    private float Ø­à;
    private int µÕ;
    private boolean Æ;
    private boolean Šáƒ;
    private boolean Ï­Ðƒà;
    private boolean áŒŠà;
    private boolean ŠÄ;
    private static final String Ñ¢á = "CL_00000660";
    public GameSettings Ø­áŒŠá;
    public ResourceLocation_1975012498 Âµá€;
    public boolean Ó;
    public float à;
    
    static {
        Ø = new ResourceLocation_1975012498[256];
    }
    
    public FontRenderer(final GameSettings p_i1035_1_, final ResourceLocation_1975012498 p_i1035_2_, final TextureManager p_i1035_3_, final boolean p_i1035_4_) {
        this.áŒŠÆ = new float[256];
        this.HorizonCode_Horizon_È = 9;
        this.Â = new Random();
        this.áˆºÑ¢Õ = new byte[65536];
        this.Ý = new int[32];
        this.Ó = true;
        this.à = 1.0f;
        this.Ø­áŒŠá = p_i1035_1_;
        this.Âµá€ = p_i1035_2_;
        this.ÂµÈ = p_i1035_2_;
        this.á = p_i1035_3_;
        this.Å = p_i1035_4_;
        p_i1035_3_.HorizonCode_Horizon_È(this.ÂµÈ = HorizonCode_Horizon_È(this.Âµá€));
        for (int var5 = 0; var5 < 32; ++var5) {
            final int var6 = (var5 >> 3 & 0x1) * 85;
            int var7 = (var5 >> 2 & 0x1) * 170 + var6;
            int var8 = (var5 >> 1 & 0x1) * 170 + var6;
            int var9 = (var5 >> 0 & 0x1) * 170 + var6;
            if (var5 == 6) {
                var7 += 85;
            }
            if (p_i1035_1_.Âµá€) {
                final int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                final int var11 = (var7 * 30 + var8 * 70) / 100;
                final int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }
            this.Ý[var5] = ((var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | (var9 & 0xFF));
        }
        this.Ø­áŒŠá();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager resourceManager) {
        this.ÂµÈ = HorizonCode_Horizon_È(this.Âµá€);
        for (int i = 0; i < FontRenderer.Ø.length; ++i) {
            FontRenderer.Ø[i] = null;
        }
        this.Ý();
    }
    
    private void Ý() {
        BufferedImage bufferedimage;
        try {
            bufferedimage = TextureUtil.HorizonCode_Horizon_È(Minecraft.áŒŠà().Âµà().HorizonCode_Horizon_È(this.ÂµÈ).Â());
        }
        catch (IOException var18) {
            throw new RuntimeException(var18);
        }
        final int imgWidth = bufferedimage.getWidth();
        final int imgHeight = bufferedimage.getHeight();
        final int charW = imgWidth / 16;
        final int charH = imgHeight / 16;
        final float kx = imgWidth / 128.0f;
        this.à = kx;
        final int[] ai = new int[imgWidth * imgHeight];
        bufferedimage.getRGB(0, 0, imgWidth, imgHeight, ai, 0, imgWidth);
        for (int k = 0; k < 256; ++k) {
            final int cx = k % 16;
            final int cy = k / 16;
            final boolean px = false;
            int var19;
            for (var19 = charW - 1; var19 >= 0; --var19) {
                final int x = cx * charW + var19;
                boolean flag = true;
                for (int py = 0; py < charH && flag; ++py) {
                    final int ypos = (cy * charH + py) * imgWidth;
                    final int col = ai[x + ypos];
                    final int al = col >> 24 & 0xFF;
                    if (al > 16) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            if (k == 65) {
                k = k;
            }
            if (k == 32) {
                if (charW <= 8) {
                    var19 = (int)(2.0f * kx);
                }
                else {
                    var19 = (int)(1.5f * kx);
                }
            }
            this.áŒŠÆ[k] = (var19 + 1) / kx + 1.0f;
        }
        this.Ó();
    }
    
    private void Ø­áŒŠá() {
        InputStream var1 = null;
        try {
            var1 = Minecraft.áŒŠà().Âµà().HorizonCode_Horizon_È(new ResourceLocation_1975012498("font/glyph_sizes.bin")).Â();
            var1.read(this.áˆºÑ¢Õ);
        }
        catch (IOException var2) {
            throw new RuntimeException(var2);
        }
        finally {
            IOUtils.closeQuietly(var1);
        }
        IOUtils.closeQuietly(var1);
    }
    
    private float HorizonCode_Horizon_È(final int p_78278_1_, final char p_78278_2_, final boolean p_78278_3_) {
        return (p_78278_2_ == ' ') ? this.áŒŠÆ[p_78278_2_] : ((p_78278_2_ == ' ') ? 4.0f : (("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(p_78278_2_) != -1 && !this.Å) ? this.HorizonCode_Horizon_È(p_78278_1_, p_78278_3_) : this.HorizonCode_Horizon_È(p_78278_2_, p_78278_3_)));
    }
    
    private float HorizonCode_Horizon_È(final int p_78266_1_, final boolean p_78266_2_) {
        final float var3 = p_78266_1_ % 16 * 8;
        final float var4 = p_78266_1_ / 16 * 8;
        final float var5 = p_78266_2_ ? 1.0f : 0.0f;
        this.á.HorizonCode_Horizon_È(this.ÂµÈ);
        final float var6 = 7.99f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var3 / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.ˆÏ­ + var5, this.£á, 0.0f);
        GL11.glTexCoord2f(var3 / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.ˆÏ­ - var5, this.£á + 7.99f, 0.0f);
        GL11.glTexCoord2f((var3 + var6 - 1.0f) / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.ˆÏ­ + var6 - 1.0f + var5, this.£á, 0.0f);
        GL11.glTexCoord2f((var3 + var6 - 1.0f) / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.ˆÏ­ + var6 - 1.0f - var5, this.£á + 7.99f, 0.0f);
        GL11.glEnd();
        return this.áŒŠÆ[p_78266_1_];
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final int p_111271_1_) {
        if (FontRenderer.Ø[p_111271_1_] == null) {
            FontRenderer.Ø[p_111271_1_] = new ResourceLocation_1975012498(String.format("textures/font/unicode_page_%02x.png", p_111271_1_));
            FontRenderer.Ø[p_111271_1_] = HorizonCode_Horizon_È(FontRenderer.Ø[p_111271_1_]);
        }
        return FontRenderer.Ø[p_111271_1_];
    }
    
    private void Â(final int p_78257_1_) {
        this.á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(p_78257_1_));
    }
    
    private float HorizonCode_Horizon_È(final char p_78277_1_, final boolean p_78277_2_) {
        if (this.áˆºÑ¢Õ[p_78277_1_] == 0) {
            return 0.0f;
        }
        final int var3 = p_78277_1_ / 'Ā';
        this.Â(var3);
        final int var4 = this.áˆºÑ¢Õ[p_78277_1_] >>> 4;
        final int var5 = this.áˆºÑ¢Õ[p_78277_1_] & 0xF;
        final float var6 = var4;
        final float var7 = var5 + 1;
        final float var8 = p_78277_1_ % '\u0010' * '\u0010' + var6;
        final float var9 = (p_78277_1_ & 'ÿ') / '\u0010' * '\u0010';
        final float var10 = var7 - var6 - 0.02f;
        final float var11 = p_78277_2_ ? 1.0f : 0.0f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var8 / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.ˆÏ­ + var11, this.£á, 0.0f);
        GL11.glTexCoord2f(var8 / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.ˆÏ­ - var11, this.£á + 7.99f, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.ˆÏ­ + var10 / 2.0f + var11, this.£á, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.ˆÏ­ + var10 / 2.0f - var11, this.£á + 7.99f, 0.0f);
        GL11.glEnd();
        return (var7 - var6) / 2.0f + 1.0f;
    }
    
    public int HorizonCode_Horizon_È(final String p_175063_1_, final float p_175063_2_, final float p_175063_3_, final int p_175063_4_) {
        return this.HorizonCode_Horizon_È(p_175063_1_, p_175063_2_, p_175063_3_, p_175063_4_, true);
    }
    
    public int HorizonCode_Horizon_È(final String text, final int x, final int y, final int color) {
        return this.Ó ? this.HorizonCode_Horizon_È(text, x, (float)y, color, false) : 0;
    }
    
    public int HorizonCode_Horizon_È(final String p_175065_1_, final float p_175065_2_, final float p_175065_3_, final int p_175065_4_, final boolean p_175065_5_) {
        GlStateManager.Ø­áŒŠá();
        this.Âµá€();
        int var6;
        if (p_175065_5_) {
            var6 = this.Â(p_175065_1_, p_175065_2_ + 1.0f, p_175065_3_ + 1.0f, p_175065_4_, true);
            var6 = Math.max(var6, this.Â(p_175065_1_, p_175065_2_, p_175065_3_, p_175065_4_, false));
        }
        else {
            var6 = this.Â(p_175065_1_, p_175065_2_, p_175065_3_, p_175065_4_, false);
        }
        return var6;
    }
    
    private String Â(final String p_147647_1_) {
        try {
            final Bidi var3 = new Bidi(new ArabicShaping(8).shape(p_147647_1_), 127);
            var3.setReorderingMode(0);
            return var3.writeReordered(2);
        }
        catch (ArabicShapingException var4) {
            return p_147647_1_;
        }
    }
    
    private void Âµá€() {
        this.Æ = false;
        this.Šáƒ = false;
        this.Ï­Ðƒà = false;
        this.áŒŠà = false;
        this.ŠÄ = false;
    }
    
    private void HorizonCode_Horizon_È(final String p_78255_1_, final boolean p_78255_2_) {
        for (int var3 = 0; var3 < p_78255_1_.length(); ++var3) {
            final char var4 = p_78255_1_.charAt(var3);
            if (var4 == '§' && var3 + 1 < p_78255_1_.length()) {
                int var5 = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(var3 + 1));
                if (var5 < 16) {
                    this.Æ = false;
                    this.Šáƒ = false;
                    this.ŠÄ = false;
                    this.áŒŠà = false;
                    this.Ï­Ðƒà = false;
                    if (var5 < 0 || var5 > 15) {
                        var5 = 15;
                    }
                    if (p_78255_2_) {
                        var5 += 16;
                    }
                    final int var6 = this.Ý[var5];
                    this.µÕ = var6;
                    GlStateManager.Ý((var6 >> 16) / 255.0f, (var6 >> 8 & 0xFF) / 255.0f, (var6 & 0xFF) / 255.0f, this.Ø­à);
                }
                else if (var5 == 16) {
                    this.Æ = true;
                }
                else if (var5 == 17) {
                    this.Šáƒ = true;
                }
                else if (var5 == 18) {
                    this.ŠÄ = true;
                }
                else if (var5 == 19) {
                    this.áŒŠà = true;
                }
                else if (var5 == 20) {
                    this.Ï­Ðƒà = true;
                }
                else if (var5 == 21) {
                    this.Æ = false;
                    this.Šáƒ = false;
                    this.ŠÄ = false;
                    this.áŒŠà = false;
                    this.Ï­Ðƒà = false;
                    GlStateManager.Ý(this.µà, this.ˆà, this.¥Æ, this.Ø­à);
                }
                ++var3;
            }
            else {
                int var5 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(var4);
                if (this.Æ && var5 != -1) {
                    int var6;
                    do {
                        var6 = this.Â.nextInt(this.áŒŠÆ.length);
                    } while ((int)this.áŒŠÆ[var5] != (int)this.áŒŠÆ[var6]);
                    var5 = var6;
                }
                final float var7 = this.Å ? 0.5f : (1.0f / this.à);
                final boolean var8 = (var4 == '\0' || var5 == -1 || this.Å) && p_78255_2_;
                if (var8) {
                    this.ˆÏ­ -= var7;
                    this.£á -= var7;
                }
                float var9 = this.HorizonCode_Horizon_È(var5, var4, this.Ï­Ðƒà);
                if (var8) {
                    this.ˆÏ­ += var7;
                    this.£á += var7;
                }
                if (this.Šáƒ) {
                    this.ˆÏ­ += var7;
                    if (var8) {
                        this.ˆÏ­ -= var7;
                        this.£á -= var7;
                    }
                    this.HorizonCode_Horizon_È(var5, var4, this.Ï­Ðƒà);
                    this.ˆÏ­ -= var7;
                    if (var8) {
                        this.ˆÏ­ += var7;
                        this.£á += var7;
                    }
                    var9 += var7;
                }
                if (this.ŠÄ) {
                    final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
                    final WorldRenderer var11 = var10.Ý();
                    GlStateManager.Æ();
                    var11.Â();
                    var11.Â(this.ˆÏ­, this.£á + this.HorizonCode_Horizon_È / 2, 0.0);
                    var11.Â(this.ˆÏ­ + var9, this.£á + this.HorizonCode_Horizon_È / 2, 0.0);
                    var11.Â(this.ˆÏ­ + var9, this.£á + this.HorizonCode_Horizon_È / 2 - 1.0f, 0.0);
                    var11.Â(this.ˆÏ­, this.£á + this.HorizonCode_Horizon_È / 2 - 1.0f, 0.0);
                    var10.Â();
                    GlStateManager.µÕ();
                }
                if (this.áŒŠà) {
                    final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
                    final WorldRenderer var11 = var10.Ý();
                    GlStateManager.Æ();
                    var11.Â();
                    final int var12 = this.áŒŠà ? -1 : 0;
                    var11.Â(this.ˆÏ­ + var12, this.£á + this.HorizonCode_Horizon_È, 0.0);
                    var11.Â(this.ˆÏ­ + var9, this.£á + this.HorizonCode_Horizon_È, 0.0);
                    var11.Â(this.ˆÏ­ + var9, this.£á + this.HorizonCode_Horizon_È - 1.0f, 0.0);
                    var11.Â(this.ˆÏ­ + var12, this.£á + this.HorizonCode_Horizon_È - 1.0f, 0.0);
                    var10.Â();
                    GlStateManager.µÕ();
                }
                this.ˆÏ­ += var9;
            }
        }
    }
    
    private int HorizonCode_Horizon_È(final String p_78274_1_, int p_78274_2_, final int p_78274_3_, final int p_78274_4_, final int p_78274_5_, final boolean p_78274_6_) {
        if (this.£à) {
            final int var7 = this.HorizonCode_Horizon_È(this.Â(p_78274_1_));
            p_78274_2_ = p_78274_2_ + p_78274_4_ - var7;
        }
        return this.Â(p_78274_1_, p_78274_2_, p_78274_3_, p_78274_5_, p_78274_6_);
    }
    
    private int Â(String p_180455_1_, final float p_180455_2_, final float p_180455_3_, int p_180455_4_, final boolean p_180455_5_) {
        if (p_180455_1_ == null) {
            return 0;
        }
        if (this.£à) {
            p_180455_1_ = this.Â(p_180455_1_);
        }
        if ((p_180455_4_ & 0xFC000000) == 0x0) {
            p_180455_4_ |= 0xFF000000;
        }
        if (p_180455_5_) {
            p_180455_4_ = ((p_180455_4_ & 0xFCFCFC) >> 2 | (p_180455_4_ & 0xFF000000));
        }
        this.µà = (p_180455_4_ >> 16 & 0xFF) / 255.0f;
        this.ˆà = (p_180455_4_ >> 8 & 0xFF) / 255.0f;
        this.¥Æ = (p_180455_4_ & 0xFF) / 255.0f;
        this.Ø­à = (p_180455_4_ >> 24 & 0xFF) / 255.0f;
        GlStateManager.Ý(this.µà, this.ˆà, this.¥Æ, this.Ø­à);
        this.ˆÏ­ = p_180455_2_;
        this.£á = p_180455_3_;
        this.HorizonCode_Horizon_È(p_180455_1_, p_180455_5_);
        return (int)this.ˆÏ­;
    }
    
    public int HorizonCode_Horizon_È(final String p_78256_1_) {
        if (p_78256_1_ == null) {
            return 0;
        }
        float var2 = 0.0f;
        boolean var3 = false;
        for (int var4 = 0; var4 < p_78256_1_.length(); ++var4) {
            char var5 = p_78256_1_.charAt(var4);
            float var6 = this.Ý(var5);
            if (var6 < 0.0f && var4 < p_78256_1_.length() - 1) {
                ++var4;
                var5 = p_78256_1_.charAt(var4);
                if (var5 != 'l' && var5 != 'L') {
                    if (var5 == 'r' || var5 == 'R') {
                        var3 = false;
                    }
                }
                else {
                    var3 = true;
                }
                var6 = 0.0f;
            }
            var2 += var6;
            if (var3 && var6 > 0.0f) {
                ++var2;
            }
        }
        return (int)var2;
    }
    
    public int HorizonCode_Horizon_È(final char par1) {
        return Math.round(this.Ý(par1));
    }
    
    private float Ý(final char p_78263_1_) {
        if (p_78263_1_ == '§') {
            return -1.0f;
        }
        if (p_78263_1_ == ' ') {
            return this.áŒŠÆ[32];
        }
        final int var2 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(p_78263_1_);
        if (p_78263_1_ > '\0' && var2 != -1 && !this.Å) {
            return this.áŒŠÆ[var2];
        }
        if (this.áˆºÑ¢Õ[p_78263_1_] != 0) {
            int var3 = this.áˆºÑ¢Õ[p_78263_1_] >>> 4;
            int var4 = this.áˆºÑ¢Õ[p_78263_1_] & 0xF;
            if (var4 > 7) {
                var4 = 15;
                var3 = 0;
            }
            return (++var4 - var3) / 2 + 1;
        }
        return 0.0f;
    }
    
    public String HorizonCode_Horizon_È(final String p_78269_1_, final int p_78269_2_) {
        return this.HorizonCode_Horizon_È(p_78269_1_, p_78269_2_, false);
    }
    
    public String HorizonCode_Horizon_È(final String p_78262_1_, final int p_78262_2_, final boolean p_78262_3_) {
        final StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        final int var6 = p_78262_3_ ? (p_78262_1_.length() - 1) : 0;
        final int var7 = p_78262_3_ ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < p_78262_1_.length() && var5 < p_78262_2_; var10 += var7) {
            final char var11 = p_78262_1_.charAt(var10);
            final float var12 = this.Ý(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                }
                else {
                    var9 = true;
                }
            }
            else if (var12 < 0.0f) {
                var8 = true;
            }
            else {
                var5 += var12;
                if (var9) {
                    ++var5;
                }
            }
            if (var5 > p_78262_2_) {
                break;
            }
            if (p_78262_3_) {
                var4.insert(0, var11);
            }
            else {
                var4.append(var11);
            }
        }
        return var4.toString();
    }
    
    private String Ø­áŒŠá(String p_78273_1_) {
        while (p_78273_1_ != null && p_78273_1_.endsWith("\n")) {
            p_78273_1_ = p_78273_1_.substring(0, p_78273_1_.length() - 1);
        }
        return p_78273_1_;
    }
    
    public void HorizonCode_Horizon_È(String str, final int x, final int y, final int wrapWidth, final int textColor) {
        this.Âµá€();
        this.µÕ = textColor;
        str = this.Ø­áŒŠá(str);
        this.HorizonCode_Horizon_È(str, x, y, wrapWidth, false);
    }
    
    private void HorizonCode_Horizon_È(final String str, final int x, int y, final int wrapWidth, final boolean addShadow) {
        final List var6 = this.Ý(str, wrapWidth);
        for (final String var8 : var6) {
            this.HorizonCode_Horizon_È(var8, x, y, wrapWidth, this.µÕ, addShadow);
            y += this.HorizonCode_Horizon_È;
        }
    }
    
    public int Â(final String p_78267_1_, final int p_78267_2_) {
        return this.HorizonCode_Horizon_È * this.Ý(p_78267_1_, p_78267_2_).size();
    }
    
    public void HorizonCode_Horizon_È(final boolean p_78264_1_) {
        this.Å = p_78264_1_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Å;
    }
    
    public void Â(final boolean p_78275_1_) {
        this.£à = p_78275_1_;
    }
    
    public List Ý(final String str, final int wrapWidth) {
        return Arrays.asList(this.Ø­áŒŠá(str, wrapWidth).split("\n"));
    }
    
    String Ø­áŒŠá(final String str, final int wrapWidth) {
        final int var3 = this.Âµá€(str, wrapWidth);
        if (str.length() <= var3) {
            return str;
        }
        final String var4 = str.substring(0, var3);
        final char var5 = str.charAt(var3);
        final boolean var6 = var5 == ' ' || var5 == '\n';
        final String var7 = String.valueOf(Ý(var4)) + str.substring(var3 + (var6 ? 1 : 0));
        return String.valueOf(var4) + "\n" + this.Ø­áŒŠá(var7, wrapWidth);
    }
    
    private int Âµá€(final String str, final int wrapWidth) {
        final int var3 = str.length();
        float var4 = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            final char var8 = str.charAt(var5);
            Label_0163: {
                switch (var8) {
                    case '\n': {
                        --var5;
                        break Label_0163;
                    }
                    case '§': {
                        if (var5 >= var3 - 1) {
                            break Label_0163;
                        }
                        ++var5;
                        final char var9 = str.charAt(var5);
                        if (var9 == 'l' || var9 == 'L') {
                            var7 = true;
                            break Label_0163;
                        }
                        if (var9 == 'r' || var9 == 'R' || Ø­áŒŠá(var9)) {
                            var7 = false;
                        }
                        break Label_0163;
                    }
                    case ' ': {
                        var6 = var5;
                        break;
                    }
                }
                var4 += this.Ý(var8);
                if (var7) {
                    ++var4;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > wrapWidth) {
                break;
            }
            ++var5;
        }
        return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    private static boolean Ø­áŒŠá(final char colorChar) {
        return (colorChar >= '0' && colorChar <= '9') || (colorChar >= 'a' && colorChar <= 'f') || (colorChar >= 'A' && colorChar <= 'F');
    }
    
    private static boolean Âµá€(final char formatChar) {
        return (formatChar >= 'k' && formatChar <= 'o') || (formatChar >= 'K' && formatChar <= 'O') || formatChar == 'r' || formatChar == 'R';
    }
    
    public static String Ý(final String p_78282_0_) {
        String var1 = "";
        int var2 = -1;
        final int var3 = p_78282_0_.length();
        while ((var2 = p_78282_0_.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = p_78282_0_.charAt(var2 + 1);
                if (Ø­áŒŠá(var4)) {
                    var1 = "§" + var4;
                }
                else {
                    if (!Âµá€(var4)) {
                        continue;
                    }
                    var1 = String.valueOf(var1) + "§" + var4;
                }
            }
        }
        return var1;
    }
    
    public boolean Â() {
        return this.£à;
    }
    
    public int Â(final char p_175064_1_) {
        final int index = "0123456789abcdef".indexOf(p_175064_1_);
        return (index >= 0 && index < this.Ý.length) ? this.Ý[index] : 16777215;
    }
    
    private void Ó() {
        final String fontFileName = this.ÂµÈ.Â();
        final String suffix = ".png";
        if (fontFileName.endsWith(suffix)) {
            final String fileName = String.valueOf(fontFileName.substring(0, fontFileName.length() - suffix.length())) + ".properties";
            try {
                final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(this.ÂµÈ.Ý(), fileName);
                final InputStream in = Config.HorizonCode_Horizon_È(Config.ˆáŠ(), e);
                if (in == null) {
                    return;
                }
                Config.Ø­áŒŠá("Loading " + fileName);
                final Properties props = new Properties();
                props.load(in);
                final Set keySet = props.keySet();
                for (final String key : keySet) {
                    final String prefix = "width.";
                    if (key.startsWith(prefix)) {
                        final String numStr = key.substring(prefix.length());
                        final int num = Config.HorizonCode_Horizon_È(numStr, -1);
                        if (num < 0 || num >= this.áŒŠÆ.length) {
                            continue;
                        }
                        final String value = props.getProperty(key);
                        final float width = Config.HorizonCode_Horizon_È(value, -1.0f);
                        if (width < 0.0f) {
                            continue;
                        }
                        this.áŒŠÆ[num] = width;
                    }
                }
            }
            catch (FileNotFoundException ex) {}
            catch (IOException var16) {
                var16.printStackTrace();
            }
        }
    }
    
    private static ResourceLocation_1975012498 HorizonCode_Horizon_È(final ResourceLocation_1975012498 fontLoc) {
        if (!Config.ˆÓ()) {
            return fontLoc;
        }
        if (fontLoc == null) {
            return fontLoc;
        }
        String fontName = fontLoc.Â();
        final String texturesStr = "textures/";
        final String mcpatcherStr = "mcpatcher/";
        if (!fontName.startsWith(texturesStr)) {
            return fontLoc;
        }
        fontName = fontName.substring(texturesStr.length());
        fontName = String.valueOf(mcpatcherStr) + fontName;
        final ResourceLocation_1975012498 fontLocHD = new ResourceLocation_1975012498(fontLoc.Ý(), fontName);
        return Config.Â(Config.ˆáŠ(), fontLocHD) ? fontLocHD : fontLoc;
    }
}

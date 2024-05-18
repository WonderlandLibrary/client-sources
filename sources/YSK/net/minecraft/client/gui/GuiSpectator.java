package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.spectator.categories.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiSpectator extends Gui implements ISpectatorMenuRecipient
{
    public static final ResourceLocation field_175269_a;
    private SpectatorMenu field_175271_i;
    private static final String[] I;
    private static final ResourceLocation field_175267_f;
    private long field_175270_h;
    private final Minecraft field_175268_g;
    
    static {
        I();
        field_175267_f = new ResourceLocation(GuiSpectator.I["".length()]);
        field_175269_a = new ResourceLocation(GuiSpectator.I[" ".length()]);
    }
    
    public boolean func_175262_a() {
        if (this.field_175271_i != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void func_175263_a(final ScaledResolution scaledResolution) {
        final int n = (int)(this.func_175265_c() * 255.0f);
        if (n > "   ".length() && this.field_175271_i != null) {
            final ISpectatorMenuObject func_178645_b = this.field_175271_i.func_178645_b();
            String s;
            if (func_178645_b != SpectatorMenu.field_178657_a) {
                s = func_178645_b.getSpectatorName().getFormattedText();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                s = this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
            }
            final String s2 = s;
            if (s2 != null) {
                final int n2 = (scaledResolution.getScaledWidth() - this.field_175268_g.fontRendererObj.getStringWidth(s2)) / "  ".length();
                final int n3 = scaledResolution.getScaledHeight() - (0x1D ^ 0x3E);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(118 + 654 - 623 + 621, 228 + 191 + 239 + 113, " ".length(), "".length());
                this.field_175268_g.fontRendererObj.drawStringWithShadow(s2, n2, n3, 12543066 + 5362464 - 17247894 + 16119579 + (n << (0x95 ^ 0x8D)));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_175266_a(final int n, final int n2, final float n3, final float n4, final ISpectatorMenuObject spectatorMenuObject) {
        this.field_175268_g.getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        if (spectatorMenuObject != SpectatorMenu.field_178657_a) {
            final int n5 = (int)(n4 * 255.0f);
            GlStateManager.pushMatrix();
            GlStateManager.translate(n2, n3, 0.0f);
            float n6;
            if (spectatorMenuObject.func_178662_A_()) {
                n6 = 1.0f;
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                n6 = 0.25f;
            }
            final float n7 = n6;
            GlStateManager.color(n7, n7, n7, n4);
            spectatorMenuObject.func_178663_a(n7, n5);
            GlStateManager.popMatrix();
            final String value = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[n].getKeyCode()));
            if (n5 > "   ".length() && spectatorMenuObject.func_178662_A_()) {
                this.field_175268_g.fontRendererObj.drawStringWithShadow(value, n2 + (0x49 ^ 0x5A) - "  ".length() - this.field_175268_g.fontRendererObj.getStringWidth(value), n3 + 6.0f + 3.0f, 13568964 + 3137945 - 4173762 + 4244068 + (n5 << (0xB9 ^ 0xA1)));
            }
        }
    }
    
    public void renderTooltip(final ScaledResolution scaledResolution, final float n) {
        if (this.field_175271_i != null) {
            final float func_175265_c = this.func_175265_c();
            if (func_175265_c <= 0.0f) {
                this.field_175271_i.func_178641_d();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                final int n2 = scaledResolution.getScaledWidth() / "  ".length();
                final float zLevel = this.zLevel;
                this.zLevel = -90.0f;
                this.func_175258_a(scaledResolution, func_175265_c, n2, scaledResolution.getScaledHeight() - 22.0f * func_175265_c, this.field_175271_i.func_178646_f());
                this.zLevel = zLevel;
            }
        }
    }
    
    @Override
    public void func_175257_a(final SpectatorMenu spectatorMenu) {
        this.field_175271_i = null;
        this.field_175270_h = 0L;
    }
    
    public void func_175261_b() {
        this.field_175270_h = Minecraft.getSystemTime();
        if (this.func_175262_a()) {
            final int func_178648_e = this.field_175271_i.func_178648_e();
            if (func_178648_e != -" ".length()) {
                this.field_175271_i.func_178644_b(func_178648_e);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        else {
            this.field_175271_i = new SpectatorMenu(this);
        }
    }
    
    public void func_175260_a(final int n) {
        this.field_175270_h = Minecraft.getSystemTime();
        if (this.field_175271_i != null) {
            this.field_175271_i.func_178644_b(n);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            this.field_175271_i = new SpectatorMenu(this);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0019+;:'\u001f+0a5\u0018'l9;\t)&:!C>-)", "mNCNR");
        GuiSpectator.I[" ".length()] = I("\u00077\u000e\u001e\"\u00017\u0005E0\u0006;Y\u0019'\u00161\u0002\u000b#\u001c )\u001d>\u00175\u0013\u001e$]\"\u0018\r", "sRvjW");
    }
    
    protected void func_175258_a(final ScaledResolution scaledResolution, final float n, final int n2, final float n3, final SpectatorDetails spectatorDetails) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(61 + 50 + 448 + 211, 177 + 655 - 818 + 757, " ".length(), "".length());
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
        this.field_175268_g.getTextureManager().bindTexture(GuiSpectator.field_175267_f);
        this.drawTexturedModalRect(n2 - (0x34 ^ 0x6F), n3, "".length(), "".length(), 157 + 111 - 187 + 101, 0x6 ^ 0x10);
        if (spectatorDetails.func_178681_b() >= 0) {
            this.drawTexturedModalRect(n2 - (0x77 ^ 0x2C) - " ".length() + spectatorDetails.func_178681_b() * (0x82 ^ 0x96), n3 - 1.0f, "".length(), 0x38 ^ 0x2E, 0x5E ^ 0x46, 0xA3 ^ 0xB5);
        }
        RenderHelper.enableGUIStandardItemLighting();
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < (0xAE ^ 0xA7)) {
            this.func_175266_a(i, scaledResolution.getScaledWidth() / "  ".length() - (0x11 ^ 0x4B) + i * (0xB ^ 0x1F) + "  ".length(), n3 + 3.0f, n, spectatorDetails.func_178680_a(i));
            ++i;
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
    
    public void func_175259_b(final int n) {
        int n2 = this.field_175271_i.func_178648_e() + n;
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (n2 >= 0 && n2 <= (0x80 ^ 0x88) && (this.field_175271_i.func_178643_a(n2) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(n2).func_178662_A_())) {
            n2 += n;
        }
        if (n2 >= 0 && n2 <= (0x82 ^ 0x8A)) {
            this.field_175271_i.func_178644_b(n2);
            this.field_175270_h = Minecraft.getSystemTime();
        }
    }
    
    public GuiSpectator(final Minecraft field_175268_g) {
        this.field_175268_g = field_175268_g;
    }
    
    private float func_175265_c() {
        return MathHelper.clamp_float((this.field_175270_h - Minecraft.getSystemTime() + 5000L) / 2000.0f, 0.0f, 1.0f);
    }
}

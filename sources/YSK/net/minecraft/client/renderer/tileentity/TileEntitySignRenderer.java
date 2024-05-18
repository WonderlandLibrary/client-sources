package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign>
{
    private static final String[] I;
    private final ModelSign model;
    private static final ResourceLocation SIGN_TEXTURE;
    
    private static void I() {
        (I = new String[0xA4 ^ 0xA0])["".length()] = I("\f/\r&\u0016\n/\u0006}\u0006\u0016>\u001c&\u001aW9\u001c5\rV:\u001b5", "xJuRc");
        TileEntitySignRenderer.I[" ".length()] = I("", "ojrQH");
        TileEntitySignRenderer.I["  ".length()] = I("oz", "QZMsG");
        TileEntitySignRenderer.I["   ".length()] = I("wQ", "WmIaV");
    }
    
    static {
        I();
        SIGN_TEXTURE = new ResourceLocation(TileEntitySignRenderer.I["".length()]);
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntitySign tileEntitySign, final double n, final double n2, final double n3, final float n4, final int n5) {
        final Block blockType = tileEntitySign.getBlockType();
        GlStateManager.pushMatrix();
        final float n6 = 0.6666667f;
        if (blockType == Blocks.standing_sign) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n6, (float)n3 + 0.5f);
            GlStateManager.rotate(-(tileEntitySign.getBlockMetadata() * (290 + 222 - 334 + 182) / 16.0f), 0.0f, 1.0f, 0.0f);
            this.model.signStick.showModel = (" ".length() != 0);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            final int blockMetadata = tileEntitySign.getBlockMetadata();
            float n7 = 0.0f;
            if (blockMetadata == "  ".length()) {
                n7 = 180.0f;
            }
            if (blockMetadata == (0x8E ^ 0x8A)) {
                n7 = 90.0f;
            }
            if (blockMetadata == (0x8A ^ 0x8F)) {
                n7 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n6, (float)n3 + 0.5f);
            GlStateManager.rotate(-n7, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.model.signStick.showModel = ("".length() != 0);
        }
        if (n5 >= 0) {
            this.bindTexture(TileEntitySignRenderer.DESTROY_STAGES[n5]);
            GlStateManager.matrixMode(5090 + 2191 - 6575 + 5184);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(4750 + 2655 - 6069 + 4552);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            this.bindTexture(TileEntitySignRenderer.SIGN_TEXTURE);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(n6, -n6, -n6);
        this.model.renderSign();
        GlStateManager.popMatrix();
        final FontRenderer fontRenderer = this.getFontRenderer();
        final float n8 = 0.015625f * n6;
        GlStateManager.translate(0.0f, 0.5f * n6, 0.07f * n6);
        GlStateManager.scale(n8, -n8, n8);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * n8);
        GlStateManager.depthMask("".length() != 0);
        final int length = "".length();
        if (n5 < 0) {
            int i = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (i < tileEntitySign.signText.length) {
                if (tileEntitySign.signText[i] != null) {
                    final List<IChatComponent> func_178908_a = GuiUtilRenderComponents.func_178908_a(tileEntitySign.signText[i], 0x76 ^ 0x2C, fontRenderer, "".length() != 0, " ".length() != 0);
                    String formattedText;
                    if (func_178908_a != null && func_178908_a.size() > 0) {
                        formattedText = func_178908_a.get("".length()).getFormattedText();
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        formattedText = TileEntitySignRenderer.I[" ".length()];
                    }
                    final String s = formattedText;
                    if (i == tileEntitySign.lineBeingEdited) {
                        final String string = TileEntitySignRenderer.I["  ".length()] + s + TileEntitySignRenderer.I["   ".length()];
                        fontRenderer.drawString(string, -fontRenderer.getStringWidth(string) / "  ".length(), i * (0xF ^ 0x5) - tileEntitySign.signText.length * (0x9C ^ 0x99), length);
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / "  ".length(), i * (0x9B ^ 0x91) - tileEntitySign.signText.length * (0x50 ^ 0x55), length);
                    }
                }
                ++i;
            }
        }
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
        if (n5 >= 0) {
            GlStateManager.matrixMode(667 + 2753 - 311 + 2781);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(2394 + 1022 - 1479 + 3951);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntitySign)tileEntity, n, n2, n3, n4, n5);
    }
    
    public TileEntitySignRenderer() {
        this.model = new ModelSign();
    }
}

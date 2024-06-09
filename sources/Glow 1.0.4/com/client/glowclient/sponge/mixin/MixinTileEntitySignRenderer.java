package com.client.glowclient.sponge.mixin;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.util.text.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.tileentity.*;

@Mixin({ TileEntitySignRenderer.class })
public abstract class MixinTileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign>
{
    @Shadow
    private static final ResourceLocation field_147513_b;
    @Shadow
    private final ModelSign field_147514_c;
    
    public MixinTileEntitySignRenderer() {
        super();
        this.model = new ModelSign();
    }
    
    @Overwrite
    public void render(final TileEntitySign tileEntitySign, final double n, final double n2, final double n3, final float n4, final int n5, final float n6) {
        final Block blockType = tileEntitySign.getBlockType();
        GlStateManager.pushMatrix();
        if (blockType == Blocks.STANDING_SIGN) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.5f, (float)n3 + 0.5f);
            GlStateManager.rotate(-(tileEntitySign.getBlockMetadata() * 360 / 16.0f), 0.0f, 1.0f, 0.0f);
            this.model.signStick.showModel = true;
        }
        else {
            final int blockMetadata = tileEntitySign.getBlockMetadata();
            float n7 = 0.0f;
            if (blockMetadata == 2) {
                n7 = 180.0f;
            }
            if (blockMetadata == 4) {
                n7 = 90.0f;
            }
            if (blockMetadata == 5) {
                n7 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.5f, (float)n3 + 0.5f);
            GlStateManager.rotate(-n7, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.model.signStick.showModel = false;
        }
        if (n5 >= 0) {
            this.bindTexture(MixinTileEntitySignRenderer.DESTROY_STAGES[n5]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        }
        else {
            this.bindTexture(MixinTileEntitySignRenderer.SIGN_TEXTURE);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6666667f, -0.6666667f, -0.6666667f);
        this.model.renderSign();
        GlStateManager.popMatrix();
        final FontRenderer fontRenderer = this.getFontRenderer();
        GlStateManager.translate(0.0f, 0.33333334f, 0.046666667f);
        GlStateManager.scale(0.010416667f, -0.010416667f, 0.010416667f);
        GlStateManager.glNormal3f(0.0f, 0.0f, -0.010416667f);
        GlStateManager.depthMask(false);
        if (n5 < 0) {
            for (int i = 0; i < tileEntitySign.signText.length; ++i) {
                if (tileEntitySign.signText[i] != null) {
                    final List splitText = GuiUtilRenderComponents.splitText(tileEntitySign.signText[i], (int)HookTranslator.m14(), fontRenderer, false, true);
                    final String s = (splitText != null && !splitText.isEmpty()) ? splitText.get(0).getFormattedText() : "";
                    if (i == tileEntitySign.lineBeingEdited) {
                        final String string = "> " + s + " <";
                        fontRenderer.drawString(string, -fontRenderer.getStringWidth(string) / 2, i * 10 - tileEntitySign.signText.length * 5, 0);
                    }
                    else {
                        fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, i * 10 - tileEntitySign.signText.length * 5, 0);
                    }
                }
            }
        }
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
        if (n5 >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Overwrite
    public void render(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5, final float n6) {
        this.render((TileEntitySign)tileEntity, n, n2, n3, n4, n5, n6);
    }
    
    static {
        MixinTileEntitySignRenderer.SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
    }
}

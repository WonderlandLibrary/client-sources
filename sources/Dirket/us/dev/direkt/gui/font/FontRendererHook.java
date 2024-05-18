package us.dev.direkt.gui.font;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public final class FontRendererHook extends FontRenderer {
    public FontRendererHook(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4) {
        super(par1GameSettings, par2ResourceLocation, par3TextureManager, par4);
        FontManager.GLOBAL_FONT.initialize("textures/font/ascii.png", this.colorCode);
    }

    @Override
    protected int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        } else {
            if (this.bidiFlag) {
                text = this.bidiReorder(text);
            }

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (dropShadow) {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float) (color >> 16 & 255) / 255.0F;
            this.green = (float) (color >> 8 & 255) / 255.0F;
            this.blue = (float) (color & 255) / 255.0F;
            this.alpha = (float) (color >> 24 & 255) / 255.0F;
            this.setColor(this.red, this.green, this.blue, this.alpha);
            this.posX = x;
            this.posY = y;

            if (FontManager.usingCustomFont && FontManager.GLOBAL_FONT.stringCache != null) {
                this.posX += (float) FontManager.GLOBAL_FONT.renderString(text, x, y, color, dropShadow);
            } else
                this.renderStringAtPos(text, dropShadow);

            return (int) this.posX;
        }
    }

    @Override
    public int getStringWidth(String par1Str) {
        return FontManager.usingCustomFont ? FontManager.GLOBAL_FONT.getStringWidth(par1Str) : super.getStringWidth(par1Str);
    }

	@Override
	public String trimStringToWidth(String par1Str, int par2, boolean par3) {
		return FontManager.usingCustomFont ? FontManager.GLOBAL_FONT.stringCache.trimStringToWidth(par1Str, par2, par3) : super.trimStringToWidth(par1Str, par2, par3);
	}

	@Override
	protected int sizeStringToWidth(String par1Str, int par2) {
		return FontManager.usingCustomFont ? FontManager.GLOBAL_FONT.stringCache.sizeStringToWidth(par1Str, par2) : super.sizeStringToWidth(par1Str, par2);
	}
}
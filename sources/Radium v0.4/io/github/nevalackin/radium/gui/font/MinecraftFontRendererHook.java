package io.github.nevalackin.radium.gui.font;

import net.minecraft.client.gui.MinecraftFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public final class MinecraftFontRendererHook extends MinecraftFontRenderer {

    public MinecraftFontRendererHook(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn) {
        super(gameSettingsIn, location, textureManagerIn);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        GlStateManager.enableAlpha();
        resetStyles();
        if (dropShadow) {
            renderString(text, x + 1F, y + 1F, color, true);
        }

        return renderString(text, x, y, color, false);
    }

    @Override
    public float getWidth(String string) {
        int strLen;
        // Assert string is not null before calling length()
        if (string != null && (strLen = string.length()) != 0) {
            // We know the string is at least 1 char long
            float len = getCharWidthFloat(string.charAt(0));
            boolean boldChar = false;
            for (int i = 1; i < strLen; i++) {
                float cLen = getCharWidthFloat(string.charAt(i));
                // If char width is less than 0 and we are not at the last letter
                if (cLen < 0.0f && i < strLen - 1) {
                    i++;
                    // Get next char
                    char c = string.charAt(i);
                    if (c != 'L' && c != 'l') {
                        if (c == 'R' || c == 'r') boldChar = false;
                    } else boldChar = true;
                    cLen = 0.0f;
                }
                // If char is bold and not a zero width char account for by adding `offsetBold`
                if (boldChar && cLen > 0.0f) cLen += offsetBold;
                len += cLen;
            }
            return (int) len;
        }

        return 0;
    }
}

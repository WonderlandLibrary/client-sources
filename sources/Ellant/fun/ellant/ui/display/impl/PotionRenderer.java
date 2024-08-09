package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;

public class PotionRenderer implements ElementRenderer {
    private final Dragging dragging;
    private float width;
    private float height;
    private float animation;

    final ResourceLocation potion = new ResourceLocation("expensive/images/hud/potions.png");
    final float iconSize = 10;

    public void render(final EventDisplay eventDisplay) {
        final MatrixStack ms = eventDisplay.getMatrixStack();
        final float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        final float fontSize = 6.5f;
        final float padding = 5.0f;
        String name = "Potions";

        DisplayUtils.drawRoundedRect(posX, posY + 1.0f, this.animation, 18.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 210));
        DisplayUtils.drawImage(potion, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawText(ms, "Potions", posX + iconSize + padding * 2, posY + padding + 1.5f, ColorUtils.rgb(255, 255, 255), 6.5f);
        DisplayUtils.drawRectVerticalW(posX + 18.0f, posY + 3.0f, width - 1, 14.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));
        posY += fontSize + padding * 2.0f;
        float maxWidth = Fonts.sfui.getWidth(name, fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        posY += 3.0f;

        for (EffectInstance ef : PotionRenderer.mc.player.getActivePotionEffects()) {
            final int amp = ef.getAmplifier();
            String ampStr = "";
            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1));
            }
            final String nameText = I18n.format(ef.getEffectName()) + ampStr;
            final float nameWidth = Fonts.sfui.getWidth(nameText, fontSize);
            final String bindText = EffectUtils.getPotionDurationString(ef, 1.0f);
            final float bindWidth = Fonts.sfui.getWidth(bindText, fontSize);
            final float localWidth = nameWidth + bindWidth + iconSize + padding * 4.0f;

            DisplayUtils.drawRoundedRect(posX, posY, this.animation, 12.0f, 3.0f, ColorUtils.rgba(21, 21, 21, 215));

            Fonts.sfui.drawText(ms, nameText, posX + padding, posY + 2.5f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
            Fonts.sfui.drawText(ms, bindText, posX + this.animation - iconSize - padding * 2 - bindWidth, posY + 2.5f, ColorUtils.rgba(200, 200, 255, 255), fontSize);

            Effect effect = ef.getPotion();
            PotionSpriteUploader potionspriteuploader = Minecraft.getInstance().getPotionSpriteUploader();
            TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
            Minecraft.getInstance().getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
            DisplayEffectsScreen.blit(ms, (int) (posX + this.animation - iconSize - padding), (int) (posY + 1), 10, 10, 10, textureatlassprite);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }
            posY += 7.5f + padding;
            localHeight += fontSize + padding;
        }
        this.animation = MathUtil.lerp(this.animation, Math.max(maxWidth, 80.0f), 10.0f);
        this.height = localHeight + 2.5f;
        this.dragging.setWidth(this.animation);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(final float x, final float y, final float width, final float height, final float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1.0f, height + 1.0f, radius + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(255, 255, 255, 205));
    }

    public PotionRenderer(final Dragging dragging) {
        this.dragging = dragging;
    }
}

package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EllantPotionRenderer implements ElementRenderer {

    final Dragging dragging;

    float width;
    float height;
    float animation;

    final ResourceLocation potion = new ResourceLocation("expensive/images/hud/potions.png");
    final float iconSize = 10;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;
        float bindwidhttext = 2;

        ITextComponent name = GradientUtil.white("potion");

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawShadow(posX, posY, width, height + 1, 15, ColorUtils.rgba(21, 24, 25, 160));

        DisplayUtils.drawRoundedRect(posX, posY, width, height, 4.2f, ColorUtils.rgba(0, 0, 0, 150));
        DisplayUtils.drawRoundedRect(posX, posY, width, padding * 2.2f + fontSize, 4, ColorUtils.rgba(0, 0, 0, 240));
        DisplayUtils.drawImage(potion, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfui.drawCenteredText(ms, name, posX + width / 2, posY + padding + 0.5f, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth(name, fontSize) + padding * 2;
        float localHeight = fontSize + padding * 2;

        for (EffectInstance ef : Minecraft.getInstance().player.getActivePotionEffects()) {
            int amp = ef.getAmplifier();

            String ampStr = "";

            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1));
            }
            String nameText = I18n.format(ef.getEffectName()) + ampStr;
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);

            String bindText = EffectUtils.getPotionDurationString(ef, 1);
            float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);

            float localWidth = iconSize + padding + nameWidth + bindWidth + padding * 2;

            Effect effect = ef.getPotion();
            PotionSpriteUploader potionspriteuploader = Minecraft.getInstance().getPotionSpriteUploader();
            TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
            Minecraft.getInstance().getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
            DisplayEffectsScreen.blit(ms, (int) (posX + padding), (int) (posY + 2), 11, 10, 10, textureatlassprite);

            Fonts.sfMedium.drawText(ms, nameText, posX + padding + iconSize + padding, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            Fonts.sfMedium.drawText(ms, bindText, posX + width - padding - bindWidth, posY, ColorUtils.rgba(150, 150, 210, 255), fontSize);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (fontSize + padding);
            localHeight += (fontSize + padding);
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 90);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }
}

package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.potion.Effect;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Scissor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import wtf.resolute.utiled.render.font.Fonts;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PotionRenderer implements ElementRenderer {


    final Dragging dragging;


    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;

        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawShadow(posX, posY, width + 8, height, 8, new Color(16, 16, 16).getRGB());
        DisplayUtils.drawRoundedRect(posX, posY, width + 8, height, 3, DisplayUtils.reAlphaInt(new Color(8, 8, 8).getRGB(), 210));
        DisplayUtils.drawRoundedRect(posX + 3, posY + 3, width + 4, height - 6, 3, DisplayUtils.reAlphaInt(new Color(8, 8, 8).getRGB(), 210));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width + 8, height);
        Fonts.sfbold.drawCenteredText(ms, "Potions", posX + (width + 8) / 2, posY + padding + 0.5f,-1, fontSize);
        posY += fontSize + padding * 2;
        float maxWidth = Fonted.nunito[17].getWidth("Active Potion") + padding * 2;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1 + 8, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3f;

        for (EffectInstance ef : mc.player.getActivePotionEffects()) {
            int amp = ef.getAmplifier();

            String ampStr = "";

            if (amp >= 1 && amp <= 9) {
                ampStr = " " + I18n.format("enchantment.level." + (amp + 1));
            }

            String nameText = I18n.format(ef.getEffectName()) + ampStr;
            float nameWidth = Fonted.rubik[14].getWidth(nameText);
            String bindText = EffectUtils.getPotionDurationString(ef, 1);
            float bindWidth = Fonted.rubik[14].getWidth(bindText);
            float localWidth = nameWidth + bindWidth + padding * 3;
            Fonted.rubik[14].drawString(ms, I18n.format(ef.getEffectName(), new Object[0]), posX + padding + 8, posY + 1, ColorUtils.rgba(210, 210, 210, 255));
            Fonted.rubik[14].drawString(ms, bindText, posX + width - padding - bindWidth + 8, posY + 1, ColorUtils.rgba(210, 210, 210, 255));
            Effect effect = ef.getPotion();
            PotionSpriteUploader potionspriteuploader = mc.getPotionSpriteUploader();
            TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
            mc.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
            DisplayEffectsScreen.blit(ms, (int) (posX + padding), (int) posY, 10, 7, 7, textureatlassprite);
            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (fontSize + padding);
            localHeight += (fontSize + padding);
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }
}

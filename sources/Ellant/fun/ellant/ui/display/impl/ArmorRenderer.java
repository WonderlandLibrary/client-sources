package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.events.EventDisplay;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ArmorRenderer implements ElementRenderer {

    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/armor.png");

    final Dragging dragging;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        Minecraft mc = Minecraft.getInstance();

        float width = 100;
        float height = 79;

        float iconSizeX = 10;
        float iconSizeY = 10;

        float x = dragging.getX();
        float y = dragging.getY();

        float padding = 5;

        float finalPosY = y;
        DisplayUtils.drawShadow(x, y, width, height + 1, 15, ColorUtils.rgba(21, 24, 25, 160));

        DisplayUtils.drawRoundedRect(x, y, width, height + 1, 3, ColorUtils.rgba(25, 26, 26, 160));

        float imagePosX = x + width - iconSizeX - padding;
        DisplayUtils.drawImage(logo, imagePosX, finalPosY + 3.7f, iconSizeX, iconSizeY, ColorUtils.rgb(255, 255, 255));

        Fonts.sfui.drawText(ms, "Armor", x + 5, y + 5, -1, 6.5f);

        int posX = (int) x + 3;
        int posY = (int) y + 63;

        for (ItemStack itemStack : mc.player.getArmorInventoryList()) {
            if (!itemStack.isEmpty()) {
                mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, posX, posY);

                float damagePercentage = (itemStack.getDamage() * 100.0f) / itemStack.getMaxDamage();
                int red = (int) (255 * (damagePercentage / 100));
                int green = 255 - red;

                int barHeight2 = Math.round((45 * (100 - damagePercentage)) / 100);

                DisplayUtils.drawRoundedRect(posX + 20, posY + 6, 45, 3, 1, ColorUtils.rgb(15, 15, 15));
                DisplayUtils.drawRoundedRect(posX + 20, posY + 6, barHeight2, 3, 1, ColorUtils.rgb(red, green, 0));
                DisplayUtils.drawShadow(posX + 20, posY + 6, barHeight2, 3, 8, ColorUtils.rgb(red, green, 0));

                Fonts.sfui.drawCenteredText(ms, (100 - (itemStack.getDamage() * 100) / itemStack.getMaxDamage()) + "%", posX + width - 20, posY + 4, -1, 6.5f);
            } else {
                Fonts.sfui.drawCenteredText(ms, "-", posX + 8, posY + 2.5f , ColorUtils.rgb(135, 136, 135), 9);

                DisplayUtils.drawRoundedRect(posX + 20, posY + 6, 45, 3, 1, ColorUtils.rgb(135, 136, 134));
            }

            posY -= 16;
        }

        dragging.setWidth(width);
        dragging.setHeight(height);
    }
}
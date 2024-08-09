package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.evented.EventDisplay;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.ItemStack;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;

import java.awt.*;

import static wtf.resolute.utiled.render.DisplayUtils.drawRoundedRect;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ArmorRenderer implements ElementRenderer {
//ДА ДА ЕБАТЬ КОМЕНТАРИИ ДЛЯ ОТСТАЛОСТЕЙ КОТОРЫЕ ЭТО ЧИТАЮТ(НУ ДА ЧИТАЮ ЭТО ТОЛЬКО Я)
     final Dragging drag;
    float width;
    float height;
    @Override
    public void render(EventDisplay eventDisplay) {
        float posX = drag.getX();
        float posY = drag.getY();
        MatrixStack ms = eventDisplay.getMatrixStack();
        int screenWidth = mc.getMainWindow().getScaledWidth();

        // Массивы для координат Y для каждого слота брони
        int[] armorYPositions = new int[]{
                (int) posY,       // Шлем
                (int) (posY - 18),  // Нагрудник
                (int) (posY - 36),  // Поножи
                (int) (posY - 54)   // Ботинки
        };

        int index = 0;
        for (ItemStack itemStack : mc.player.getArmorInventoryList()) {
            int slotPosY = armorYPositions[index];
            if (itemStack.isEmpty()) {
                int firstColor = ColorUtils.getColorStyle(0);
                int secondColor = ColorUtils.getColorStyle(100);
                DisplayUtils.drawShadow(posX, posY, width + 1, height, 4, firstColor, secondColor);
                DisplayUtils.drawRoundedRect(posX, posY, width, height, 1, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 250));
                DisplayUtils.drawRoundedRect(posX, posY, width, 2, 1, ColorUtils.getColor(0));
                index++;
                continue;
            }

            int maxDamage = itemStack.getMaxDamage();
            int currentDamage = maxDamage - itemStack.getDamage();

            // Рендерим фон
            int firstColor = ColorUtils.getColorStyle(0);
            int secondColor = ColorUtils.getColorStyle(100);
            DisplayUtils.drawShadow(posX, posY, width + 1, height, 4, firstColor, secondColor);
            DisplayUtils.drawRoundedRect(posX, posY, width, height, 1, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 250));
            DisplayUtils.drawRoundedRect(posX, posY, width, 2, 1, ColorUtils.getColor(0));
            mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, (int) posX + 3, slotPosY + 57);

            // Определяем позицию для отображения прочности
            int durabilityPercentage = (currentDamage * 100) / maxDamage;
            String durabilityText = durabilityPercentage + "%";
            float textPosX;

            // Если элемент находится в правой половине экрана, отображаем проценты слева
            if (posX > screenWidth / 2) {
                textPosX = posX - mc.fontRenderer.getStringWidth(durabilityText) + 3;
            } else {
                textPosX = posX + 19 - 2 - mc.fontRenderer.getStringWidth(durabilityText) + 34;
            }

            Fonted.rubik[13].drawString(ms, durabilityText, textPosX, slotPosY + 63.5f, -1);
            index++;
        }
        width = Math.max(width, 22);
        height = 75;
        drag.setWidth(width);
        drag.setHeight(height);
    }
}
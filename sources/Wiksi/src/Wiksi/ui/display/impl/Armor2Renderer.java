package src.Wiksi.ui.display.impl;

import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.ItemStack;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Armor2Renderer implements ElementRenderer {

    public Armor2Renderer(Dragging armorRendere22) {
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        int posX = window.getScaledWidth() / 2 + 95;
        int posY = window.getScaledHeight() - (16 + 2);
        DisplayUtils.drawRoundedRect(posX - 81.5f  , posY - 39 , 72.5f, 16.0f, 3, ColorUtils.rgba(21, 21, 21, 215));
        for (ItemStack itemStack : mc.player.getArmorInventoryList()) {
            if (itemStack.isEmpty()) continue;

            mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, posX - 80, posY - 40);
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, posX - 80, posY - 40, null);



            posX += 16 + 2;
        }
    }
}

package im.expensive.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.ui.ab.model.IItem;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddedItemComponent extends Component {



    int scroll;

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        final float panelWidth = 120;
        final float panelHeight = 55;
        final float iconWidth = 20;
        final float iconHeight = 20;
        final float padding = 4;
        final float iconPadding = 2;
        float tx = 0;
        float fak = 0;
        for (IItem addingItem : Expensive.getInstance().getItemStorage().getItems()) {
            tx += panelWidth + (padding * 2.6f);
            if (x + tx + panelWidth >= x + width) {
                tx = 0;
                fak += padding * 16;
            }
        }
        scroll = (int) MathHelper.clamp(scroll, -fak - scroll, 0);

        float currentX = 0;
        float currentY = scroll;

        for (IItem addingItem : Expensive.getInstance().getItemStorage().getItems()) {
            Scissor.push();
            Scissor.setFromComponentCoordinates(x + currentX, y + currentY, panelWidth, panelHeight);
            Item item = addingItem.getItem();
            String displayName = item.getName().getString();
            DisplayUtils.drawRoundedRect(x + currentX, y + currentY, panelWidth, panelHeight, 3, ColorUtils.rgba(23, 23, 23, 255));
            drawIcon(item, currentX, currentY, padding, iconWidth, iconHeight, iconPadding,addingItem);


            float fontHeight = Fonts.montserrat.getHeight(7);
            Fonts.montserrat.drawText(stack, displayName, x + currentX + iconWidth + (padding * 2), y + currentY + padding + (iconHeight / 2) - (fontHeight / 2), -1, 7);
            Fonts.montserrat.drawText(stack, "Цена: " + addingItem.getPrice() + "$", x + currentX + (padding), y + currentY + iconHeight + (padding * 2), -1, 6);
            Fonts.montserrat.drawText(stack, "Кол-во: " + addingItem.getQuantity() + " шт.", x + currentX + (padding), y + currentY + iconHeight + (padding * 4), -1, 6);
            Fonts.montserrat.drawText(stack, "Чары" + renderEnchantmentsText(addingItem.getEnchantments()), x + currentX + (padding), y + currentY + iconHeight + (padding * 6), -1, 6);

            currentX += panelWidth + (padding * 2.6f);
            if (x + currentX + panelWidth >= x + width) {
                currentX = 0;
                currentY += padding * 16;
            }
            Scissor.unset();
            Scissor.pop();
        }


    }

    private String renderEnchantmentsText(Map<Enchantment, Integer> enchantments) {
        StringBuilder enchantmentsText = new StringBuilder();
        if (!enchantments.isEmpty()) {
            enchantmentsText.append("Чары: ");
            for (Enchantment enchantment : enchantments.keySet()) {
                int level = enchantments.get(enchantment);

                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());

                String enchantmentText = iformattabletextcomponent.getString().substring(0, 2) + level;
                enchantmentsText.append(enchantmentText).append(" ").trimToSize();
            }
        }

        return enchantmentsText.toString();
    }

    private void drawIcon(Item item, float currentX, float currentY, float padding, float iconWidth, float iconHeight, float iconPadding, IItem it1) {
        DisplayUtils.drawRoundedRect(x + currentX + padding, y + currentY + padding, iconWidth, iconHeight, 3, ColorUtils.rgba(30, 30, 30, 255));
        ItemStack stack = item.getDefaultInstance();

        float d = item.getMaxDamage() * (1 - ((float)it1.getDamage() / 100f));
        stack.setDamage((int) d);

        Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(
                Minecraft.getInstance().fontRenderer,
                stack,
                (int) (x + currentX + padding + iconPadding),
                (int) (y + currentY + padding + iconPadding) + 2, "");
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(
                stack,
                (int) (x + currentX + padding + iconPadding),
                (int) (y + currentY + padding + iconPadding));


    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float) mouseX, (float) mouseY,x,y,width,height)) {
            scroll += delta * 15;
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 1) {
            final float panelWidth = 120;
            final float panelHeight = 55;
            final float padding = 4;
            float currentX = 0;
            float currentY = scroll;
            for (IItem addingItem : Expensive.getInstance().getItemStorage().getItems()) {
                if (isHovered(mouseX, mouseY, x + currentX, y + currentY, panelWidth, panelHeight)) {
                    Expensive.getInstance().getItemStorage().getItems().remove(addingItem);
                }
                currentX += panelWidth + (padding * 2.6f);
                if (x + currentX + panelWidth >= x + width) {
                    currentX = 0;
                    currentY += padding * 16;
                }
            }


        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}

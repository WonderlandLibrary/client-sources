/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.ui.ab.model.IItem;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class AddedItemComponent
extends Component {
    private int scroll;

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        float panelWidth = 120.0f;
        float panelHeight = 55.0f;
        float iconWidth = 20.0f;
        float iconHeight = 20.0f;
        float padding = 4.0f;
        float iconPadding = 2.0f;
        float tx = 0.0f;
        float fak = 0.0f;
        for (IItem addingItem : Ellant.getInstance().getItemStorage().getItems()) {
            if (!(this.x + (tx += 130.4f) + 120.0f >= this.x + this.width)) continue;
            tx = 0.0f;
            fak += 64.0f;
        }
        this.scroll = (int)MathHelper.clamp((float)this.scroll, -fak - (float)this.scroll, 0.0f);
        float currentX = 0.0f;
        float currentY = this.scroll;
        for (IItem addingItem : Ellant.getInstance().getItemStorage().getItems()) {
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x + currentX, this.y + currentY, 120.0, 55.0);
            Item item = addingItem.getItem();
            String displayName = item.getName().getString();
            DisplayUtils.drawRoundedRect(this.x + currentX, this.y + currentY, 120.0f, 55.0f, 3.0f, ColorUtils.rgba(23, 23, 23, 255));
            this.drawIcon(item, currentX, currentY, 4.0f, 20.0f, 20.0f, 2.0f, addingItem);
            float fontHeight = Fonts.montserrat.getHeight(7.0f);
            Fonts.montserrat.drawText(stack, displayName, this.x + currentX + 20.0f + 8.0f, this.y + currentY + 4.0f + 10.0f - fontHeight / 2.0f, -1, 7.0f);
            Fonts.montserrat.drawText(stack, "\u0426\u0435\u043d\u0430: " + addingItem.getPrice() + "$", this.x + currentX + 4.0f, this.y + currentY + 20.0f + 8.0f, -1, 6.0f);
            Fonts.montserrat.drawText(stack, "\u041a\u043e\u043b-\u0432\u043e: " + addingItem.getQuantity() + " \u0448\u0442.", this.x + currentX + 4.0f, this.y + currentY + 20.0f + 16.0f, -1, 6.0f);
            Fonts.montserrat.drawText(stack, "\u0427\u0430\u0440\u044b" + this.renderEnchantmentsText(addingItem.getEnchantments()), this.x + currentX + 4.0f, this.y + currentY + 20.0f + 24.0f, -1, 6.0f);
            currentX += 130.4f;
            if (this.x + currentX + 120.0f >= this.x + this.width) {
                currentX = 0.0f;
                currentY += 64.0f;
            }
            Scissor.unset();
            Scissor.pop();
        }
    }

    private String renderEnchantmentsText(Map<Enchantment, Integer> enchantments) {
        StringBuilder enchantmentsText = new StringBuilder();
        if (!enchantments.isEmpty()) {
            enchantmentsText.append("\u0427\u0430\u0440\u044b: ");
            for (Enchantment enchantment : enchantments.keySet()) {
                int level = enchantments.get(enchantment);
                TranslationTextComponent iformattabletextcomponent = new TranslationTextComponent(enchantment.getName());
                String enchantmentText = iformattabletextcomponent.getString().substring(0, 2) + level;
                enchantmentsText.append(enchantmentText).append(" ").trimToSize();
            }
        }
        return enchantmentsText.toString();
    }

    private void drawIcon(Item item, float currentX, float currentY, float padding, float iconWidth, float iconHeight, float iconPadding, IItem it1) {
        DisplayUtils.drawRoundedRect(this.x + currentX + padding, this.y + currentY + padding, iconWidth, iconHeight, 3.0f, ColorUtils.rgba(30, 30, 30, 255));
        ItemStack stack = item.getDefaultInstance();
        float d = (float)item.getMaxDamage() * (1.0f - (float)it1.getDamage() / 100.0f);
        stack.setDamage((int)d);
        Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, (int)(this.x + currentX + padding + iconPadding), (int)(this.y + currentY + padding + iconPadding) + 2, "");
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, (int)(this.x + currentX + padding + iconPadding), (int)(this.y + currentY + padding + iconPadding));
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, this.x, this.y, this.width, this.height)) {
            this.scroll = (int)((double)this.scroll + delta * 15.0);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 1) {
            float panelWidth = 120.0f;
            float panelHeight = 55.0f;
            float padding = 4.0f;
            float currentX = 0.0f;
            float currentY = this.scroll;
            for (IItem addingItem : Ellant.getInstance().getItemStorage().getItems()) {
                if (this.isHovered(mouseX, mouseY, this.x + currentX, this.y + currentY, 120.0f, 55.0f)) {
                    Ellant.getInstance().getItemStorage().getItems().remove(addingItem);
                }
                if (!(this.x + (currentX += 130.4f) + 120.0f >= this.x + this.width)) continue;
                currentX = 0.0f;
                currentY += 64.0f;
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


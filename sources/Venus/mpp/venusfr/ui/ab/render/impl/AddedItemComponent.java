/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import mpp.venusfr.ui.ab.model.IItem;
import mpp.venusfr.ui.ab.render.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
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
    public void render(MatrixStack matrixStack, int n, int n2) {
        float f = 120.0f;
        float f2 = 55.0f;
        float f3 = 20.0f;
        float f4 = 20.0f;
        float f5 = 4.0f;
        float f6 = 2.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (IItem iItem : venusfr.getInstance().getItemStorage().getItems()) {
            if (!(this.x + (f7 += 130.4f) + 120.0f >= this.x + this.width)) continue;
            f7 = 0.0f;
            f8 += 64.0f;
        }
        this.scroll = (int)MathHelper.clamp((float)this.scroll, -f8 - (float)this.scroll, 0.0f);
        float f9 = 0.0f;
        float f10 = this.scroll;
        for (IItem iItem : venusfr.getInstance().getItemStorage().getItems()) {
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x + f9, this.y + f10, 120.0, 55.0);
            Item item = iItem.getItem();
            String string = item.getName().getString();
            DisplayUtils.drawRoundedRect(this.x + f9, this.y + f10, 120.0f, 55.0f, 3.0f, ColorUtils.rgba(23, 23, 23, 255));
            this.drawIcon(item, f9, f10, 4.0f, 20.0f, 20.0f, 2.0f, iItem);
            float f11 = Fonts.montserrat.getHeight(7.0f);
            Fonts.montserrat.drawText(matrixStack, string, this.x + f9 + 20.0f + 8.0f, this.y + f10 + 4.0f + 10.0f - f11 / 2.0f, -1, 7.0f);
            Fonts.montserrat.drawText(matrixStack, "\u0426\u0435\u043d\u0430: " + iItem.getPrice() + "$", this.x + f9 + 4.0f, this.y + f10 + 20.0f + 8.0f, -1, 6.0f);
            Fonts.montserrat.drawText(matrixStack, "\u041a\u043e\u043b-\u0432\u043e: " + iItem.getQuantity() + " \u0448\u0442.", this.x + f9 + 4.0f, this.y + f10 + 20.0f + 16.0f, -1, 6.0f);
            Fonts.montserrat.drawText(matrixStack, "\u0427\u0430\u0440\u044b" + this.renderEnchantmentsText(iItem.getEnchantments()), this.x + f9 + 4.0f, this.y + f10 + 20.0f + 24.0f, -1, 6.0f);
            f9 += 130.4f;
            if (this.x + f9 + 120.0f >= this.x + this.width) {
                f9 = 0.0f;
                f10 += 64.0f;
            }
            Scissor.unset();
            Scissor.pop();
        }
    }

    private String renderEnchantmentsText(Map<Enchantment, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!map.isEmpty()) {
            stringBuilder.append("\u0427\u0430\u0440\u044b: ");
            for (Enchantment enchantment : map.keySet()) {
                int n = map.get(enchantment);
                TranslationTextComponent translationTextComponent = new TranslationTextComponent(enchantment.getName());
                String string = translationTextComponent.getString().substring(0, 2) + n;
                stringBuilder.append(string).append(" ").trimToSize();
            }
        }
        return stringBuilder.toString();
    }

    private void drawIcon(Item item, float f, float f2, float f3, float f4, float f5, float f6, IItem iItem) {
        DisplayUtils.drawRoundedRect(this.x + f + f3, this.y + f2 + f3, f4, f5, 3.0f, ColorUtils.rgba(30, 30, 30, 255));
        ItemStack itemStack = item.getDefaultInstance();
        float f7 = (float)item.getMaxDamage() * (1.0f - (float)iItem.getDamage() / 100.0f);
        itemStack.setDamage((int)f7);
        Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, itemStack, (int)(this.x + f + f3 + f6), (int)(this.y + f2 + f3 + f6) + 2, "");
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(itemStack, (int)(this.x + f + f3 + f6), (int)(this.y + f2 + f3 + f6));
    }

    @Override
    public void mouseScrolled(double d, double d2, double d3) {
        if (MathUtil.isHovered((float)d, (float)d2, this.x, this.y, this.width, this.height)) {
            this.scroll = (int)((double)this.scroll + d3 * 15.0);
        }
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n == 1) {
            float f = 120.0f;
            float f2 = 55.0f;
            float f3 = 4.0f;
            float f4 = 0.0f;
            float f5 = this.scroll;
            for (IItem iItem : venusfr.getInstance().getItemStorage().getItems()) {
                if (this.isHovered(d, d2, this.x + f4, this.y + f5, 120.0f, 55.0f)) {
                    venusfr.getInstance().getItemStorage().getItems().remove(iItem);
                }
                if (!(this.x + (f4 += 130.4f) + 120.0f >= this.x + this.width)) continue;
                f4 = 0.0f;
                f5 += 64.0f;
            }
        }
    }

    @Override
    public void mouseReleased(double d, double d2, int n) {
    }

    @Override
    public void keyTyped(int n, int n2, int n3) {
    }

    @Override
    public void charTyped(char c, int n) {
    }
}


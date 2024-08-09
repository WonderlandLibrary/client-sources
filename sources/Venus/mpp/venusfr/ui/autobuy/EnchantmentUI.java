/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.impl.EaseBackIn;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class EnchantmentUI
implements IMinecraft {
    public ItemStack stack;
    public boolean drag;
    public int draggi;
    public int scroll;
    Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
    Map<Enchantment, Float> animations = new HashMap<Enchantment, Float>();
    final Animation openAnimation = new EaseBackIn(400, 1.0, 1.0f);

    public EnchantmentUI(ItemStack itemStack) {
        this.stack = itemStack;
    }

    public void render(MatrixStack matrixStack, float f, float f2, float f3) {
        List list = Registry.ENCHANTMENT.stream().filter(this::lambda$render$0).toList();
        if (this.stack == null || list.isEmpty()) {
            return;
        }
        int n = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n2 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f4 = n / 2 - 200;
        float f5 = n2 / 2 - 150;
        f4 -= 150.0f;
        f5 += 110.0f;
        float f6 = 100.0f;
        float f7 = 100.0f;
        float f8 = (float)this.openAnimation.getOutput();
        float f9 = (1.0f - f8) / 2.0f;
        float f10 = f4 + f6 * f9;
        float f11 = f5 + f7 * f9;
        float f12 = f6 * f8;
        float f13 = f7 * f8;
        GlStateManager.pushMatrix();
        EnchantmentUI.sizeAnimation(f4 + f6 / 2.0f, f5 + f7 / 2.0f, f8);
        DisplayUtils.drawShadow(f4, f5, f6, f7, 10, ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(f4, f5, 100.0f, 100.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        Scissor.push();
        Scissor.setFromComponentCoordinates(f10, f11, f12, f13);
        int n3 = 0;
        this.scroll = list.size() * 16 >= 100 ? MathHelper.clamp(this.scroll, -(list.size() * 16 - 90), 0) : 0;
        float f14 = 6.0f;
        for (Enchantment enchantment : list) {
            if (!this.animations.containsKey(enchantment)) {
                this.animations.put(enchantment, Float.valueOf(0.0f));
            }
            Fonts.montserrat.drawText(matrixStack, this.enchantmentIntegerMap.containsKey(enchantment) ? enchantment.getDisplayName(this.enchantmentIntegerMap.get(enchantment)).getString() : I18n.format(enchantment.getName(), new Object[0]), f4 + 5.0f, f5 + 5.0f + (float)n3 * (f14 + 10.0f) + (float)this.scroll, -1, f14);
            DisplayUtils.drawRoundedRect(f4 + 5.0f, f5 + 5.0f + (float)n3 * (f14 + 10.0f) + 10.0f + (float)this.scroll, f6 - 10.0f, 3.0f, 1.0f, ColorUtils.rgba(21, 24, 40, 165));
            float f15 = (f6 - 10.0f) * (float)(EnchantmentHelper.getEnchantmentLevel(enchantment, this.stack) - (enchantment.getMinLevel() - 1)) / (float)(5 - (enchantment.getMinLevel() - 1));
            this.animations.put(enchantment, Float.valueOf(MathUtil.fast(this.animations.get(enchantment).floatValue(), f15, 10.0f)));
            DisplayUtils.drawRoundedRect(f4 + 5.0f, f5 + 5.0f + (float)n3 * (f14 + 10.0f) + 10.0f + (float)this.scroll, this.animations.get(enchantment).floatValue(), 3.0f, 1.0f, ColorUtils.getColor(0));
            ++n3;
        }
        if (this.drag) {
            n3 = 0;
            for (Enchantment enchantment : list) {
                Integer n4;
                int n5 = (int)MathHelper.clamp(MathUtil.round((f - f4 - 5.0f) / (f6 - 10.0f) * (float)(5 - (enchantment.getMinLevel() - 1)) + (float)(enchantment.getMinLevel() - 1), 1.0), (double)(enchantment.getMinLevel() - 1), 5.0);
                if (n3 == this.draggi) {
                    this.enchantmentIntegerMap.put(enchantment, n5);
                }
                if ((n4 = this.enchantmentIntegerMap.get(enchantment)) != null && n4 == 0) {
                    this.enchantmentIntegerMap.remove(enchantment);
                }
                ++n3;
            }
            EnchantmentHelper.setEnchantments(this.enchantmentIntegerMap, this.stack);
        }
        Scissor.unset();
        Scissor.pop();
        GlStateManager.popMatrix();
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }

    public void press(int n, int n2) {
        if (this.stack == null) {
            return;
        }
        int n3 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n4 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f = n3 / 2 - 200;
        float f2 = n4 / 2 - 150;
        f -= 150.0f;
        f2 += 110.0f;
        float f3 = 100.0f;
        float f4 = 100.0f;
        List list = Registry.ENCHANTMENT.stream().filter(this::lambda$press$1).toList();
        int n5 = 0;
        float f5 = 6.0f;
        for (Enchantment enchantment : list) {
            if (MathUtil.isHovered(n, n2, f + 5.0f, f2 + 5.0f + (float)n5 * (f5 + 10.0f) + 10.0f - 5.0f + (float)this.scroll, f3 - 10.0f, 13.0f)) {
                this.drag = true;
                this.draggi = n5;
            }
            ++n5;
        }
    }

    public void scroll(float f, float f2, float f3) {
        int n = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n2 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f4 = n / 2 - 200;
        float f5 = n2 / 2 - 150;
        float f6 = 100.0f;
        float f7 = 100.0f;
        if (MathUtil.isHovered(f2, f3, f4 -= 150.0f, f5 += 150.0f, f6, f7)) {
            this.scroll = (int)((float)this.scroll + f * 10.0f);
        }
    }

    public void rel() {
        this.drag = false;
        this.draggi = -1;
    }

    private boolean lambda$press$1(Enchantment enchantment) {
        return enchantment.canApply(this.stack);
    }

    private boolean lambda$render$0(Enchantment enchantment) {
        return enchantment.canApply(this.stack);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.HashMap;
import mpp.venusfr.ui.autobuy.AutoBuy;
import mpp.venusfr.ui.autobuy.AutoBuyConfig;
import mpp.venusfr.ui.autobuy.AutoBuyHandler;
import mpp.venusfr.ui.autobuy.EnchantmentUI;
import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;
import mpp.venusfr.utils.animations.impl.EaseBackIn;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class SelectorUI
implements IMinecraft {
    public Item selected;
    public ItemStack instance;
    public String price = "500";
    public String count = "1";
    public boolean priceClicked;
    public boolean countClicked;
    final Animation openAnimation = new EaseBackIn(400, 1.0, 1.0f);
    public EnchantmentUI enchantmentUI = new EnchantmentUI(null);
    public boolean ench;
    public boolean items;
    public boolean fake;
    public boolean don;
    public ItemStack last;

    public SelectorUI(Item item) {
        this.openAnimation.setDirection(Direction.FORWARDS);
        this.selected = item;
        if (this.selected != null) {
            this.instance = item.getDefaultInstance();
        }
        if (this.instance != null) {
            this.enchantmentUI.stack = this.instance;
        }
    }

    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3;
        if (this.selected != null && this.openAnimation.getOutput() == 0.0 && this.openAnimation.getDirection() == Direction.BACKWARDS) {
            this.selected = null;
        }
        if (this.selected == null) {
            this.instance = null;
            return;
        }
        if (!this.count.isEmpty()) {
            n3 = Integer.parseInt(this.count);
            n3 = MathHelper.clamp(n3, 0, this.selected.getMaxStackSize());
            this.count = String.valueOf(n3);
        }
        n3 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n4 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f2 = n3 / 2 - 200;
        float f3 = n4 / 2 - 150;
        float f4 = 100.0f;
        float f5 = 100.0f;
        float f6 = (float)this.openAnimation.getOutput();
        GlStateManager.pushMatrix();
        SelectorUI.sizeAnimation((f2 -= 150.0f) + f4 / 2.0f, f3 + f5 / 2.0f, f6);
        DisplayUtils.drawShadow(f2, f3, f4, f5, 10, ColorUtils.rgba(17, 17, 17, 128));
        Scissor.push();
        Scissor.setFromComponentCoordinates(f2, f3, f4, f5);
        DisplayUtils.drawRoundedRect(f2, f3, 100.0f, 100.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.instance, (int)f2 + 5, (int)f3 + 5);
        Fonts.montserrat.drawText(matrixStack, TextFormatting.getTextWithoutFormattingCodes(this.selected.getName().getString()), f2 + 24.0f, f3 + 9.0f, -1, 6.0f, 0.05f);
        DisplayUtils.drawShadow(f2 + 5.0f, f3 + 100.0f - 20.0f, 90.0f, 15.0f, 10, ColorUtils.rgba(27, 27, 27, 255));
        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(this.selected.getName().getString()), 6.0f, 0.05f) + 24.0f > f4) {
            DisplayUtils.drawRectVerticalW(f2 + f4 - 10.0f, f3 + 9.0f, 10.0, 6.0, ColorUtils.rgba(21, 24, 40, 165), ColorUtils.rgba(17, 17, 17, 0));
        }
        DisplayUtils.drawRoundedRect(f2 + 5.0f, f3 + 100.0f - 20.0f, 90.0f, 15.0f, 3.0f, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", f2 + f4 / 2.0f, f3 + f5 - 20.0f + 3.5f, -1, 7.0f, 0.05f);
        Fonts.montserrat.drawText(matrixStack, "\u0426\u0435\u043d\u0430", f2 + 8.0f, f3 + 23.0f, -1, 6.0f, 0.03f);
        DisplayUtils.drawRoundedRect(f2 + 7.0f, f3 + 30.0f, 50.0f, 11.0f, 2.0f, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawText(matrixStack, this.price + (this.priceClicked ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), f2 + 10.0f, f3 + 33.0f, -1, 5.0f, 0.03f);
        Block block = Block.getBlockFromItem(this.selected);
        if (block instanceof ShulkerBoxBlock) {
            ShulkerBoxBlock shulkerBoxBlock = (ShulkerBoxBlock)block;
            Fonts.montserrat.drawText(matrixStack, "\u0415\u0441\u0442\u044c \u0434\u043e\u043d\u0430\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b?", f2 + 8.0f, f3 + 23.0f + 20.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(f2 + 8.0f - 2.0f, f3 + 23.0f + 28.0f - 2.0f + 0.5f, Fonts.montserrat.getWidth(this.don ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(21, 24, 40, 165));
            Fonts.montserrat.drawText(matrixStack, this.don ? "\u0414\u0430" : "\u041d\u0435\u0442", f2 + 8.0f, f3 + 23.0f + 28.0f, -1, 6.0f, 0.03f);
            Fonts.montserrat.drawText(matrixStack, "\u0415\u0441\u0442\u044c \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b?", f2 + 8.0f, f3 + 23.0f + 20.0f + 17.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(f2 + 8.0f - 2.0f, f3 + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.items ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(21, 24, 40, 165));
            Fonts.montserrat.drawText(matrixStack, this.items ? "\u0414\u0430" : "\u041d\u0435\u0442", f2 + 8.0f, f3 + 23.0f + 28.0f + 17.0f, -1, 6.0f, 0.03f);
        } else {
            Fonts.montserrat.drawText(matrixStack, "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e", f2 + 8.0f, f3 + 23.0f + 20.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(f2 + 7.0f, f3 + 30.0f + 20.0f, 20.0f, 8.0f, 2.0f, ColorUtils.rgba(21, 24, 40, 165));
            Fonts.montserrat.drawText(matrixStack, this.count + (this.countClicked ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), f2 + 10.0f, f3 + 33.0f + 19.0f, -1, 5.0f, 0.03f);
            Fonts.montserrat.drawText(matrixStack, "\u041f\u0440\u043e\u0432\u0435\u0440\u044f\u0442\u044c \u043d\u0430 \u0444\u0435\u0439\u043a", f2 + 8.0f, f3 + 23.0f + 20.0f + 17.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(f2 + 8.0f - 2.0f, f3 + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.fake ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(21, 24, 40, 165));
            Fonts.montserrat.drawText(matrixStack, this.fake ? "\u0414\u0430" : "\u041d\u0435\u0442", f2 + 8.0f, f3 + 23.0f + 28.0f + 17.0f, -1, 6.0f, 0.03f);
        }
        GlStateManager.pushMatrix();
        GL11.glTranslatef(f2 + f4 - 12.0f + 6.0f, f3 + 2.0f + 6.0f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-(f2 + f4 - 12.0f + 6.0f), -(f3 + 2.0f + 6.0f), 0.0f);
        Fonts.montserrat.drawText(matrixStack, "+", f2 + f4 - 12.0f, f3 + 2.0f, ColorUtils.rgba(255, 255, 255, 128), 12.0f);
        GlStateManager.popMatrix();
        Scissor.unset();
        Scissor.pop();
        GlStateManager.popMatrix();
        this.enchantmentUI.render(matrixStack, n, n2, f);
    }

    public void mouseClicked(double d, double d2, int n) {
        Object object;
        Block block;
        if (this.selected == null) {
            return;
        }
        this.enchantmentUI.press((int)d, (int)d2);
        int n2 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n3 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f = n2 / 2 - 200;
        float f2 = n3 / 2 - 150;
        float f3 = 100.0f;
        float f4 = 100.0f;
        if (MathUtil.isHovered((float)d, (float)d2, (f -= 150.0f) + f3 - 12.0f, f2 + 2.0f, 10.0f, 10.0f)) {
            this.enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            this.openAnimation.setDirection(Direction.BACKWARDS);
        }
        if ((block = Block.getBlockFromItem(this.selected)) instanceof ShulkerBoxBlock) {
            object = (ShulkerBoxBlock)block;
            if (MathUtil.isHovered((float)d, (float)d2, f + 8.0f - 2.0f, f2 + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.items ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
                this.items = !this.items;
            }
            if (MathUtil.isHovered((float)d, (float)d2, f + 8.0f - 2.0f, f2 + 23.0f + 28.0f - 2.0f + 0.5f, Fonts.montserrat.getWidth(this.ench ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
                this.don = !this.don;
            }
        } else {
            if (MathUtil.isHovered((float)d, (float)d2, f + 8.0f - 2.0f, f2 + 23.0f + 28.0f - 2.0f + 0.5f, Fonts.montserrat.getWidth(this.ench ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
                this.ench = !this.ench;
            }
            if (MathUtil.isHovered((float)d, (float)d2, f + 8.0f - 2.0f, f2 + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.fake ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
                boolean bl = this.fake = !this.fake;
            }
        }
        if (MathUtil.isHovered((float)d, (float)d2, f + 5.0f, f2 + 100.0f - 20.0f, 90.0f, 15.0f)) {
            object = new HashMap<Enchantment, Integer>(this.enchantmentUI.enchantmentIntegerMap);
            AutoBuyHandler.instance.items.add(new AutoBuy(this.selected, Integer.parseInt(this.price), Integer.parseInt(this.count), (HashMap<Enchantment, Integer>)object, this.ench, this.items, this.fake, this.don));
            this.items = false;
            AutoBuyConfig.updateFile();
            this.openAnimation.setDirection(Direction.BACKWARDS);
            this.enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            if (this.openAnimation.getOutput() == 0.0) {
                this.selected = null;
            }
        }
        if (MathUtil.isHovered((float)d, (float)d2, f + 7.0f, f2 + 30.0f, 50.0f, 11.0f)) {
            boolean bl = this.priceClicked = !this.priceClicked;
        }
        if (MathUtil.isHovered((float)d, (float)d2, f + 7.0f, f2 + 30.0f + 20.0f, 20.0f, 8.0f)) {
            boolean bl = this.countClicked = !this.countClicked;
        }
        if (MathUtil.isHovered((float)d, (float)d2, f + 7.0f, f2 + 30.0f + 11.0f + 8.0f, 50.0f, 11.0f)) {
            this.enchantmentUI.stack = this.instance;
            this.enchantmentUI.enchantmentIntegerMap.clear();
            this.enchantmentUI.draggi = -1;
            this.enchantmentUI.drag = false;
        }
    }

    public void release(double d, double d2) {
        this.enchantmentUI.rel();
    }

    public void charTyped(char c) {
        if (Character.isDigit(c) && this.priceClicked) {
            this.price = this.price + c;
        }
        if (Character.isDigit(c) && this.countClicked) {
            this.count = this.count + c;
        }
    }

    public void scroll(float f, float f2, float f3) {
        this.enchantmentUI.scroll(f, f2, f3);
    }

    public void key(int n) {
        if (n == 259 && this.priceClicked && !this.price.isEmpty()) {
            this.price = this.price.substring(0, this.price.length() - 1);
        }
        if (n == 259 && this.countClicked && !this.count.isEmpty()) {
            this.count = this.count.substring(0, this.count.length() - 1);
        }
        if (n == 257) {
            this.priceClicked = false;
            this.countClicked = false;
        }
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }
}


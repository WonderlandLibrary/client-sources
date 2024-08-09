/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.ui.autobuy.AutoBuy;
import fun.ellant.ui.autobuy.AutoBuyConfig;
import fun.ellant.ui.autobuy.AutoBuyHandler;
import fun.ellant.ui.autobuy.EnchantmentUI;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import java.util.HashMap;
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

    public SelectorUI(Item selected) {
        this.openAnimation.setDirection(Direction.FORWARDS);
        this.selected = selected;
        if (this.selected != null) {
            this.instance = selected.getDefaultInstance();
        }
        if (this.instance != null) {
            this.enchantmentUI.stack = this.instance;
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.selected != null && this.openAnimation.getOutput() == 0.0 && this.openAnimation.getDirection() == Direction.BACKWARDS) {
            this.selected = null;
        }
        if (this.selected == null) {
            this.instance = null;
            return;
        }
        if (!this.count.isEmpty()) {
            int co = Integer.parseInt(this.count);
            co = MathHelper.clamp(co, 0, this.selected.getMaxStackSize());
            this.count = String.valueOf(co);
        }
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float x = windowWidth / 2 - 200;
        float y = windowHeight / 2 - 150;
        float width = 100.0f;
        float height = 100.0f;
        float animation = (float)this.openAnimation.getOutput();
        GlStateManager.pushMatrix();
        SelectorUI.sizeAnimation((x -= 150.0f) + width / 2.0f, y + height / 2.0f, animation);
        DisplayUtils.drawShadow(x, y, width, height, 10, ColorUtils.rgba(17, 17, 17, 128));
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y, width, height);
        DisplayUtils.drawRoundedRect(x, y, 100.0f, 100.0f, 4.0f, ColorUtils.rgba(17, 17, 17, 255));
        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.instance, (int)x + 5, (int)y + 5);
        Fonts.montserrat.drawText(matrixStack, TextFormatting.getTextWithoutFormattingCodes(this.selected.getName().getString()), x + 24.0f, y + 9.0f, -1, 6.0f, 0.05f);
        DisplayUtils.drawShadow(x + 5.0f, y + 100.0f - 20.0f, 90.0f, 15.0f, 10, ColorUtils.rgba(27, 27, 27, 255));
        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(this.selected.getName().getString()), 6.0f, 0.05f) + 24.0f > width) {
            DisplayUtils.drawRectVerticalW(x + width - 10.0f, y + 9.0f, 10.0, 6.0, ColorUtils.rgba(17, 17, 17, 255), ColorUtils.rgba(17, 17, 17, 0));
        }
        DisplayUtils.drawRoundedRect(x + 5.0f, y + 100.0f - 20.0f, 90.0f, 15.0f, 3.0f, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, "Добавить", x + width / 2.0f, y + height - 20.0f + 3.5f, -1, 7.0f, 0.05f);
        Fonts.montserrat.drawText(matrixStack, "Цена предмета", x + 8.0f, y + 23.0f, -1, 6.0f, 0.03f);
        DisplayUtils.drawRoundedRect(x + 7.0f, y + 30.0f, 50.0f, 11.0f, 2.0f, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawText(matrixStack, this.price + (this.priceClicked ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), x + 10.0f, y + 33.0f, -1, 5.0f, 0.03f);
        Block block = Block.getBlockFromItem(this.selected);
        if (block instanceof ShulkerBoxBlock) {
            ShulkerBoxBlock b = (ShulkerBoxBlock)block;
            Fonts.montserrat.drawText(matrixStack, "Есть донат предметы?", x + 8.0f, y + 23.0f + 20.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8.0f - 2.0f, y + 23.0f + 28.0f - 2.0f + 0.5f, Fonts.montserrat.getWidth(this.don ? "Да" : "Нет", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, this.don ? "Да" : "Нет", x + 8.0f, y + 23.0f + 28.0f, -1, 6.0f, 0.03f);
            Fonts.montserrat.drawText(matrixStack, "Есть предметы?", x + 8.0f, y + 23.0f + 20.0f + 17.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8.0f - 2.0f, y + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.items ? "Да" : "Нет", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, this.items ? "Да" : "Нет", x + 8.0f, y + 23.0f + 28.0f + 17.0f, -1, 6.0f, 0.03f);
        } else {
            Fonts.montserrat.drawText(matrixStack, "Количество", x + 8.0f, y + 23.0f + 20.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(x + 7.0f, y + 30.0f + 20.0f, 20.0f, 8.0f, 2.0f, ColorUtils.rgba(27, 27, 27, 255));
            Fonts.montserrat.drawText(matrixStack, this.count + (this.countClicked ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), x + 10.0f, y + 33.0f + 19.0f, -1, 5.0f, 0.03f);
            Fonts.montserrat.drawText(matrixStack, "Проверять на фейк", x + 8.0f, y + 23.0f + 20.0f + 17.0f, -1, 6.0f, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8.0f - 2.0f, y + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.fake ? "Да" : "Нет", 6.0f, 0.03f) + 4.5f, 9.0f, 2.0f, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, this.fake ? "Да" : "Нет", x + 8.0f, y + 23.0f + 28.0f + 17.0f, -1, 6.0f, 0.03f);
        }
        GlStateManager.pushMatrix();
        GL11.glTranslatef(x + width - 12.0f + 6.0f, y + 2.0f + 6.0f, 0.0f);
        GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-(x + width - 12.0f + 6.0f), -(y + 2.0f + 6.0f), 0.0f);
        Fonts.montserrat.drawText(matrixStack, "+", x + width - 12.0f, y + 2.0f, ColorUtils.rgba(255, 255, 255, 128), 12.0f);
        GlStateManager.popMatrix();
        Scissor.unset();
        Scissor.pop();
        GlStateManager.popMatrix();
        this.enchantmentUI.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        Block block;
        if (this.selected == null) {
            return;
        }
        this.enchantmentUI.press((int)mouseX, (int)mouseY);
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float x = windowWidth / 2 - 200;
        float y = windowHeight / 2 - 150;
        float width = 100.0f;
        float height = 100.0f;
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, (x -= 150.0f) + width - 12.0f, y + 2.0f, 10.0f, 10.0f)) {
            this.enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            this.openAnimation.setDirection(Direction.BACKWARDS);
        }
        if ((block = Block.getBlockFromItem(this.selected)) instanceof ShulkerBoxBlock) {
            ShulkerBoxBlock b = (ShulkerBoxBlock)block;
            if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 8.0f - 2.0f, y + 23.0f + 28.0f - 2.0f + 0.5f, Fonts.montserrat.getWidth(this.ench ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
                this.don = !this.don;
            }
        } else if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 8.0f - 2.0f, y + 23.0f + 28.0f - 2.0f + 0.5f + 17.0f, Fonts.montserrat.getWidth(this.fake ? "\u0414\u0430" : "\u041d\u0435\u0442", 6.0f, 0.03f) + 4.0f, 9.0f)) {
            boolean bl = this.fake = !this.fake;
        }
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 5.0f, y + 100.0f - 20.0f, 90.0f, 15.0f)) {
            HashMap<Enchantment, Integer> ench = new HashMap<Enchantment, Integer>(this.enchantmentUI.enchantmentIntegerMap);
            AutoBuyHandler.instance.items.add(new AutoBuy(this.selected, Integer.parseInt(this.price), Integer.parseInt(this.count), ench, this.ench, this.items, this.fake, this.don));
            this.items = true;
            AutoBuyConfig.updateFile();
            this.openAnimation.setDirection(Direction.BACKWARDS);
            this.enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            if (this.openAnimation.getOutput() == 0.0) {
                this.selected = null;
            }
        }
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 7.0f, y + 30.0f, 50.0f, 11.0f)) {
            boolean bl = this.priceClicked = !this.priceClicked;
        }
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 7.0f, y + 30.0f + 20.0f, 20.0f, 8.0f)) {
            boolean bl = this.countClicked = !this.countClicked;
        }
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, x + 7.0f, y + 30.0f + 11.0f + 8.0f, 50.0f, 11.0f)) {
            this.enchantmentUI.stack = this.instance;
            this.enchantmentUI.enchantmentIntegerMap.clear();
            this.enchantmentUI.draggi = -1;
            this.enchantmentUI.drag = false;
        }
    }

    public void release(double mX, double mY) {
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

    public void scroll(float delta, float mouseX, float mouseY) {
        this.enchantmentUI.scroll(delta, mouseX, mouseY);
    }

    public void key(int k) {
        if (k == 259 && this.priceClicked && !this.price.isEmpty()) {
            this.price = this.price.substring(0, this.price.length() - 1);
        }
        if (k == 259 && this.countClicked && !this.count.isEmpty()) {
            this.count = this.count.substring(0, this.count.length() - 1);
        }
        if (k == 257) {
            this.priceClicked = false;
            this.countClicked = false;
        }
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0.0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0.0);
    }
}


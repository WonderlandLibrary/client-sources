package im.expensive.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class SelectorUI implements IMinecraft {

    public Item selected;
    public ItemStack instance;

    public String price = "500";
    public String count = "1";
    public boolean priceClicked;
    public boolean countClicked;
    final Animation openAnimation = new EaseBackIn(400, 1, 1);

    public EnchantmentUI enchantmentUI = new EnchantmentUI(null);


    public SelectorUI(Item selected) {
        openAnimation.setDirection(Direction.FORWARDS);
        this.selected = selected;
        if (this.selected != null)
            this.instance = selected.getDefaultInstance();
        if (instance != null)
            enchantmentUI.stack = instance;
    }

    public boolean ench;
    public boolean items;
    public boolean fake;
    public boolean don;
    public ItemStack last;

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (selected != null && openAnimation.getOutput() == 0.0f && openAnimation.getDirection() == Direction.BACKWARDS) {
            selected = null;
        }
        if (selected == null) {

            instance = null;
            return;
        }
        if (!count.isEmpty()) {
            int co = Integer.parseInt(count);
            co = MathHelper.clamp(co, 0, selected.getMaxStackSize());
            count = String.valueOf(co);
        }
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());

        float x = windowWidth / 2 - 400 / 2;
        float y = windowHeight / 2 - 300 / 2;
        x -= 150;

        float width = 100;
        float height = 100;


        float animation = (float) openAnimation.getOutput();
        GlStateManager.pushMatrix();
        sizeAnimation(x + (width / 2), y + (height / 2), animation);
        DisplayUtils.drawShadow(x, y, width, height, 10,
                ColorUtils.rgba(17, 17, 17, 128));
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y, width, height);
        DisplayUtils.drawRoundedRect(x, y, 100, 100, 4, ColorUtils.rgba(17, 17, 17, 255));
        mc.getItemRenderer().renderItemAndEffectIntoGUI(instance, (int) x + 5, (int) y + 5);
        Fonts.montserrat.drawText(matrixStack, TextFormatting.getTextWithoutFormattingCodes(selected.getName().getString()), x + 24, y + 9f, -1, 6, 0.05f);
        DisplayUtils.drawShadow(x + 5, y + 100 - 20, 100 - 10, 15, 10,
                ColorUtils.rgba(27, 27, 27, 255));

        if (Fonts.montserrat.getWidth(TextFormatting.getTextWithoutFormattingCodes(selected.getName().getString()), 6, 0.05f) + 24 > width) {
            DisplayUtils.drawRectVerticalW(x + width - 10, y + 9f, 10, 6, ColorUtils.rgba(17, 17, 17, 255), ColorUtils.rgba(17, 17, 17, 0));
        }

        DisplayUtils.drawRoundedRect(x + 5, y + 100 - 20, 100 - 10, 15, 3, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, "Добавить", x + width / 2f, y + height - 20 + 7 / 2F, -1, 7, 0.05F);
        Fonts.montserrat.drawText(matrixStack, "Цена", x + 8, y + 23, -1, 6, 0.03f);
        DisplayUtils.drawRoundedRect(x + 7, y + 30, 50, 11, 2, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawText(matrixStack, price + (priceClicked ? System.currentTimeMillis() % 1000 > 500 ? "_" : "" : ""), x + 10, y + 33, -1, 5, 0.03f);

        if (Block.getBlockFromItem(selected) instanceof ShulkerBoxBlock b) {
            Fonts.montserrat.drawText(matrixStack, "Есть донат предметы?", x + 8, y + 23 + 20, -1, 6, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8 - 2, y + 23 + 28 - 2 + 0.5f, Fonts.montserrat.getWidth(don ? "Да" : "Нет", 6, 0.03f) + 4.5f, 5 + 4, 2, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, don ? "Да" : "Нет", x + 8, y + 23 + 28, -1, 6, 0.03f);

            Fonts.montserrat.drawText(matrixStack, "Есть предметы?", x + 8, y + 23 + 20 + 17, -1, 6, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8 - 2, y + 23 + 28 - 2 + 0.5f + 17, Fonts.montserrat.getWidth(items ? "Да" : "Нет", 6, 0.03f) + 4.5f, 5 + 4, 2, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, items ? "Да" : "Нет", x + 8, y + 23 + 28 + 17, -1, 6, 0.03f);
        } else {
            Fonts.montserrat.drawText(matrixStack, "Количество", x + 8, y + 23 + 20, -1, 6, 0.03f);
            DisplayUtils.drawRoundedRect(x + 7, y + 30 + 20, 20, 8, 2, ColorUtils.rgba(27, 27, 27, 255));
            Fonts.montserrat.drawText(matrixStack, count + (countClicked ? System.currentTimeMillis() % 1000 > 500 ? "_" : "" : ""), x + 10, y + 33 + 19, -1, 5, 0.03f);

            Fonts.montserrat.drawText(matrixStack, "Проверять на фейк", x + 8, y + 23 + 20 + 17, -1, 6, 0.03f);
            DisplayUtils.drawRoundedRect(x + 8 - 2, y + 23 + 28 - 2 + 0.5f + 17, Fonts.montserrat.getWidth(fake ? "Да" : "Нет", 6, 0.03f) + 4.5f, 5 + 4, 2, ColorUtils.rgba(25, 25, 25, 255));
            Fonts.montserrat.drawText(matrixStack, fake ? "Да" : "Нет", x + 8, y + 23 + 28 + 17, -1, 6, 0.03f);
        }
        GlStateManager.pushMatrix();
        glTranslatef(x + width - 12 + 12 / 2f, y + 2 + 12 / 2f, 0);
        glRotatef(45, 0, 0, 1);
        glTranslatef(-(x + width - 12 + 12 / 2f), -(y + 2 + 12 / 2f), 0);
        Fonts.montserrat.drawText(matrixStack, "+", x + width - 12, y + 2, ColorUtils.rgba(255, 255, 255, 128), 12f);
        GlStateManager.popMatrix();
        Scissor.unset();
        Scissor.pop();
        GlStateManager.popMatrix();

        enchantmentUI.render(matrixStack, mouseX, mouseY, partialTicks);


    }


    public void mouseClicked(double mouseX, double mouseY, int button) {

        if (selected == null) return;
        enchantmentUI.press((int) mouseX, (int) mouseY);
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float x = windowWidth / 2 - 400 / 2;
        float y = windowHeight / 2 - 300 / 2;
        x -= 150;

        float width = 100;
        float height = 100;

        // нажал на крестик
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + width - 12, y + 2, 10, 10)) {
            //instance = null;
            enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            openAnimation.setDirection(Direction.BACKWARDS);
        }


        if (Block.getBlockFromItem(selected) instanceof ShulkerBoxBlock b) {
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 8 - 2, y + 23 + 28 - 2 + 0.5f + 17, Fonts.montserrat.getWidth(items ? "Да" : "Нет", 6, 0.03f) + 4, 5 + 4)) {
                items = !items;

            }
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 8 - 2, y + 23 + 28 - 2 + 0.5f, Fonts.montserrat.getWidth(ench ? "Да" : "Нет", 6, 0.03f) + 4, 5 + 4)) {
                don = !don;
            }
        } else {
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 8 - 2, y + 23 + 28 - 2 + 0.5f, Fonts.montserrat.getWidth(ench ? "Да" : "Нет", 6, 0.03f) + 4, 5 + 4)) {
                ench = !ench;
            }
            if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 8 - 2, y + 23 + 28 - 2 + 0.5f + 17, Fonts.montserrat.getWidth(fake ? "Да" : "Нет", 6, 0.03f) + 4, 5 + 4)) {
                fake = !fake;
            }
        }
        // Добавил предмет
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 5, y + 100 - 20, 100 - 10, 15)) {
            HashMap<Enchantment, Integer> ench = new HashMap<>(enchantmentUI.enchantmentIntegerMap);

            AutoBuyHandler.instance.items.add(new AutoBuy(selected, Integer.parseInt(price),Integer.parseInt(count), ench, this.ench, this.items, this.fake, this.don));
            this.items = false;
            AutoBuyConfig.updateFile();
            openAnimation.setDirection(Direction.BACKWARDS);
            enchantmentUI.openAnimation.setDirection(Direction.BACKWARDS);
            if (openAnimation.getOutput() == 0.0f) {
                selected = null;
            }
        }
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 7, y + 30, 50, 11)) {
            priceClicked = !priceClicked;
        }
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 7, y + 30 + 20, 20, 8)) {
            countClicked = !countClicked;
        }
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x + 7, y + 30 + 11 + 8, 50, 11)) {
            enchantmentUI.stack = instance;
            enchantmentUI.enchantmentIntegerMap.clear();
            enchantmentUI.draggi = -1;
            enchantmentUI.drag = false;

        }
    }

    public void release(double mX, double mY) {
        enchantmentUI.rel();
    }

    public void charTyped(char c) {
        if (Character.isDigit(c) && priceClicked) {
            price += c;
        }
        if (Character.isDigit(c) && countClicked) {
            count += c;
        }
    }

    public void scroll(float delta, float mouseX, float mouseY) {
        enchantmentUI.scroll(delta, mouseX, mouseY);
    }

    public void key(int k) {
        if (k == GLFW.GLFW_KEY_BACKSPACE && priceClicked) {
            if (!price.isEmpty()) {
                price = price.substring(0, price.length() - 1);
            }
        }
        if (k == GLFW.GLFW_KEY_BACKSPACE && countClicked) {
            if (!count.isEmpty()) {
                count = count.substring(0, count.length() - 1);
            }
        }
        if (k == GLFW.GLFW_KEY_ENTER) {
            priceClicked = false;
            countClicked = false;
        }
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }

}

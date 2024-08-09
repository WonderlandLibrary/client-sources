package im.expensive.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentUI implements IMinecraft {

    public ItemStack stack;
    public boolean drag;

    public int draggi;
    public int scroll;
    Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();

    Map<Enchantment, Float> animations = new HashMap<>();
    final Animation openAnimation = new EaseBackIn(400, 1, 1);


    public EnchantmentUI(ItemStack stack) {
        this.stack = stack;
    }

    public void render(MatrixStack stack, float mouseX, float mouseY, float pt) {
        List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter(m -> m.canApply(this.stack)).toList();
        if (this.stack == null || enchantmentsMap.isEmpty()) return;

        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());

        float x = windowWidth / 2 - 400 / 2;
        float y = windowHeight / 2 - 300 / 2;
        x -= 150;
        y += 110;
        float width = 100;
        float height = 100;
        float animation = (float) openAnimation.getOutput();
        float halfAnimationValueRest = (1 - animation) / 2f;

        float testX = x + (width * halfAnimationValueRest);
        float testY = y + (height * halfAnimationValueRest);
        float testW = width * animation;
        float testH = height * animation;
        GlStateManager.pushMatrix();
        sizeAnimation(x + (width / 2), y + (height / 2), animation);
        DisplayUtils.drawShadow(x, y, width, height, 10,
                ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(x, y, 100, 100, 4, ColorUtils.rgba(17, 17, 17, 255));
        Scissor.push();
        Scissor.setFromComponentCoordinates(testX, testY, testW, testH);
        int i = 0;
        if (enchantmentsMap.size() * (6 + 10) >= 100)
            scroll = MathHelper.clamp(scroll, -(enchantmentsMap.size() * (6 + 10) - 90), 0);
        else {
            scroll = 0;
        }
        float size = 6;
        for (Enchantment enchantment : enchantmentsMap) {
            if (!animations.containsKey(enchantment)) {
                animations.put(enchantment, 0f);
            }
            Fonts.montserrat.drawText(stack, enchantmentIntegerMap.containsKey(enchantment) ? enchantment.getDisplayName(enchantmentIntegerMap.get(enchantment)).getString() : I18n.format(enchantment.getName()), x + 5, y + 5 + i * (size + 10) + scroll, -1, size);
            DisplayUtils.drawRoundedRect(x + 5, y + 5 + i * (size + 10) + 10 + scroll, width - 10, 3, 1, ColorUtils.rgba(25, 25, 25, 255));
            float widh = (width - 10) * (EnchantmentHelper.getEnchantmentLevel(enchantment, this.stack) - (enchantment.getMinLevel() - 1)) / (5 - (enchantment.getMinLevel() - 1));
            animations.put(enchantment, MathUtil.fast(animations.get(enchantment), widh, 10));

            DisplayUtils.drawRoundedRect(x + 5, y + 5 + i * (size + 10) + 10 + scroll, animations.get(enchantment), 3, 1, ColorUtils.getColor(0));
            i++;
        }

        if (drag) {
            i = 0;
            for (Enchantment enchantment : enchantmentsMap) {
                int level = (int) MathHelper.clamp(MathUtil.round((mouseX - x - 5) / (width - 10) * (5 - (enchantment.getMinLevel() - 1)) + (enchantment.getMinLevel() - 1), 1), enchantment.getMinLevel() - 1, 5);

                if (i == draggi) {
                    enchantmentIntegerMap.put(enchantment, level);
                }
                Integer i1 = enchantmentIntegerMap.get(enchantment);
                if (i1 != null && i1 == 0) {
                    enchantmentIntegerMap.remove(enchantment);
                }
                i++;
            }

            EnchantmentHelper.setEnchantments(enchantmentIntegerMap, this.stack);
        }
        Scissor.unset();
        Scissor.pop();
        GlStateManager.popMatrix();
    }
    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }

    public void press(int mX, int mY) {
        if (this.stack == null) return;

        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());

        float x = windowWidth / 2 - 400 / 2;
        float y = windowHeight / 2 - 300 / 2;
        x -= 150;
        y += 110;
        float width = 100;
        float height = 100;

        List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter(m -> m.canApply(this.stack)).toList();

        int i = 0;
        float size = 6;
        for (Enchantment enchantment : enchantmentsMap) {

            if (MathUtil.isHovered(mX, mY, x + 5, y + 5 + i * (size + 10) + 10 - 5 + scroll, width - 10, 3 + 10)) {
                drag = true;
                draggi = i;
            }
            i++;
        }
    }

    public void scroll(float delta, float mouseX, float mouseY) {
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());

        float x = windowWidth / 2 - 400 / 2;
        float y = windowHeight / 2 - 300 / 2;
        x -= 150;
        y += 150;
        float width = 100;
        float height = 100;
        if (MathUtil.isHovered(mouseX,mouseY,x,y,width,height)) {
            scroll += delta * 10;
        }
    }


    public void rel() {
        drag = false;
        draggi = -1;
    }

}

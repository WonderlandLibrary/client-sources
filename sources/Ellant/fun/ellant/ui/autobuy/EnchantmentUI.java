//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fun.ellant.ui.autobuy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class EnchantmentUI implements IMinecraft {
    public ItemStack stack;
    public boolean drag;
    public int draggi;
    public int scroll;
    Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap();
    Map<Enchantment, Float> animations = new HashMap();
    final Animation openAnimation = new EaseBackIn(400, 1.0, 1.0F);

    public EnchantmentUI(ItemStack stack) {
        this.stack = stack;
    }

    public void render(MatrixStack stack, float mouseX, float mouseY, float pt) {
        List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter((m) -> {
            return m.canApply(this.stack);
        }).toList();
        if (this.stack != null && !enchantmentsMap.isEmpty()) {
            int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
            int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
            float x = (float)(windowWidth / 2 - 200);
            float y = (float)(windowHeight / 2 - 150);
            x -= 150.0F;
            y += 110.0F;
            float width = 100.0F;
            float height = 100.0F;
            float animation = (float)this.openAnimation.getOutput();
            float halfAnimationValueRest = (1.0F - animation) / 2.0F;
            float testX = x + width * halfAnimationValueRest;
            float testY = y + height * halfAnimationValueRest;
            float testW = width * animation;
            float testH = height * animation;
            GlStateManager.pushMatrix();
            sizeAnimation((double)(x + width / 2.0F), (double)(y + height / 2.0F), (double)animation);
            DisplayUtils.drawShadow(x, y, width, height, 10, ColorUtils.rgba(17, 17, 17, 128));
            DisplayUtils.drawRoundedRect(x, y, 100.0F, 100.0F, 4.0F, ColorUtils.rgba(17, 17, 17, 255));
            Scissor.push();
            Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)testW, (double)testH);
            int i = 0;
            if (enchantmentsMap.size() * 16 >= 100) {
                this.scroll = MathHelper.clamp(this.scroll, -(enchantmentsMap.size() * 16 - 90), 0);
            } else {
                this.scroll = 0;
            }

            float size = 6.0F;

            Iterator var20;
            Enchantment enchantment;
            for(var20 = enchantmentsMap.iterator(); var20.hasNext(); ++i) {
                enchantment = (Enchantment)var20.next();
                if (!this.animations.containsKey(enchantment)) {
                    this.animations.put(enchantment, 0.0F);
                }

                Fonts.montserrat.drawText(stack, this.enchantmentIntegerMap.containsKey(enchantment) ? enchantment.getDisplayName((Integer)this.enchantmentIntegerMap.get(enchantment)).getString() : I18n.format(enchantment.getName(), new Object[0]), x + 5.0F, y + 5.0F + (float)i * (size + 10.0F) + (float)this.scroll, -1, size);
                DisplayUtils.drawRoundedRect(x + 5.0F, y + 5.0F + (float)i * (size + 10.0F) + 10.0F + (float)this.scroll, width - 10.0F, 3.0F, 1.0F, ColorUtils.rgba(25, 25, 25, 255));
                float widh = (width - 10.0F) * (float)(EnchantmentHelper.getEnchantmentLevel(enchantment, this.stack) - (enchantment.getMinLevel() - 1)) / (float)(5 - (enchantment.getMinLevel() - 1));
                this.animations.put(enchantment, MathUtil.fast((Float)this.animations.get(enchantment), widh, 10.0F));
                DisplayUtils.drawRoundedRect(x + 5.0F, y + 5.0F + (float)i * (size + 10.0F) + 10.0F + (float)this.scroll, (Float)this.animations.get(enchantment), 3.0F, 1.0F, ColorUtils.getColor(0));
            }

            if (this.drag) {
                i = 0;

                for(var20 = enchantmentsMap.iterator(); var20.hasNext(); ++i) {
                    enchantment = (Enchantment)var20.next();
                    int level = (int)MathHelper.clamp(MathUtil.round((double)((mouseX - x - 5.0F) / (width - 10.0F) * (float)(5 - (enchantment.getMinLevel() - 1)) + (float)(enchantment.getMinLevel() - 1)), 1.0), (double)(enchantment.getMinLevel() - 1), 5.0);
                    if (i == this.draggi) {
                        this.enchantmentIntegerMap.put(enchantment, level);
                    }

                    Integer i1 = (Integer)this.enchantmentIntegerMap.get(enchantment);
                    if (i1 != null && i1 == 0) {
                        this.enchantmentIntegerMap.remove(enchantment);
                    }
                }

                EnchantmentHelper.setEnchantments(this.enchantmentIntegerMap, this.stack);
            }

            Scissor.unset();
            Scissor.pop();
            GlStateManager.popMatrix();
        }
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0.0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0.0);
    }

    public void press(int mX, int mY) {
        if (this.stack != null) {
            int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
            int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
            float x = (float)(windowWidth / 2 - 200);
            float y = (float)(windowHeight / 2 - 150);
            x -= 150.0F;
            y += 110.0F;
            float width = 100.0F;
            float height = 100.0F;
            List<Enchantment> enchantmentsMap = Registry.ENCHANTMENT.stream().filter((m) -> {
                return m.canApply(this.stack);
            }).toList();
            int i = 0;
            float size = 6.0F;

            for(Iterator var12 = enchantmentsMap.iterator(); var12.hasNext(); ++i) {
                Enchantment enchantment = (Enchantment)var12.next();
                if (MathUtil.isHovered((float)mX, (float)mY, x + 5.0F, y + 5.0F + (float)i * (size + 10.0F) + 10.0F - 5.0F + (float)this.scroll, width - 10.0F, 13.0F)) {
                    this.drag = true;
                    this.draggi = i;
                }
            }

        }
    }

    public void scroll(float delta, float mouseX, float mouseY) {
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float x = (float)(windowWidth / 2 - 200);
        float y = (float)(windowHeight / 2 - 150);
        x -= 150.0F;
        y += 150.0F;
        float width = 100.0F;
        float height = 100.0F;
        if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
            this.scroll = (int)((float)this.scroll + delta * 10.0F);
        }

    }

    public void rel() {
        this.drag = false;
        this.draggi = -1;
    }
}

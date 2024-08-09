package dev.excellent.client.module.impl.misc.autobuy.entity;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Data;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2f;

import java.util.HashMap;

@Data
public class AutoBuyItem implements IScreen, IAccess {
    private final ItemStack itemStack;
    private final HashMap<Enchantment, Boolean> enchants;
    private int price;

    public AutoBuyItem(ItemStack itemStack, HashMap<Enchantment, Boolean> enchants, int price) {
        this.itemStack = itemStack;
        this.enchants = enchants;
        this.price = price;
    }

    private float offset = 0;
    private Vector2f position = new Vector2f();
    private Font font = Fonts.INTER_BOLD.get(14);

    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        float x = position.x;
        float y = position.y;

        String itemName = getItemStack()
                .getItem()
                .getTranslationKey()
                .replaceAll("item.minecraft.", "")
                .replaceAll("block.minecraft.", "")
                .replaceAll("_", " ");

        if (this instanceof DonateItem donate) {
            itemName = donate.getName();
        }

        Font font = Fonts.INTER_REGULAR.get(14);

        GlStateManager.pushMatrix();
        GLUtils.scaleStart(x + 8, (y + offset) + 8, (float) AutoBuy.singleton.get().getAutoBuyScreen().getAlphaAnimation().getValue());
        mc.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(getItemStack(), x, (y + offset));
        GLUtils.scaleEnd();
        font.draw(matrixStack, itemName, (x + 16 + 3), (y + offset + 4), ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, AutoBuy.singleton.get().getAutoBuyScreen().getAlphaAnimation().getValue() * 255))));
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public void onClose() {

    }
}

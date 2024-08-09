package dev.excellent.client.module.impl.misc.autobuy.pages;

import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.client.module.impl.misc.autobuy.entity.AutoBuyItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.DonateItem;
import dev.excellent.client.module.impl.misc.autobuy.screen.AutoBuyScreen;
import dev.excellent.client.module.impl.misc.autobuy.screen.IAutoBuyPage;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.text.TextAlign;
import dev.excellent.impl.util.render.text.TextBox;
import lombok.Getter;
import net.minecraft.util.math.vector.Vector4f;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2f;

import java.awt.*;

@Getter
public class AutoBuyAddItemPage implements IAutoBuyPage {
    private final Vector4f vec = new Vector4f();
    private final ScrollUtil allItemsScroll = new ScrollUtil();
    private final ScrollUtil selectedItemsScroll = new ScrollUtil();
    private final TextBox allItemsSearchField = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(14), ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Название предмета", 0);
    private final TextBox selectedItemsSearchField = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(14), ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Название предмета", 0);

    @Override
    public Vector4f position() {
        return vec;
    }

    @Override
    public void init() {
        excellent.getAutoBuyManager().set();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float x, y, width, height;
        x = position().getX();
        y = position().getY();
        width = position().getZ();
        height = position().getW();
        float margin = 40;
        Font font = parent().getFont();

        int alphaPC = (int) (parent().getAlphaAnimation().getValue() * 235);
        Color colorFirst = ColorUtil.withAlpha(getTheme().getFirstColor(), alphaPC);
        Color colorSecond = ColorUtil.withAlpha(getTheme().getSecondColor(), alphaPC);
        int color = ColorUtil.overCol(colorFirst.hashCode(), colorSecond.hashCode());
        boolean bloom = true;
        float round = 4, shadow = 2, dark = 0.2F;
        int halfColor = ColorUtil.multAlpha(color, .5F);

        float buttonHeight = font.getHeight() * 1.5F;
        float buttonWidth;
        float buttonX;
        float buttonY;
        String btnText;
        btnText = "очистить список";

        buttonWidth = font.getWidth(btnText) + 4;

        // -> button
        btnText = "->";
        buttonY = y + (height / 2F) - buttonHeight - 5;
        buttonX = x + (width / 2F) - (buttonWidth / 2F) - 2;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, buttonX, buttonY, buttonX + buttonWidth, buttonY + buttonHeight, 2, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, buttonX, buttonY, buttonX + buttonWidth, buttonY + buttonHeight, 2, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();
        font.drawCenter(matrixStack, btnText, buttonX + buttonWidth / 2F, buttonY + (buttonHeight / 6F), ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));


        // очистить
        btnText = "очистить список";
        buttonY = y + (height / 2F) + 5;

        int red = ColorUtil.overCol(ColorUtil.getColor(255, 0, 0, alphaPC), halfColor, 0.2F);
        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, buttonX, buttonY, buttonX + buttonWidth, buttonY + buttonHeight, 2, shadow, red, red, red, red, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, buttonX, buttonY, buttonX + buttonWidth, buttonY + buttonHeight, 2, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();

        font.drawCenter(matrixStack, btnText, buttonX + buttonWidth / 2F, buttonY + (buttonHeight / 6F), ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));

        //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

        float panelX = x + 10;
        float panelY = y + margin - 10;

        float panelWidth = 160;
        float panelHeight = height - margin - margin;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();

        font.draw(matrixStack, "Все предметы", panelX + 5, panelY + 5, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));

        allItemsScroll.setEnabled(isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        allItemsScroll.setAutoReset(isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        allItemsScroll.update();
        allItemsScroll.setSpeed(20);

        float yPos = (float) (panelY + font.getHeight() + 5 + 5 + allItemsScroll.getScroll());

        StencilBuffer.init();
        RectUtil.drawRect(matrixStack, panelX, panelY + font.getHeight() + 5, panelX + panelWidth, panelY + panelHeight, ColorUtil.getColor(255, 255, 255, 255));
        StencilBuffer.read(1);

        float offset = 0;
        int itemSize = 16;
        for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager()) {
            if (searchCheck(autoBuyItem, allItemsSearchField)) continue;
            if (excellent.getAutoBuyManager().getSelectedItems().stream().anyMatch(stack -> stack.getItemStack().equals(autoBuyItem.getItemStack())))
                continue;
            autoBuyItem.getPosition().set(panelX + 5, yPos);
            autoBuyItem.setOffset(offset);

            float itemY = autoBuyItem.getPosition().y + offset;

            if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                autoBuyItem.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            offset += itemSize;
        }
        StencilBuffer.cleanup();

        allItemsScroll.render(matrixStack, new Vector2f(panelX + 1, panelY + font.getHeight() + 5 + 5), (panelHeight - 5) - (font.getHeight() + 5 + 5), 255);
        allItemsScroll.setMax(offset, (panelHeight - 5) - (font.getHeight() + 5 + 5));

        panelX = x + width - panelWidth - 10;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();

        font.draw(matrixStack, "Выбранные предметы", panelX + 5, panelY + 5, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));

        selectedItemsScroll.setEnabled(isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        selectedItemsScroll.setAutoReset(!isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        selectedItemsScroll.update();
        selectedItemsScroll.setSpeed(20);

        yPos = (float) (panelY + font.getHeight() + 5 + 5 + selectedItemsScroll.getScroll());

        StencilBuffer.init();
        RectUtil.drawRect(matrixStack, panelX, panelY + font.getHeight() + 5, panelX + panelWidth, panelY + panelHeight, ColorUtil.getColor(255, 255, 255, 255));
        StencilBuffer.read(1);

        offset = 0;
        for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
            if (searchCheck(autoBuyItem, selectedItemsSearchField)) continue;

            autoBuyItem.getPosition().set(panelX + 5, yPos);
            autoBuyItem.setOffset(offset);

            float itemY = autoBuyItem.getPosition().y + offset;

            if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                autoBuyItem.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            offset += itemSize;
        }
        StencilBuffer.cleanup();

        selectedItemsScroll.render(matrixStack, new Vector2f(panelX + 1, panelY + font.getHeight() + 5 + 5), (panelHeight - 5) - (font.getHeight() + 5 + 5), 255);
        selectedItemsScroll.setMax(offset, (panelHeight - 5) - (font.getHeight() + 5 + 5));

        //
        float searchFieldY = panelY + panelHeight + 5;

        float allItemsSearchFieldX = x + 10;
        float allItemsSearchFieldHeight = allItemsSearchField.getFont().getHeight() * 1.5F;

        allItemsSearchField.getPosition().set(allItemsSearchFieldX + 5, (searchFieldY + (allItemsSearchFieldHeight / 2F) - (allItemsSearchField.getFont().getHeight() / 2F)));
        allItemsSearchField.setWidth(panelWidth - 10);
        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, allItemsSearchFieldX, searchFieldY, allItemsSearchFieldX + panelWidth, searchFieldY + allItemsSearchFieldHeight, 2, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, allItemsSearchFieldX, searchFieldY, allItemsSearchFieldX + panelWidth, searchFieldY + allItemsSearchFieldHeight, 2, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();
        allItemsSearchField.draw(matrixStack);

        float selectedItemsSearchFieldX = x + width - panelWidth - 10;
        float selectedItemsSearchFieldHeight = selectedItemsSearchField.getFont().getHeight() * 1.5F;
        selectedItemsSearchField.getPosition().set(selectedItemsSearchFieldX + 5, (searchFieldY + (selectedItemsSearchFieldHeight / 2F) - (selectedItemsSearchField.getFont().getHeight() / 2F)));
        selectedItemsSearchField.setWidth(panelWidth - 10);
        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, selectedItemsSearchFieldX, searchFieldY, selectedItemsSearchFieldX + panelWidth, searchFieldY + selectedItemsSearchFieldHeight, 2, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, selectedItemsSearchFieldX, searchFieldY, selectedItemsSearchFieldX + panelWidth, searchFieldY + selectedItemsSearchFieldHeight, 2, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();
        selectedItemsSearchField.draw(matrixStack);
        //

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        allItemsSearchField.mouse(mouseX, mouseY, button);
        selectedItemsSearchField.mouse(mouseX, mouseY, button);
        float x, y, width, height;
        x = position().getX();
        y = position().getY();
        width = position().getZ();
        height = position().getW();
        float margin = 40;
        Font font = parent().getFont();

        float buttonHeight = font.getHeight() * 1.5F;
        float buttonWidth;
        float buttonX;
        float buttonY;
        String btnText;
        btnText = "очистить список";

        buttonWidth = font.getWidth(btnText) + 4;

        boolean isHover;

        // -> button
        buttonY = y + (height / 2F) - buttonHeight - 5;
        buttonX = x + (width / 2F) - (buttonWidth / 2F) - 2;
        isHover = isHover(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight);
        if (isHover) {
            excellent.getAutoBuyManager().getSelectedItems().clear();
            excellent.getAutoBuyManager().getSelectedItems().addAll(excellent.getAutoBuyManager());
            excellent.getAutoBuyManager().set();
            allItemsScroll.setTarget(0);
        }
        // очистить
        buttonY = y + (height / 2F) + 5;
        isHover = isHover(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight);
        if (isHover) {
            excellent.getAutoBuyManager().getSelectedItems().clear();
            excellent.getAutoBuyManager().set();
            selectedItemsScroll.setTarget(0);
        }

        float panelX = x + 10;
        float panelY = y + margin - 10;

        float panelWidth = 160;
        float panelHeight = height - margin - margin;
        int itemSize = 16;

//        "Все предметы"
        if (isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5, panelWidth, panelHeight - (font.getHeight() + 5))) {
            float yPos = (float) (panelY + font.getHeight() + 5 + 5 + allItemsScroll.getScroll());

            float offset = 0;
            for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager()) {
                if (searchCheck(autoBuyItem, allItemsSearchField)) continue;
                if (excellent.getAutoBuyManager().getSelectedItems().stream().anyMatch(stack -> stack.getItemStack().equals(autoBuyItem.getItemStack())))
                    continue;
                autoBuyItem.getPosition().set(panelX + 5, yPos);
                autoBuyItem.setOffset(offset);

                float itemY = autoBuyItem.getPosition().y + offset;

                if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                    if (isHover(mouseX, mouseY, panelX + 2, yPos + offset + 0.5, panelWidth - 4, itemSize - 1)) {
                        autoBuyItem.mouseClicked(mouseX, mouseY, button);
                        if (isLClick(button) || isRClick(button)) {
                            excellent.getAutoBuyManager().getSelectedItems().add(autoBuyItem);
                            excellent.getAutoBuyManager().set();
                        }
                    }
                }

                offset += itemSize;
            }
        }
//        "Выбранные предметы"
        panelX = x + width - panelWidth - 10;

        if (isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5, panelWidth, panelHeight - (font.getHeight() + 5))) {
            float yPos = (float) (panelY + font.getHeight() + 5 + 5 + selectedItemsScroll.getScroll());

            float offset = 0;
            for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
                if (searchCheck(autoBuyItem, selectedItemsSearchField)) continue;
                autoBuyItem.getPosition().set(panelX + 5, yPos);
                autoBuyItem.setOffset(offset);

                float itemY = autoBuyItem.getPosition().y + offset;

                if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                    if (isHover(mouseX, mouseY, panelX + 2, yPos + offset + 0.5, panelWidth - 4, itemSize - 1)) {
                        autoBuyItem.mouseClicked(mouseX, mouseY, button);
                        if (isLClick(button) || isRClick(button)) {
                            excellent.getAutoBuyManager().getSelectedItems().remove(autoBuyItem);
                            excellent.getAutoBuyManager().set();
                        }
                    }
                }

                offset += itemSize;
            }
        }

        return false;
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        allItemsSearchField.keyPressed(keyCode);
        selectedItemsSearchField.keyPressed(keyCode);
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        allItemsSearchField.charTyped(codePoint);
        selectedItemsSearchField.charTyped(codePoint);
        return false;
    }

    @Override
    public void onClose() {
        allItemsSearchField.setText("");
        selectedItemsSearchField.setText("");
        excellent.getAutoBuyManager().set();
    }

    private AutoBuyScreen parent() {
        return AutoBuy.singleton.get().getAutoBuyScreen();
    }

    private boolean searchCheck(AutoBuyItem autoBuyItem, TextBox textBox) {
        String itemName = autoBuyItem
                .getItemStack()
                .getItem()
                .getTranslationKey()
                .replaceAll("item.minecraft.", "")
                .replaceAll("block.minecraft.", "")
                .replaceAll(" ", "")
                .replaceAll("_", "")
                .toLowerCase();

        if (autoBuyItem instanceof DonateItem donate) {
            itemName = donate.getName()
                    .replaceAll(" ", "")
                    .toLowerCase();
        }

        return !textBox.isEmpty() && !itemName
                .contains(textBox
                        .getText()
                        .replaceAll(" ", "")
                        .toLowerCase());
    }
}

package dev.excellent.client.module.impl.misc.autobuy.pages;

import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.client.module.impl.misc.autobuy.entity.AutoBuyItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.DonateItem;
import dev.excellent.client.module.impl.misc.autobuy.screen.AutoBuyScreen;
import dev.excellent.client.module.impl.misc.autobuy.screen.IAutoBuyPage;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.text.TextAlign;
import dev.excellent.impl.util.render.text.TextBox;
import dev.excellent.impl.util.render.text.TextUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class AutoBuyEditItemPage implements IAutoBuyPage {
    private final Vector4f vec = new Vector4f();
    private final ScrollUtil selectedItemsScroll = new ScrollUtil();
    private final ScrollUtil settingsScroll = new ScrollUtil();
    @Setter
    private AutoBuyItem selectedItem = null;
    private final HashMap<Enchantment, Boolean> selectedEnchantMap = new HashMap<>();

    private final TextBox textBox = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(14), ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Введите сумму", 0);
    private final TextBox selectedItemsSearchField = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(14), ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Название предмета", 0);

    @Override
    public Vector4f position() {
        return vec;
    }

    @Override
    public void init() {
        setSelectedItem(null);
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


        float panelX = x + 10;
        float panelY = y + margin - 10;

        float panelWidth = 160;
        float panelHeight = height - margin - margin;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();

        font.draw(matrixStack, "Выбранные предметы", panelX + 5, panelY + 5, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));

        selectedItemsScroll.setEnabled(isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        selectedItemsScroll.setAutoReset(isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5 + 5, panelWidth, panelHeight - 5));
        selectedItemsScroll.update();
        selectedItemsScroll.setSpeed(20);

        float yPos = (float) (panelY + font.getHeight() + 5 + 5 + selectedItemsScroll.getScroll());

        StencilBuffer.init();
        RectUtil.drawRect(matrixStack, panelX, panelY + font.getHeight() + 5, panelX + panelWidth, panelY + panelHeight, ColorUtil.getColor(255, 255, 255, 255));
        StencilBuffer.read(1);

        float offset = 0;
        int itemSize = 16;
        for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
            if (searchCheck(autoBuyItem, selectedItemsSearchField)) continue;
            autoBuyItem.getPosition().set(panelX + 5, yPos);
            autoBuyItem.setOffset(offset);

            float itemY = autoBuyItem.getPosition().y + offset;

            if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                if (selectedItem != null && selectedItem.equals(autoBuyItem)) {
                    float selectedX = autoBuyItem.getPosition().x + (itemSize / 2F);
                    float selectedY = autoBuyItem.getPosition().y + offset + (itemSize / 2F);
                    int selectedColor = ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 128)));
                    RectUtil.drawShadowSegmentsExtract(matrixStack, selectedX, selectedY, selectedX, selectedY, 0, itemSize, selectedColor, selectedColor, selectedColor, selectedColor, true, true);
                }
                autoBuyItem.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            offset += itemSize;
        }
        StencilBuffer.cleanup();

        selectedItemsScroll.render(matrixStack, new Vector2f(panelX + 1, panelY + font.getHeight() + 5 + 5), (panelHeight - 5) - (font.getHeight() + 5 + 5), 255);
        selectedItemsScroll.setMax(offset, (panelHeight - 5) - (font.getHeight() + 5 + 5));


        panelX += panelWidth + 15;
        panelWidth = (x + width) - panelX - 10;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();

        font.draw(matrixStack, "Настройка", panelX + 5, panelY + 5, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));

        //
        float searchFieldY = panelY + panelHeight + 5;
        float selectedItemsSearchFieldX = x + 10;
        float selectedItemsSearchFieldHeight = selectedItemsSearchField.getFont().getHeight() * 1.5F;
        float searchFieldWidth = 160;
        selectedItemsSearchField.getPosition().set(selectedItemsSearchFieldX + 5, (searchFieldY + (selectedItemsSearchFieldHeight / 2F) - (selectedItemsSearchField.getFont().getHeight() / 2F)));
        selectedItemsSearchField.setWidth(searchFieldWidth - 10);
        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, selectedItemsSearchFieldX, searchFieldY, selectedItemsSearchFieldX + searchFieldWidth, searchFieldY + selectedItemsSearchFieldHeight, 2, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, selectedItemsSearchFieldX, searchFieldY, selectedItemsSearchFieldX + searchFieldWidth, searchFieldY + selectedItemsSearchFieldHeight, 2, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
        GlStateManager.popMatrix();
        selectedItemsSearchField.draw(matrixStack);
        //

        if (selectedItem == null) {
            font.drawCenter(matrixStack, "Выберите предмет", panelX + (panelWidth / 2F), panelY + (panelHeight / 2F) - (font.getHeight() / 2F), ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));
            return;
        }

        yPos = (float) (panelY + font.getHeight() + 5 + 5 + settingsScroll.getScroll());
        settingsScroll.update();
        settingsScroll.setSpeed(20);

        dark = 0.4F;
        round = 1;
        shadow = 5;
        float textBoxWidth = Math.max(font.getWidth(textBox.getText()), font.getWidth("Введите сумму")) + 10;

        {
            font.draw(matrixStack, "Цена за единицу предмета " + TextFormatting.GRAY + "(Цена за стак: " + (selectedItem.getPrice() * 64) + ")", panelX + 5, yPos, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));
            yPos += font.getHeight() + 5;

            boolean enabled = textBox.selected;
            int textBoxGlow = enabled ? color : halfColor;

            GlStateManager.pushMatrix();
            RectUtil.drawRoundedRectShadowed(matrixStack, panelX + 5, yPos, panelX + 5 + textBoxWidth, yPos + textBox.getFont().getHeight() + 4, round, shadow, textBoxGlow, textBoxGlow, textBoxGlow, textBoxGlow, bloom, true, false, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, panelX + 5, yPos, panelX + 5 + textBoxWidth, yPos + textBox.getFont().getHeight() + 4, round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
            GlStateManager.popMatrix();

            String textBoxText = TextUtils.removeForbiddenCharacters(textBox.getText(), TextUtils.NUMBERS);

            if (!textBoxText.isEmpty()) {
                try {
                    int parsedValue = Integer.parseInt(textBoxText);
                    textBoxText = String.valueOf(Math.min(parsedValue, Integer.MAX_VALUE));
                } catch (NumberFormatException ignored) {
                    textBoxText = String.valueOf(Math.min(selectedItem.getPrice(), Integer.MAX_VALUE));
                }
            }

            textBox.setText(textBoxText);

            if (!textBoxText.isEmpty()) {
                selectedItem.setPrice(Integer.parseInt(textBoxText));
            } else {
                textBox.setText("");
            }

            textBox.getPosition().set(panelX + 10, yPos + 2);
            textBox.setWidth(textBoxWidth - 10);
            textBox.draw(matrixStack);
            yPos += font.getHeight() + 5 + 4;
        }
//
        float yOffset = 0;
        if (!selectedItem.getEnchants().entrySet().isEmpty()) {
            font.draw(matrixStack, "Имеет зачарования", panelX + 5, yPos, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));
            yPos += font.getHeight() + 5;

            StringBuilder enchantLine = new StringBuilder();
            offset = 0;
            for (Map.Entry<Enchantment, Boolean> enchant : selectedItem.getEnchants().entrySet()) {
                String enchantName = enchant.getKey().getName()
                        .replaceAll("enchantment.minecraft.", "")
                        .replaceAll("_", " ").trim();
                enchantLine.append(enchantName);
                enchantLine.append(":");
                offset += font.getHeight();
            }

            //noinspection RegExpRepeatedSpace
            String splitter = "    ";

            for (String line : TextUtils
                    .splitLine(enchantLine
                            .toString()
                            .trim(), font, panelWidth - 10, splitter)
            ) {
                String enchantName = line.trim();
                float xOffset = 0;
                for (String s : enchantName
                        .split(splitter)) {
                    float rectX = panelX + 5 + xOffset;
                    float rectY = yPos + yOffset;
                    float finalRound = round;
                    float finalDark = dark;
                    float finalShadow = shadow;
                    selectedItem.getEnchants()
                            .entrySet()
                            .stream()
                            .filter((enchant) -> enchant
                                    .getKey()
                                    .getName()
                                    .replaceAll("enchantment.minecraft.", "")
                                    .replaceAll("_", " ")
                                    .trim()
                                    .equals(s))
                            .findFirst()
                            .ifPresent(ench -> {

                                boolean enabled = ench.getValue();
                                int btnColorGlow = enabled ? color : halfColor;

                                GlStateManager.pushMatrix();
                                RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + font.getWidth(s), rectY + font.getHeight(), finalRound, finalShadow, btnColorGlow, btnColorGlow, btnColorGlow, btnColorGlow, bloom, true, false, true);
                                RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + font.getWidth(s), rectY + font.getHeight(), finalRound, .5F, ColorUtil.multDark(color, finalDark), ColorUtil.multDark(color, finalDark), ColorUtil.multDark(color, finalDark), ColorUtil.multDark(color, finalDark), false, false, true, true);
                                GlStateManager.popMatrix();
                            });
                    xOffset += font.getWidth(s + splitter);
                }
                font.draw(matrixStack, enchantName, panelX + 5, yPos + yOffset, ColorUtil.getColor(255, 255, 255, (int) (Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255))));
                yOffset += font.getHeight() + 10;
            }
            yPos -= font.getHeight() - 5;
        }
        settingsScroll.render(matrixStack, new Vector2f(panelX + 1, panelY + font.getHeight() + 5 + 5), (panelHeight - 5) - (font.getHeight() + 5 + 5), 255);
        settingsScroll.setMax(yOffset, (panelHeight - 5) - (font.getHeight() + 5 + 5));

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        selectedItemsSearchField.mouse(mouseX, mouseY, button);
        float x, y, width, height;
        x = position().getX();
        y = position().getY();
        width = position().getZ();
        height = position().getW();
        float margin = 40;
        Font font = parent().getFont();

        float panelX = x + 10;
        float panelY = y + margin - 10;

        float panelWidth = 160;
        float panelHeight = height - margin - margin;
        int itemSize = 16;

        float yPos = (float) (panelY + font.getHeight() + 5 + 5 + selectedItemsScroll.getScroll());
        float offset = 0;
        if (isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5, panelWidth, panelHeight - (font.getHeight() + 5))) {
            for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
                if (searchCheck(autoBuyItem, selectedItemsSearchField)) continue;
                autoBuyItem.getPosition().set(panelX + 5, yPos);
                autoBuyItem.setOffset(offset);

                float itemY = autoBuyItem.getPosition().y + offset;

                if (itemY > y + (margin / 2F) && itemY < y + height - (margin / 2F)) {
                    if (isHover(mouseX, mouseY, panelX + 2, yPos + offset + 0.5, panelWidth - 4, itemSize - 1)) {
                        autoBuyItem.mouseClicked(mouseX, mouseY, button);
                        if (isLClick(button) || isRClick(button)) {
                            if (selectedItem != null) {
                                String textBoxText = TextUtils.removeForbiddenCharacters(textBox.getText(), TextUtils.NUMBERS);
                                if (!textBoxText.isEmpty()) {
                                    try {
                                        int parsedValue = Integer.parseInt(textBoxText);
                                        textBoxText = String.valueOf(Math.min(parsedValue, Integer.MAX_VALUE));
                                    } catch (NumberFormatException ignored) {
                                        textBoxText = String.valueOf(Math.min(selectedItem.getPrice(), Integer.MAX_VALUE));
                                    }
                                }
                                if (!textBoxText.isEmpty()) {
                                    selectedItem.setPrice(Integer.parseInt(textBoxText));
                                    excellent.getAutoBuyManager().set();
                                }
                            }
                            setSelectedItem(autoBuyItem);
                            textBox.setText(String.valueOf(autoBuyItem.getPrice()));
                            textBox.setCursor(textBox.getText().length());
                            excellent.getAutoBuyManager().set();
                        }
                    }
                }

                offset += itemSize;
            }
        }

        panelX += panelWidth + 15;
        panelWidth = (x + width) - panelX - 10;

        if (isHover(mouseX, mouseY, panelX, panelY + font.getHeight() + 5, panelWidth, panelHeight)) {
            yPos = (float) (panelY + font.getHeight() + 5 + 5 + settingsScroll.getScroll());

            yPos += font.getHeight() + 5;
            textBox.mouse(mouseX, mouseY, button);
            textBox.setCursor(textBox.getText().length());
            yPos += font.getHeight() + 5 + 4;

            float yOffset = 0;
            if (!selectedItem.getEnchants().entrySet().isEmpty()) {
                yPos += font.getHeight() + 5;

                StringBuilder enchantLine = new StringBuilder();
                offset = 0;
                for (Map.Entry<Enchantment, Boolean> enchant : selectedItem.getEnchants().entrySet()) {
                    String enchantName = enchant.getKey().getName()
                            .replaceAll("enchantment.minecraft.", "")
                            .replaceAll("_", " ").trim();
                    enchantLine.append(enchantName);
                    enchantLine.append(":");
                    offset += font.getHeight();
                }

                //noinspection RegExpRepeatedSpace
                String splitter = "    ";

                for (String line : TextUtils
                        .splitLine(enchantLine
                                .toString()
                                .trim(), font, panelWidth - 10, splitter)
                ) {
                    String enchantName = line.trim();
                    float xOffset = 0;
                    for (String s : enchantName
                            .split(splitter)) {
                        float rectX = panelX + 5 + xOffset;
                        float rectY = yPos + yOffset;
                        selectedItem.getEnchants()
                                .entrySet()
                                .stream()
                                .filter((enchant) -> enchant
                                        .getKey()
                                        .getName()
                                        .replaceAll("enchantment.minecraft.", "")
                                        .replaceAll("_", " ")
                                        .trim()
                                        .equals(s))
                                .findFirst()
                                .ifPresent(ench -> {
                                    boolean enabled = ench.getValue();
                                    if (isHover(mouseX, mouseY, rectX, rectY, font.getWidth(s), font.getHeight())) {
                                        ench.setValue(!enabled);
                                        excellent.getAutoBuyManager().set();
                                    }
                                });
                        xOffset += font.getWidth(s + splitter);
                    }
                    yOffset += font.getHeight() + 10;
                }
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
        selectedItemsSearchField.keyPressed(keyCode);
        textBox.keyPressed(keyCode);
        if (keyCode == Keyboard.KEY_ENTER.keyCode) {
            textBox.setSelected(false);
            excellent.getAutoBuyManager().set();
        }
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        selectedItemsSearchField.charTyped(codePoint);
        if (Character.isDigit(codePoint)) {
            textBox.charTyped(codePoint);
        }
        return false;
    }

    @Override
    public void onClose() {
        excellent.getAutoBuyManager().set();
        setSelectedItem(null);
        selectedItemsSearchField.setText("");
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

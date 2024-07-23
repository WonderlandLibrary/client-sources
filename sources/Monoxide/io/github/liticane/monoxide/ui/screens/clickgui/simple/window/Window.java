package io.github.liticane.monoxide.ui.screens.clickgui.simple.window;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Window extends GuiScreen{

    private final Module module;
    private final GuiScreen prevScreen;
    private final ArrayList<Value> expanded = new ArrayList<>();

    public Window(Module module, GuiScreen prevScreen) {
        this.module = module;
        this.prevScreen = prevScreen;
    }

    float scroll;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        scroll += Mouse.getDWheel() / 10F;
        scroll = Math.min(scroll, 0);
        float x = super.width / 2 - 170, y = super.height / 2 - 230, width = 340, height = 460;
        RenderableShaders.renderAndRun(() -> {
            RenderUtil.drawRect(x, y, width, height, new Color(0, 0, 0, 180).getRGB());
        });
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI", 18);
        float valueY = y + 20 + scroll;
        float valueX = x + 20;
        RenderUtil.startScissorBox();
        RenderUtil.drawScissorBox(x, y, width, height);
        for(Value value : ValueManager.getInstance().getValues(module)) {
            if(!value.isVisible())
                continue;
            if(value instanceof BooleanValue) {
                BooleanValue booleanValue = (BooleanValue)value;
                fontRenderer.drawStringWithShadow(value.getName(), valueX, valueY, -1);
                RenderUtil.drawBorderedRect(valueX + width - 40 - 10, valueY - 1, valueX + width - 40 - 10 + 10, valueY - 1 + 10, 1, booleanValue.getValue() ? new Color(200, 200, 200).getRGB() : new Color(0, 0, 0, 50).getRGB(), new Color(200, 200, 200).getRGB(), true);
                valueY += fontRenderer.FONT_HEIGHT + 8;
            } else if(value instanceof MultiBooleanValue) {
                MultiBooleanValue multiBooleanValue = (MultiBooleanValue)value;
                float length = fontRenderer.getStringWidthInt(multiBooleanValue.getValue().size() + " Enabled");
                fontRenderer.drawStringWithShadow(value.getName(), valueX, valueY, -1);
                if(this.expanded.contains(multiBooleanValue)) {
                    RenderUtil.drawRect(valueX + width - 50 - length, valueY - 1, length + 10, 15 + multiBooleanValue.getValues().length * 15, new Color(0, 0, 0, 50).getRGB());
                    float stringsY = valueY - 1 + 15;
                    for(String string : multiBooleanValue.getValues()) {
                        fontRenderer.drawStringWithShadow(string, valueX + width - 50 - length + 5.5f, stringsY + 3, multiBooleanValue.getValue().contains(string) ? -1 : new Color(200, 200, 200).getRGB());
                        stringsY += 15;
                    }
                    fontRenderer.drawStringWithShadow((multiBooleanValue.getValue().size() - 1) + " Enabled", valueX + width - 50 - length + 5.5f, valueY + 3, -1);
                } else {
                    RenderUtil.drawRect(valueX + width - 50 - length, valueY - 1, length + 10, 15, new Color(0, 0, 0, 50).getRGB());
                    fontRenderer.drawStringWithShadow((multiBooleanValue.getValue().size() - 1) + " Enabled", valueX + width - 50 - length + 5.5f, valueY + 3, -1);
                }
                valueY += fontRenderer.FONT_HEIGHT + 13 + (this.expanded.contains(multiBooleanValue) ? multiBooleanValue.getValues().length * 15 : 0);
            } else if(value instanceof ModeValue) {
                ModeValue modeValue = (ModeValue)value;
                float length = fontRenderer.getStringWidthInt(modeValue.getValue());
                fontRenderer.drawStringWithShadow(value.getName(), valueX, valueY, -1);
                if(this.expanded.contains(modeValue)) {
                    String longest = "";
                    for(String string : modeValue.getValues()) {
                        if(fontRenderer.getStringWidthInt(string) > fontRenderer.getStringWidthInt(longest))
                            longest = string;
                    }
                    float longestLength = fontRenderer.getStringWidthInt(longest);
                    RenderUtil.drawRect(valueX + width - 50 - longestLength, valueY - 1, longestLength + 10, 15 + modeValue.getValues().length * 15, new Color(0, 0, 0, 50).getRGB());
                    float stringsY = valueY - 1 + 15;
                    for(String string : modeValue.getValues()) {
                        fontRenderer.drawStringWithShadow(string, valueX + width - 50 - longestLength + 5.5f, stringsY + 3, -1);
                        stringsY += 15;
                    }
                    fontRenderer.drawStringWithShadow(modeValue.getValue(), valueX + width - 50 - length + 5.5f, valueY + 3, -1);
                } else {
                    RenderUtil.drawRect(valueX + width - 50 - length, valueY - 1, length + 10, 15, new Color(0, 0, 0, 50).getRGB());
                    fontRenderer.drawStringWithShadow(modeValue.getValue(), valueX + width - 50 - length + 5.5f, valueY + 3, -1);
                }
                valueY += fontRenderer.FONT_HEIGHT + 13 + (this.expanded.contains(modeValue) ? modeValue.getValues().length * 15 : 0);
            } else if(value instanceof NumberValue) {
                NumberValue numberValue = (NumberValue) value;
                fontRenderer.drawStringWithShadow(value.getName(), valueX, valueY, -1);
                fontRenderer.drawStringWithShadow(numberValue.getValue().floatValue() + "", valueX + width - fontRenderer.getStringWidthInt(numberValue.getValue().floatValue() + "") - 40, valueY, -1);
                RenderUtil.drawRect(valueX + 3, valueY + fontRenderer.FONT_HEIGHT + 2, 160, 2.5f, new Color(0, 0, 0, 50).getRGB());
                float sliderX = valueX + 3, sliderY = valueY + fontRenderer.FONT_HEIGHT + 2, sliderWidth = 160, sliderHeight = 2.5f;
                float length = MathHelper
                        .floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue())
                                / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderWidth);
                RenderUtil.drawRect(sliderX, sliderY, length, sliderHeight, new Color(200, 200, 200).getRGB());
                if(Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, sliderX, sliderY, sliderWidth, sliderHeight)) {
                    double min1 = numberValue.getMinimum().floatValue();
                    double max1 = numberValue.getMaximum().floatValue();
                    double newValue = MathUtil.round((mouseX - sliderX) * (max1 - min1) / (sliderWidth - 1.0f) + min1, numberValue.getDecimals());
                    numberValue.setValue(newValue);
                }
                valueY += fontRenderer.FONT_HEIGHT + 13;
            }
        }
        RenderUtil.endScissorBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        float x = super.width / 2 - 170, y = super.height / 2 - 230, width = 340, height = 460;
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI", 18);
        float valueY = y + 20 + scroll;
        float valueX = x + 20;
        for(Value value : ValueManager.getInstance().getValues(module)) {
            if(!value.isVisible())
                continue;
            if(value instanceof BooleanValue) {
                if (RenderUtil.isHovered(mouseX, mouseY, valueX + width - 40 - 20, valueY - 1, 20, 10)) {
                    BooleanValue booleanValue = (BooleanValue) value;
                    booleanValue.setValue(!booleanValue.getValue());
                }
                valueY += fontRenderer.FONT_HEIGHT + 8;
            } else if(value instanceof ModeValue) {
                ModeValue modeValue = (ModeValue)value;
                float length = fontRenderer.getStringWidthInt(modeValue.getValue());
                if(this.expanded.contains(modeValue)) {
                    String longest = "";
                    for(String string : modeValue.getValues()) {
                        if(fontRenderer.getStringWidthInt(string) > fontRenderer.getStringWidthInt(longest))
                            longest = string;
                    }
                    float longestLength = fontRenderer.getStringWidthInt(longest);
                    if(RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - longestLength, valueY - 1, longestLength + 10, 15 + modeValue.getValues().length * 15)) {
                        this.expanded.remove(modeValue);
                    }
                    float stringsY = valueY - 1 + 15;
                    for(String string : modeValue.getValues()) {
                        if(RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - longestLength, stringsY, longestLength + 10, 13)) {
                            modeValue.setValue(string);
                        }
                        stringsY += 15;
                    }
                } else {
                    if (RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - length, valueY - 1, length + 10, 15)) {
                        this.expanded.add(modeValue);
                    }
                }
                valueY += fontRenderer.FONT_HEIGHT + 13 + (this.expanded.contains(modeValue) ? modeValue.getValues().length * 15 : 0);
            } else if(value instanceof MultiBooleanValue) {
                MultiBooleanValue multiBooleanValue = (MultiBooleanValue)value;
                float length = fontRenderer.getStringWidthInt(multiBooleanValue.getValue().size()  + " Enabled");
                if(this.expanded.contains(multiBooleanValue)) {
                    if(RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - length, valueY - 1, length + 10, 15 + multiBooleanValue.getValues().length * 15)) {
                        this.expanded.remove(multiBooleanValue);
                    }
                    float stringsY = valueY - 1 + 15;
                    for(String string : multiBooleanValue.getValues()) {
                        if(RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - length, stringsY, length + 10, 13)) {
                            if(multiBooleanValue.getValue().contains(string)) {
                                multiBooleanValue.getValue().remove(string);
                            } else {
                                multiBooleanValue.getValue().add(string);
                            }
                        }
                        stringsY += 15;
                    }
                } else {
                    if (RenderUtil.isHovered(mouseX, mouseY, valueX + width - 50 - length, valueY - 1, length + 10, 15)) {
                        this.expanded.add(multiBooleanValue);
                    }
                }
                valueY += fontRenderer.FONT_HEIGHT + 13 + (this.expanded.contains(multiBooleanValue) ? multiBooleanValue.getValues().length * 15 : 0);
            } else if(value instanceof NumberValue) {
                valueY += fontRenderer.FONT_HEIGHT + 13;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char key, int code) {
        if (code == 1) {
            mc.displayGuiScreen(prevScreen);
        }
    }

}

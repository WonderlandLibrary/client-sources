package io.github.liticane.monoxide.ui.screens.clickgui;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.impl.hud.ClickGuiModule;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.interfaces.ClientInformationAccess;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.shader.shaders.GradientShader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AtaniClickGuiScreen extends GuiScreen implements ClientInformationAccess {

    float width = 450, height = 350;
    float draggingX = -1337, draggingY = -1337;
    float x = -1337, y = -1337;
    ModuleCategory selectedCategory;
    ArrayList<Value> expanded = new ArrayList<>();
    FontRenderer fontRendererSmall = FontStorage.getInstance().findFont("SF UI",  18);
    FontRenderer fontRendererSmaller = FontStorage.getInstance().findFont("SF UI",  17);
    Color textColor = new Color(15, 15, 15);
    DecelerateAnimation openingAnimation = new DecelerateAnimation(600, 1, Direction.BACKWARDS);
    ClickGuiModule clickGui;
    private float scroll, addY, addX;

    @Override
    public void initGui() {
        this.clickGui = ModuleManager.getInstance().getModule(ClickGuiModule.class);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, super.width - 60, super.height - 25, 55, 20, "Reset Gui"));
        openingAnimation.setDirection(Direction.FORWARDS);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(null);
                ClickGuiModule.clickGuiScreenAtani = null;
                ModuleManager.getInstance().getModule(ClickGuiModule.class).toggle();
                break;
        }
    }

    @Override
    protected void keyTyped(char key, int code) {
        if (code == 1) {
            if (clickGui.openingAnimation.getValue()) {
                this.openingAnimation.setDirection(Direction.BACKWARDS);
            } else {
                mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.x == -1337 || this.y == -1337) {
            this.x = super.width / 2 - this.width / 2;
            this.y = super.height / 2 - this.height / 2;
        }
        if(Mouse.isButtonDown(0) && draggingY != -1337 && draggingX != -1337) {
            x = mouseX - draggingX;
            y = mouseY - draggingY;
        } else if(!Mouse.isButtonDown(0)) {
            draggingY = -1337;
            draggingX = -1337;
        }
        ScaledResolution sr = new ScaledResolution(mc);
        float animationLeftRight = 0;
        float animationUpDown = 0;
        if(clickGui.openingAnimation.getValue()) {
            switch (clickGui.nonDropdownAnimation.getValue()) {
                case "Left to Right":
                    animationLeftRight = (float) ((width + x) * (1 - openingAnimation.getOutput()));
                    if(openingAnimation.getDirection() == Direction.FORWARDS) {
                        animationLeftRight = (float) -((width + x) * (1 - openingAnimation.getOutput()));
                    }
                    break;
                case "Right to Left":
                    animationLeftRight = (float) (sr.getScaledWidth() * (1 - openingAnimation.getOutput()));
                    if(openingAnimation.getDirection() == Direction.BACKWARDS) {
                        animationLeftRight = (float) -(sr.getScaledWidth() * (1 - openingAnimation.getOutput()));
                    }
                    break;
                case "Up to Down":
                    animationUpDown = (float) ((height + y) * (1 - openingAnimation.getOutput()));
                    if(openingAnimation.getDirection() == Direction.FORWARDS) {
                        animationUpDown = (float) -((height + y) * (1 - openingAnimation.getOutput()));
                    }
                    break;
                case "Down to Up":
                    animationUpDown = (float) (sr.getScaledHeight() * (1 - openingAnimation.getOutput()));
                    if(openingAnimation.getDirection() == Direction.BACKWARDS) {
                        animationUpDown = (float) -(sr.getScaledHeight() * (1 - openingAnimation.getOutput()));
                    }
                    break;
            }
            addX = animationLeftRight;
            addY = animationUpDown;
        }
        float beforeX = x, beforeY = y;
        x += addX;
        y += addY;
        scroll += Mouse.getDWheel() / 10f;
        scroll = Math.min(0, scroll);
        RenderUtil.drawRect(x, y, width, height, new Color(0, 58, 105).brighter().getRGB());
        GradientShader.drawGradientTB(x + 2, y + 2, width - 4, 30, 1, new Color(0, 48, 95).brighter().brighter(), new Color(0, 48, 95).brighter());
        RenderUtil.drawRect(x + 2, y + 32, width - 4, height - 34, new Color(0, 48, 95).brighter().getRGB());
        RenderUtil.drawRect(x + 2, y + 2 + 30 + 2, width - 4, height - (2 + 30 + 2) - 2, new Color(240, 240, 240).getRGB());
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI", 21);
        fontRenderer.drawStringWithShadow(CLIENT_NAME + " Client", x + 10, y + 2 + 30 / 2 - fontRenderer.FONT_HEIGHT, -1);
        fontRenderer.drawStringWithShadow("v" + CLIENT_VERSION, x + 10, y + 2 + 30 / 2 + 2, -1);
        fontRenderer.drawStringWithShadow(mc.isSingleplayer() ? "SinglePlayer" : mc.getCurrentServerData().serverIP, x + width - 10 - fontRenderer.getStringWidthInt(mc.isSingleplayer() ? "SinglePlayer" : mc.getCurrentServerData().serverIP), y + 2 + 30 / 2 - fontRenderer.FONT_HEIGHT, -1);
        GradientShader.drawGradientTB(x + 2, y + 2 + 30 + 2, width - 4, 20, 1, new Color(50, 50, 50), new Color(10, 10, 10));
        float spaceWidth = width - 4;
        float categoryWidth = spaceWidth / ModuleCategory.values().length;
        float categoryX = x + 2;
        for(ModuleCategory category : ModuleCategory.values()) {
            fontRendererSmall.drawTotalCenteredStringWithShadow((selectedCategory == category ? ChatFormatting.BOLD.toString() : "") + category.getName().replace("Movement", "Move"), categoryX + categoryWidth / 2, y + 30 + 4 + 20 / 2, -1);
            if(RenderUtil.isHovered(mouseX, mouseY, categoryX, y + 30 + 4, categoryWidth, 20))
                RenderUtil.drawRect(categoryX, y + 30 + 4, categoryWidth, 20, new Color(0, 0, 0, 40).getRGB());
            categoryX += categoryWidth;
        }
        RenderUtil.startScissorBox();
        RenderUtil.drawScissorBox(x + 2, y + 2 + 30 + 20.5f + 2, width - 4, height - (2 + 30 + 2) - 20.5f - 2);
        if(selectedCategory != null) {
            float third = spaceWidth / 3;
            int counter = 0;
            float firstY = y + 30 + 4 + 20 + 10 + 10 + scroll, secondY = y + 30 + 4 + 20 + 10 + 10 + scroll, thirdY = y + 30 + 4 + 20 + 10 + 10 + scroll;
            float modX = x + 2 + 10;
            float modWidth = third - 13.5f;
            for(Module module : ModuleManager.getInstance().getModules(selectedCategory)) {
                if(module.shouldHide())
                    continue;
                float modY = 0;
                switch (counter) {
                    case 0:
                        modY = firstY;
                        break;
                    case 1:
                        modY = secondY;
                        break;
                    case 2:
                        modY = thirdY;
                        break;
                }
                fontRendererSmaller.drawString(ChatFormatting.BOLD + module.getName(), modX + 4, modY - fontRendererSmaller.FONT_HEIGHT - 4, textColor.getRGB());
                float valY = modY + 10;
                // Getting Y
                // Enabled field
                valY += fontRendererSmall.FONT_HEIGHT + 2;
                // Values
                for(Value value : ValueManager.getInstance().getValues(module)) {
                    if(!value.isVisible())
                        continue;
                    if(value instanceof BooleanValue) {
                        valY += fontRendererSmall.FONT_HEIGHT + 2 ;
                    } else if(value instanceof NumberValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2) * 2;
                    } else if(value instanceof ModeValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2);
                        if(this.expanded.contains(value)) {
                            ModeValue modeValue = (ModeValue) value;
                            for(String s : modeValue.getValues()) {
                                valY += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    } else if(value instanceof MultiBooleanValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2);
                        if(this.expanded.contains(value)) {
                            MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                            for(String s : multiBooleanValue.getValues()) {
                                valY += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    }
                }
                // Rect
                float height = (valY + 8) - modY;
                RenderUtil.drawBorderedRect(modX, modY, modX + modWidth, modY + height, 1f,-1, new Color(200, 200, 200).getRGB(), true);
                // Rendering
                float valY2 = modY + 10;
                fontRendererSmaller.drawString("Enabled: ", modX + 10, valY2, textColor.getRGB());
                GradientShader.drawGradientTB(modX + 10 + fontRendererSmaller.getStringWidthInt("Enabled: "), valY2 - 1.5f, 9, 9, 1, new Color(0, 48, 95).brighter().brighter(), new Color(0, 48, 95).brighter());
                if(!module.isEnabled()) {
                    RenderUtil.drawRect(modX + 10 + fontRendererSmaller.getStringWidthInt("Enabled: ") + 1, valY2 - 1.5f + 1, 9 - 2, 9 - 2, -1);
                }
                valY2 += fontRendererSmall.FONT_HEIGHT + 2 ;
                for(Value value : ValueManager.getInstance().getValues(module)) {
                    if(!value.isVisible())
                        continue;
                    if(value instanceof BooleanValue) {
                        fontRendererSmaller.drawString(value.getName() + ": ", modX + 10, valY2, textColor.getRGB());
                        GradientShader.drawGradientTB(modX + 10 + fontRendererSmaller.getStringWidthInt(value.getName() + ": "), valY2 - 1.5f, 9, 9, 1, new Color(0, 48, 95).brighter().brighter(), new Color(0, 48, 95).brighter());
                        if(!((BooleanValue) value).getValue()) {
                            RenderUtil.drawRect(modX + 10 + fontRendererSmaller.getStringWidthInt(value.getName() + ": ") + 1, valY2 - 1.5f + 1, 9 - 2, 9 - 2, -1);
                        }
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                    } else if(value instanceof NumberValue) {
                        NumberValue numberValue = (NumberValue) value;
                        fontRendererSmaller.drawString(value.getName() + ": " + ((NumberValue)value).getValue().floatValue(), modX + 10, valY2, textColor.getRGB());
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                        float sliderWidth = modWidth - 20, sliderX = modX + 10, sliderY = valY2 - 1.5f;
                        GradientShader.drawGradientTB(sliderX, sliderY, sliderWidth, 9, 1, new Color(0, 48, 95).brighter().brighter(), new Color(0, 48, 95).brighter());
                        RenderUtil.drawRect(modX + 10 + 1, valY2 - 1.5f + 1, sliderWidth - 2, 9 - 2, -1);
                        float length = MathHelper
                                .floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue())
                                        / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderWidth);
                        GradientShader.drawGradientTB(sliderX, sliderY, length, 9, 1, new Color(0, 48, 95).brighter().brighter(), new Color(0, 48, 95).brighter());
                        if(Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11)) {
                            double min1 = numberValue.getMinimum().floatValue();
                            double max1 = numberValue.getMaximum().floatValue();
                            double newValue = MathUtil.round((mouseX - sliderX) * (max1 - min1) / (sliderWidth - 1.0f) + min1, numberValue.getDecimals());
                            numberValue.setValue(newValue);
                        }
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                    } else if(value instanceof ModeValue) {
                        fontRendererSmaller.drawString(value.getName() + ": " + value.getValue(), modX + 10, valY2, textColor.getRGB());
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                        if(this.expanded.contains(value)) {
                            ModeValue modeValue = (ModeValue) value;
                            for(String s : modeValue.getValues()) {
                                fontRendererSmaller.drawString(" - " + s, modX + 10, valY2, textColor.getRGB());
                                valY2 += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    } else if(value instanceof MultiBooleanValue) {
                        MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                        fontRendererSmaller.drawString(value.getName() + ": " + (multiBooleanValue.getValue().size() - 1) + " Enabled", modX + 10, valY2, textColor.getRGB());
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                        if(this.expanded.contains(value)) {
                            for(String s : multiBooleanValue.getValues()) {
                                fontRendererSmaller.drawString(" - " + (multiBooleanValue.get(s) ? ChatFormatting.BOLD : "") + s, modX + 10, valY2, textColor.getRGB());
                                valY2 += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    }
                }
                counter++;
                switch (counter - 1) {
                    case 0:
                        firstY += height + 20;
                        break;
                    case 1:
                        secondY += height + 20;
                        break;
                    case 2:
                        thirdY += height + 20;
                        break;
                }
                if(counter != 0 && counter % 3 == 0) {
                    modX = x + 2 + 10;
                    counter = 0;
                } else {
                    modX += modWidth + 10;
                }
            }
        }
        RenderUtil.endScissorBox();
        x = beforeX;
        y = beforeY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (clickGui.openingAnimation.getValue()) {
            if (this.openingAnimation.finished(Direction.BACKWARDS))
                mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        float beforeX = x, beforeY = y;
        x += addX;
        y += addY;
        if (RenderUtil.isHovered(mouseX, mouseY, x + 2, y + 2, width - 4, 30)) {
            draggingX = mouseX - x;
            draggingY = mouseY - y;
        }
        float spaceWidth = width - 4;
        float categoryWidth = spaceWidth / ModuleCategory.values().length;
        float categoryX = x + 2;
        for(ModuleCategory category : ModuleCategory.values()) {
            if(RenderUtil.isHovered(mouseX, mouseY, categoryX, y + 30 + 4, categoryWidth, 20))
                selectedCategory = category;
            categoryX += categoryWidth;
        }
        if(!RenderUtil.isHovered(mouseX, mouseY, x + 2, y + 2 + 30 + 20.5f + 2, width - 4, height - (2 + 30 + 2) - 20.5f - 2)) {
            x = beforeX;
            y = beforeY;
            return;
        }
        if(selectedCategory != null) {
            float third = spaceWidth / 3;
            int counter = 0;
            float firstY = y + 30 + 4 + 20 + 10 + 10 + scroll, secondY = y + 30 + 4 + 20 + 10 + 10 + scroll, thirdY = y + 30 + 4 + 20 + 10 + 10 + scroll;
            float modX = x + 2 + 10;
            float modWidth = third - 13.5f;
            for(Module module : ModuleManager.getInstance().getModules(selectedCategory)) {
                if(module.shouldHide())
                    continue;
                float modY = 0;
                switch (counter) {
                    case 0:
                        modY = firstY;
                        break;
                    case 1:
                        modY = secondY;
                        break;
                    case 2:
                        modY = thirdY;
                        break;
                }
                float valY = modY + 10;
                // Getting Y
                // Enabled field
                valY += fontRendererSmall.FONT_HEIGHT + 2 ;
                // Values
                for(Value value : ValueManager.getInstance().getValues(module)) {
                    if(!value.isVisible())
                        continue;
                    if(value instanceof BooleanValue) {
                        valY += fontRendererSmall.FONT_HEIGHT + 2 ;
                    } else if(value instanceof NumberValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2) * 2;
                    } else if(value instanceof ModeValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2);
                        if(this.expanded.contains(value)) {
                            ModeValue modeValue = (ModeValue) value;
                            for(String s : modeValue.getValues()) {
                                valY += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    } else if(value instanceof MultiBooleanValue) {
                        valY += (fontRendererSmall.FONT_HEIGHT + 2);
                        if(this.expanded.contains(value)) {
                            MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                            for(String s : multiBooleanValue.getValues()) {
                                valY += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    }
                }
                // Rect
                float height = (valY + 8) - modY;
                // Rendering
                float valY2 = modY + 10;
                if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11))
                    module.setEnabled(!module.isEnabled());
                valY2 += fontRendererSmall.FONT_HEIGHT + 2 ;
                for(Value value : ValueManager.getInstance().getValues(module)) {
                    if(!value.isVisible())
                        continue;
                    if(value instanceof BooleanValue) {
                        if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11))
                            value.setValue(!((BooleanValue)value).getValue());
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                    } else if(value instanceof NumberValue) {

                        valY2 += (fontRendererSmall.FONT_HEIGHT + 2) * 2;
                    } else if(value instanceof ModeValue) {
                        if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11)) {
                             if(this.expanded.contains(value))
                                 this.expanded.remove(value);
                             else {
                                 this.expanded.add(value);
                             }
                        }
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                        if(this.expanded.contains(value)) {
                            ModeValue modeValue = (ModeValue) value;
                            for(String s : modeValue.getValues()) {
                                if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11)) {
                                    modeValue.setValue(s);
                                }
                                valY2 += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                        }
                    } else if(value instanceof MultiBooleanValue) {
                        if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11)) {
                            if(this.expanded.contains(value))
                                this.expanded.remove(value);
                            else {
                                this.expanded.add(value);
                            }
                        }
                        valY2 += fontRendererSmall.FONT_HEIGHT + 2;
                        if(this.expanded.contains(value)) {
                            MultiBooleanValue multiBooleanValue = (MultiBooleanValue) value;
                            String toToggle = null;
                            for(String s : multiBooleanValue.getValues()) {
                                if(RenderUtil.isHovered(mouseX, mouseY, modX, valY2 - 2.5f, modWidth, 11)) {
                                    toToggle = s;
                                }
                                valY2 += (fontRendererSmall.FONT_HEIGHT + 2);
                            }
                            if(toToggle != null)
                                multiBooleanValue.toggle(toToggle);
                        }
                    }
                }
                counter++;
                switch (counter - 1) {
                    case 0:
                        firstY += height + 20;
                        break;
                    case 1:
                        secondY += height + 20;
                        break;
                    case 2:
                        thirdY += height + 20;
                        break;
                }
                if(counter != 0 && counter % 3 == 0) {
                    modX = x + 2 + 10;
                    counter = 0;
                } else {
                    modX += modWidth + 10;
                }
            }
        }
        x = beforeX;
        y = beforeY;
        super.mouseClicked(mouseX, mouseY, button);
    }

}

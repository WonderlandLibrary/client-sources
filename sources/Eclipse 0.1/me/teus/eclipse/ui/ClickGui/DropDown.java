package me.teus.eclipse.ui.ClickGui;

import java.awt.*;

import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.Value;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.ui.ClickGui.Content.Value.*;
import me.teus.eclipse.ui.ClickGui.Content.*;
import me.teus.eclipse.utils.TimerUtil;
import me.teus.eclipse.utils.managers.ModuleManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class DropDown extends GuiScreen {

    public final int categoryOffset = 20;
    public final int moduleOffset = 18;
    public final int valueOffset = 14;
    public final int valueShadowOffset = 0;

    public int x, y;
    public boolean drag;
    private boolean holdingMouseButton;

    private Module keybindFocusedModule;

    private TimerUtil timer;

    public DropDown() {
        timer = new TimerUtil();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Category c : Category.values()) {

            int startX = c.getPosX();
            int startY = c.getPosY();

            int endX = startX + 100;
            int endY = startY + categoryOffset;

            DrawCategory.drawCategory(c, startX, startY, endX, endY);

            int currentOffsetY = startY + categoryOffset;

            boolean firstModule = true;

            if(c.isCatOpen()) {
                for(Module m : ModuleManager.modules) {
                    if(m.getCategory() == c) {

                        int moduleStartX = startX;
                        int moduleEndX = endX;

                        int moduleStartY = currentOffsetY;
                        int moduleEndY = currentOffsetY + moduleOffset;

                        DrawModule.drawModule(m, moduleStartX, moduleStartY, moduleEndX, moduleEndY);

                        if(firstModule && m.toggled) {
                            this.drawGradientRect(moduleStartX, moduleStartY, moduleEndX, moduleStartY + 3, 0x60000000, 0x05000000);
                        }

                        currentOffsetY += moduleOffset;

                        if(m.getValues() != null && m.isShowSet()) {
                            int valueIndex = 0;

                            for(Value s : m.getValues()) {
                                if(!holdingMouseButton) {
                                    s.setHoldingMouseButton(false);
                                }

                                if(s.shown()) {
                                    int valueStartX = startX + 1;
                                    int valueEndX = endX - 1;

                                    int valueStartY = currentOffsetY;
                                    int valueEndY = currentOffsetY + valueOffset;

                                    if(valueIndex == 0) {
                                        Gui.drawRect(valueStartX, valueStartY, valueEndX, valueStartY + valueShadowOffset, new Color(50, 50, 50).getRGB());
                                        currentOffsetY += valueShadowOffset;

                                        valueStartY = currentOffsetY;
                                        valueEndY = currentOffsetY + valueOffset;
                                    }

                                    if(s instanceof BooleanValue) {
                                        BooleanValue b = (BooleanValue) s;
                                        DrawBooleanValue.drawBooleanValue(b, valueStartX, valueStartY, valueEndX, valueEndY);
                                    } else if(s instanceof ModeValue) {
                                        ModeValue mode = (ModeValue) s;
                                        DrawModeValue.drawModeValue(mode, valueStartX, valueStartY, valueEndX, valueEndY);
                                    } else if(s instanceof NumberValue) {
                                        NumberValue n = (NumberValue) s;
                                        DrawNumberValue.drawNumberValue(n, valueStartX, valueStartY, valueEndX, valueEndY, mouseX, mouseY);
                                    }

                                    currentOffsetY += valueOffset;

                                    valueIndex++;
                                } else {
                                    s.setHoldingMouseButton(false);
                                }
                            }
                        }
                        firstModule = false;
                    }
                }
            }
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        holdingMouseButton = true;

        for(Category c : Category.values()) {

            int startX = c.getPosX();
            int startY = c.getPosY();

            int endX = startX + 100;
            int endY = startY + categoryOffset;

            if(mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
                DrawCategory.onCategoryClicked(c, mouseX, mouseY, button);
            }

            int currentOffsetY = startY + categoryOffset;

            if(c.isCatOpen()) {
                for(Module m : ModuleManager.modules) {
                    if(m.getCategory() == c) {
                        int moduleStartX = startX;
                        int moduleEndX = startX + 100;

                        int moduleStartY = currentOffsetY;
                        int moduleEndY = currentOffsetY + moduleOffset;

                        if(mouseX >= moduleStartX && mouseX <= moduleEndX && mouseY >= moduleStartY && mouseY <= moduleEndY) {
                            DrawModule.onModuleClicked(m, mouseX, mouseY, button);
                        }

                        currentOffsetY += moduleOffset;

                        if(m.getValues() != null && m.isShowSet()) {
                            int count = 0;

                            for(Value v : m.getValues()) {
                                if(v.shown()) {
                                    int valueStartX = startX + 1;
                                    int valueEndX = endX - 1;

                                    int valueStartY = currentOffsetY;
                                    int valueEndY = currentOffsetY + valueOffset;

                                    if(count == 0) {
                                        currentOffsetY += valueShadowOffset;

                                        valueStartY = currentOffsetY;
                                        valueEndY = currentOffsetY + valueOffset;
                                    }

                                    if(mouseX >= valueStartX && mouseX <= valueEndX && mouseY >= valueStartY && mouseY <= valueEndY) {
                                        v.setHoldingMouseButton(true);
                                        if(v instanceof BooleanValue) {
                                            BooleanValue b = (BooleanValue) v;
                                            DrawBooleanValue.onBooleanClicked(b);
                                        } else if(v instanceof ModeValue) {
                                            ModeValue mode = (ModeValue) v;
                                            DrawModeValue.onModeClicked(mode, button);
                                        }
                                    }

                                    currentOffsetY += valueOffset;

                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onGuiClosed() {
        onClickGuiDisabled();
    }

    private void onClickGuiDisabled() {
        keybindFocusedModule = null;
        holdingMouseButton = false;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(state == 0 && drag == true) drag = false;

        holdingMouseButton = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
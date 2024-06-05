package koks.gui.clickgui;

import koks.Koks;
import koks.gui.clickgui.elements.*;
import koks.modules.Module;
import koks.modules.impl.combat.KillAura;
import koks.utilities.ColorUtil;
import koks.utilities.RenderUtils;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:41
 */
public class ModuleButton {

    public Module module;
    public float x, y, width, height;
    public RenderUtils renderUtils = new RenderUtils();
    public float yMaxElements;

    public final List<Element> elementList = new ArrayList<>();

    public boolean extended;

    public ModuleButton(Module module) {
        this.module = module;
        elementList.add(new ElementToggle(module));
        elementList.add(new ElementVisible(module));
        elementList.add(new ElementKeyBind(module));
        Koks.getKoks().valueManager.getValues().forEach(value -> {
            if (value.getModule().equals(module)) {
                if (value instanceof BooleanValue) {
                    this.elementList.add(new ElementBoolean((BooleanValue) value));
                }
                if (value instanceof NumberValue) {
                    if (((NumberValue) value).getMinDefaultValue() == null) {
                        this.elementList.add(new ElementSlider((NumberValue) value));
                    } else {
                        this.elementList.add(new ElementSliderBetween((NumberValue) value));
                    }
                }
                if (value instanceof ModeValue) {
                    if (((ModeValue) value).getObjects() == null) {
                        this.elementList.add(new ElementMode((ModeValue) value));
                    } else {
                        this.elementList.add(new ElementModeCheckBox((ModeValue) value));
                    }
                }
                if (value instanceof TitleValue) {
                    this.elementList.add(new ElementTitle((TitleValue) value));
                }
            }
        });
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (extended) {

            int[] yHeight = {0};
            this.elementList.forEach(element -> {
                if (element.getValue() != null) {

                    if (element.getValue().isVisible()) {
                            element.setPosition(x + 3, this.y + height + yHeight[0], width - 6, height - 2);
                            element.drawScreen(mouseX, mouseY);

                            if (element instanceof ElementMode) {
                                Arrays.stream(((ElementMode) element).modeValue.getModes()).forEach(module -> {
                                    if (element.isExtended())
                                        yHeight[0] += height - 2;
                                });
                            }

                            if (element instanceof ElementModeCheckBox) {
                                Arrays.stream(((ElementModeCheckBox) element).modeValue.getObjects()).forEach(module -> {
                                    if (element.isExtended())
                                        if (module.isVisible())
                                            yHeight[0] += height - 2;
                                });
                            }

                            yHeight[0] += height;
                            yMaxElements = yHeight[0];
                        }
                } else {
                    element.setPosition(x + 3, this.y + height + yHeight[0], width - 6, height - 2);
                    element.drawScreen(mouseX, mouseY);

                    yHeight[0] += height;
                    yMaxElements = yHeight[0];
                }
            });
        } else {
            yMaxElements = 0F;
        }
        ColorUtil colorUtil = new ColorUtil();
        renderUtils.drawOutlineRect(x, y, x + width, y + height + yMaxElements, 1, this.module.isBypassed() ? Koks.getKoks().client_color.darker() : new Color(40, 39, 42, 255));
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(module.getModuleName(), x + 3F, y - 2, this.module.isToggled() ? -1 : Color.gray.getRGB());
    }

    public void mouseReleased() {
        this.elementList.forEach(Element::mouseReleased);
    }

    public void keyTyped(int keyCode) {
        elementList.forEach(element -> element.keyTyped(keyCode));
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                module.setBypassed(!module.isBypassed());
            } else {
                this.extended = !this.extended;
            }
        }
        this.elementList.forEach(element -> {
            if (this.extended) element.mouseClicked(mouseX, mouseY, mouseButton);
        });
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public void setRenderUtils(RenderUtils renderUtils) {
        this.renderUtils = renderUtils;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setInformation(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}

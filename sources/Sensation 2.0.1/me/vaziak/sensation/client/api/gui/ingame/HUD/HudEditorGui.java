package me.vaziak.sensation.client.api.gui.ingame.HUD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.Range;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.HUD.components.*;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.gui.ingame.clickui.utility.ExpandAnimation;
import me.vaziak.sensation.client.api.gui.menu.components.Component;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.math.TimerUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author antja03
 */
public class HudEditorGui extends GuiScreen {

    public boolean dragging;
    public Element elementBeingDragged;

    public Element elementBeingEdited;
    public Element nextElementEdited;

    public double actualPositionX, actualPositionY;
    public double prevFrameMouseX, prevFrameMouseY;

    private final ArrayList<Component> components = new ArrayList<>();

    private ExpandAnimation expandAnimation = new ExpandAnimation(0, 0);
    private double alphaModifer = 0.0f;
    private TimerUtil alphaStopwatch = new TimerUtil();

    private boolean closing;
    private boolean fullClosing;

    @Override
    public void initGui() {
        if (elementBeingEdited != null) {
            refreshComponents();
            closing = false;
        }
        fullClosing = false;

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        if (dragging) {
            final double differenceX = mouseX - this.prevFrameMouseX;
            final double differenceY = mouseY - this.prevFrameMouseY;
            actualPositionX += differenceX;
            actualPositionY += differenceY;

            if (actualPositionX > 0 && actualPositionX < resolution.getScaledWidth()) {
                double midPoint = 0;
                if (elementBeingDragged.quadrant.equals(Quadrant.TOP_LEFT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_LEFT)) {
                    midPoint = actualPositionX + elementBeingDragged.width / 2;
                } else if (elementBeingDragged.quadrant.equals(Quadrant.TOP_RIGHT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                    midPoint = resolution.getScaledWidth() - actualPositionX + elementBeingDragged.width / 2;
                }

                if (Range.between(resolution.getScaledWidth() / 2 - 6, resolution.getScaledWidth() / 2 + 6).contains((int) Math.round(midPoint))) {
                    elementBeingDragged.positionX = resolution.getScaledWidth() / 2 - (elementBeingDragged.width / 2);
                } else {
                    if (elementBeingDragged.quadrant.equals(Quadrant.TOP_LEFT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_LEFT)) {
                        elementBeingDragged.positionX = actualPositionX;
                    } else if (elementBeingDragged.quadrant.equals(Quadrant.TOP_RIGHT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                        elementBeingDragged.positionX = resolution.getScaledWidth() - actualPositionX;
                    }
                }
            }
            if (actualPositionY > 0 && actualPositionY < resolution.getScaledHeight()) {
                if (Range.between(resolution.getScaledHeight() / 2 - 6, resolution.getScaledHeight() / 2 + 6).contains((int) Math.round(actualPositionY + elementBeingDragged.height / 2))) {
                    elementBeingDragged.positionY = resolution.getScaledHeight() / 2 - (elementBeingDragged.height / 2);
                } else {
                    elementBeingDragged.positionY = actualPositionY;
                }
            }
        }

        Sensation.instance.hud.getElementRegistry().values().forEach(module -> {
            double x1 = 0;
            double x2 = 0;
            if (module.quadrant.equals(Quadrant.TOP_LEFT) || module.quadrant.equals(Quadrant.BOTTOM_LEFT)) {
                x1 = module.editX;
                x2 = module.editX + module.width;
            } else if (module.quadrant.equals(Quadrant.TOP_RIGHT) || module.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                x2 = resolution.getScaledWidth() - module.editX;
                x1 = resolution.getScaledWidth() - module.editX - module.width;
            }

            double y1 = 0;
            double y2 = 0;
            if (module.quadrant.equals(Quadrant.TOP_LEFT) || module.quadrant.equals(Quadrant.TOP_MID) || module.quadrant.equals(Quadrant.TOP_RIGHT)) {
                y1 = module.editY;
                y2 = module.editY + module.height;
            } else if (module.quadrant.equals(Quadrant.BOTTOM_LEFT) || module.quadrant.equals(Quadrant.BOTTOM_MID) || module.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                y1 = resolution.getScaledHeight() - module.editY;
                y2 = resolution.getScaledHeight() - module.editY - module.height;
            }

            Draw.drawRectangle(x1, y1, x2, y2,ColorCreator.create(0, 0, 0, fullClosing ? (int) (120 * alphaModifer) : 120));

            if (!isFullClosing()) {
                module.drawElement(true);
            }
        });

        if (elementBeingEdited != null) {
            if (!closing) {
                expandAnimation.expand((float) width * 2, (float) height * 4, 0.04385f, 0.06385f);
                if (alphaStopwatch.hasPassed(20)) {
                    alphaModifer += 0.1;
                    if (alphaModifer > 1) {
                        alphaModifer = 1;
                    }
                    alphaStopwatch.reset();
                }
            } else {
                expandAnimation.expand(0f, 0f, 0.2685f, 0.2385f);
                if (alphaStopwatch.hasPassed(20)) {
                    alphaModifer -= 0.1;

                    if (alphaModifer <= 0.1) {
                        elementBeingEdited = nextElementEdited;
                        nextElementEdited = null;
                        if (elementBeingEdited != null) {
                            refreshComponents();
                            closing = false;
                            return;
                        }

                    }

                    if (alphaModifer <= 0) {
                        alphaModifer = 0;
                    }
                    alphaStopwatch.reset();
                }
            }
            int height = components.size() * 25 + 4;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            double scissorX = resolution.getScaledWidth() / 2 - 55 + 110 / 2 - expandAnimation.getX();
            double scissorY = (resolution.getScaledHeight() - (resolution.getScaledHeight() / 2 - height / 2 - 10 + height + 10)) + height / 2 + 10 / 2 - (expandAnimation.getY() / 2);

            GL11.glScissor(
                    (int) (scissorX) * resolution.getScaleFactor(),
                    (int) (scissorY) * resolution.getScaleFactor(),
                    (int) (expandAnimation.getX() * 2) * resolution.getScaleFactor(),
                    (int) (expandAnimation.getY()) * resolution.getScaleFactor());

            Draw.drawBorderedRectangle(resolution.getScaledWidth() / 2 - 55, resolution.getScaledHeight() / 2 - height / 2 - 5,
                    resolution.getScaledWidth() / 2 + 55, resolution.getScaledHeight() / 2 + height / 2,
                    0.5, ColorCreator.create(15, 15, 15, (int) (255 * alphaModifer)), ColorCreator.create(255, 107, 200, (int) (100 * alphaModifer)), true);

            for (int i = components.size() - 1; i >= 0; i--) {
                components.get(i).drawComponent(mouseX, mouseY);
            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }

        prevFrameMouseX = mouseX;
        prevFrameMouseY = mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        Sensation.instance.hud.getElementRegistry().values().forEach(element -> {
            double x1 = 0;
            double x2 = 0;
            if (element.quadrant.equals(Quadrant.TOP_LEFT) || element.quadrant.equals(Quadrant.BOTTOM_LEFT)) {
                x1 = element.positionX - 1;
                x2 = element.positionX - 1 + element.width;
            } else if (element.quadrant.equals(Quadrant.TOP_RIGHT) || element.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                x2 = resolution.getScaledWidth() - element.positionX + 1;
                x1 = resolution.getScaledWidth() - element.positionX + 1 - element.width;
            }

            double y1 = 0;
            double y2 = 0;
            if (element.quadrant.equals(Quadrant.TOP_LEFT) || element.quadrant.equals(Quadrant.TOP_MID) || element.quadrant.equals(Quadrant.TOP_RIGHT)) {
                y1 = element.positionY - 1;
                y2 = element.positionY - 1 + element.height;
            } else if (element.quadrant.equals(Quadrant.BOTTOM_LEFT) || element.quadrant.equals(Quadrant.BOTTOM_MID) || element.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                y1 = resolution.getScaledHeight() - element.positionY + 1;
                y2 = resolution.getScaledHeight() - element.positionY + 1 - element.height;
            }

            if (mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
                if (mouseButton == 0) {
                    dragging = true;
                    elementBeingDragged = element;

                    if (elementBeingDragged.quadrant.equals(Quadrant.TOP_LEFT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_LEFT)) {
                        actualPositionX = elementBeingDragged.editX;
                    } else if (elementBeingDragged.quadrant.equals(Quadrant.TOP_RIGHT) || elementBeingDragged.quadrant.equals(Quadrant.BOTTOM_RIGHT)) {
                        actualPositionX = resolution.getScaledWidth() - elementBeingDragged.editX + 1;
                    }

                    actualPositionY = elementBeingDragged.editY;
                } else if (mouseButton == 1) {
                    if (elementBeingEdited != element) {
                        setElementBeingEdited(element);
                    } else {
                        setElementBeingEdited(null);
                    }
                }
            }
        });

        for (int i = components.size() - 1; i >= 0; i--) {
            if (components.get(i).mouseClicked(mouseX, mouseY, mouseButton))
                break;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        elementBeingDragged = null;

        for (int i = components.size() - 1; i >= 0; i--) {
            components.get(i).mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            if (wheel > 1) {
                wheel = -1;
            }
            if (wheel < -1) {
                wheel = 1;
            }
        }
        super.handleMouseInput();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Sensation.instance.cheatManager.getCheatRegistry().get("Hud Editor").getBind() || keyCode == Keyboard.KEY_ESCAPE) {
            closing = true;
            fullClosing = true;
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void setElementBeingEdited(Element element) {
        if (elementBeingEdited == null) {
            elementBeingEdited = element;
            closing = false;
            refreshComponents();
        } else {
            nextElementEdited = element;
            closing = true;
        }
    }

    public void refreshComponents() {
        components.clear();

        if (elementBeingEdited == null)
            return;

        ArrayList<Value> activeValues = new ArrayList<>();
        for (Value value : elementBeingEdited.getValueRegistry().values()) {
            if (value.checkDependency())
                activeValues.add(value);
        }

        int height = activeValues.size() * 25;
        int y = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2 - height / 2;

        for (Value value : elementBeingEdited.getValueRegistry().values()) {
            if (!value.checkDependency())
                continue;

            if (value instanceof BooleanProperty) {
                components.add(new CheckboxComponent(value, this.width / 2, y));
            }
            if (value instanceof StringsProperty) {
                components.add(new ComboBoxComponent(value, this.width / 2, y));
            }
            if (value instanceof DoubleProperty) {
                components.add(new SliderComponent(value, this.width / 2, y));
            }
            if (value instanceof ColorProperty) {
                components.add(new ColorPicker(value, this.width / 2, y));
            }

            y += 25;
        }
    }

    public ArrayList<Component> getActiveComponents() {
        ArrayList<Component> activeComponents = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof ValueComponent) {
                ValueComponent valueComponent = (ValueComponent) component;
                if (valueComponent.getValue().checkDependency())
                    activeComponents.add(component);
            } else {
                activeComponents.add(component);

            }
        }
        return activeComponents;
    }

    public double getAlphaModifer() {
        return alphaModifer;
    }

    public boolean isClosing() {
        return closing;
    }

    public boolean isFullClosing() {
        return fullClosing;
    }
}

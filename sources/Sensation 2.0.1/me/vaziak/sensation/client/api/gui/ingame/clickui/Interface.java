package me.vaziak.sensation.client.api.gui.ingame.clickui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.Component;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.main.MainButtonTab;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.Tab;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.TabConfiguration;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.cheat.TabDefaultCheat;
import me.vaziak.sensation.client.api.gui.ingame.clickui.utility.ExpandAnimation;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.TimerUtil;

import java.util.ArrayList;

/**
 * @author antja03
 */
public class Interface {
    private Screen screen;

    private double positionX, positionY, width = 300, height = 150;

    private double currentFrameMouseX, currentFrameMouseY, prevFrameMouseX, prevFrameMouseY;

    private boolean moving = false;

    private boolean closing = false;

    private final ArrayList<Component> components = new ArrayList<>();

    private final ArrayList<Tab> tabs = new ArrayList<>();

    private Tab currentTab;

    private ExpandAnimation expandAnimation = new ExpandAnimation(0, 0);

    private double alphaModifer;

    private TimerUtil alphaStopwatch;

    private TimerUtil tickStopwatch = new TimerUtil();

    public Interface(Screen screen) {
        this.screen = screen;
        positionX = (screen.getResolution().getScaledWidth() >> 1) - this.width / 2;
        positionY = (screen.getResolution().getScaledHeight() >> 1) - this.height / 2;
        alphaModifer = 0.0;
        alphaStopwatch = new TimerUtil();

        tabs.add(new TabDefaultCheat(this, Category.COMBAT));
        tabs.add(new TabDefaultCheat(this, Category.MOVEMENT));
        tabs.add(new TabDefaultCheat(this, Category.VISUAL));
        tabs.add(new TabDefaultCheat(this, Category.PLAYER));
        tabs.add(new TabDefaultCheat(this, Category.MISC));
        tabs.add(new TabConfiguration(this));

        double inc = (height - 32) / 7;
        components.add(new MainButtonTab(this, tabs.get(0), new ResourceLocation("client/gui/icon/interface/combat_icon.png"), 4, 32 + (inc * 0),
                20, inc, button -> currentTab = tabs.get(0)));
        components.add(new MainButtonTab(this, tabs.get(1), new ResourceLocation("client/gui/icon/interface/movement_icon.png"), 4, 32 + (inc * 1),
                20, inc, button -> currentTab = tabs.get(1)));
        components.add(new MainButtonTab(this, tabs.get(2), new ResourceLocation("client/gui/icon/interface/visuals_icon.png"), 4, 32 + (inc * 2),
                20, inc, button -> currentTab = tabs.get(2)));
        components.add(new MainButtonTab(this, tabs.get(3), new ResourceLocation("client/gui/icon/interface/player_icon.png"), 4, 32 + (inc * 3),
                20, inc, button -> currentTab = tabs.get(3)));
        components.add(new MainButtonTab(this, tabs.get(4), new ResourceLocation("client/gui/icon/interface/misc_icon.png"), 4, 32 + (inc * 4),
                20, inc, button -> currentTab = tabs.get(4)));
        components.add(new MainButtonTab(this, tabs.get(5), new ResourceLocation("client/gui/icon/interface/configs_icon.png"), 4, 32 + (inc * 5),
                20, inc, button -> currentTab = tabs.get(5)));
    }

    void initializeInterface() {
        moving = false;
        closing = false;
        expandAnimation.setX(0);
        expandAnimation.setY(0);
        tickStopwatch.reset();
    }

    public void drawInterface(double mouseX, double mouseY) {
        this.currentFrameMouseX = mouseX;
        this.currentFrameMouseY = mouseY;
        if (moving) {
            final double differenceX = this.currentFrameMouseX - this.prevFrameMouseX;
            final double differenceY = this.currentFrameMouseY - this.prevFrameMouseY;
            if ((this.positionX + differenceX > 0 || differenceX > 0) &&
                    (this.positionX + width + differenceX < screen.getResolution().getScaledWidth() || differenceX < 0)) {
                this.positionX += differenceX;

            }
            if ((this.positionY + differenceY > 0 || differenceY > 0)
                    && (this.positionY + height + differenceY < screen.getResolution().getScaledHeight() || differenceY < 0)) {
                this.positionY += differenceY;
            }
        }

        if (closing) {
            expandAnimation.expand(0f, 0f, 0.2685f, 0.2385f);
            if (alphaStopwatch.hasPassed(20)) {
                alphaModifer -= 0.1;
                if (alphaModifer <= 0) {
                    alphaModifer = 0;
                }
                alphaStopwatch.reset();
            }
        } else {
            if (alphaStopwatch.hasPassed(20)) {
                alphaModifer += 0.1;
                if (alphaModifer > 1) {
                    alphaModifer = 1;
                }
                alphaStopwatch.reset();
            }
            expandAnimation.expand((float) width * 2, (float) height * 4, 0.04385f, 0.06385f);
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        double scissorX = positionX + width / 2 - expandAnimation.getX();
        double scissorY = (screen.getResolution().getScaledHeight() - (positionY + height + 10)) + height / 2 + 10 / 2 - (expandAnimation.getY() / 2);

        GL11.glScissor(
                (int) (scissorX) * screen.getResolution().getScaleFactor(),
                (int) (scissorY) * screen.getResolution().getScaleFactor(),
                (int) (expandAnimation.getX() * 2) * screen.getResolution().getScaleFactor(),
                (int) (expandAnimation.getY()) * screen.getResolution().getScaleFactor());

        //Base
        {
            Draw.drawRectangle(positionX - 0.5, positionY - 0.5, positionX + width + 0.5, positionY + height + 0.5, getColor(15, 15, 15, 255));
            Draw.drawRectangle(positionX - 1, positionY - 1, positionX + width + 1, positionY + height + 1, getColor(15, 15, 15, 100));
            Draw.drawRectangle(positionX - 1.5, positionY - 1.5, positionX + width + 1.5, positionY + height + 1.5, getColor(15, 15, 15, 50));
        }

        //Side bar + watermark
        {
            Draw.drawRectangle(positionX, positionY, positionX + 24, positionY + height, getColor(25, 25, 25, 60));
            GL11.glColor3f(1f, 1f, 1f);
            Draw.drawImg(new ResourceLocation("client/gui/logo/32x32.png"), positionX + 4, positionY + 4, 16, 16);

            Draw.drawRectangle(positionX, positionY + 24, positionX + 24, positionY + 25, getColor(0, 0, 0, 50));

            Draw.drawRectangle(positionX + 24, positionY, positionX + 25.5, positionY + height, getColor(0, 0, 0, 50));
            Draw.drawRectangle(positionX + 24, positionY, positionX + 25, positionY + height, getColor(0, 0, 0, 50));
            Draw.drawRectangle(positionX + 24, positionY, positionX + 24.5, positionY + height, getColor(0, 0, 0, 50));

            for (Component component : components) {
                if (component != null) {
                    if (component instanceof MainButtonTab) {
                        MainButtonTab tabButton = (MainButtonTab) component;
                        if (tabButton.getTab() == currentTab) {
                            Draw.drawRectangle(positionX + 24, positionY + tabButton.positionY - 2, positionX + 22, positionY + tabButton.positionY + 16, getColor(255, 0, 0));
                        }
                    }
                }
            }
        }


        if (currentTab == null) {
            currentTab = tabs.get(5);
        } else {
            components.forEach(component -> component.drawComponent(positionX + component.positionX, positionY + component.positionY));
            currentTab.components.forEach(component -> component.drawComponent(positionX + component.positionX, positionY + component.positionY));
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        if (tickStopwatch.hasPassed(50)) {
            if (currentTab != null) {
                currentTab.onTick();
            }
            tickStopwatch.reset();
        }

        this.prevFrameMouseX = this.currentFrameMouseX;
        this.prevFrameMouseY = this.currentFrameMouseY;
    }

    void mouseButtonClicked(int button) {
        if (isMouseInBounds(positionX, positionX + 24, positionY, positionY + 24)) {
            currentTab = null;
            return;
        }
        for (Component component : components) {
            if (component.mouseButtonClicked(button)) {
                return;
            }
        }
        if (currentTab != null) {
            for (Component component : currentTab.components) {
                if (component.mouseButtonClicked(button)) {
                    return;
                }
            }
        }
        moving = Minecraft.getMinecraft().theWorld == null ? false : true;
    }

    void mouseButtonReleased(int state) {
        if (moving) {
            moving = false;
            return;
        }

        for (Component component : components) {
            component.mouseReleased();
        }

        if (currentTab != null) {
            for (Component component : currentTab.components) {
                component.mouseReleased();
            }
        }
    }

    void mouseScrolled(int scrollDirection) {
        for (Component component : components) {
            component.mouseScrolled(scrollDirection);
        }

        if (currentTab != null) {
            for (Component component : currentTab.components) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }

    boolean handleMouseInput() {
        for (Component components : components) {
            if (components.handleMouseInput())
                return true;
        }

        if (currentTab != null) {
            for (Component component : currentTab.components) {
                if (component.handleMouseInput())
                    return true;
            }
        }

        return false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        for (Component component : components) {
            if (component.keyTyped(typedChar, keyCode)) {
                return true;
            }
        }

        if (currentTab != null) {
            for (Component component : currentTab.components) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMouseInBounds(double x1, double x2, double y1, double y2) {
        if (x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }

        if (y1 > y2) {
            double temp = y1;
            y1 = y2;
            y2 = temp;
        }

        return currentFrameMouseX > x1 && currentFrameMouseX < x2
                && currentFrameMouseY > y1 && currentFrameMouseY < y2;
    }

    public int getColor(int red, int green, int blue) {
        return ColorCreator.create(red, green, blue, (int) (255 * alphaModifer));
    }

    public int getColor(int red, int green, int blue, int alpha) {
        return ColorCreator.create(red, green, blue, (int) (alpha * alphaModifer));
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getWidth() {
        return width - 24;
    }

    public double getHeight() {
        return height;
    }

    public boolean isClosing() {
        return closing;
    }

    public void setClosing(boolean closing) {
        this.closing = closing;
        if (closing) {
            components.forEach(Component::onGuiClose);
            if (currentTab != null)
                currentTab.components.forEach(Component::onGuiClose);
        }
    }

    public Tab getCurrentTab() {
        return currentTab;
    }

    public void setWidth(double width) {
        this.width = width + 24;
    }

    public double getCurrentFrameMouseX() {
        return currentFrameMouseX;
    }

    public double getCurrentFrameMouseY() {
        return currentFrameMouseY;
    }

    public double getAlphaModifer() {
        return alphaModifer;
    }

    public void setAlphaModifer(double alphaModifer) {
        this.alphaModifer = alphaModifer;
    }

    public void setHeight(int i) {
        this.height = i;
    }
}

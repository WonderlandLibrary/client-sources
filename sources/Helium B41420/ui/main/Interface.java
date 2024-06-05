package rip.helium.ui.main;

import net.minecraft.client.*;
import net.minecraft.util.*;
import rip.helium.cheat.*;
import rip.helium.ui.main.components.*;
import rip.helium.ui.main.components.tab.main.*;
import rip.helium.ui.main.tab.*;
import rip.helium.ui.main.tab.cheat.*;
import rip.helium.ui.main.utility.*;
import rip.helium.utils.*;
import rip.helium.utils.font.*;

import org.lwjgl.opengl.*;

import java.util.*;

public class Interface
{
    private Screen screen;
    private double positionX;
    private double positionY;
    private double width;
    private double height;
    static Minecraft mc;
    private double currentFrameMouseX;
    private double currentFrameMouseY;
    private double prevFrameMouseX;
    private double prevFrameMouseY;
    private boolean moving;
    private boolean closing;
    private final ArrayList<Component> components;
    private final ArrayList<Tab> tabs;
    private Tab currentTab;
    private ExpandAnimation expandAnimation;
    private double alphaModifer;
    private Stopwatch alphaStopwatch;
    private Stopwatch tickStopwatch;
    
    public Interface(final Screen screen) {
        this.width = 300.0;
        this.height = 200.0;
        this.moving = false;
        this.closing = false;
        this.components = new ArrayList<Component>();
        this.tabs = new ArrayList<Tab>();
        this.expandAnimation = new ExpandAnimation(0.0f, 0.0f);
        this.tickStopwatch = new Stopwatch();
        this.screen = screen;
        this.positionX = (screen.getResolution().getScaledWidth() >> 1) - this.width / 2.0;
        this.positionY = (screen.getResolution().getScaledHeight() >> 1) - this.height / 2.0;
        this.alphaModifer = 0.0;
        this.alphaStopwatch = new Stopwatch();
        this.tabs.add(new TabDefaultCheat(this, CheatCategory.COMBAT));
        this.tabs.add(new TabDefaultCheat(this, CheatCategory.MOVEMENT));
        this.tabs.add(new TabDefaultCheat(this, CheatCategory.VISUAL));
        this.tabs.add(new TabDefaultCheat(this, CheatCategory.PLAYER));
        this.tabs.add(new TabDefaultCheat(this, CheatCategory.MISC));
        this.tabs.add(new TabConfiguration(this));
        final double inc = (this.height - 32.0) / 7.0;
        this.components.add(new MainButtonTab(this, this.tabs.get(0), new ResourceLocation("client/gui/icon/interface/combat_icon.png"), 4.0, 32.0 + inc * 0.0, 20.0, inc, button -> this.currentTab = this.tabs.get(0)));
        this.components.add(new MainButtonTab(this, this.tabs.get(1), new ResourceLocation("client/gui/icon/interface/movement_icon.png"), 4.0, 32.0 + inc * 1.0, 20.0, inc, button -> this.currentTab = this.tabs.get(1)));
        this.components.add(new MainButtonTab(this, this.tabs.get(2), new ResourceLocation("client/gui/icon/interface/visuals_icon.png"), 4.0, 32.0 + inc * 2.0, 20.0, inc, button -> this.currentTab = this.tabs.get(2)));
        this.components.add(new MainButtonTab(this, this.tabs.get(3), new ResourceLocation("client/gui/icon/interface/player_icon.png"), 4.0, 32.0 + inc * 3.0, 20.0, inc, button -> this.currentTab = this.tabs.get(3)));
        this.components.add(new MainButtonTab(this, this.tabs.get(4), new ResourceLocation("client/gui/icon/interface/misc_icon.png"), 4.0, 32.0 + inc * 4.0, 20.0, inc, button -> this.currentTab = this.tabs.get(4)));
        this.components.add(new MainButtonTab(this, this.tabs.get(5), new ResourceLocation("client/gui/icon/interface/configs_icon.png"), 4.0, 32.0 + inc * 5.0, 20.0, inc, button -> this.currentTab = this.tabs.get(5)));
    }
    
    void initializeInterface() {
        this.moving = false;
        this.closing = false;
        this.expandAnimation.setX(0.0f);
        this.expandAnimation.setY(0.0f);
        this.tickStopwatch.reset();
    }
    
    public void drawInterface(final double mouseX, final double mouseY) {
        this.currentFrameMouseX = mouseX;
        this.currentFrameMouseY = mouseY;
        if (this.moving) {
            final double differenceX = this.currentFrameMouseX - this.prevFrameMouseX;
            final double differenceY = this.currentFrameMouseY - this.prevFrameMouseY;
            if ((this.positionX + differenceX > 0.0 || differenceX > 0.0) && (this.positionX + this.width + differenceX < this.screen.getResolution().getScaledWidth() || differenceX < 0.0)) {
                this.positionX += differenceX;
            }
            if ((this.positionY + differenceY > 0.0 || differenceY > 0.0) && (this.positionY + this.height + differenceY < this.screen.getResolution().getScaledHeight() || differenceY < 0.0)) {
                this.positionY += differenceY;
            }
        }
        if (this.closing) {
            this.expandAnimation.expand(0.0f, 0.0f, 0.2685f, 0.2385f);
            if (this.alphaStopwatch.hasPassed(20.0)) {
                this.alphaModifer -= 0.1;
                if (this.alphaModifer <= 0.0) {
                    this.alphaModifer = 0.0;
                }
                this.alphaStopwatch.reset();
            }
        }
        else {
            if (this.alphaStopwatch.hasPassed(20.0)) {
                this.alphaModifer += 0.1;
                if (this.alphaModifer > 1.0) {
                    this.alphaModifer = 1.0;
                }
                this.alphaStopwatch.reset();
            }
            this.expandAnimation.expand((float)this.width * 2.0f, (float)this.height * 4.0f, 0.04385f, 0.06385f);
        }
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        final double scissorX = this.positionX + this.width / 2.0 - this.expandAnimation.getX();
        final double scissorY = this.screen.getResolution().getScaledHeight() - (this.positionY + this.height + 10.0) + this.height / 2.0 + 5.0 - this.expandAnimation.getY() / 2.0f;
        GL11.glScissor((int)scissorX * this.screen.getResolution().getScaleFactor(), (int)scissorY * this.screen.getResolution().getScaleFactor(), (int)(this.expandAnimation.getX() * 2.0f) * this.screen.getResolution().getScaleFactor(), (int)this.expandAnimation.getY() * this.screen.getResolution().getScaleFactor());
        Draw.drawRectangle(this.positionX - 0.5, this.positionY - 0.5, this.positionX + this.width + 0.5, this.positionY + this.height + 0.5, this.getColor(255, 255, 255, 255));
        Draw.drawRectangle(this.positionX - 1.0, this.positionY - 1.0, this.positionX + this.width + 1.0, this.positionY + this.height + 1.0, this.getColor(255, 255, 255, 255));
        Draw.drawRectangle(this.positionX - 1.5, this.positionY - 1.5, this.positionX + this.width + 1.5, this.positionY + this.height + 1.5, this.getColor(255, 255, 255, 255));
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        //Draw.drawImg(new ResourceLocation("client/gui/logo/32x32.png"), this.positionX + 4.0, this.positionY + 4.0, 16.0, 16.0);
       // Draw.drawRectangle(this.positionX, this.positionY + 24.0, this.positionX + 24.0, this.positionY + 25.0, this.getColor(0, 0, 0, 50));
        //Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 25.5, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        //Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 25.0, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        //Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 24.5, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        for (final Component component2 : this.components) {
            if (component2 instanceof MainButtonTab) {
                final MainButtonTab tabButton = (MainButtonTab)component2;
                if (tabButton.getTab() != this.currentTab) {
                    continue;
                }
                Draw.drawRectangle(this.positionX + 24.0, this.positionY + tabButton.positionY - 7.0, this.positionX + 25.0, this.positionY + tabButton.positionY + 24.0, this.getColor(255, 132, 0));
            }
        }
        this.components.forEach(component -> component.drawComponent(this.positionX + component.positionX, this.positionY + component.positionY));
        if (this.currentTab == null) {
            //Fonts.f14.drawString("Welcome to §2§lHelium!", this.positionX + 30.0, this.positionY + 6.0, this.getColor(215, 215, 215));
            //Fonts.f14.drawString("This is a §2§lBETA §fbuild, Report any bugs to Mere, Kansio or Shotbowxd", this.positionX + 30.0, this.positionY + 16.0, this.getColor(215, 215, 215));
        }
        else {
            this.currentTab.components.forEach(component -> component.drawComponent(this.positionX + component.positionX, this.positionY + component.positionY));
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        if (this.tickStopwatch.hasPassed(50.0)) {
            if (this.currentTab != null) {
                this.currentTab.onTick();
            }
            this.tickStopwatch.reset();
        }
        this.prevFrameMouseX = this.currentFrameMouseX;
        this.prevFrameMouseY = this.currentFrameMouseY;
    }
    
    void mouseButtonClicked(final int button) {
        if (this.isMouseInBounds(this.positionX, this.positionX + 24.0, this.positionY, this.positionY + 24.0)) {
            this.currentTab = null;
            return;
        }
        for (final Component component : this.components) {
            if (component.mouseButtonClicked(button)) {
                return;
            }
        }
        if (this.currentTab != null) {
            for (final Component component : this.currentTab.components) {
                if (component.mouseButtonClicked(button)) {
                    return;
                }
            }
        }
        this.moving = true;
    }
    
    void mouseButtonReleased(final int state) {
        if (this.moving) {
            this.moving = false;
            return;
        }
        for (final Component component : this.components) {
            component.mouseReleased();
        }
        if (this.currentTab != null) {
            for (final Component component : this.currentTab.components) {
                component.mouseReleased();
            }
        }
    }
    
    void mouseScrolled(final int scrollDirection) {
        for (final Component component : this.components) {
            component.mouseScrolled(scrollDirection);
        }
        if (this.currentTab != null) {
            for (final Component component : this.currentTab.components) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }
    
    public boolean keyTyped(final char typedChar, final int keyCode) {
        for (final Component component : this.components) {
            if (component.keyTyped(typedChar, keyCode)) {
                return true;
            }
        }
        if (this.currentTab != null) {
            for (final Component component : this.currentTab.components) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMouseInBounds(double x1, double x2, double y1, double y2) {
        if (x1 > x2) {
            final double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            final double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        return this.currentFrameMouseX > x1 && this.currentFrameMouseX < x2 && this.currentFrameMouseY > y1 && this.currentFrameMouseY < y2;
    }
    
    public int getColor(final int red, final int green, final int blue) {
        return ColorCreator.create(red, green, blue, (int)(255.0 * this.alphaModifer));
    }
    
    public int getColor(final int red, final int green, final int blue, final int alpha) {
        return ColorCreator.create(red, green, blue, (int)(alpha * this.alphaModifer));
    }
    
    public double getPositionX() {
        return this.positionX;
    }
    
    public double getPositionY() {
        return this.positionY;
    }
    
    public double getWidth() {
        return this.width - 24.0;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public boolean isClosing() {
        return this.closing;
    }
    
    public void setClosing(final boolean closing) {
        this.closing = closing;
        if (closing) {
            this.components.forEach(Component::onGuiClose);
            if (this.currentTab != null) {
                this.currentTab.components.forEach(Component::onGuiClose);
            }
        }
    }
    
    public Tab getCurrentTab() {
        return this.currentTab;
    }
    
    public void setWidth(final double width) {
        this.width = width + 24.0;
    }
    
    public double getCurrentFrameMouseX() {
        return this.currentFrameMouseX;
    }
    
    public double getCurrentFrameMouseY() {
        return this.currentFrameMouseY;
    }
}

/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.menu;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.module.Module;
import winter.module.modules.Nametags;
import winter.module.modules.modes.Mode;
import winter.utils.Render;
import winter.utils.render.xd.OGLRender;
import winter.utils.value.Value;
import winter.utils.value.types.NumberValue;

public class Menu
extends GuiScreen {
    private int x = 5;
    private int y = 5;
    private boolean isDragging = false;
    public int dragX;
    public int dragY;
    public ArrayList<Tab> tabs = new ArrayList();
    private int selectedTab = 0;
    public boolean mobz = false;
    public boolean playerz = true;
    public boolean animalz = false;
    public boolean teamz = false;

    public Menu() {
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category cat2 = arrcategory[n2];
            Tab tab = new Tab(cat2);
            this.tabs.add(tab);
            ++n2;
        }
    }

    public static void drawString(String text, float x2, float y2, int color) {
        int shadowColor = -16777216;
        float size = 1.0f;
        GL11.glPushMatrix();
        GL11.glScalef(0.5001f, 0.5001f, 0.5001f);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, x2, y2, color);
        GL11.glPopMatrix();
    }

    public void drawGroupBox(String title, int x2, int y2, int width, int height) {
        int width2 = this.mc.fontRendererObj.getStringWidth(title) / 2;
        Render.drawBorderedRect(x2, y2, x2 + width, y2 + height, 1.0, 0, -12303292);
        Render.drawRectWH(x2 + 3, y2 - 1, width2 + 4, 3.0, -3355444);
        Menu.drawString(title, (float)((x2 + 5) * 2) - 0.5f, (y2 - 2) * 2 + 2, 0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        OGLRender.drawGradientRect(this.x - 4, this.y, this.x + 324, this.y + 185 - 3, -10281956, -10281956);
        Render.drawRectWH(this.x, this.y + 40, 320.0, 138.0, new Color(-3355444).getRGB());
        OGLRender.drawGradientRect(this.x - 4, this.y, this.x + 324, this.y + 20, -10281956, -6544862);
        Menu.drawString("Winter for Minecraft", this.x * 2 + 2, (this.y + 7) * 2 + 2, -1);
        int tabX = this.x;
        for (Tab tab : this.tabs) {
            tab.update(mouseX, mouseY, tab.getCat() == this.tabs.get(this.selectedTab).getCat(), this.x, this.y);
            tab.renderTab(tabX, this.y + 20);
            tabX += 64;
        }
        this.tabs.get(this.selectedTab).renderComponents();
        this.updatePosition(mouseX, mouseY);
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        }
        this.tabs.get(this.selectedTab).keyPressed(key);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.dragX = mouseX - this.x;
        this.dragY = mouseY - this.y;
        if (mouseButton == 0 && this.isDraggable(mouseX, mouseY)) {
            this.isDragging = true;
        } else if (mouseButton == 0) {
            int i2 = 0;
            while (i2 < this.tabs.size()) {
                if (this.tabs.get(i2).isHovering(mouseX, mouseY)) {
                    this.selectedTab = i2;
                }
                ++i2;
            }
        }
        this.tabs.get(this.selectedTab).updateComponents(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.tabs.get(this.selectedTab).mouseReleaseXD(mouseX, mouseY);
        this.isDragging = false;
    }

    public void updatePosition(int x2, int y2) {
        if (this.isDragging) {
            this.x = x2 - this.dragX;
            this.y = y2 - this.dragY;
        }
    }

    public boolean isDraggable(int x2, int y2) {
        if (x2 >= this.x - 4 && x2 <= this.x + 324 && y2 >= this.y && y2 <= this.y + 20) {
            return true;
        }
        return false;
    }

    static /* synthetic */ Minecraft access$0(Menu menu) {
        return menu.mc;
    }

    public class Checkbox {
        Module m;
        int x;
        int y;
        int x2;
        int y2;
        boolean binding;

        public Checkbox(Module mod) {
            this.binding = false;
            this.m = mod;
        }

        public Module getMod() {
            return this.m;
        }

        public void leftClicked(int mouseX, int mouseY) {
            if (this.isOver(mouseX, mouseY) && !Keyboard.isKeyDown(42)) {
                this.m.toggle();
            } else if (this.isOver(mouseX, mouseY) && Keyboard.isKeyDown(42)) {
                this.m.visible(!this.m.visible());
            } else if (this.isOverMode(mouseX, mouseY) && this.m.getModes().size() > 0) {
                this.m.mode(this.m.getNextMode().getDisplay());
            }
        }

        public void rightClicked(int mouseX, int mouseY) {
            if (this.isOver(mouseX, mouseY)) {
                this.binding = !this.binding;
            }
        }

        public void keyPressed(int key) {
            if (this.binding) {
                if (key != 57) {
                    this.m.setBind(key);
                } else {
                    this.m.setBind(0);
                }
                this.binding = false;
            }
        }

        public void renderCheckbox(int x2, int y2) {
            this.x = x2;
            this.y = y2;
            Render.drawRectWH(x2, y2, 10.0, 10.0, -11184811);
            String bind = this.binding ? " [Press key...]" : " [" + Keyboard.getKeyName(this.m.getBind()) + "]";
            String vis = this.m.visible() ? " [V]" : " [H]";
            this.x2 = x2 + 270;
            this.y2 = y2 + 62;
            if (this.m.isEnabled()) {
                Render.drawRectWH(x2 + 1, y2 + 1, 8.0, 8.0, -6710887);
            }
            Menu.drawString(String.valueOf(this.m.getName()) + bind, (x2 + 12) * 2, ((float)y2 + 1.5f) * 2.0f + 3.5f, 0);
            if (this.m.getModes().size() > 0) {
                Render.drawBorderedRect(this.x2, this.y2, this.x2 + 40, this.y2 + 10, 1.0, -11184811, -11184811);
                Menu.drawString("Mode: " + this.m.getMode(), (this.x2 + 3) * 2, ((float)this.y2 + 1.5f) * 2.0f + 3.5f, 0);
            }
        }

        public boolean isOver(int x2, int y2) {
            if (x2 >= this.x && x2 <= this.x + 70 && y2 >= this.y && y2 <= this.y + 10) {
                return true;
            }
            return false;
        }

        public boolean isOverMode(int x2, int y2) {
            if (x2 >= this.x2 && x2 <= this.x2 + 40 && y2 >= this.y2 && y2 <= this.y2 + 10) {
                return true;
            }
            return false;
        }

        public boolean isOverVis(int x2, int y2) {
            if (x2 >= this.x && x2 <= this.x + 40 && y2 >= this.y && y2 <= this.y + 10) {
                return true;
            }
            return false;
        }
    }

    public class Slider {
        NumberValue val;
        int x;
        int y;
        boolean dragging;
        private double renderWidth;

        public Slider(NumberValue val) {
            this.dragging = false;
            this.val = val;
        }

        public NumberValue getVal() {
            return this.val;
        }

        public void update(int mouseX) {
            double diff = Math.min(120, Math.max(0, mouseX - this.x));
            double min = this.val.getMin();
            double max = this.val.getMax();
            this.renderWidth = 120.0 * (this.val.getValue() - min) / (max - min);
            if (this.dragging) {
                if (diff == 0.0) {
                    this.val.setVal(this.val.getMin());
                } else {
                    double newValue = Nametags.roundToPlace(diff / 120.0 * (max - min) + min, 2);
                    this.val.setVal(newValue);
                }
            }
        }

        public void leftClicked(int mouseX, int mouseY) {
            if (this.isOver(mouseX, mouseY)) {
                this.dragging = true;
            }
        }

        public void leftReleased(int mouseX, int mouseY) {
            this.dragging = false;
        }

        public void render(int x2, int y2) {
            this.x = x2;
            this.y = y2;
            Render.drawBorderedRect(x2 + 2, y2, x2 + 120, y2 + 10, 1.0, 0, -12303292);
            int drag = (int)(this.val.getValue() / this.val.getMax() * 120.0);
            Gui.drawRect(x2 + 2, y2, x2 + (int)this.renderWidth, y2 + 10, -12303292);
            Menu.drawString(this.val.getName(), (x2 + 4) * 2, (y2 + 3) * 2, 0);
            Menu.drawString("" + this.val.getValue(), (x2 + 60) * 2 - Menu.access$0((Menu)Menu.this).fontRendererObj.getStringWidth("" + this.val.getValue()) / 2, (y2 + 3) * 2, 0);
        }

        public boolean isOver(int x2, int y2) {
            if (x2 >= this.x && x2 <= this.x + 120 && y2 >= this.y && y2 <= this.y + 10) {
                return true;
            }
            return false;
        }
    }

    public class Tab {
        Module.Category c;
        boolean hovered;
        boolean current;
        int x;
        int y;
        int fX;
        int fY;
        int sX;
        int sY;
        ArrayList<Checkbox> components;
        ArrayList<Slider> slides;

        public Tab(Module.Category cat2) {
            this.current = false;
            this.c = cat2;
            this.components = new ArrayList();
            this.slides = new ArrayList();
            for (Module mod : Client.getManager().getModulesInCategory(cat2)) {
                Checkbox box = new Checkbox(mod);
                this.components.add(box);
                for (Value val : mod.getValues()) {
                    if (!(val instanceof NumberValue)) continue;
                    NumberValue val2 = (NumberValue)val;
                    Slider slider = new Slider(val2);
                    this.slides.add(slider);
                }
            }
        }

        public Module.Category getCat() {
            return this.c;
        }

        public void update(int mouseX, int mouseY, boolean current, int frameX, int frameY) {
            this.current = current;
            this.hovered = this.isHovering(mouseX, mouseY);
            this.fX = frameX;
            this.fY = frameY;
            this.sX = frameX;
            this.sY = frameY;
            for (Slider slide : this.slides) {
                slide.update(mouseX);
            }
        }

        public void renderTab(int x2, int y2) {
            this.x = x2;
            this.y = y2;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            OGLRender.drawGradientRect(x2, y2, x2 + 64, y2 + 20, this.current ? (this.hovered ? new Color(-13948117).darker().darker().darker().darker().darker().darker().darker().darker().getRGB() : new Color(-13948117).darker().darker().darker().darker().darker().darker().darker().darker().getRGB()) : (this.hovered ? new Color(-13948117).darker().darker().darker().getRGB() : new Color(-13948117).darker().darker().getRGB()), this.current ? -13421773 : -12105913);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Menu.drawString(this.c.name(), (x2 + 32) * 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.c.name()) / 2, ((float)(y2 + 10) - 1.5f) * 2.0f, 16777215);
        }

        public void renderComponents() {
            int checkX = this.fX + 5;
            int checkY = this.fY + 45;
            int slideX = this.fX + 195;
            int slideY = this.fY + 45;
            for (Checkbox check : this.components) {
                check.renderCheckbox(checkX, checkY);
                checkY += 12;
            }
            for (Slider slide : this.slides) {
                slide.render(slideX, slideY);
                slideY += 12;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void updateComponents(int mouseX, int mouseY, int button) {
            if (button == 0) {
                for (Checkbox check : this.components) {
                    check.leftClicked(mouseX, mouseY);
                }
                for (Slider slide : this.slides) {
                    slide.leftClicked(mouseX, mouseY);
                }
                return;
            } else {
                if (button != 1) return;
                for (Checkbox check : this.components) {
                    check.rightClicked(mouseX, mouseY);
                }
            }
        }

        public void mouseReleaseXD(int mouseX, int mouseY) {
            for (Slider slide : this.slides) {
                slide.leftReleased(mouseX, mouseY);
            }
        }

        public void keyPressed(int key) {
            for (Checkbox check : this.components) {
                check.keyPressed(key);
            }
        }

        public boolean isHovering(int x2, int y2) {
            if (x2 >= this.x && x2 <= this.x + 64 && y2 >= this.y && y2 <= this.y + 20) {
                return true;
            }
            return false;
        }
    }

}


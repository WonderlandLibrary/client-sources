package cc.slack.ui.clickgui.component;

import java.util.ArrayList;

import cc.slack.start.Slack;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;

//Your Imports
import cc.slack.ui.clickgui.component.components.Button;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;

public class Frame {

    public ArrayList<Component> components;
    public Category category;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private final int height;
    private boolean isDragging;
    public int dragX;
    public int dragY;

    public Frame(Category cat) {
        this.components = new ArrayList<>();
        this.category = cat;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.height = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.height;

        for (Module mod : Slack.getInstance().getModuleManager().getModulesByCategory(category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height, 3, category.getColorRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        fontRenderer.drawStringWithShadow(this.category.name(), (this.x + 2) * 2 + 5, (this.y + 2.5f) * 2 + 5, 0xFFFFFFFF);
        fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
        GL11.glPopMatrix();
        refresh();
        if (this.open) {
            if (!this.components.isEmpty()) {
                //Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
                //Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, new Color(0, 200, 20, 150).getRGB());
                //Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
                components.forEach(Component::renderComponent);
            }
        }
    }

    public void refresh() {
        int off = this.height;
        for (Component comp : components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updatePosition(int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        if (this.isDragging) {
            this.setX(MathHelper.clamp_int(mouseX - dragX, 0, sr.getScaledWidth() - getWidth()));
            this.setY(MathHelper.clamp_int(mouseY - dragY, 0, sr.getScaledHeight() - getHeight()));
        }
    }

    public boolean isWithinHeader(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            return true;
        }
        return false;
    }
}

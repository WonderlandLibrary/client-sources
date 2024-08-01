package wtf.diablo.client.gui.draggable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.util.render.RenderUtil;

import javax.vecmath.Vector2f;


//TODO: make it so that you cant drag out of boundaries
public abstract class AbstractDraggableElement {
    protected final Minecraft mc = Minecraft.getMinecraft();

    private final String name;
    private final AbstractModule module;

    private final float defaultX, defaultY;
    private float x, y;
    private int width, height;

    private boolean dragging;
    private Vector2f mouseOffset;


    public AbstractDraggableElement(final String name, final float x, final float y, final int width, final int height, final AbstractModule module) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
        this.module = module;

        this.dragging = false;
        Diablo.getInstance().getDraggableElementHandler().registerDraggableElement(this);
    }

    protected abstract void draw();

    protected void shader() {}

    public void onDraw() {
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.prepareScissorBox(x - 1, y - 1, (x + width) + 1, (y + height) + 1);
        GlStateManager.translate(x, y, 0);

        final Vector2f mouse = getMousePos();

        if (hovered(mouse.x,mouse.y) && mc.currentScreen instanceof GuiChat) {
            RenderUtil.drawBorderRect(0, 0, width, height, 1, -1);
        }

        if (dragging) {
            setPos(mouse.x - mouseOffset.x, mouse.y - mouseOffset.y);
        }

        this.draw();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.translate(-x, -y, 0);
        GlStateManager.popMatrix();
    }

    public final void onMouseClick(final float mouseX, final float mouseY, final int mouseButton) {
        this.dragging = hovered(mouseX, mouseY) && mouseButton == 0;
        if (mouseOffset == null && this.dragging) {
            Vector2f mouse = getMousePos();
            this.mouseOffset = new Vector2f(mouse.x - x, mouse.y - y);
        }
    }

    public final void onMouseReleased() {
        this.dragging = false;
        this.mouseOffset = null;
    }

    public final void onReset() {
        this.x = this.defaultX;
        this.y = this.defaultY;
    }


    public final void setPos(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public final float getX() {
        return x;
    }

    public final float getY() {
        return y;
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    public final boolean dragging() {
        return this.dragging;
    }

    public final String getName() {
        return name;
    }

    public final AbstractModule getModule() {
        return module;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    private boolean hovered(final float mouseX, final float mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    private static Vector2f getMousePos() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float posX = Mouse.getX();
        float posY = Minecraft.getMinecraft().displayHeight - Mouse.getY();
        posX /= (float) sr.getScaleFactor();
        posY /= ((float) sr.getScaleFactor());
        return new Vector2f(posX, posY - 1);
    }
}
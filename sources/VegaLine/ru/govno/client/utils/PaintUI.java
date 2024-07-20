/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec2f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class PaintUI {
    public float panelX = 10.0f;
    public float panelY = 260.0f;
    public float draggingX;
    public float draggingY;
    public boolean isOpen;
    public boolean isDragging;
    public DoingEnum selectedDoing;
    public List<Vec2f> brushPoints = new ArrayList<Vec2f>();
    ResourceLocation PANEL_BG_OPENNED = new ResourceLocation("vegaline/ui/paint/paintpanelopennd.png");
    ResourceLocation PANEL_BG_CLOSED = new ResourceLocation("vegaline/ui/paint/paintpanelclosed.png");
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = this.tessellator.getBuffer();

    public PaintUI() {
    }

    public PaintUI(float panelX, float panelY) {
        this.panelX = panelX;
        this.panelY = panelY;
    }

    public boolean mouseIsInPage(int mouseX, int mouseY) {
        return (float)mouseX > this.panelX + 3.0f && (float)mouseY > this.panelY + 20.0f && (float)mouseX < this.panelX + 297.0f && (float)mouseY < this.panelY + 317.0f;
    }

    public boolean isPainting() {
        return this.isOpen && this.selectedDoing == DoingEnum.BRUSH;
    }

    public boolean isErasing() {
        return this.isOpen && this.selectedDoing == DoingEnum.ERASE;
    }

    public boolean isClearring() {
        return this.isOpen && this.selectedDoing == DoingEnum.CLEAR;
    }

    public boolean isOpenning() {
        return this.selectedDoing == DoingEnum.OPEN;
    }

    public boolean isClosing() {
        return this.selectedDoing == DoingEnum.CLOSE;
    }

    public DoingEnum hoveredDoing(int mouseX, int mouseY) {
        for (DoingEnum Enum2 : DoingEnum.values()) {
            if (Enum2 == DoingEnum.OPEN && this.isOpen || !this.isOpen && Enum2 != DoingEnum.OPEN) continue;
            float x = this.panelX + Enum2.fieldX;
            float y = this.panelY + Enum2.fieldY;
            float x2 = this.panelX + Enum2.fieldX2;
            float y2 = this.panelY + Enum2.fieldY2;
            if (!((float)mouseX >= x) || !((float)mouseY >= y) || !((float)mouseX <= x2) || !((float)mouseY <= y2)) continue;
            return Enum2;
        }
        return null;
    }

    public float eraseRadius() {
        return 10.0f;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (button != 0) {
            return;
        }
        DoingEnum doing = this.hoveredDoing(mouseX, mouseY);
        if (doing != null) {
            this.selectedDoing = doing;
        } else if ((float)mouseX > this.panelX && (float)mouseY > this.panelY) {
            float f = mouseX;
            int n = this.isOpen ? 300 : 80;
            if (f < this.panelX + (float)n && (float)mouseY < this.panelY + 20.0f) {
                this.isDragging = true;
            }
        }
        if (this.selectedDoing == DoingEnum.BRUSH && this.mouseIsInPage(mouseX, mouseY)) {
            this.brushPoints.add(new Vec2f((float)mouseX - this.panelX, (float)mouseY - this.panelY));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        if (button == 0) {
            this.isDragging = false;
        }
    }

    public void onCloseOrInit(boolean isInit) {
        this.isDragging = false;
    }

    public void updatePainting(int mouseX, int mouseY) {
        if (this.isClosing()) {
            this.isOpen = false;
            this.selectedDoing = null;
        }
        if (this.isOpenning()) {
            this.isOpen = true;
            this.selectedDoing = null;
        }
        if (this.isClearring()) {
            this.brushPoints.clear();
            this.selectedDoing = null;
        }
        if (this.isErasing() && this.mouseIsInPage(mouseX, mouseY)) {
            if (Mouse.isButtonDown(0)) {
                this.brushPoints.removeIf(vec -> {
                    float dx = (float)((int)vec.x - mouseX) + this.panelX;
                    float dy = (float)((int)vec.y - mouseY) + this.panelY;
                    return Math.sqrt(dx * dx + dy * dy) < (double)this.eraseRadius();
                });
            }
            RenderUtils.drawClientCircleWithOverallToColor(mouseX, mouseY, this.eraseRadius(), 360.0f, 1.5f, 1.0f, -1, 1.0f);
        }
        if (this.isPainting() && this.mouseIsInPage(mouseX, mouseY)) {
            if (Mouse.isButtonDown(0) && (Mouse.getDX() != 0 || Mouse.getDY() != 0)) {
                this.brushPoints.add(new Vec2f((float)mouseX - this.panelX, (float)mouseY - this.panelY));
            }
            RenderUtils.drawClientCircleWithOverallToColor(mouseX, mouseY, this.pointScale() / 2.0f - 1.0f, 360.0f, 1.5f, 1.0f, -1, 1.0f);
        }
    }

    public float pointScale() {
        return 10.0f;
    }

    public void updateDragging(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.panelX = -(this.draggingX - (float)mouseX);
            this.panelY = -(this.draggingY - (float)mouseY);
        } else {
            this.draggingX = (float)mouseX - this.panelX;
            this.draggingY = (float)mouseY - this.panelY;
        }
    }

    public void drawBrushedPoints(int color) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(this.panelX + 3.0f, this.panelY + 20.0f, this.panelX + 297.0f, this.panelY + 317.0f, -1);
        StencilUtil.readStencilBuffer(1);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glEnable(2832);
        RenderUtils.glColor(color);
        GL11.glPointSize(this.pointScale());
        GL11.glBegin(0);
        this.brushPoints.forEach(vec -> GL11.glVertex2f(vec.x + this.panelX, vec.y + this.panelY));
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glDisable(2896);
        GL11.glEnable(3553);
        GlStateManager.resetColor();
        StencilUtil.uninitStencilBuffer();
    }

    public void renderUpdatePanel(int mouseX, int mouseY) {
        this.updateDragging(mouseX, mouseY);
        this.updatePainting(mouseX, mouseY);
        ResourceLocation backgroundTexture = this.isOpen ? this.PANEL_BG_OPENNED : this.PANEL_BG_CLOSED;
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.disableAlpha();
        RenderUtils.glColor(-1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundTexture);
        this.buffer.begin(9, DefaultVertexFormats.POSITION_TEX);
        this.buffer.pos(this.panelX, this.panelY + 320.0f).tex(0.0, 1.0).endVertex();
        this.buffer.pos(this.panelX + 300.0f, this.panelY + 320.0f).tex(1.0, 1.0).endVertex();
        this.buffer.pos(this.panelX + 300.0f, this.panelY).tex(1.0, 0.0).endVertex();
        this.buffer.pos(this.panelX, this.panelY).tex(0.0, 0.0).endVertex();
        GL11.glShadeModel(7425);
        this.tessellator.draw();
        GlStateManager.disableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.resetColor();
        if (this.selectedDoing != null) {
            RenderUtils.drawLightContureRect(this.panelX + this.selectedDoing.fieldX, this.panelY + this.selectedDoing.fieldY, this.panelX + this.selectedDoing.fieldX2, this.panelY + this.selectedDoing.fieldY2, -1);
        }
        if (!this.brushPoints.isEmpty() && this.isOpen) {
            this.drawBrushedPoints(-1);
        }
    }

    public static enum DoingEnum {
        BRUSH(281.5f, 2.0f, 297.5f, 18.0f),
        ERASE(262.5f, 2.0f, 278.5f, 18.0f),
        CLEAR(243.5f, 2.0f, 259.5f, 18.0f),
        OPEN(61.0f, 2.0f, 77.0f, 18.0f),
        CLOSE(224.5f, 2.0f, 240.5f, 18.0f);

        float fieldX;
        float fieldY;
        float fieldX2;
        float fieldY2;

        private DoingEnum(float fieldX, float fieldY, float fieldX2, float fieldY2) {
            this.fieldX = fieldX;
            this.fieldY = fieldY;
            this.fieldX2 = fieldX2;
            this.fieldY2 = fieldY2;
        }
    }
}


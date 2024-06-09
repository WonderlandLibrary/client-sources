/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.imgui;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.imgui.Checkbox;
import winter.utils.imgui.CheckboxEvent;
import winter.utils.imgui.DropDown;
import winter.utils.render.xd.OGLRender;

public class Panel {
    public static Minecraft mc = Minecraft.getMinecraft();
    public ArrayList<DropDown> drops;
    public String title;
    public static int pX = 2;
    public static int pY = 5;
    public static float width = 120.0f;
    public static float height = 81.0f;
    private int y;
    public boolean isDragging;
    public boolean resizing;
    public boolean open;
    public int dragX;
    public int dragY;

    public Panel(String title) {
        this.title = title;
        this.drops = new ArrayList();
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category cat2 = arrcategory[n2];
            DropDown drop = new DropDown(cat2.name(), this);
            for (final Module mod : Client.getManager().getModulesInCategory(cat2)) {
                Checkbox check = new Checkbox(mod.getName(), this);
                check.state = mod.isEnabled();
                check.setStateChangedEvent(new CheckboxEvent(){

                    @Override
                    public void onStateChanged(Checkbox check) {
                        mod.setEnabled(check.state);
                    }
                });
                drop.addCheckbox(check);
            }
            this.drops.add(drop);
            ++n2;
        }
    }

    public void render() {
        ScaledResolution sr2 = new ScaledResolution(mc, Panel.mc.displayWidth, Panel.mc.displayHeight);
        OGLRender.drawFullCircle(pX + 3, pY + 3, 3.0, -8947798);
        OGLRender.drawFullCircle(pX + (int)width - 1, pY + 3, 3.0, -8947798);
        Render.drawRect(pX, pY + 4, (float)(pX + 2) + width, (float)pY + 10.5f, -8947798);
        Render.drawRect(pX + 3, pY, (float)(pX - 1) + width, (float)pY + 10.5f, -8947798);
        Render.drawRect(pX, (float)pY + 10.5f, (float)(pX + 2) + width, (float)pY + 10.5f + height, Integer.MIN_VALUE);
        OGLRender.drawFullCircle(pX + (int)width - 3, pY + 5, 4.0, -6710819);
        Panel.mc.fontRendererObj.drawString("\u25bc", pX + 5, pY + 1.5f, -1);
        Panel.mc.fontRendererObj.drawString(this.title, pX + 12, pY + 2, -1);
        int off = 0;
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        for (DropDown drop : this.drops) {
            drop.render(pX + 2, pY + this.y + 12 + off);
            off += drop.getHeight();
        }
        Panel.scissorBox((float)pX + 0.5f, (float)pY + 10.5f, (float)pX + width + 1.5f, (float)pY + height + 10.0f, sr2);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        Panel.triangle((float)pX + width - 3.0f, (double)pY + 5.5 + (double)height, (float)pX + width + 2.0f, (double)pY + 10.5 + (double)height, -10066330);
    }

    public void setX(int newX) {
        pX = newX;
    }

    public void setY(int newY) {
        pY = newY;
    }

    public void update(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
        if (this.resizing) {
            width = mouseX - pX;
            if (width < 120.0f) {
                width = 120.0f;
            }
            height = mouseY - pY;
            if ((height -= 10.0f) < 81.0f) {
                height = 81.0f;
            }
        }
    }

    public static void triangle(double left, double d2, double right, double e2, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (d2 < e2) {
            var5 = d2;
            d2 = e2;
            e2 = var5;
        }
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var6 = (float)(color >> 16 & 255) / 255.0f;
        float var7 = (float)(color >> 8 & 255) / 255.0f;
        float var8 = (float)(color & 255) / 255.0f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        var10.startDrawing(4);
        var10.addVertex(left, e2, 0.0);
        var10.addVertex(right, d2, 0.0);
        var10.addVertex(left, d2, 0.0);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }

    public boolean mouseOverTitle(int mouseX, int mouseY) {
        if (mouseX > pX && (float)mouseX < (float)(pX + 2) + width && mouseY > pY && mouseY < pY + 11) {
            return true;
        }
        return false;
    }

    public boolean mouseOverSize(int mouseX, int mouseY) {
        if ((float)mouseX > (float)pX + width - 3.0f && (float)mouseX < (float)(pX + 2) + width && (float)mouseY > (float)(pY + 5) + height && (float)mouseY < (float)(pY + 10) + height) {
            return true;
        }
        return false;
    }

    public void scroll() {
        int mouseWheel = Mouse.getEventDWheel();
        if (mouseWheel > 0) {
            this.y += 5;
        } else if (mouseWheel < 0) {
            this.y -= 5;
        }
    }

    public static void scissorBox(float x2, float y2, float xend, float yend, ScaledResolution sr2) {
        if (Panel.mc.currentScreen == null) {
            return;
        }
        int width = (int)(xend - x2);
        int height = (int)(yend - y2);
        int factor = sr2.getScaleFactor();
        int bottomY = (int)((float)Panel.mc.currentScreen.height - yend);
        GL11.glScissor((int)(x2 * (float)factor), bottomY * factor, width * factor, height * factor);
    }

}


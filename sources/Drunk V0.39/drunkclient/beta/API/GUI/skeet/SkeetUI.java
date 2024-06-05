/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.API.GUI.skeet;

import drunkclient.beta.API.GUI.skeet.comp.Component;
import drunkclient.beta.IMPL.font.CFontRenderer;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.UTILS.Math.MathUtils;
import drunkclient.beta.UTILS.render.LockedResolution;
import drunkclient.beta.UTILS.render.RenderUtil;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SkeetUI
extends GuiScreen {
    public static final int GROUP_BOX_MARGIN = 8;
    public static final int GROUP_BOX_LEFT_MARGIN = 3;
    public static final CFontRenderer ICONS_RENDERER;
    public static final CFontRenderer FONT_RENDERER;
    public static final CFontRenderer KEYBIND_FONT_RENDERER;
    public static final int ENABLE_BUTTON_Y_OFFSET = 6;
    public static final int ENABLE_BUTTON_Y_GAP = 4;
    private static final int WIDTH = 370;
    private static final int HEIGHT = 350;
    private static final float TOTAL_BORDER_WIDTH = 3.5f;
    private static final float RAINBOW_BAR_WIDTH = 1.5f;
    private static final int TAB_SELECTOR_WIDTH = 48;
    public static final float USABLE_AREA_WIDTH = 315.0f;
    public boolean closed;
    public static final float GROUP_BOX_WIDTH = 94.333336f;
    public static final float HALF_GROUP_BOX = 40.166668f;
    private static final ResourceLocation BACKGROUND_IMAGE;
    private static final char[] ICONS;
    private static final float USABLE_AREA_HEIGHT = 341.5f;
    private static final int TAB_SELECTOR_HEIGHT;
    private static double alpha;
    private static boolean open;
    private final Component rootComponent;
    private double targetAlpha;
    private boolean dragging;
    private float prevX;
    private float prevY;

    @Override
    public void initGui() {
        alpha = 0.0;
        this.targetAlpha = 255.0;
        open = true;
        this.closed = false;
        super.initGui();
    }

    public SkeetUI() {
        alpha = 0.0;
        this.targetAlpha = 255.0;
        open = true;
        this.closed = false;
        this.rootComponent = new Component(null, 0.0f, 0.0f, 370.0f, 350.0f){

            @Override
            public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
                if (SkeetUI.this.dragging) {
                    this.setX(Math.max(0.0f, Math.min((float)lockedResolution.getWidth() - this.getWidth(), (float)mouseX - SkeetUI.this.prevX)));
                    this.setY(Math.max(0.0f, Math.min((float)lockedResolution.getWidth() - this.getWidth(), (float)mouseY - SkeetUI.this.prevY)));
                }
                float borderX = this.getX();
                float borderY = this.getY();
                float width = this.getWidth();
                float height = this.getHeight();
                Gui.drawRect(borderX, borderY, borderX + width, borderY + height, SkeetUI.getColor(0x10110E));
                Gui.drawRect(borderX + 0.5f, borderY + 0.5f, borderX + width - 0.5f, borderY + height - 0.5f, SkeetUI.getColor(0x373A3A));
                Gui.drawRect(borderX + 1.0f, borderY + 1.0f, borderX + width - 1.0f, borderY + height - 1.0f, SkeetUI.getColor(0x232323));
                Gui.drawRect(borderX + 3.0f, borderY + 3.0f, borderX + width - 3.0f, borderY + height - 3.0f, SkeetUI.getColor(0x2F2F2F));
                float left = borderX + 3.5f;
                float top = borderY + 3.5f;
                float right = borderX + width - 3.5f;
                float bottom = borderY + height - 3.5f;
                Gui.drawRect(left, top, right, bottom, SkeetUI.getColor(0x151515));
                if (!(alpha > 20.0)) return;
                GL11.glEnable((int)3089);
                RenderUtil.startScissorBox(lockedResolution, (int)left, (int)top, (int)(right - left), (int)(bottom - top));
                RenderUtil.drawImage(left, top, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderUtil.drawImage(left + 325.0f, top + 1.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderUtil.drawImage(left + 1.0f, top + 275.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderUtil.drawImage(left + 326.0f, top + 276.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                GL11.glDisable((int)3089);
            }
        };
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            this.dragging = true;
            this.prevX = (float)mouseX - this.rootComponent.getX();
            this.prevY = (float)mouseY - this.rootComponent.getY();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        alpha = SkeetUI.linearAnimation(alpha, this.targetAlpha, 10.0);
        this.rootComponent.drawComponent(RenderUtil.getLockedResolution(), mouseX, mouseY);
    }

    private static boolean isVisible() {
        if (open) return true;
        if (alpha > 0.0) return true;
        return false;
    }

    public static int getColor(int color) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = (int)alpha;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static double linearAnimation(double now, double desired, double speed) {
        double dif = Math.abs(now - desired);
        int fps = Minecraft.getDebugFPS();
        if (!(dif > 0.0)) return now;
        double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(10.0, Math.max(0.005, 144.0 / (double)fps * speed)), 0.005);
        if (dif != 0.0 && dif < animationSpeed) {
            animationSpeed = dif;
        }
        if (now < desired) {
            return now + animationSpeed;
        }
        if (!(now > desired)) return now;
        return now - animationSpeed;
    }

    static {
        BACKGROUND_IMAGE = new ResourceLocation("/assets/skeetchainmail.png", 0);
        ICONS = new char[]{'A', 'G', 'F', 'C', 'J', 'E', 'I', 'H'};
        TAB_SELECTOR_HEIGHT = 321 / ICONS.length;
        ICONS_RENDERER = FontLoaders.NovICON38;
        FONT_RENDERER = FontLoaders.Tahoma12;
        KEYBIND_FONT_RENDERER = FontLoaders.Tahoma9;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.ui.dropdown.Panel;
import mpp.venusfr.ui.dropdown.PanelStyle;
import mpp.venusfr.ui.dropdown.SearchField;
import mpp.venusfr.ui.dropdown.ThemeEditor;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.client.Vec2i;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.KawaseBlur;
import mpp.venusfr.utils.render.Scissor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class DropDown
extends Screen
implements IMinecraft {
    public SearchField searchField;
    public ThemeEditor themeEditor;
    private final PanelStyle panelstul;
    private final List<Panel> panels = new ArrayList<Panel>();
    private static Animation animation = new Animation();
    public static float scale = 1.0f;

    public DropDown(ITextComponent iTextComponent) {
        super(iTextComponent);
        for (Category category : Category.values()) {
            if (category == Category.Theme) continue;
            this.panels.add(new Panel(category));
        }
        this.panelstul = new PanelStyle(Category.Theme);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    protected void init() {
        int n = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n2 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        float f = (float)n / 2.0f - (float)(this.panels.size() * 145) / 2.0f + 290.0f + 27.5f;
        float f2 = (float)n2 / 2.0f + 162.5f + 30.0f;
        this.themeEditor = new ThemeEditor(n - 80, n2 - 80, 60, 60);
        this.searchField = new SearchField((int)f, (int)f2, 120, 16, "\u041f\u043e\u0438\u0441\u043a");
        animation = animation.animate(1.0, 0.25, Easings.EXPO_OUT);
        super.init();
    }

    @Override
    public void closeScreen() {
        super.closeScreen();
        GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        Vec2i vec2i = this.adjustMouseCoordinates((int)d, (int)d2);
        Vec2i vec2i2 = ClientUtil.getMouse(vec2i.getX(), vec2i.getY());
        d = vec2i2.getX();
        d2 = vec2i2.getY();
        for (Panel panel : this.panels) {
            if (!MathUtil.isHovered((float)d, (float)d2, panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight())) continue;
            panel.setScroll((float)((double)panel.getScroll() + d3 * 20.0));
        }
        if (this.themeEditor.toRender && MathUtil.isHovered((float)d, (float)d2, this.panelstul.getX(), this.panelstul.getY(), this.panelstul.getWidth(), this.panelstul.getHeight())) {
            this.panelstul.setScroll((float)((double)this.panelstul.getScroll() + d3 * 20.0));
        }
        return super.mouseScrolled(d, d2, d3);
    }

    @Override
    public boolean charTyped(char c, int n) {
        if (this.searchField.charTyped(c, n)) {
            return false;
        }
        for (Panel panel : this.panels) {
            panel.charTyped(c, n);
        }
        if (this.themeEditor.toRender) {
            this.panelstul.charTyped(c, n);
        }
        return super.charTyped(c, n);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        DisplayUtils.drawContrast(1.0f - (float)(animation.getValue() / 3.0) * 0.7f);
        DisplayUtils.drawWhite((float)animation.getValue() * 0.7f);
        KawaseBlur.blur.updateBlur(2.0f, 3);
        DropDown.mc.gameRenderer.setupOverlayRendering(2);
        animation.update();
        if (animation.getValue() < 0.1) {
            this.closeScreen();
        }
        float f2 = 10.0f;
        float f3 = (float)this.panels.size() * 115.0f;
        this.updateScaleBasedOnScreenWidth();
        int n3 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n4 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        Vec2i vec2i = this.adjustMouseCoordinates(n, n2);
        Vec2i vec2i2 = ClientUtil.getMouse(vec2i.getX(), vec2i.getY());
        n = vec2i2.getX();
        n2 = vec2i2.getY();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)n3 / 2.0f, (float)n4 / 2.0f, 0.0f);
        GlStateManager.scaled(animation.getValue(), animation.getValue(), 1.0);
        GlStateManager.scaled(scale, scale, 1.0);
        GlStateManager.translatef((float)(-n3) / 2.0f, (float)(-n4) / 2.0f, 0.0f);
        for (Panel panel : this.panels) {
            panel.setY((float)n4 / 2.0f - 110.0f);
            panel.setX((float)n3 / 2.0f - f3 / 2.0f + (float)panel.getCategory().ordinal() * 115.0f + 5.0f);
            float f4 = (float)animation.getValue() * scale;
            float f5 = (1.0f - f4) / 2.0f;
            float f6 = panel.getX() + panel.getWidth() * f5;
            float f7 = panel.getY() + panel.getHeight() * f5;
            float f8 = panel.getWidth() * f4;
            float f9 = panel.getHeight() * f4;
            f6 = f6 * f4 + ((float)n3 - f8) * f5;
            Scissor.push();
            Scissor.setFromComponentCoordinates(f6, f7, f8, f9 - 0.5f);
            panel.render(matrixStack, n, n2);
            Scissor.unset();
            Scissor.pop();
        }
        if (this.themeEditor.toRender) {
            animation.update();
            this.panelstul.setY((float)n4 / 2.0f - 110.0f + 90.0f);
            this.panelstul.setX((float)n3 / 2.0f - f3 / 2.0f + (float)this.panelstul.getCategory().ordinal() * 115.0f + 5.0f + 80.0f);
            this.panelstul.render(matrixStack, n, n2);
        }
        this.themeEditor.render(matrixStack, n, n2, f);
        this.searchField.render(matrixStack, n, n2, f);
        GlStateManager.popMatrix();
    }

    private void updateScaleBasedOnScreenWidth() {
        float f;
        float f2 = 105.0f;
        float f3 = 10.0f;
        float f4 = 1.0f;
        float f5 = (float)this.panels.size() * 115.0f;
        if (f5 >= (f = (float)mc.getMainWindow().getScaledWidth())) {
            scale = f / f5;
            scale = MathHelper.clamp(scale, 1.0f, 1.0f);
        } else {
            scale = 1.0f;
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.searchField.keyPressed(n, n2, n3)) {
            return false;
        }
        for (Panel panel : this.panels) {
            panel.keyPressed(n, n2, n3);
        }
        if (this.themeEditor.toRender) {
            this.panelstul.keyPressed(n, n2, n3);
        }
        if (n == 256) {
            animation = animation.animate(0.0, 0.25, Easings.EXPO_OUT);
            return true;
        }
        return super.keyPressed(n, n2, n3);
    }

    private Vec2i adjustMouseCoordinates(int n, int n2) {
        int n3 = mc.getMainWindow().getScaledWidth();
        int n4 = mc.getMainWindow().getScaledHeight();
        float f = ((float)n - (float)n3 / 2.0f) / scale + (float)n3 / 2.0f;
        float f2 = ((float)n2 - (float)n4 / 2.0f) / scale + (float)n4 / 2.0f;
        return new Vec2i((int)f, (int)f2);
    }

    private double pathX(float f, float f2) {
        if (f2 == 1.0f) {
            return f;
        }
        int n = mc.getMainWindow().scaledWidth();
        int n2 = mc.getMainWindow().scaledHeight();
        f /= f2;
        return f -= (float)n / 2.0f - (float)n / 2.0f * f2;
    }

    private double pathY(float f, float f2) {
        if (f2 == 1.0f) {
            return f;
        }
        int n = mc.getMainWindow().scaledWidth();
        int n2 = mc.getMainWindow().scaledHeight();
        f /= f2;
        return f -= (float)n2 / 2.0f - (float)n2 / 2.0f * f2;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.searchField.mouseClicked(d, d2, n)) {
            return false;
        }
        if (this.themeEditor.mouseClicked(d, d2, n)) {
            return false;
        }
        Vec2i vec2i = this.adjustMouseCoordinates((int)d, (int)d2);
        Vec2i vec2i2 = ClientUtil.getMouse(vec2i.getX(), vec2i.getY());
        d = vec2i2.getX();
        d2 = vec2i2.getY();
        for (Panel panel : this.panels) {
            panel.mouseClick((float)d, (float)d2, n);
        }
        this.panelstul.mouseClick((float)d, (float)d2, n);
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        Vec2i vec2i = this.adjustMouseCoordinates((int)d, (int)d2);
        Vec2i vec2i2 = ClientUtil.getMouse(vec2i.getX(), vec2i.getY());
        d = vec2i2.getX();
        d2 = vec2i2.getY();
        for (Panel panel : this.panels) {
            panel.mouseRelease((float)d, (float)d2, n);
        }
        return super.mouseReleased(d, d2, n);
    }

    public boolean isSearching() {
        return !this.searchField.isEmpty();
    }

    public String getSearchText() {
        return this.searchField.getText();
    }

    public boolean searchCheck(String string) {
        return this.isSearching() && !string.replaceAll(" ", "").toLowerCase().contains(this.getSearchText().replaceAll(" ", "").toLowerCase());
    }

    public boolean isRendered() {
        return this.themeEditor.toRender;
    }

    public static Animation getAnimation() {
        return animation;
    }
}


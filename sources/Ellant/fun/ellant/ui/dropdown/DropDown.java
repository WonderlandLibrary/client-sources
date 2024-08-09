package fun.ellant.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.functions.api.Category;
import fun.ellant.utils.CustomFramebuffer;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.client.Vec2i;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.*;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.ArrayList;
import java.util.List;

public class DropDown extends Screen implements IMinecraft {

    private final List<Panel> panels = new ArrayList<>();
    @Getter
    private static Animation animation = new Animation();

    public DropDown(ITextComponent titleIn) {
        super(titleIn);
        for (Category category : Category.values()) {
            if (category == Category.THEME) continue;
            panels.add(new Panel(category));
        }
        panels.add(new PanelStyle(Category.THEME));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        animation = animation.animate(1, 0.25f, Easings.EXPO_OUT);
        super.init();
    }

    public static float scale = 1.0f;

    @Override
    public void closeScreen() {
        super.closeScreen();
        GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // TODO Auto-generated method stub
        Vec2i fixMouse = adjustMouseCoordinates((int) mouseX, (int) mouseY);

        Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
        mouseX = fix.getX();
        mouseY = fix.getY();

        for (Panel panel : panels) {
            if (MathUtil.isInRegion((float) mouseX, (float) mouseY, panel.getX(), panel.getY(), panel.getWidth(),
                    panel.getHeight())) {
                panel.setScroll((float) (panel.getScroll() + (delta * 20)));
            }
        }

        // System.out.println(delta + " " + scale + " " + mouseX + " " + mouseY))
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        for (Panel panel : panels) {
            panel.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }


    @Override
    public void tick() {
        super.tick();
        final KeyBinding[] pressedKeys = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
                mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump,
                mc.gameSettings.keyBindSprint};

        for (KeyBinding keyBinding : pressedKeys) {
            boolean isKeyPressed = InputMappings.isKeyDown(mc.getMainWindow().getHandle(), keyBinding.getDefault().getKeyCode());
            keyBinding.setPressed(isKeyPressed);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        KawaseBlur.blur.updateBlur(3, 3);
        mc.gameRenderer.setupOverlayRendering(2);
        animation.update();

        if (animation.getValue() < 0.1) {
            closeScreen();
        }
        final float off = 10;
        float width = panels.size() * (135 + off);

        updateScaleBasedOnScreenWidth();

        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());

        Vec2i fixMouse = adjustMouseCoordinates(mouseX, mouseY);

        Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
        mouseX = fix.getX();
        mouseY = fix.getY();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(windowWidth / 2f, windowHeight / 2f, 0);
        GlStateManager.scaled(animation.getValue(), animation.getValue(), 1);
        GlStateManager.scaled(scale, scale, 1);
        GlStateManager.translatef(-windowWidth / 2f, -windowHeight / 2f, 0);

        for (Panel panel : panels) {
            DisplayUtils.drawRoundedRect(panel.getX(), panel.getY(), panel.getWidth(),
                    panel.getHeight() - 2,
                    new Vector4f(7, 7, 7, 7), -1);
        }
        GlStateManager.popMatrix();
        Stencil.readStencilBuffer(1);
        GlStateManager.bindTexture(KawaseBlur.blur.BLURRED.framebufferTexture);
        CustomFramebuffer.drawTexture();
        Stencil.uninitStencilBuffer();

        DisplayUtils.drawContrast(1 - (float) (animation.getValue() / 3f));
            DisplayUtils.drawWhite((float) animation.getValue());

        GlStateManager.pushMatrix();
        GlStateManager.translatef(windowWidth / 2f, windowHeight / 2f, 0);
        GlStateManager.scaled(animation.getValue(), animation.getValue(), 1);
        GlStateManager.scaled(scale, scale, 1);
        GlStateManager.translatef(-windowWidth / 2f, -windowHeight / 2f, 0);
        for (Panel panel : panels) {
            panel.setY(windowHeight / 2f - (747 / 2) / 2f);
            panel.setX((windowWidth / 2f) - (width / 2f) + panel.getCategory().ordinal() *
                    (135 + off) + off / 2f);
            float animationValue = (float) animation.getValue() * scale;

            float halfAnimationValueRest = (1 - animationValue) / 2f;

            float testX = panel.getX() + (panel.getWidth() * halfAnimationValueRest);
            float testY = panel.getY() + (panel.getHeight() * halfAnimationValueRest);
            float testW = panel.getWidth() * animationValue;
            float testH = panel.getHeight() * animationValue;

            testX = testX * animationValue + ((windowWidth - testW) *
                    halfAnimationValueRest);

            Scissor.push();
            Scissor.setFromComponentCoordinates(testX, testY, testW, testH - 0.5f);
            panel.render(matrixStack, mouseX, mouseY);
            Scissor.unset();
            Scissor.pop();
        }
        GlStateManager.popMatrix();
        mc.gameRenderer.setupOverlayRendering();


    }

    private void updateScaleBasedOnScreenWidth() {
        final float PANEL_WIDTH = 135;
        final float MARGIN = 10;
        final float MIN_SCALE = 0.5f;

        float totalPanelWidth = panels.size() * (PANEL_WIDTH + MARGIN);
        float screenWidth = mc.getMainWindow().getScaledWidth();

        if (totalPanelWidth >= screenWidth) {
            scale = screenWidth / totalPanelWidth;
            scale = MathHelper.clamp(scale, MIN_SCALE, 1.0f);
        } else {
            scale = 1f;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Panel panel : panels) {
            panel.keyPressed(keyCode, scanCode, modifiers);
        }
        // TODO Auto-generated method stub
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            animation = animation.animate(0, 0.25f, Easings.EXPO_OUT);
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private Vec2i adjustMouseCoordinates(int mouseX, int mouseY) {
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        float adjustedMouseX = (mouseX - windowWidth / 2f) / scale + windowWidth / 2f;
        float adjustedMouseY = (mouseY - windowHeight / 2f) / scale + windowHeight / 2f;

        return new Vec2i((int) adjustedMouseX, (int) adjustedMouseY);
    }

    private double pathX(float mouseX, float scale) {
        if (scale == 1) return mouseX;
        int windowWidth = mc.getMainWindow().scaledWidth();
        int windowHeight = mc.getMainWindow().scaledHeight();
        mouseX /= (scale);
        mouseX -= (windowWidth / 2f) - (windowWidth / 2f) * (scale);
        return mouseX;
    }

    private double pathY(float mouseY, float scale) {
        if (scale == 1) return mouseY;
        int windowWidth = mc.getMainWindow().scaledWidth();
        int windowHeight = mc.getMainWindow().scaledHeight();
        mouseY /= scale;
        mouseY -= (windowHeight / 2f) - (windowHeight / 2f) * (scale);
        return mouseY;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixMouse = adjustMouseCoordinates((int) mouseX, (int) mouseY);

        Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
        mouseX = fix.getX();
        mouseY = fix.getY();

        for (Panel panel : panels) {
            panel.mouseClick((float) mouseX, (float) mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // TODO Auto-generated method stub
        Vec2i fixMouse = adjustMouseCoordinates((int) mouseX, (int) mouseY);

        Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
        mouseX = fix.getX();
        mouseY = fix.getY();
        for (Panel panel : panels) {
            panel.mouseRelease((float) mouseX, (float) mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

}

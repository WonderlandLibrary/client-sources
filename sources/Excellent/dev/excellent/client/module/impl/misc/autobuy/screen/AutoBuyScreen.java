package dev.excellent.client.module.impl.misc.autobuy.screen;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.client.module.impl.misc.autobuy.pages.AutoBuyAddItemPage;
import dev.excellent.client.module.impl.misc.autobuy.pages.AutoBuyEditItemPage;
import dev.excellent.client.module.impl.misc.autobuy.pages.AutoBuyMainPage;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@SuppressWarnings("FieldMayBeFinal")
@Getter
public class AutoBuyScreen extends Screen implements IAccess {

    public AutoBuyScreen() {
        super(StringTextComponent.EMPTY);
    }

    private final Font font = Fonts.INTER_MEDIUM.get(14);

    private boolean exit = false;
    private final Animation scaleAnimation = new Animation(Easing.LINEAR, 300);
    private final Animation alphaAnimation = new Animation(Easing.LINEAR, 300);

    private IAutoBuyPage currentPage = Page.MAIN.screen;

    public void switchPage(Page page) {
        if (!currentPage.equals(page.screen)) {
            currentPage = page.screen;
            currentPage.init();
        }
    }

    @Override
    protected void init() {
        super.init();
        SoundUtil.playSound("guiopen.wav", 1);
        scaleAnimation.setValue(2);
        alphaAnimation.setValue(0);
        exit = false;

        currentPage.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = (int) mouse.x;
        mouseY = (int) mouse.y;
        ScaleMath.scalePre();
        scaleAnimation.run(exit ? 5 : 1);
        alphaAnimation.run(exit ? 0 : 1);

        closeCheck();

        float x, y, width, height;
        width = 480;
        height = 320;
        x = (float) ((scaled().x / 2F) - (width / 2F));
        y = (float) ((scaled().y / 2F) - (height / 2F));

        float scale = (float) scaleAnimation.getValue();
        GLUtils.scaleStart((float) (scaled().x / 2F), (float) (scaled().y / 2F), scale);

        drawBackground(matrixStack, x, y, width, height);

        drawPages(matrixStack, x + (width / 2F), y + height);

        currentPage.position().set(x, y, width, height);
        currentPage.render(matrixStack, mouseX, mouseY, partialTicks);
        for (Page page : Page.values()) {
            if (page.screen.equals(currentPage)) {
                float margin = 10;
                Fonts.INTER_BOLD.get(16).drawOutline(matrixStack, page.name, x + margin, y + margin, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, alphaAnimation.getValue() * 255)));
            }
        }

        GLUtils.scaleEnd();
        ScaleMath.scalePost();
    }

    private void drawPages(MatrixStack matrixStack, float x, float y) {
        Font font = Fonts.INTER_MEDIUM.get(15);
        float margin = 2.5F;
        float pagesWidth = 0;

        for (Page page : Page.values()) {
            pagesWidth += margin + font.getWidth(page.name) + margin;
        }

        float startX = x - (pagesWidth / 2F);
        float startY = y - font.getHeight() - (margin * 4);
        startX -= 5;

        float offset = 0;
        for (Page page : Page.values()) {
            float pageWidth = margin + font.getWidth(page.name) + margin;
            int alphaPC = (int) (alphaAnimation.getValue() * 235);

            GlStateManager.pushMatrix();
            Color colorFirst = ColorUtil.withAlpha(getTheme().getFirstColor(), alphaPC);
            Color colorSecond = ColorUtil.withAlpha(getTheme().getSecondColor(), alphaPC);

            boolean current = currentPage.equals(page.screen);

            int themeColor = ColorUtil.overCol(colorFirst.hashCode(), colorSecond.hashCode());
            int color = current ? ColorUtil.overCol(themeColor, Color.WHITE.hashCode(), 0.3F) : themeColor;

            boolean bloom = true;
            float round = 1, shadow = 4, dark = 0.2F;
            int halfColor = ColorUtil.multAlpha(color, .5F);
            RectUtil.drawRoundedRectShadowed(matrixStack, startX + offset, startY, startX + offset + pageWidth, startY + font.getHeight(), round, shadow, halfColor, halfColor, halfColor, halfColor, bloom, true, false, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, startX + offset, startY, startX + offset + pageWidth, startY + font.getHeight(), round, .5F, ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), ColorUtil.multDark(color, dark), false, false, true, true);
            GlStateManager.popMatrix();

            font.drawOutline(matrixStack, page.name, startX + offset + margin, startY, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, alphaAnimation.getValue() * 255)));
            offset += pageWidth + (margin * 2F);
        }


    }


    private void drawBackground(MatrixStack matrixStack, float x, float y, float width, float height) {
        int alphaPC = (int) (alphaAnimation.getValue() * 235);

        GlStateManager.pushMatrix();
        Color colorFirst = ColorUtil.withAlpha(getTheme().getFirstColor(), alphaPC);
        Color colorSecond = ColorUtil.withAlpha(getTheme().getSecondColor(), alphaPC);
        int speed = 5;
        int color1 = ColorUtil.lerp(speed, 0, colorFirst, colorSecond).hashCode();
        int color2 = ColorUtil.lerp(speed, 90, colorFirst, colorSecond).hashCode();
        int color3 = ColorUtil.lerp(speed, 180, colorFirst, colorSecond).hashCode();
        int color4 = ColorUtil.lerp(speed, 270, colorFirst, colorSecond).hashCode();
        boolean bloom = true;
        float round = 8, shadow = 8, dark = 0.15F;
        int halfC1 = ColorUtil.multAlpha(color1, .5F), halfC2 = ColorUtil.multAlpha(color2, .5F), halfC3 = ColorUtil.multAlpha(color3, .5F), halfC4 = ColorUtil.multAlpha(color4, .5F);
        RectUtil.drawRoundedRectShadowed(matrixStack, x, y, x + width, y + height, round, shadow, halfC1, halfC2, halfC3, halfC4, bloom, true, false, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, x, y, x + width, y + height, round, .5F, ColorUtil.multDark(color1, dark), ColorUtil.multDark(color2, dark), ColorUtil.multDark(color3, dark), ColorUtil.multDark(color4, dark), false, false, true, true);
        GlStateManager.popMatrix();
    }

    public void closeCheck() {
        boolean scaleCheck = scaleAnimation.isFinished();
        if (exit && scaleCheck) {
            closeScreen();
            exit = false;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        if (!exit) {

            float x, y, width, height;
            width = 480;
            height = 320;
            x = (float) ((scaled().x / 2F) - (width / 2F));
            y = (float) ((scaled().y / 2F) - (height / 2F));

            if (isLClick(button)) {
                x = x + (width / 2F);
                y = y + height;
                Font font = Fonts.INTER_MEDIUM.get(15);
                float margin = 2.5F;
                float pagesWidth = 0;

                for (Page page : Page.values()) {
                    pagesWidth += margin + font.getWidth(page.name) + margin;
                }

                float startX = x - (pagesWidth / 2F);
                float startY = y - font.getHeight() - (margin * 4);

                float offset = 0;
                for (Page page : Page.values()) {
                    float pageWidth = margin + font.getWidth(page.name) + margin;
                    if (isHover(mouseX, mouseY, startX + offset, startY, pageWidth, font.getHeight())) {
                        switchPage(page);
                    }
                    offset += pageWidth + (margin * 2F);
                }
            }

            currentPage.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        if (!exit) {
            currentPage.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!exit && keyCode == GLFW.GLFW_KEY_ESCAPE) {
            SoundUtil.playSound("guiclose.wav", 1);
            exit = true;
        }
        if (!exit) {
            currentPage.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (!exit) {
            currentPage.keyReleased(keyCode, scanCode, modifiers);
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (!exit) {
            currentPage.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        currentPage.onClose();
        for (Page page : Page.values()) {
            page.screen.onClose();
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public AutoBuy getInst() {
        return AutoBuy.singleton.get();
    }


    @AllArgsConstructor
    public enum Page {
        MAIN("Главная", new AutoBuyMainPage()),
        ADD_ITEM("Добавить", new AutoBuyAddItemPage()),
        EDIT_ITEM("Изменить", new AutoBuyEditItemPage());
        private final String name;
        private final IAutoBuyPage screen;
    }
}

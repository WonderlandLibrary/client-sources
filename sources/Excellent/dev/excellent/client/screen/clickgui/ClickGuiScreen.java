package dev.excellent.client.screen.clickgui;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.client.screen.clickgui.component.category.CategoryComponent;
import dev.excellent.client.screen.clickgui.component.module.ModuleComponent;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.util.render.text.TextAlign;
import dev.excellent.impl.util.render.text.TextBox;
import dev.excellent.impl.util.shader.factory.RenderFactory;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClickGuiScreen extends Screen implements IAccess, IShader {
    private List<CategoryComponent> categoryComponents;
    private ScrollUtil scrollUtilV;
    private ScrollUtil scrollUtilH;
    @Setter
    public ModuleComponent expandedModule = null;
    @Setter
    public Module hoveredModule = null;
    private boolean exit = false;
    private TextBox searchField;

    public ClickGuiScreen() {
        super(StringTextComponent.EMPTY);
        cppInit();
    }

    @Protect(Protect.Type.VIRTUALIZATION)
    @Native
    private void cppInit() {
        searchField = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(20), ColorUtil.getColor(255, 255, 255), TextAlign.CENTER, "нажмите CTRL + F", 0);
        categoryComponents = new ArrayList<>();
        scrollUtilV = new ScrollUtil();
        scrollUtilH = new ScrollUtil();
        scaleAnimation = new Animation(Easing.LINEAR, 300);
        alphaAnimation = new Animation(Easing.LINEAR, 300);
        searchFieldAlphaAnimation = new Animation(Easing.LINEAR, 300);
        mouseAnimation = new Animation(Easing.LINEAR, 300);
        firstInit = true;
        float posX = 0;
        for (final Category category : Category.values()) {
            CategoryComponent component = new CategoryComponent(category);
            component.getPosition().set(posX, 0);
            categoryComponents.add(component);
            posX += component.getWidth() + 5;
        }

    }

    private Animation scaleAnimation;
    private Animation alphaAnimation;
    private Animation searchFieldAlphaAnimation;
    private Animation mouseAnimation;
    private boolean firstInit;

    @Native
    private void cppFirstInit() {
        float margin = 20F;
        double categoryHeight = categoryComponents.stream()
                .filter(CategoryComponent::isExpanded)
                .mapToDouble(CategoryComponent::getHeight)
                .max()
                .orElse(0) + margin;
        scrollUtilH.setMax((float) scaled().x, (categoryComponents.size() * (100 + 5) - 5));
        scrollUtilV.setMax((float) scaled().y, (float) -(categoryHeight));

        scrollUtilH.setScroll(scrollUtilH.getMax() / 2F);
        scrollUtilH.setTarget(scrollUtilH.getMax() / 2F);
        scrollUtilV.setScroll(scrollUtilV.getMax() * 0.8F);
        scrollUtilV.setTarget(scrollUtilV.getMax() * 0.8F);
        firstInit = false;
    }

    @Override
    public void init() {
        GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
        searchField.setText("");
        searchField.setSelected(false);
        hoveredModule = null;
        if (firstInit) {
            cppFirstInit();
        }

        SoundUtil.playSound("guiopen.wav", 0.5);
        exit = false;
        scaleAnimation.setValue(2);
        alphaAnimation.setValue(0);
        searchFieldAlphaAnimation.setValue(0);

        scaleAnimation.setEasing(Easing.EASE_OUT_QUAD);
        mouseAnimation.setEasing(Easing.EASE_OUT_EXPO);

        for (final CategoryComponent category : categoryComponents) {
            category.init();
        }
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = (int) mouse.x;
        mouseY = (int) mouse.y;

        int finalMouseX = mouseX;
        int finalMouseY = mouseY;
        RenderFactory.addGuiTask(() -> draw(matrixStack, finalMouseX, finalMouseY, partialTicks));
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void draw(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        matrixStack.push();
        alphaAnimation.run(searchField.isSelected() ? 0.5 : (exit ? 0 : 1));
        scaleAnimation.run(exit ? 2 : 1);
        mouseAnimation.run(exit ? (scaled().x / 2F) - ((scaled().x - mouseX)) : 0);

        closeCheck();
        componentMove(matrixStack);

        ClickGui clickGui = ClickGui.singleton.get();

        if (clickGui.blur.getValue()) {
            clickGui.BLUR_SHADER.addTask2D(() -> RectUtil.drawRect(matrixStack, (float) 0, (float) 0, (float) scaled().x, (float) scaled().y, ColorUtil.getColor(0, 0, 0, (int) (alphaAnimation.getValue() * 255))));
        }

        if (clickGui.shader.getValue()) {
            if (clickGui.shaderMode.is("Треугольники")) {
                CLICK_GUI_SHADER.drawShader1(mouseX, mouseY, excellent.getInitTime(), (float) (alphaAnimation.getValue() * 0.2));
            }
            if (clickGui.shaderMode.is("Треугольники 2")) {
                CLICK_GUI_SHADER.drawShader2(mouseX, mouseY, excellent.getInitTime(), (float) (alphaAnimation.getValue() * 0.2));
            }
        }

        final int vingetteColor = ColorUtil.getColor(0, 0, 0, (int) (alphaAnimation.getValue() * 180));

        RectUtil.drawRect(matrixStack, (float) 0, (float) 0, (float) scaled().x, (float) scaled().y, vingetteColor);

        if (ClickGui.singleton.get().gradient.getValue()) {
            for (int index = 0; index < scaled().x / 50; index++) {
                float xPos = index * 60;
                float yPos = (float) (scaled().y + 30);

                int color = getTheme().getClientColor(index * 30, (float) alphaAnimation.getValue() * 0.5F);
                RectUtil.drawShadowSegmentsExtract(matrixStack, xPos, yPos, xPos, yPos, 0, 150, color, color, color, color, true, true);
            }
        }

        float scale = (float) scaleAnimation.getValue();
        GLUtils.scaleStart((float) ((scaled().x / 2F) - mouseAnimation.getValue()), (float) (scaled().y / 2f), scale);

        if (!searchField.isSelected()) {
            renderSearchField(matrixStack);
        }

        for (final CategoryComponent category : categoryComponents) {
            category.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        if (searchField.isSelected()) {
            renderSearchField(matrixStack);
        }

        GLUtils.scaleEnd();

        if (hoveredModule != null && alphaAnimation.isFinished()) {
            RenderUtil.renderClientRect(matrixStack, mouseX + 10, mouseY, Fonts.INTER_BOLD.get(14).getWidth(hoveredModule.getModuleInfo().description()) + 4, Fonts.INTER_BOLD.get(14).getHeight(), false, 0, (int) (searchField.isSelected() ? searchFieldAlphaAnimation.getValue() : alphaAnimation.getValue()) * 64);
            Fonts.INTER_BOLD.get(14).drawGradient(matrixStack, hoveredModule.getModuleInfo().description(), mouseX + 12, mouseY, ColorUtil.replAlpha(ColorUtil.overCol(-1, getTheme().getFirst().hashCode()), (int) Mathf.clamp(5, 255, alphaAnimation.getValue() * 255)), ColorUtil.replAlpha(ColorUtil.overCol(-1, getTheme().getSecond().hashCode()), (int) Mathf.clamp(5, 255, alphaAnimation.getValue() * 255)));
        }

        matrixStack.pop();
    }

    private void renderSearchField(MatrixStack matrixStack) {
        searchField.setEmptyText("нажмите CTRL + F");
        float searchFieldHeight = searchField.getFont().getHeight() * 1.5F;

        double searchFieldX = (scaled().x / 2F);
        double searchFieldY = (scaled().y - (scaled().y / 10F));

        searchField.position.set(searchFieldX, searchFieldY + ((searchFieldHeight / 2F) - (searchField.getFont().getHeight() / 2F)));

        float searchWidth = isSearching() ? searchField.getFont().getWidth(searchField.getText()) + 10F : searchField.getFont().getWidth(searchField.getEmptyText()) / 2 + 10F;
        searchField.setWidth(searchWidth);

        searchFieldAlphaAnimation.run(searchField.isSelected() ? 1 : (exit ? 0 : 0.3));

        searchField.setColor(ColorUtil.replAlpha(searchField.getColor(), (int) (Mathf.clamp(5, 255, searchFieldAlphaAnimation.getValue() * 128))));

        int color = ColorUtil.getColor(0, 0, 0, (int) (searchFieldAlphaAnimation.getValue() * 96));
        int shadowColor = ColorUtil.getColor(0, 0, 0, (int) (searchFieldAlphaAnimation.getValue() * 64));
        boolean bloom = false;
        float round = 2, shadow = 6;

        GlStateManager.pushMatrix();
        RectUtil.drawRoundedRectShadowed(matrixStack, (int) (searchFieldX - searchWidth), (int) searchFieldY, (int) (searchFieldX + searchWidth), (int) (searchFieldY + searchFieldHeight), round, shadow, shadowColor, shadowColor, shadowColor, shadowColor, bloom, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, (int) (searchFieldX - searchWidth), (int) searchFieldY, (int) (searchFieldX + searchWidth), (int) (searchFieldY + searchFieldHeight), round, 0.5F, color, color, color, color, false, false, true, true);

        GlStateManager.popMatrix();

        searchField.draw(matrixStack);
    }

    public void closeCheck() {
        boolean noneMatch = categoryComponents.stream().noneMatch(category -> category.getModuleComponents().stream().anyMatch(ModuleComponent::isBinding));

        boolean scaleCheck = (scaleAnimation.isFinished() && noneMatch);
        if (exit && scaleCheck) {
            closeScreen();
            exit = false;
        }
    }

    private void componentMove(MatrixStack matrixStack) {
        float margin = 20F;
        double categoryHeight = categoryComponents.stream()
                .filter(CategoryComponent::isExpanded)
                .mapToDouble(CategoryComponent::getHeight)
                .max()
                .orElse(0) + margin;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT_SHIFT.keyCode)) {
            scrollUtilV.setEnabled(false);
            scrollUtilH.setEnabled(true);
            scrollUtilH.update();
            scrollUtilH.setMax((float) scaled().x, (categoryComponents.size() * (100 + 5) - 5));
            scrollUtilH.renderH(matrixStack, new Vector2f(5F, (float) (scaled().y - 5)), (float) (scaled().x - 10), (float) (alphaAnimation.getValue() * 255));
        } else {
            scrollUtilH.setEnabled(false);
            scrollUtilV.setEnabled(true);
            scrollUtilV.update();
            scrollUtilV.setMax((float) scaled().y, (float) -(categoryHeight));
        }
        scrollUtilV.render(matrixStack, new Vector2f(5, 5), (float) (scaled().y - 10), (float) (alphaAnimation.getValue() * 255));
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        if (!exit) {
            if (searchField.isSelected() && !isHover(mouseX, mouseY, searchField.position.x, searchField.position.y, searchField.getWidth(), searchField.getFont().getHeight())) {
                searchField.setSelected(false);
                return super.mouseClicked(mouseX, mouseY, button);
            }
            if (!searchField.isSelected())
                for (final CategoryComponent category : categoryComponents) {
                    category.mouseClicked(mouseX, mouseY, button);
                }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        if (!searchField.isSelected()) {
            for (final CategoryComponent category : categoryComponents) {
                category.mouseReleased(mouseX, mouseY, button);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!exit) {
            if (searchField.isSelected() && keyCode == Keyboard.KEY_ESCAPE.keyCode) {
                searchField.setSelected(false);
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT_CONTROL.keyCode) && keyCode == Keyboard.KEY_F.keyCode) {
                searchField.setSelected(!searchField.isSelected());
            }
            searchField.keyPressed(keyCode);
        }
        if (!searchField.isSelected()) {
            boolean noneMatch = categoryComponents.stream().noneMatch(category -> category.getModuleComponents().stream().anyMatch(ModuleComponent::isBinding));
            if (!exit && keyCode == GLFW.GLFW_KEY_ESCAPE && noneMatch) {
                scaleAnimation.setEasing(Easing.EASE_IN_QUAD);
                mouseAnimation.setEasing(Easing.EASE_IN_EXPO);
                hoveredModule = null;
                exit = true;
                SoundUtil.playSound("guiclose.wav", 0.5);
            }
            if (!exit) {
                for (CategoryComponent component : categoryComponents) {
                    component.keyPressed(keyCode, scanCode, modifiers);
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (!exit) {
            searchField.charTyped(codePoint);
            if (!searchField.isSelected()) {
                for (final CategoryComponent category : categoryComponents) {
                    category.charTyped(codePoint, modifiers);
                }
            }
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        searchField.setText("");
        searchField.setSelected(false);
        for (final CategoryComponent category : categoryComponents) {
            category.onClose();
        }
        scaleAnimation.setValue(2);
        alphaAnimation.setValue(0);
        searchFieldAlphaAnimation.setValue(0);
        hoveredModule = null;
        ClickGui.singleton.get().setEnabled(false);
        GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
        super.onClose();
    }

    public boolean isSearching() {
        return !searchField.isEmpty();
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public boolean searchCheck(String text) {
        return isSearching() && !text
                .replaceAll(" ", "")
                .toLowerCase()
                .contains(getSearchText()
                        .replaceAll(" ", "")
                        .toLowerCase());
    }
}

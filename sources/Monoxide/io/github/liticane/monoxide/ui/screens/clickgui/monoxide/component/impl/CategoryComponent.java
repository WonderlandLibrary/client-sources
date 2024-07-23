package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.impl;

import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api.Component;
import io.github.liticane.monoxide.util.render.animation.advanced.Animation;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.EaseInOutQuad;
import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.util.render.updated.NewColorUtil;
import io.github.liticane.monoxide.util.render.updated.NewRenderUtil;
import io.github.liticane.monoxide.util.render.updated.NewStencilUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryComponent extends Component {

    public Animation openAnimation;
    public Animation expandAnimation;

    private final ModuleCategory category;
    private final List<ModuleComponent> modules = new ArrayList<>();

    private int lastMouseX, lastMouseY;
    private float lastRotationAngle;

    public CategoryComponent(MonoxideClickGuiScreen parent, ModuleCategory category, float x, float y) {
        super(parent);
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 135;
        this.height = 25;

        for (Module module : ModuleManager.getInstance().getModules(category)) {
            ModuleComponent moduleComponent = new ModuleComponent(this.parent, module);
            this.modules.add(moduleComponent);
        }
    }

    @Override
    public void init() {
        modules.forEach(ModuleComponent::init);

        expandAnimation = new EaseInOutQuad(500, 1.F);
        expandAnimation.setDirection(Direction.FORWARDS);
        expandAnimation.reset();

        openAnimation = new EaseInOutQuad(100 + 100 * (category.getIndex() + 1), 1.F);
        openAnimation.setDirection(Direction.FORWARDS);
        openAnimation.reset();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int i = 0;

        final float deltaMouseX = mouseX - lastMouseX;
        final float deltaMouseY = mouseY - lastMouseY;

        if (dragging) {
            setX(getX() + deltaMouseX);
            setY(getY() + deltaMouseY);
        }

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        GL11.glPushMatrix();

        float goalRotationAngle = dragging ? MathHelper.clamp_float(deltaMouseX * 4, -35, 35) : 0;
        goalRotationAngle = lastRotationAngle + (goalRotationAngle - lastRotationAngle) / 45F;

        GL11.glTranslated(mouseX, mouseY, 0);
        GL11.glRotated(goalRotationAngle, 0, 0, 1);
        GL11.glTranslated(-mouseX, -mouseY, 0);

        lastRotationAngle = goalRotationAngle;

        for (ModuleComponent component : modules) {
            component.setWidth(width);
            component.setHeight(height);

            component.setX(x);
            component.setY(y + height + component.height * i);
            ++i;
        }

        Color color1 = new Color(85, 45, 85);
        Color color2 = new Color(25, 45, 85);

        NewRenderUtil.scale(x, y, openAnimation.getOutput().floatValue(), () -> {
            NewRenderUtil.horizontalGradient(
                    x, y, width, height,
                    NewColorUtil.interpolateColorsBackAndForth(5, (int) x, color1, color2),
                    NewColorUtil.interpolateColorsBackAndForth(5, (int) (x + width), color1, color2)
            );

            FontRenderer fontRenderer = FontStorage.getInstance().findFont("SF UI Medium", 24);

            fontRenderer.drawStringWithShadow(
                    category.getName(),
                    x + width / 2.0F - fontRenderer.getStringWidth(category.getName()) / 2.0F,
                    y + height / 2.0F - fontRenderer.getHeight() / 2.0F,
                    Color.WHITE.getRGB()
            );

            float actualHeight = modules.size() * height * expandAnimation.getOutput().floatValue();

            NewRenderUtil.rectangle(
                    x, y + height, width, actualHeight,
                    new Color(25, 25, 25)
            );

            Runnable stencilInit = () -> NewRenderUtil.rectangle(x, y + height, width, actualHeight, Color.WHITE);
            Runnable stencilData = () -> modules.forEach(module -> module.draw(mouseX, mouseY));

            NewStencilUtil.renderStencil(
                    stencilInit,
                    stencilData
            );
        });

        GL11.glPopMatrix();
    }

    @Override
    public void clicked(int mouseX, int mouseY, int button) {
        if (NewRenderUtil.isHovered(mouseX, mouseY, x, y, width, height) && button == 0) {
            dragging = true;
        }

        modules.forEach(module -> module.clicked(mouseX, mouseY, button));
    }

    @Override
    public void released(int mouseX, int mouseY, int button) {
        dragging = false;

        modules.forEach(module -> module.released(mouseX, mouseY, button));
    }

    @Override
    public void keyboard(char character, int keyCode) {
        if (keyCode == 1)
            expandAnimation.setDirection(Direction.BACKWARDS);

        modules.forEach(module -> module.keyboard(character, keyCode));
    }
}

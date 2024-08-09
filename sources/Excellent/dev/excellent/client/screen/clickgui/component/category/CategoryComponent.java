package dev.excellent.client.screen.clickgui.component.category;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.screen.clickgui.component.module.ModuleComponent;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;
import lombok.Getter;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class CategoryComponent implements IAccess, IScreen {
    private Font font;
    private Category category;

    private boolean expanded = false;
    private Vector2f position;
    private float width;
    private float height;
    private List<ModuleComponent> moduleComponents;
    private Animation expandAnimation;

    public CategoryComponent(Category category) {
        cppInit(category);
    }

    @Protect(Protect.Type.VIRTUALIZATION)
    @Native
    private void cppInit(Category category) {
        font = Fonts.INTER_BOLD.get(18);
        position = new Vector2f();
        width = 100F;
        height = 20F;
        moduleComponents = new ArrayList<>();
        expandAnimation = new Animation(Easing.LINEAR, 50);
        this.category = category;
        for (final Module module : getModules()) {
            moduleComponents.add(new ModuleComponent(module, new Vector2f(), width));
        }
    }

    private List<Module> getModules() {
        List<Module> modulesList = excellent.getModuleManager().get(category);
        modulesList.sort(Comparator.comparing(module -> -font.getWidth(module.getModuleInfo().name())));
        return modulesList;
    }

    @Override
    public void init() {
        for (ModuleComponent module : moduleComponents) {
            module.init();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float margin = 20F;
        expandAnimation.run(!expanded ? margin : (height + margin + 2));
        float x = (float) ((scaled().x + position.x) + excellent.getClickGui().getScrollUtilH().getScroll()) - (excellent.getClickGui().getCategoryComponents().size() * (width + 5) - 5);
        float y = (float) (scaled().y + excellent.getClickGui().getScrollUtilV().getScroll());

        int headerTextColor = ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (excellent.getClickGui().getAlphaAnimation().getValue() * 255)));

        RenderUtil.renderClientRect(matrixStack, x, y, width, (float) expandAnimation.getValue(), true, margin, (int) (excellent.getClickGui().getAlphaAnimation().getValue() * 200));

        font.draw(matrixStack, category.getName(), x + 5 + Fonts.CATEGORY_ICON.get(20).getWidth(category.getIcon()) + 5, y + 5F, headerTextColor);

        Fonts.CATEGORY_ICON.get(20).draw(matrixStack, category.getIcon(), x + 5, y + 5F, headerTextColor);

        if (!expandAnimation.isFinished()) {
            StencilBuffer.init();
            RectUtil.drawRect(matrixStack, x, y + margin, x + width, y + margin + (float) expandAnimation.getValue() - margin, -1);
            StencilBuffer.read(StencilBuffer.Action.OUTSIDE.getAction());
        }

        float offset = 0F;
        for (ModuleComponent module : moduleComponents) {
            if (excellent.getClickGui().searchCheck(module.getModule().getDisplayName())) continue;
            if (expandAnimation.getValue() != margin) {
                if (module.getOffset() != offset)
                    module.setOffset(offset);
                if (module.getParent() != this)
                    module.setParent(this);
                module.setPosition(new Vector2f(x, y));
                module.render(matrixStack, mouseX, mouseY, partialTicks);
            } else {
                module.setExpanded(false);
            }
            offset += module.getHeight() + 2;
        }
        if (!expandAnimation.isFinished()) {
            StencilBuffer.cleanup();
        }
        height = offset;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float margin = 20F;
        float x = (float) ((scaled().x + position.x) + excellent.getClickGui().getScrollUtilH().getScroll()) - (excellent.getClickGui().getCategoryComponents().size() * (width + 5) - 5);
        float y = (float) (scaled().y + excellent.getClickGui().getScrollUtilV().getScroll()) + position.y;
        if (isHover(mouseX, mouseY, x, y, width, margin) && isRClick(button)) {
            expanded = !expanded;
            if (expanded) {
                SoundUtil.playSound("moduleopen.wav");
            } else {
                SoundUtil.playSound("moduleclose.wav");
            }
        }
        if (isHover(mouseX, mouseY, x, y + margin, width, height) && expanded) {
            for (ModuleComponent module : moduleComponents) {
                if (excellent.getClickGui().searchCheck(module.getModule().getDisplayName())) continue;
                module.mouseClicked(mouseX, mouseY, button);
            }
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (ModuleComponent module : moduleComponents) {
            if (excellent.getClickGui().searchCheck(module.getModule().getDisplayName())) continue;
            module.mouseReleased(mouseX, mouseY, button);
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (expanded) {
            for (ModuleComponent module : moduleComponents) {
                if (excellent.getClickGui().searchCheck(module.getModule().getDisplayName())) continue;
                module.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return true;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (expanded) {
            for (ModuleComponent module : moduleComponents) {
                if (excellent.getClickGui().searchCheck(module.getModule().getDisplayName())) continue;
                module.charTyped(codePoint, modifiers);
            }
        }
        return true;
    }

    @Override
    public void onClose() {
        for (ModuleComponent module : moduleComponents) {
            module.onClose();
        }
    }
}

package dev.excellent.client.screen.clickgui.component.module;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.client.screen.clickgui.component.category.CategoryComponent;
import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.client.screen.clickgui.component.value.impl.*;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.font.Icon;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.*;
import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Getter
@Setter
public class ModuleComponent implements IAccess, IScreen {
    private Module module;
    private Vector2f position;
    private float width;
    private float offset = 0;
    private float height;
    private boolean expanded = false;
    private Font font;
    private CategoryComponent parent;
    public ArrayList<ValueComponent> valueList;
    private Animation offsetAnimation;
    private Animation expandAnimation;
    private Animation rotateAnimation;
    private Animation hoverAnimation;
    @SuppressWarnings("FieldMayBeFinal")
    public boolean cursorHovered = false;

    public ModuleComponent(Module module, Vector2f position, float width) {
        cppInit(module, position, width);
    }

    @Native
    private void cppInit(Module module, Vector2f position, float width) {
        font = Fonts.INTER_SEMIBOLD.get(14);
        valueList = new ArrayList<>();
        offsetAnimation = new Animation(Easing.LINEAR, 50);
        expandAnimation = new Animation(Easing.LINEAR, 50);
        rotateAnimation = new Animation(Easing.LINEAR, 100);
        hoverAnimation = new Animation(Easing.LINEAR, 100);
        this.module = module;
        this.position = position;
        this.width = width;
        for (Value<?> value : module.getAllValues()) {
            if (value instanceof ModeValue) {
                valueList.add(new ModeValueComponent(value, width));
            } else if (value instanceof BooleanValue) {
                valueList.add(new BooleanValueComponent(value, width));
            } else if (value instanceof StringValue) {
                valueList.add(new StringValueComponent(value, width));
            } else if (value instanceof NumberValue) {
                valueList.add(new NumberValueComponent(value, width));
            } else if (value instanceof BoundsNumberValue) {
                valueList.add(new BoundsNumberValueComponent(value, width));
            } else if (value instanceof ListValue<?>) {
                valueList.add(new ListValueComponent(value, width));
            } else if (value instanceof ColorValue) {
                valueList.add(new ColorValueComponent(value, width));
            } else if (value instanceof MultiBooleanValue) {
                valueList.add(new MultiBooleanValueComponent(value, width));
            } else if (value instanceof KeyValue) {
                valueList.add(new KeyValueComponent(value, width));
            }
        }
    }

    @Override
    public void init() {
        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.init();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (excellent.getClickGui().getExpandedModule() != this) expanded = false;
        font = Fonts.INTER_MEDIUM.get(14);
        float x = position.x;
        float y = position.y;
        rotateAnimation.run(expanded ? 1 : 0);

        float margin = 5;

        float moduleHeight = 15;

        int modulebackgroundColor = module.isEnabled() ? getTheme().getClientColor(0, (float) (excellent.getClickGui().getAlphaAnimation().getValue() * 0.15F)) : ColorUtil.getColor(40, 40, 40, (int) (excellent.getClickGui().getAlphaAnimation().getValue() * 64));
        int moduleTextColor = module.isEnabled() ? ColorUtil.getColor(230, 255, 255, (int) Mathf.clamp(5, 255, (excellent.getClickGui().getAlphaAnimation().getValue() * 255))) : ColorUtil.getColor(180, 180, 180, (int) Mathf.clamp(5, 255, (excellent.getClickGui().getAlphaAnimation().getValue() * 255)));

        float yPos = y + 20 + 2;
        expandAnimation.run(height);
        offsetAnimation.run(this.offset);

        float offset = (float) offsetAnimation.getValue();

        RectUtil.drawRect(matrixStack, x + 2, yPos + offset, x + 2 + width - 4, (float) (yPos + offset + expandAnimation.getValue()), modulebackgroundColor);

        boolean hovered = isHover(mouseX, mouseY, x + 2, yPos + offset, width - 4, moduleHeight);

        if (hovered) {
            if (!cursorHovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                cursorHovered = true;
            }
        } else {
            if (cursorHovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                cursorHovered = false;
            }
        }

        hoverAnimation.setDuration(150);

        int hoveredColor = getTheme().getClientColor(0, (float) Math.min(hoverAnimation.getValue() * 0.35F, excellent.getClickGui().getAlphaAnimation().getValue() * 0.35F));
        RectUtil.drawRect(matrixStack, x + 2, yPos + offset, x + 2 + width - 4, (float) (yPos + offset + expandAnimation.getValue()), hoveredColor);

        hoverAnimation.run(hovered ? 1 : 0);
        Module hoveredModule = excellent.getClickGui().hoveredModule;
        if (hovered) {
            if (hoveredModule != this.module && parent.isExpanded()) {
                if (ClickGui.singleton.get().sounds.getValue())
                    SoundUtil.playSound("moduleopen.wav", 0.05F);
                excellent.getClickGui().setHoveredModule(this.module);
            }
        } else {
            if (hoveredModule == this.module) {
                excellent.getClickGui().setHoveredModule(null);
            }
        }

        if (isBinding()) {
            font.draw(matrixStack, "[" + Keyboard.keyName(module.getKeyCode()) + "] " + "Бинд..", x + margin, yPos + ((moduleHeight / 2F) - (font.getHeight() / 2F)) + offset, moduleTextColor);
        } else {
            font.draw(matrixStack, module.getModuleInfo().name(), x + margin, yPos + ((moduleHeight / 2F) - (font.getHeight() / 2F)) + offset, moduleTextColor);
            if (!valueList.isEmpty()) {
                Font settings = Fonts.ICON.get(15);
                String settingsTextDown = Icon.DOWN.getCharacter();
                String settingsTextUP = Icon.UP.getCharacter();

                float settingsWidthDown = settings.getWidth(settingsTextDown);
                float settingsWidthUp = settings.getWidth(settingsTextUP);

                float settingsXDown = x + width - margin - (settingsWidthDown / 2F);
                float settingsXUp = x + width - margin - (settingsWidthUp / 2F);

                float settingsY = yPos + ((moduleHeight / 2F)) + offset;

                if (rotateAnimation.getValue() < 0.5) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translated(settingsXDown, settingsY, 0);
                    GlStateManager.scaled(1, 1 - rotateAnimation.getValue(), 1);
                    GlStateManager.translated(-settingsXDown, -settingsY, 0);
                    settings.drawRight(matrixStack, settingsTextDown, x + width - 7.5, yPos + ((moduleHeight / 2F) - (settings.getHeight() / 2F)) + offset, moduleTextColor);
                    GlStateManager.popMatrix();
                } else {
                    GlStateManager.pushMatrix();
                    GlStateManager.translated(settingsXUp, settingsY, 0);
                    GlStateManager.scaled(1, rotateAnimation.getValue(), 1);
                    GlStateManager.translated(-settingsXUp, -settingsY, 0);
                    settings.drawRight(matrixStack, settingsTextUP, x + width - 7.5, yPos + ((moduleHeight / 2F) - (settings.getHeight() / 2F)) + offset, moduleTextColor);
                    GlStateManager.popMatrix();
                }

            }
        }

        if (!expanded && parent.getExpandAnimation().isFinished()) {
            StencilBuffer.init();
            RectUtil.drawRect(matrixStack, x + 2, yPos + offset + moduleHeight, x + 2 + width - 4, (float) (yPos + offset + moduleHeight + expandAnimation.getValue() - moduleHeight), -1);
            StencilBuffer.read(StencilBuffer.Action.OUTSIDE.getAction());
        }

        if (expanded) {
            RectUtil.drawRect(matrixStack, x + 5, yPos + offset + moduleHeight, x + 5 + width - 10, yPos + offset + moduleHeight + 0.5F, ColorUtil.getColor(215, 255, 255, (int) (excellent.getClickGui().getAlphaAnimation().getValue() * 50)));
            float i = 0;
            for (ValueComponent value : valueList) {
                if (isVisible(value)) {
                    continue;
                }
                value.setPosition(new Vector2f(x, yPos + 1 + moduleHeight + offset + i));
                value.render(matrixStack, mouseX, mouseY, partialTicks);

                i += value.height;
            }
            height = moduleHeight + i;
        } else {
            height = moduleHeight;
        }
        if (!expanded && parent.getExpandAnimation().isFinished()) {
            StencilBuffer.cleanup();
        }
    }

    private boolean isVisible(ValueComponent component) {
        return component.getValue() != null && component.getValue().getHideIf() != null && component.getValue().getHideIf().getAsBoolean();
    }

    @Getter
    @Setter
    private boolean binding = false;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float x = position.x;
        float y = position.y;

        float moduleHeight = 15;

        float yPos = y + 20 + 2;

        if (isHover(mouseX, mouseY, x, yPos + offset, width, moduleHeight)) {
            if (!isBinding() && isRClick(button)) {
                if (!valueList.isEmpty()) {
                    expanded = !expanded;
                    if (expanded) {
                        SoundUtil.playSound("moduleopen.wav");
                    } else {
                        SoundUtil.playSound("moduleclose.wav");
                    }
                    if (expanded)
                        excellent.getClickGui().setExpandedModule(this);
                }
            }
        }
        if (isHover(mouseX, mouseY, x, yPos + offset, width, moduleHeight)) {
            if (!isBinding() && isLClick(button)) {
                if (!(module instanceof ClickGui))
                    module.toggle();
            } else if (isMClick(button)) {
                excellent.getClickGui().getCategoryComponents()
                        .stream()
                        .filter(component -> component.getCategory().equals(module.getModuleInfo().category()))
                        .forEach(component -> {
                            component.getModuleComponents().stream()
                                    .filter(module -> module != this)
                                    .forEach(module -> module.setBinding(false));
                        });

                setBinding(!isBinding());
            }
        }
        boolean valid = button != Keyboard.MOUSE_MIDDLE.keyCode
                && button != Keyboard.MOUSE_RIGHT.keyCode
                && button != Keyboard.MOUSE_LEFT.keyCode;

        if (isBinding()) {
            if (valid) {
                module.setKeyCode(button);
                setBinding(false);
            }
        } else {
            setBinding(false);
        }

        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.mouseClicked(mouseX, mouseY, button);
            }
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.mouseReleased(mouseX, mouseY, button);
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (isBinding()) {
            if (keyCode == Keyboard.KEY_ESCAPE.keyCode || keyCode == Keyboard.KEY_DELETE.keyCode) {
                module.setKeyCode(Keyboard.KEY_NONE.keyCode);
                setBinding(false);
                return true;
            }

            module.setKeyCode(keyCode);
            setBinding(false);
        }
        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.keyPressed(keyCode, scanCode, modifiers);
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
        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.charTyped(codePoint, modifiers);
            }
        }
        return true;
    }

    @Override
    public void onClose() {
        setBinding(false);
        if (this.isExpanded()) {
            for (ValueComponent value : valueList) {
                value.onClose();
            }
        }
    }
}

package dev.excellent.client.screen.theme;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.component.impl.DragComponent;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.other.Drag;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

import java.util.Arrays;

@Getter
public class ThemeWidget implements IScreen, IAccess, IShader {
    private final Font font = Fonts.INTER_BOLD.get(14);
    private final Vector2f position = new Vector2f();
    private final ScrollUtil scrollUtil = new ScrollUtil();
    private final float height = 100F;
    private final Animation alphaAnimation = new Animation(Easing.LINEAR, 300);
    private final Animation expandAnimation = new Animation(Easing.LINEAR, 50);
    private boolean hovered = false;
    private boolean expanded = false;
    private final Drag drag = new Drag(position, new Vector2f());

    public ThemeWidget() {

    }

    @Override
    public void init() {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (Excellent.getInst().getComponentManager().get(DragComponent.class).getSelectedValue() != null)
            drag.mouseReleased();

        float padding = 4;
        float margin = 20;

        float append = font.getHeight() + padding;
        expandAnimation.run(!expanded ? margin : (height + margin));
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);
        float height = (float) expandAnimation.getValue();

        float width = (float) Arrays.stream(Themes.values())
                .mapToDouble(theme -> padding * 2 + font.getWidth(theme.getName()))
                .max()
                .orElse(100) + 40;


        drag.render(mouseX, mouseY);
        drag.size.set(width, margin);

        float x = drag.position.x;
        float y = drag.position.y;

        RenderUtil.renderClientRect(matrixStack, x, y, width, (float) expandAnimation.getValue(), true, margin, (int) (alphaAnimation.getValue() * 200));

        font.draw(matrixStack, "Client themes", x + 5 + Fonts.CATEGORY_ICON.get(20).getWidth("F") + 5, y + (margin / 2F) - (font.getHeight() / 2F), ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        Fonts.CATEGORY_ICON.get(20).draw(matrixStack, "F", x + 5, y + 5F, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        hovered = isHover(mouseX, mouseY, x, y + margin, width, height - margin);

        scrollUtil.setEnabled(hovered);
        scrollUtil.update();
        scrollUtil.setSpeed(2);

        float offset = 0;
        StencilBuffer.init();
        RectUtil.drawRect(matrixStack, x, y + margin + 2, x + width, (float) (y + margin + 2 + expandAnimation.getValue() - margin - 4), -1);
        StencilBuffer.read(StencilBuffer.Action.OUTSIDE.getAction());

        for (Themes theme : Themes.values()) {
            if (expandAnimation.getValue() != margin) {
                Font cFont = Fonts.INTER_BOLD.get(12);
                boolean selected = theme.equals(getTheme());

                int speed = theme.getSpeed();

                int color1 = switch (theme) {
                    case RAINBOW -> ColorUtil.rainbow(speed, 0, 1, 1, 1);
                    case ASTOLFO -> ColorUtil.skyRainbow(speed, 0);
                    default -> theme.getFirst().hashCode();
                };
                int color2 = switch (theme) {
                    case RAINBOW -> ColorUtil.rainbow(speed, 180, 1, 1, 1);
                    case ASTOLFO -> ColorUtil.skyRainbow(speed, 180);
                    default -> theme.getSecond().hashCode();
                };

                int first = ColorUtil.replAlpha(color1, (int) (alphaAnimation.getValue() * 255));
                int second = ColorUtil.replAlpha(color2, (int) (alphaAnimation.getValue() * 255));

                float rectSize = 8;
                float rectY = (float) (y + scrollUtil.getScroll() + margin + offset + ((append / 2F) - (font.getHeight() / 2F) + (rectSize / 4F)));

                float rectX = x + width - padding - rectSize;
                float round = 2;

                if (rectY > y && rectY < y + height + margin / 2F) {
                    if (theme.equals(Themes.RAINBOW) || theme.equals(Themes.ASTOLFO)) {
                        int color3;
                        int color4;
                        if (theme.equals(Themes.RAINBOW)) {
                            color1 = ColorUtil.rainbow(speed, 0, 1, 1, 1);
                            color2 = ColorUtil.rainbow(speed, 90, 1, 1, 1);
                            color3 = ColorUtil.rainbow(speed, 180, 1, 1, 1);
                            color4 = ColorUtil.rainbow(speed, 270, 1, 1, 1);
                        } else {
                            color1 = ColorUtil.skyRainbow(speed, 0);
                            color2 = ColorUtil.skyRainbow(speed, 90);
                            color3 = ColorUtil.skyRainbow(speed, 180);
                            color4 = ColorUtil.skyRainbow(speed, 270);
                        }
                        rectX -= rectSize + padding;
                        if (selected) {
                            RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + rectSize + rectSize + padding, rectY + rectSize, round, 2, color1, color2, color3, color4, true, true, true, true);
                        }
                        ROUNDED_GRADIENT.draw(rectX, rectY, rectSize + rectSize + padding, rectSize, round, color4, color1, color3, color2);
                    } else {
                        if (selected)
                            RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + rectSize, rectY + rectSize, round, 0.5F, first, first, first, first, false, true, true, true);
                        RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + rectSize, rectY + rectSize, round, selected ? 2 : 0.5F, first, first, first, first, selected, true, true, true);
                        rectX -= rectSize + padding;
                        if (selected)
                            RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + rectSize, rectY + rectSize, round, 0.5F, second, second, second, second, false, true, true, true);
                        RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + rectSize, rectY + rectSize, round, selected ? 2 : 0.5F, second, second, second, second, selected, true, true, true);
                    }

                    int textColor = getTheme().equals(theme) ? ColorUtil.getColor(230, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))) : ColorUtil.getColor(200, 235, 235, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255)));
                    cFont.drawOutline(matrixStack, theme.getName(), x + padding, (int) (y + scrollUtil.getScroll() + ((append / 2F) - (cFont.getHeight() / 2F) + (padding / 2F)) + margin + offset), ColorUtil.replAlpha(textColor, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));
                }
            }
            offset += append + 1;
        }
        scrollUtil.render(matrixStack, new Vector2f(x + 0.5F, y + margin + 3F), height - margin - 6F, (float) (alphaAnimation.getValue() * 255));
        StencilBuffer.cleanup();
        scrollUtil.setMax(offset, height - margin - 2F);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float padding = 4;
        float margin = 20;
        float append = font.getHeight() + padding;
        float width = drag.size.x;
        float height = this.height + margin;
        float x = position.x;
        float y = position.y;

        drag.mouseClicked(mouseX, mouseY, button);

        if (isHover(mouseX, mouseY, x, y, width, margin) && isRClick(button)) {
            expanded = !expanded;
            if (expanded) {
                SoundUtil.playSound("moduleopen.wav");
            } else {
                SoundUtil.playSound("moduleclose.wav");
            }
        }
        if (isHover(mouseX, mouseY, x, y + margin, width, height - margin) && expanded) {
            float offset = 0;
            for (Themes theme : Themes.values()) {
                if (isHover(mouseX, mouseY, x, y + scrollUtil.getScroll() + padding / 2F + margin + offset, width, append) && isLClick(button) && !theme.equals(getTheme())) {
                    SoundUtil.playSound("select.wav");
                    setTheme(theme);
                }
                offset += append + 1;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        drag.mouseReleased();
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public void onClose() {
    }
}

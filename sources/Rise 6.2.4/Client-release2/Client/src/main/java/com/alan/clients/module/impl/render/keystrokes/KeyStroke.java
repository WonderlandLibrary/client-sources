package com.alan.clients.module.impl.render.keystrokes;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@RequiredArgsConstructor
public class KeyStroke implements Accessor {
    private final Vector2f scale;

    private final Vector2f offset;

    private final String name;

    private final KeyBinding binding;

    private final Animation animation = new Animation(Easing.LINEAR, 200);

    public KeyStroke(final Vector2f offset, final String name, final KeyBinding binding) {
        this(new Vector2f(RenderUtil.GENERIC_SCALE, RenderUtil.GENERIC_SCALE), offset, name, binding);
    }

    public KeyStroke(final Vector2f offset, final KeyBinding binding) {
        this(offset, Keyboard.getKeyName(binding.getKeyCode()), binding);
    }

    public void render(Vector2d position) {
        position = new Vector2d(
                position.getX() + offset.getX(),
                position.getY() + offset.getY()
        );

        RenderUtil.roundedRectangle(
                position.getX(), position.getY(),
                scale.getX(), scale.getY(),
                4, ColorUtil.withAlpha(getTheme().getBackgroundShade(), (int) animation.getValue())
        );

        this.updateHeld();

        final Vector2d textSize = new Vector2d(Fonts.MAIN.get(20, Weight.REGULAR).width(name), Fonts.MAIN.get(20, Weight.REGULAR).height());
        final Vector2d textPosition = new Vector2d(
                position.getX() + (scale.getX() * 0.5F) - (Fonts.MAIN.get(20, Weight.REGULAR).width(name) * 0.5F),
                position.getY() + (scale.getY() - textSize.getY()) / 2.0F + 3.0F
        );

        Fonts.MAIN.get(20, Weight.REGULAR).drawWithShadow(name, textPosition.getX(), textPosition.getY(), getTheme().getFirstColor().getRGB());
    }

    public void bloom(final Vector2d position) {
        RenderUtil.roundedRectangle(position.x + offset.x + 0.5F, position.y + offset.y + 0.5F, scale.x - 1, scale.y - 1, 4, getTheme().getDropShadow());
    }

    public void blur(final Vector2d position) {
        RenderUtil.roundedRectangle(position.x + offset.x, position.y + offset.y, scale.x, scale.y, 4, Color.BLACK);
    }

    public void updateHeld() {
        int alpha = getTheme().getBackgroundShade().getAlpha();
        animation.run(binding.isKeyDown() ? Math.min(alpha * 1.4f, 150) : alpha);
    }
}

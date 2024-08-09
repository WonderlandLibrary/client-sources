package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.DragValue;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.settings.KeyBinding;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.ArrayList;

@ModuleInfo(name = "Key Strokes", description = "Отображает нажатия кнопок управления", category = Category.RENDER)
public class Keystrokes extends Module {
    private final DragValue position = new DragValue("Position", this, new Vector2d(5, 100));
    private final BooleanValue space = new BooleanValue("Space", this, true);
    private boolean lastSpace;
    private final ArrayList<KeyStroke> keyStrokes = new ArrayList<>();

    public final Listener<Render2DEvent> onRender2D = event -> {
        int GENERIC_SCALE = 20;
        int gap = 3;
        if (mc.currentScreen != null) {
            if (lastSpace != space.getValue()) {
                keyStrokes.clear();
                keyStrokes.add(new KeyStroke(new Vector2f(GENERIC_SCALE + gap, 0), mc.gameSettings.keyBindForward));
                keyStrokes.add(new KeyStroke(new Vector2f(0, GENERIC_SCALE + gap), mc.gameSettings.keyBindLeft));
                keyStrokes.add(new KeyStroke(new Vector2f(GENERIC_SCALE * 2 + gap * 2, GENERIC_SCALE + gap), mc.gameSettings.keyBindRight));
                keyStrokes.add(new KeyStroke(new Vector2f(GENERIC_SCALE + gap, GENERIC_SCALE + gap), mc.gameSettings.keyBindBack));
                if (space.getValue()) {
                    keyStrokes.add(new KeyStroke(new Vector2f(GENERIC_SCALE * 3 + gap * 2, GENERIC_SCALE / 1.25F), new Vector2f(0, (GENERIC_SCALE + gap) * 2), "SPACE", mc.gameSettings.keyBindJump));
                    position.setSize(new Vector2d(GENERIC_SCALE * 3 + gap * 2, GENERIC_SCALE * 3 + gap));
                } else {
                    position.setSize(new Vector2d(GENERIC_SCALE * 3 + gap * 2, GENERIC_SCALE * 2 + gap));
                }
            }
            lastSpace = space.getValue();
        }
        keyStrokes.forEach(keyStroke -> keyStroke.render(event.getMatrix(), position.position));
    };

    @RequiredArgsConstructor
    public static class KeyStroke implements IAccess {
        private final Vector2f scale;
        private final Vector2f offset;
        private final String name;
        private final KeyBinding binding;
        private final Font font = Fonts.INTER_BOLD.get(14);
        private final Animation animation = new Animation(Easing.LINEAR, 200);

        public KeyStroke(final Vector2f offset, final String name, final KeyBinding binding) {
            this(new Vector2f(20, 20), offset, name, binding);
        }

        public KeyStroke(final Vector2f offset, final KeyBinding binding) {
            this(offset, Keyboard.keyName(binding.getKeyCode().getKeyCode()), binding);
        }

        public void render(MatrixStack matrix, Vector2d position) {
            this.updateHeld();
            RenderUtil.renderClientRect(matrix, (float) position.x + offset.x, (float) position.y + offset.y, scale.x, scale.y, false, 0, (int) Mathf.clamp(5, 255, animation.getValue()));
            font.drawCenter(matrix, name, position.x + offset.x + scale.x / 2F, position.y + offset.y + (scale.y / 2F) - (font.getHeight() / 2F) + 1F, -1);
        }

        public void updateHeld() {
            int alpha = 128;
            animation.run(binding.isKeyDown() ? 255 : alpha);
        }
    }
}

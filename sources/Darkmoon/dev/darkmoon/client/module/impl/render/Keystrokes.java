package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.animation.impl.DecelerateAnimation;
import dev.darkmoon.client.utility.render.animation.impl.EaseInOutQuad;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleAnnotation(name = "Strokes", category = Category.RENDER)
public class Keystrokes extends Module {

    private final NumberSetting offsetValue = new NumberSetting("Offset", 3, 2.5f, 10, 0.5f);
    private final NumberSetting sizeValue = new NumberSetting("Size", 25, 15, 35, 1);
    private static final NumberSetting opacity = new NumberSetting("Opacity", 0.5f, 0, 1, 0.05f);
    private static final NumberSetting radius = new NumberSetting("Radius", 3, 1, 17.5f, 0.5f);


    private final Draggable dragging = DragManager.create(this, "keystrokes", 10, 300);

    private Button keyBindForward;
    private Button keyBindLeft;
    private Button keyBindBack;
    private Button keyBindRight;
    private Button keyBindJump;
    
    @EventTarget
    public void onRender2DEvent(EventRender2D e) {
        float offset = offsetValue.get();
        dragging.setHeight((float) ((sizeValue.get() + offset) * 3) - offset);
        dragging.setWidth((float) ((sizeValue.get() + offset) * 3) - offset);

        if (keyBindForward == null) {
            keyBindForward = new Button(mc.gameSettings.keyBindForward);
            keyBindLeft = new Button(mc.gameSettings.keyBindLeft);
            keyBindBack = new Button(mc.gameSettings.keyBindBack);
            keyBindRight = new Button(mc.gameSettings.keyBindRight);
            keyBindJump = new Button(mc.gameSettings.keyBindJump);
        }

        float x = dragging.getX(), y = dragging.getY(), width = dragging.getWidth(), height = dragging.getHeight(), size = sizeValue.get();

        float increment = size + offset;
        keyBindForward.render(x + width / 2f - size / 2f, y, size);
        keyBindLeft.render(x, y + increment, size);
        keyBindBack.render(x + increment, y + increment, size);
        keyBindRight.render(x + (increment * 2), y + increment, size);
        keyBindJump.render(x, y + increment * 2, width, size);
    }


    public static class Button {
        private final KeyBinding binding;
        private final Animation clickAnimation = new DecelerateAnimation(125, 1);

        public Button(KeyBinding binding) {
            this.binding = binding;
        }

        public void render(float x, float y, float size) {
            render(x, y, size, size);
        }

        public void render(float x, float y, float width, float height) {
            Color color = ColorUtility2.applyOpacity(Color.BLACK, opacity.get());
            clickAnimation.setDirection(binding.isKeyDown() ? Direction.FORWARDS : Direction.BACKWARDS);

            RenderUtility.drawRound(x, y, width, height, radius.get(), color);
            float offsetX = .5f;
           int offsetY = 1;
            Fonts.tenacityBold22.drawCenteredString(Keyboard.getKeyName(binding.getKeyCode()), x + width / 2 + offsetX, y + height / 2 - Fonts.tenacityBold22.getFontHeight() / 2f + offsetY, Color.WHITE.getRGB());
            if (!clickAnimation.finished(Direction.BACKWARDS)) {
                float animation = (float) clickAnimation.getOutput();
                Color color2 = ColorUtility2.applyOpacity(Color.WHITE, (0.5f * animation));
                RenderUtility.scaleStart(x + width / 2f, y + height / 2f, animation);
                float diff = (height / 2f) - radius.get();
                Fonts.tenacityBold22.drawCenteredString(Keyboard.getKeyName(binding.getKeyCode()), x + width / 2 + offsetX, y + height / 2 - Fonts.tenacityBold22.getFontHeight() / 2f + offsetY, Color.WHITE.getRGB());

                RenderUtility.drawRound(x, y, width, height, ((height) - (diff * animation)), color2);
                RenderUtility.scaleEnd();
            }
            Fonts.tenacityBold22.drawCenteredString(Keyboard.getKeyName(binding.getKeyCode()), x + width / 2 + offsetX, y + height / 2 - Fonts.tenacityBold22.getFontHeight() / 2f + offsetY, Color.WHITE.getRGB());
        }
    }
}

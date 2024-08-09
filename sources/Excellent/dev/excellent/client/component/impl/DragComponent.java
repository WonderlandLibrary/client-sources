package dev.excellent.client.component.impl;

import dev.excellent.api.event.impl.input.GuiMouseClickEvent;
import dev.excellent.api.event.impl.input.GuiMouseReleaseEvent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.Component;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.impl.movement.Timer;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.DragValue;
import lombok.Getter;
import net.minecraft.client.gui.screen.ChatScreen;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class DragComponent extends Component implements IAccess {
    private DragValue selectedValue;
    private Vector2d offset;
    private final ArrayList<Module> modules = new ArrayList<>();
    public final TimerUtil CLOSE_TIMER_UTIL = TimerUtil.create();

    public void render() {
        final int width = (int) scaled().x;
        final int height = (int) scaled().y;

        boolean shouldRender = mc.currentScreen instanceof ChatScreen;

        if (!shouldRender) {
            selectedValue = null;
        } else {
            CLOSE_TIMER_UTIL.reset();
        }

        if (CLOSE_TIMER_UTIL.hasReached(2000)) {
            selectedValue = null;
            return;
        }

        modules.clear();
        excellent.getModuleManager().stream()
                .filter(module -> (module.isEnabled() || (module instanceof Timer timer && timer.smart.getValue())) && module.getValues().stream()
                        .anyMatch(value -> value instanceof DragValue))
                .forEach(modules::add);

        if (selectedValue != null) {
            if (!selectedValue.render) return;

            final double mouseX = mc.mouseHelper.getMouseX() * width / mc.getMainWindow().getWidth();
            final double mouseY = mc.mouseHelper.getMouseY() * height / mc.getMainWindow().getHeight();
            final double positionX = mouseX + offset.x;
            final double positionY = mouseY + offset.y;

            selectedValue.targetPosition = new Vector2d(positionX, positionY);

            for (Module module : modules) {
                Optional<Value<?>> positionValues = module.getValues().stream().filter(value ->
                        value instanceof DragValue).findFirst();
                DragValue positionValue = ((DragValue) positionValues.orElse(null));

                positionValue.position.x = Math.max(0, positionValue.position.x);
                positionValue.position.x = Math.min(width - positionValue.size.x, positionValue.position.x);

                positionValue.position.y = Math.max(0, positionValue.position.y);
                positionValue.position.y = Math.min(height - positionValue.size.y, positionValue.position.y);

                positionValue.targetPosition.x = Math.max(0, positionValue.targetPosition.x);
                positionValue.targetPosition.x = Math.min(width - positionValue.size.x, positionValue.targetPosition.x);

                positionValue.targetPosition.y = Math.max(0, positionValue.targetPosition.y);
                positionValue.targetPosition.y = Math.min(height - positionValue.size.y, positionValue.targetPosition.y);

                if (Math.abs(positionValue.position.x - positionValue.targetPosition.x) > 1 || Math.abs(positionValue.position.y - positionValue.targetPosition.y) > 1) {
                    positionValue.position.x = Interpolator.lerp(positionValue.position.x, positionValue.targetPosition.x, 0.1F);
                    positionValue.position.y = Interpolator.lerp(positionValue.position.y, positionValue.targetPosition.y, 0.1F);
                }
            }
        }
    }

    public final Listener<GuiMouseClickEvent> onGuiClick = event -> {
        if (event.getButton() != 0 || excellent.getThemeWidget().getDrag().dragging) {
            return;
        }
        if (mc.currentScreen instanceof ChatScreen) {

            for (final Module module : modules) {
                for (final Value<?> value : module.getValues()) {
                    if (value instanceof final DragValue positionValue) {
                        final Vector2d position = positionValue.position;
                        final Vector2d scale = positionValue.size;
                        final Vector2d mouse = ScaleMath.getMouse(event.getMouseX(), event.getMouseY());
                        final double mouseX = mouse.x;
                        final double mouseY = mouse.y;

                        if (!positionValue.structure && isHover(mouseX, mouseY, position.x, position.y, scale.x, scale.y)) {
                            selectedValue = positionValue;
                            if (!selectedValue.render) return;
                            offset = new Vector2d(position.x - mouseX, position.y - mouseY);
                        }
                    }
                }
            }
        }
    };

    public final Listener<GuiMouseReleaseEvent> onGuiMouseRelease = event -> {
        if (selectedValue != null) {
            selectedValue.targetPosition = selectedValue.position;
            selectedValue = null;
        }
    };

}
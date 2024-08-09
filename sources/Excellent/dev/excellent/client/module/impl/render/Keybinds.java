package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.value.impl.DragValue;
import org.joml.Vector2d;

import java.util.Arrays;

@ModuleInfo(name = "Keybinds", description = "Отображает список забиндженных модулей.", category = Category.RENDER)
public class Keybinds extends Module {

    public final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));
    private final Animation heightAnimation = new Animation(Easing.EASE_OUT_QUART, 100);
    private final Animation widthAnimation = new Animation(Easing.EASE_OUT_QUART, 100);

    private final Font interbold14 = Fonts.INTER_BOLD.get(14);
    private final Font interbold12 = Fonts.INTER_BOLD.get(12);
    private final Font interbold10 = Fonts.INTER_BOLD.get(10);

    public final Listener<Render2DEvent> onRender2D = event -> {
        boolean expand = !excellent.getModuleManager().stream().filter(module -> module.isEnabled() && module.getKeyCode() > 0 && module.getModuleInfo().allowDisable() && module != ClickGui.singleton.get()).toList().isEmpty();

        String name = "Keybinds";

        double width = widthAnimation.getValue();
        position.size.x = excellent.getModuleManager().stream()
                .filter(module -> module.isEnabled() && module.getKeyCode() > 0 && module.getModuleInfo().allowDisable() && module != ClickGui.singleton.get())
                .mapToDouble(module -> Math.max(40 + interbold14.getWidth(name), 5 + 40 + interbold12.getWidth(module.getDisplayName()) + interbold10.getWidth("[" + Arrays.stream(Keyboard.values()).filter(key -> key.keyCode == module.getKeyCode()).findFirst().orElse(Keyboard.KEY_NONE).name + "]") + 5))
                .max()
                .orElse(40 + interbold14.getWidth(name));

        heightAnimation.run(position.size.y);
        widthAnimation.run(position.size.x);

        double height = heightAnimation.getValue();

        double x = position.position.x;
        double y = position.position.y;
        if (mc.gameSettings.showDebugInfo) return;

        double expaned = 14;
        position.size.y = expaned;

        RenderUtil.renderClientRect(event.getMatrix(), (float) x, (float) y, (float) width, (float) height, expand, expaned);

        interbold14.drawCenter(event.getMatrix(), name, x + width / 2F, y + (expaned / 2F) - (interbold14.getHeight() / 2F), -1);

        if (!expand) return;

        int i = 0;
        for (Module module : excellent.getModuleManager().getAll()) {
            if (module.isEnabled() && module.getKeyCode() > 0 && module.getModuleInfo().allowDisable() && module != ClickGui.singleton.get()) {
                String keyName = Keyboard.keyName(module.getKeyCode());
                interbold12.draw(event.getMatrix(), module.getDisplayName(), x + 4, y + 10 + interbold14.getHeight() + i, -1);
                interbold10.drawRight(event.getMatrix(), "[" + keyName + "]", x + width - 5, y + 10.5 + interbold14.getHeight() + i, -1);
                i += 10;
                position.size.y = 10 + interbold14.getHeight() + i + 2;
            }
        }


    };

}

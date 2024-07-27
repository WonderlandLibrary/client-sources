package dev.nexus.modules.impl.render;

import dev.nexus.Nexus;
import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventRender2D;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;

import java.util.Comparator;
import java.util.List;

public class Interface extends Module {
    public Interface() {
        super("Interface", 0, ModuleCategory.RENDER);
    }

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (isNull()) {
            return;
        }

        List<Module> modules = Nexus.INSTANCE.getModuleManager().getEnabledModules();
        modules.sort(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName() + module.getSuffix())));

        int x = 2;
        int y = 2;

        for (Module module : modules) {
            mc.fontRendererObj.drawString(module.getName() + " " + module.getSuffix(), x, y, -1);
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    };
}

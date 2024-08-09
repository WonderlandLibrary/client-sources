package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.impl.ArrowsComponent;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;

@ModuleInfo(name = "Arrows", description = "Отображает ближайщих вам игроков.", category = Category.RENDER)
public class Arrows extends Module {
    @Override
    protected void setup() {
        super.setup();
        getComponent().setRender(isEnabled());
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        getComponent().setRender(true);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        getComponent().setRender(false);
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> getComponent().setRender(isEnabled());
    private final Listener<WorldLoadEvent> onWorldLoad = event -> getComponent().setRender(isEnabled());

    private ArrowsComponent getComponent() {
        return excellent.getComponentManager().get(ArrowsComponent.class);
    }
}

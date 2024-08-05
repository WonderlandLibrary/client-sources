package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.notification.NotificationManager;

public class Notification extends Module {
    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Dog", "Flux"}, "Flux");
    public Notification() {
        super("Notifications", ModuleCategory.RENDER);
        this.registerProperties(mode);
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent event) {
        NotificationManager.update(mode.getValue());
    }
}
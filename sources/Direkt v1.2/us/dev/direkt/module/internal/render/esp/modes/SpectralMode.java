package us.dev.direkt.module.internal.render.esp.modes;

import net.minecraft.entity.Entity;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.module.internal.render.esp.ESP;
import us.dev.direkt.module.internal.render.esp.ESPMode;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.concurrent.Executors;

/**
 * @author Foundry
 */
public class SpectralMode extends ESPMode {

    public SpectralMode() {
        super("Spectral");
    }

    @Listener
    protected Link<EventGameTick> onGameTick = new Link<>(event -> {
        Wrapper.getWorld().getLoadedEntityList().stream()
                .forEach(e -> e.setGlowing(Direkt.getInstance().getModuleManager().getModule(ESP.class).doesQualify(e)));
    });

    @Override
    public void onDisable() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Wrapper.getWorld().getLoadedEntityList().stream()
                    .filter(Entity::isGlowing)
                    .forEach(e -> e.setGlowing(false));
        });
    }
}

package us.dev.direkt.module.internal.movement.speed;

import us.dev.api.property.Property;
import us.dev.api.property.reactive.RxProperty;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.movement.speed.internal.modes.*;
import us.dev.direkt.module.property.annotations.Exposed;

/**
 * @author Foundry
 */
@ModData(label = "Speed", category = ModCategory.MOVEMENT)
public class Speed extends ToggleableModule {

    @Exposed(description = "The Speed mode to be used")
    final Property<Mode> mode = new RxProperty<>("Mode", Mode.NGROUND).withListener(((oldValue, newValue) -> {
        if (this.isRunning()) {
            Direkt.getInstance().getEventManager().unregister(oldValue.getBackingMode());
            oldValue.getBackingMode().onDisable();
            Direkt.getInstance().getEventManager().register(newValue.getBackingMode());
            newValue.getBackingMode().onEnable();
        }
    }));

    @Override
    public void onEnable() {
        Direkt.getInstance().getEventManager().register(this.mode.getValue().getBackingMode());
        this.mode.getValue().getBackingMode().onEnable();
    }

    @Override
    public void onDisable() {
        Direkt.getInstance().getEventManager().unregister(this.mode.getValue().getBackingMode());
        this.mode.getValue().getBackingMode().onDisable();
    }

    public boolean isSpeeding() {
        return this.mode.getValue().getBackingMode().isSpeeding();
    }

    public enum Mode {
        NGROUND(new NGroundMode()),  AAC(new AACMode()), TEST(new TestMode());

        private final SpeedMode backingMode;

        Mode(SpeedMode backingMode) {
            this.backingMode = backingMode;
        }

        private SpeedMode getBackingMode() {
            return this.backingMode;
        }

        @Override
        public String toString() {
            return backingMode.getLabel();
        }
    }
}

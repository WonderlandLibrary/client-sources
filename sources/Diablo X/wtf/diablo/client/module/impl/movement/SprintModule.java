package wtf.diablo.client.module.impl.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

@ModuleMetaData(
        name = "Sprint",
        description = "Automatically sprints",
        category = ModuleCategoryEnum.MOVEMENT
)
public final class SprintModule extends AbstractModule {
    private final ModeSetting<SprintMode> modeSetting = new ModeSetting<>("Mode", SprintMode.LEGIT);

    public SprintModule() {
        this.registerSettings(modeSetting);
    }

    @EventHandler
    private final Listener<MotionEvent> updateEventListener = updateEvent -> {
        this.setSuffix(modeSetting.getValue().getName());
        if (updateEvent.getEventType() == EventTypeEnum.PRE) {
            if (!modeSetting.getValue().equals(SprintMode.LEGIT))
                this.setSuffix(this.modeSetting.getValue().getName());

            if (this.modeSetting.getValue() == SprintMode.LEGIT) {
                if (this.mc.thePlayer.moveForward > 0.0f) {
                    this.mc.thePlayer.setSprinting(true);
                }
            } else if (this.modeSetting.getValue() == SprintMode.OMNI) {
                if (MovementUtil.isMoving())
                    this.mc.thePlayer.setSprinting(true);
            }
        }
    };

    private enum SprintMode implements IMode {
        LEGIT("Legit"),
        OMNI("Omni");

        private final String name;

        SprintMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}

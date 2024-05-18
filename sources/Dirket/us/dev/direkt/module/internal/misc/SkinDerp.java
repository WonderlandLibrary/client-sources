package us.dev.direkt.module.internal.misc;

import net.minecraft.entity.player.EnumPlayerModelParts;
import us.dev.api.property.Property;
import us.dev.api.property.BoundedProperty;
import us.dev.api.property.multi.MultiProperty;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

/**
 * @author Foundry
 */
@ModData(label = "Skin Derp", aliases = {"skinflash", "skinblink"}, category = ModCategory.MISC)
public final class SkinDerp extends ToggleableModule {

    @Exposed(description = "Which skin parts should be made to flash")
    private final MultiProperty<Boolean> parts = new MultiProperty.Builder<Boolean>("Parts")
            .put(new Property<>("Head", true))
            .put(new Property<>("Left Arm", true))
            .put(new Property<>("Right Arm", true))
            .put(new Property<>("Chest", true))
            .put(new Property<>("Left Leg", true))
            .put(new Property<>("Right Leg", true))
            .put(new Property<>("Cape", false))
            .build();

    @Exposed(description = "The delay between each time the skin flashes")
    private final BoundedProperty<Integer> delay = new BoundedProperty<>("Delay", 0, 400, 1000);

    @Exposed(description = "Should a random delay be added to each skin part flashing")
    private final Property<Boolean> random = new Property<>("Random", false);

    private boolean lastRandomState;
    private final Timer timer = new Timer();

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if (this.lastRandomState != this.random.getValue()) {
            for (EnumPlayerModelParts p : EnumPlayerModelParts.values()) {
                if (!Wrapper.getGameSettings().getModelParts().contains(p))
                    Wrapper.getGameSettings().switchModelPartEnabled(p);
            }
            this.lastRandomState = this.random.getValue();
        }
        if (this.timer.hasReach(this.delay.getValue())) {
            if (parts.getValue("Head").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.HAT);
            if (parts.getValue("Left Arm").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE);
            if (parts.getValue("Right Arm").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE);
            if (parts.getValue("Chest").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.JACKET);
            if (parts.getValue("Left Leg").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG);
            if (parts.getValue("Right Leg").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            if (parts.getValue("Cape").getValue() && (!this.random.getValue() || Math.random() * 3 > 1))
                Wrapper.getGameSettings().switchModelPartEnabled(EnumPlayerModelParts.CAPE);
            this.timer.reset();
        }
    });

    @Override
    public void onEnable() {
        this.lastRandomState = this.random.getValue();
    }

}

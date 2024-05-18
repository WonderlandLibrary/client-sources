package us.dev.direkt.module.internal.render.esp;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.api.property.reactive.RxProperty;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.internal.render.esp.modes.BFCEMode;
import us.dev.direkt.module.internal.render.esp.modes.BoxMode;
import us.dev.direkt.module.internal.render.esp.modes.ShaderMode;
import us.dev.direkt.module.internal.render.esp.modes.SpectralMode;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.EntityUtils;

/**
 * @author Foundry
 */
@ModData(label = "ESP", aliases = "outline", category = ModCategory.RENDER)
public class ESP extends ToggleableModule {

    @Exposed(description = "The ESP mode to be used")
    final Property<Mode> mode = new RxProperty<>("Mode", Mode.BFCE).withListener(((oldValue, newValue) -> {
        if (this.isRunning()) {
            Direkt.getInstance().getEventManager().unregister(oldValue.getBackingMode());
            oldValue.getBackingMode().onDisable();
            Direkt.getInstance().getEventManager().register(newValue.getBackingMode());
            newValue.getBackingMode().onEnable();
        }
    }));

    @Exposed(description = "The types of entities that should be valid targets")
    final MultiProperty<Boolean> targetTypes = new MultiProperty.Builder<Boolean>("Targets")
            .put(new Property<>("Players", true))
            .put(new Property<>("Hostiles", false))
            .put(new Property<>("Passives", false))
            .put(new Property<>("Neutrals", false))
            .put(new Property<>("Bosses", false))
            .put(new Property<>("Vehicles", false))
            .put(new Property<>("Others", false))
            .build();

    @Exposed(description = "Should items be outlined")
    final Property<Boolean> items = new Property<>("Items", false);

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

    public boolean doesQualify(Entity e) {
        if (e == null || e == Wrapper.getPlayer() || e instanceof EntityBoat || e instanceof EntityItemFrame || e instanceof EntityXPOrb)
            return false;
        else if (this.targetTypes.getValue("Players").getValue() && e instanceof EntityPlayer)
            return true;
        else if (this.targetTypes.getValue("Hostiles").getValue() && EntityUtils.isHostileMob(e))
            return true;
        else if (this.targetTypes.getValue("Neutrals").getValue() && EntityUtils.isNeutralMob(e))
            return true;
        else if (this.targetTypes.getValue("Passives").getValue() && EntityUtils.isPassiveMob(e))
            return true;
        else if (this.targetTypes.getValue("Bosses").getValue() && EntityUtils.isBossMob(e))
            return true;
        else if (this.targetTypes.getValue("Vehicles").getValue() && EntityUtils.isVehicleEntity(e))
            return true;
        else if (this.targetTypes.getValue("Others").getValue() && EntityUtils.isMiscellaneousEntity(e))
            return true;
        else if (this.items.getValue() && e instanceof EntityItem)
            return true;
        return false;
    }

    public enum Mode {
        BFCE(new BFCEMode()), BOX(new BoxMode()), SPECTRAL(new SpectralMode()), SHADER(new ShaderMode());

        private final ESPMode backingMode;

        Mode(ESPMode backingMode) {
            this.backingMode = backingMode;
        }

        private ESPMode getBackingMode() {
            return this.backingMode;
        }

        @Override
        public String toString() {
            return backingMode.getLabel();
        }
    }
}


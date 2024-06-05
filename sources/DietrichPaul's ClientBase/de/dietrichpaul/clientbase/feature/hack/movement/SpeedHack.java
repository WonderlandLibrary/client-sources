package de.dietrichpaul.clientbase.feature.hack.movement;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.FloatProperty;
import de.dietrichpaul.clientbase.util.minecraft.MoveUtil;

public class SpeedHack extends Hack implements UpdateListener {
    private final EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.VANILLA, Mode.values(), Mode.class);
    private final BooleanProperty vanillaUseEntitySpeed = new BooleanProperty("Use Entity Speed", true);
    private final FloatProperty vanillaSpeed = new FloatProperty("Vanilla Speed", 1.0F, 0.0F, 5.0F);

    public SpeedHack() {
        super("Speed", HackCategory.MOVEMENT);

        this.addProperty(this.mode);

        final PropertyGroup vanillaGroup = this.addPropertyGroup("Vanilla");
        vanillaGroup.addProperty(this.vanillaUseEntitySpeed);
        vanillaGroup.addProperty(this.vanillaSpeed);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);

    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        if (mode.getValue() != Mode.VANILLA) return;
        if (!MoveUtil.isPlayerMoving()) return;
        MoveUtil.setPlayerSpeedF(this.vanillaUseEntitySpeed.getState() ?
                MoveUtil.getPlayerSpeedF() : this.vanillaSpeed.getValue());
    }

    public enum Mode {
        VANILLA
    }
}

package zamorozka.ui;

import net.minecraft.entity.Entity;

public class AttackEvent extends Event {
    private final Entity targetEntity;

    public AttackEvent(final Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}
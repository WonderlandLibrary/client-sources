package zamorozka.event.events;

import net.minecraft.entity.Entity;
import zamorozka.event.Event;

public class MouseAttackEvent extends Event {
	private Entity entity;
    private boolean preAttack;

    public MouseAttackEvent(Entity targetEntity, boolean preAttack) {
        this.entity = targetEntity;
        this.preAttack = preAttack;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isPreAttack() {
        return preAttack;
    }

    public boolean isPostAttack() {
        return !preAttack;
    }
}

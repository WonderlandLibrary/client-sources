package gq.vapu.czfclient.API;

import net.minecraft.entity.Entity;

public class EventAttack extends Event {

    public static Entity entity;
    private boolean preAttack;

    public void fire(Entity targetEntity, boolean preAttack) {
        entity = targetEntity;
        this.preAttack = preAttack;
        super.fire();
    }

    public Entity getEntity() {
        return entity;
    }

    //public boolean isPreAttack() {
    //return this.preAttack;
    //}

    //public boolean isPostAttack() {
    //return !this.preAttack;
    //}
}
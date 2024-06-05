package Ascii4UwUWareClient.API.Events.World;

import Ascii4UwUWareClient.API.Event;
import net.minecraft.entity.Entity;

public class EventStep extends Event {

    private Entity entity;
    private float stepHeight;
    private boolean pre;
    private double blockHeight;

    public EventStep(Entity entity, float stepHeight, boolean pre) {
        this.entity = entity;
        this.stepHeight = stepHeight;
        this.pre = pre;
    }

    public EventStep(Entity entity, float stepHeight, boolean pre, double blockHeight) {
        this.blockHeight = blockHeight;
        this.stepHeight = stepHeight;
        this.entity = entity;
        this.pre = pre;
    }

    public double getHeightStepped() {
        return blockHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public boolean isPre() {
        return this.pre;
    }

    public void setStage(boolean pre) {
        this.pre = pre;
    }
}
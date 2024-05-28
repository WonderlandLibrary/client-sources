package arsenic.event.impl;

import arsenic.event.types.CancellableEvent;
import arsenic.event.types.Event;

public class EventJump extends CancellableEvent implements Event {
    private float yaw;
    private float motion;

    public EventJump(float yaw, float motion){
        this.yaw = yaw;
        this.motion = motion;
    }

    public float getYaw(){
        return this.yaw;
    }

    public void setYaw(float yaw){
        this.yaw = yaw;
    }

    public float getMotion(){
        return this.motion;
    }

    public void setMotion(float motion){
        this.motion = motion;
    }
}

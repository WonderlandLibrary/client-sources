package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * Created by myche on 2/3/2017.
 */
public class UpdateEvent extends EventCancellable{

    public boolean alwaysSend;
    public double y;
    public float yaw, pitch;
    public boolean onGround;
    public byte type;
    public EntityPlayerSP player;

    public UpdateEvent(double y, float[] rot, boolean onGround, EntityPlayerSP player) {
        this.y = y;
        this.yaw = rot[0];
        this.pitch = rot[1];
        this.onGround = onGround;
        this.player = player;
        this.type = EventType.PRE;
    }

    public UpdateEvent() {
        this.type = EventType.POST;
    }

    public boolean isAlwaysSend() {
        return alwaysSend;
    }

    public void setAlwaysSend(boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public EntityPlayerSP getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayerSP player) {
        this.player = player;
    }
}

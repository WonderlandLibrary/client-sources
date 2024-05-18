package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

/**
 * Created by myche on 2/5/2017.
 */
public class AttackEvent implements Event {

    private Entity inEntity;

    public AttackEvent(Entity inEntity) {
        this.inEntity = inEntity;
    }
}

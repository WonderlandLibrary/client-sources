package lol.point.returnclient.events.impl.player;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@AllArgsConstructor
public class EventAttack extends Event {
    public final EntityPlayer player;
    public final Entity target;
}

package wtf.shiyeno.modules.impl.combat;

import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.network.play.client.CUseEntityPacket.Action;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "No Friend Damage",
        type = Type.Combat
)
public class NoFriendDamage extends Function {
    public NoFriendDamage() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket packet) {
            IPacket var4 = packet.getPacket();
            if (var4 instanceof CUseEntityPacket useEntityPacket) {
                Entity entity = useEntityPacket.getEntityFromWorld(mc.world);
                if (entity instanceof RemoteClientPlayerEntity && Managment.FRIEND_MANAGER.isFriend(entity.getName().getString()) && useEntityPacket.getAction() == Action.ATTACK) {
                    event.setCancel(true);
                }
            }
        }
    }
}
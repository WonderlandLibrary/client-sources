package src.Wiksi.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.command.friends.FriendStorage;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CUseEntityPacket;

@FunctionRegister(name = "NoFriendDamage", type = Category.Combat)
public class NoFriendDamage extends Function {
    @Subscribe
    public void onEvent(EventPacket event) {
        if (event.getPacket() instanceof CUseEntityPacket) {
            CUseEntityPacket cUseEntityPacket = (CUseEntityPacket) event.getPacket();
            Entity entity = cUseEntityPacket.getEntityFromWorld(mc.world);
            if (entity instanceof RemoteClientPlayerEntity &&
                    FriendStorage.isFriend(entity.getName().getString()) &&
                    cUseEntityPacket.getAction() == CUseEntityPacket.Action.ATTACK) {
                event.cancel();
            }
        }
    }
}
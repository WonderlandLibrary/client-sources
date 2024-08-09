package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.command.friends.FriendStorage;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CUseEntityPacket;

@FunctionRegister(name = "NoFriendDamage", type = Category.COMBAT, desc = "Не бьёт друга")
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
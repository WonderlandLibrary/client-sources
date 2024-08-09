/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CUseEntityPacket;

@FunctionRegister(name="NoFriendDamage", type=Category.Combat)
public class NoFriendDamage
extends Function {
    @Subscribe
    public void onEvent(EventPacket eventPacket) {
        CUseEntityPacket cUseEntityPacket;
        Entity entity2;
        if (eventPacket.getPacket() instanceof CUseEntityPacket && (entity2 = (cUseEntityPacket = (CUseEntityPacket)eventPacket.getPacket()).getEntityFromWorld(NoFriendDamage.mc.world)) instanceof RemoteClientPlayerEntity && FriendStorage.isFriend(entity2.getName().getString()) && cUseEntityPacket.getAction() == CUseEntityPacket.Action.ATTACK) {
            eventPacket.cancel();
        }
    }
}


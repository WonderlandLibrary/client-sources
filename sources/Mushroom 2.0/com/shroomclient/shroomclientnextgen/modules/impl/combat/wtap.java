package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

/*@RegisterModule(
        name = "W Tap",
        uniqueId = "wtap",
        description = "Walks Forward After Hitting An Enemy",
        category = ModuleCategory.Combat
)*/
public class wtap extends Module {

    long HitTime = 0;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPost(MotionEvent.Pre event) {
        if (System.currentTimeMillis() - HitTime > 500) {
            lastHitEntity = null;
            return;
        }
        if (lastHitEntity != null && !C.mc.options.forwardKey.isPressed()) {
            if (lastHitEntity.hurtTime > 7) {
                C.mc.options.forwardKey.setPressed(true);
            } else {
                //if (MovementUtil.ticks % 3 == 0) {
                C.mc.options.forwardKey.setPressed(false);
                //}
            }
        }
    }

    private @Nullable PlayerEntity lastHitEntity = null;

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = ent;
                HitTime = System.currentTimeMillis();
            }
        }
    }
}

package com.polarware.module.impl.movement.noslow;

import com.polarware.component.impl.hypixel.InventoryDeSyncComponent;
import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.impl.movement.NoSlowModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PostMotionEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class OldIntaveNoSlow extends Mode<NoSlowModule> {

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
//        InventoryDeSyncComponent.setActive("/booster");

        if (mc.thePlayer.isUsingItem()) {
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
        }
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (mc.thePlayer.isUsingItem() && InventoryDeSyncComponent.isDeSynced()) {
            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };

    public OldIntaveNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }
}
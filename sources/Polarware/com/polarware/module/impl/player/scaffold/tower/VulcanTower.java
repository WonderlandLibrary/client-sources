package com.polarware.module.impl.player.scaffold.tower;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class VulcanTower extends Mode<ScaffoldModule> {

    public VulcanTower(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2) && mc.thePlayer.offGroundTicks > 3) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem];

            if (itemStack == null || (itemStack.stackSize > 2)) {
               PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));
            }
            mc.thePlayer.motionY = 0.42F;
        }
    };
}

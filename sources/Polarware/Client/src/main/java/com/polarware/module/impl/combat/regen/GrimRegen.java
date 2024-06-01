package com.polarware.module.impl.combat.regen;

import com.polarware.Client;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.module.impl.combat.RegenModule;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class GrimRegen extends Mode<RegenModule> {

    public GrimRegen(String name, RegenModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreMotionEvent = event -> {
        if(Client.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled()) {
            return;
        }
        if(mc.thePlayer.isUsingItem()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
        }
        //for (int i = 0; i < (mc.thePlayer.getHealth() > 20 ? 10 : 30); i++) {
        for (int i = 0; i < 20; i++) {
            // ChatUtil.display();
            //40
            //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false ? true : mc.thePlayer.onGround));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
        }
    };
}

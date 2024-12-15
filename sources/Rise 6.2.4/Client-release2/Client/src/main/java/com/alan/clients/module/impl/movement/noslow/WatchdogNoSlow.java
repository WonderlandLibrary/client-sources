package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.event.impl.input.RightClickEvent;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.SlowDownEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import rip.vantage.commons.packet.api.interfaces.IPacket;

public class WatchdogNoSlow extends Mode<NoSlow> {
    private int offGroundTicks;
    private boolean stop;

    private boolean disable;
    private Packet<?> interactItemPacket;
    private KillAura killAuraModule = null;

    public WatchdogNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    public final BooleanValue slab = new BooleanValue("Slow down on Slabs", this, true);

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && (!mc.thePlayer.isUsingItem() && slab.getValue())) {
            disable = false;
        }



        double posY = event.getPosY();
        if (Math.abs(posY - Math.round(posY)) > 0.03 &&  mc.thePlayer.onGround) {
            disable = true;
        }


        if (this.mc.thePlayer.isUsingItem() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {

            if (mc.thePlayer.onGround) {
                offGroundTicks = 0;
            } else {
                offGroundTicks++;
            }

            if (offGroundTicks >= 2) {
                stop = false;

                interactItemPacket = null;
            } else if (mc.thePlayer.onGround && !disable) {



                event.setPosY(event.getPosY() +  1E-14);
            }

        };


    };

    @EventLink
    public final Listener<RightClickEvent> onRightClick = event -> {
        if(mc.thePlayer.getHeldItem() == null) {
            return;
        }

        if(mc.thePlayer.isUsingItem() || mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getHeldItem().getMetadata()) || mc.thePlayer.getHeldItem().getItem() instanceof ItemFood ||  mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
            if (mc.thePlayer.offGroundTicks < 2 && mc.thePlayer.offGroundTicks != 0 && !disable) {
                ChatUtil.display("You must start eating while in the air even with potions");
                event.setCancelled();
            } else if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                event.setCancelled();
            }
        }
    };

    @EventLink
    private final Listener<PacketSendEvent> onPacketSend = event -> {
        if (killAuraModule == null) {
            killAuraModule = getModule(KillAura.class);
        }









    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {


if(!disable) {
    if (getParent().foodValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood) {

        event.setCancelled();
    }
    if (getParent().potionValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getHeldItem().getMetadata())) {

        event.setCancelled();
    }

    if (getParent().bowValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
        event.setCancelled();
    }



}

        if (getParent().swordValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            PacketUtil.send(new C09PacketHeldItemChange(getComponent(Slot.class).getItemIndex() % 7 +(int) (Math.random() * 2) + 1));
            PacketUtil.send(new C09PacketHeldItemChange(getComponent(Slot.class).getItemIndex()));
            event.setCancelled();
        }

    };
}

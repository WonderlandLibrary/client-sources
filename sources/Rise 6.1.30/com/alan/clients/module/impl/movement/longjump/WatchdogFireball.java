package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import rip.vantage.commons.util.time.StopWatch;

public class WatchdogFireball extends Mode<LongJump> {

    public WatchdogFireball(String name, LongJump parent) {
        super(name, parent);
    }

    private int lastSlot = -1;
    private int ticks = -1;
    private boolean setSpeed;
    public static boolean stopModules;
    private boolean sentPlace;
    private int initTicks;
    private boolean thrown;

    StopWatch stopWatch = new StopWatch();
    @EventLink
    public final Listener<PacketSendEvent> packetSendEventListener = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack() != null
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack().getItem() instanceof ItemFireball) {
            thrown = true;
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() != mc.thePlayer.getEntityId()) {
                return;
            }
            if (thrown) {
                ticks = 0;
                setSpeed = true;
                thrown = false;
                stopModules = true;
            }
        }
    };

    @EventLink(Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> preMotionEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        if (initTicks == 0) {
            event.setYaw(mc.thePlayer.rotationYaw - 180);
            event.setPitch(89);
            int fireballSlot = getFireball();
            if (fireballSlot != -1 && fireballSlot != mc.thePlayer.inventory.currentItem) {
                lastSlot = mc.thePlayer.inventory.currentItem;
                mc.thePlayer.inventory.currentItem = fireballSlot;
            }
        }
        if (initTicks == 1) {
            if (!sentPlace) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                sentPlace = true;
            }
        } else if (initTicks == 2) {
            if (lastSlot != -1) {
                mc.thePlayer.inventory.currentItem = lastSlot;
                lastSlot = -1;
            }
        }
        if (ticks > 1) {
            this.toggle();
            return;
        }
        if (setSpeed) {
            stopModules = true;
            this.setSpeed();
            ticks++;
        }
        if (initTicks < 3) {
            initTicks++;
        }

        if (setSpeed) {
            if (ticks > 1) {
                stopModules = setSpeed = false;
                ticks = 0;
                return;
            }
            stopModules = true;
            ticks++;
            setSpeed();
        }
    };

    public void onDisable() {
        if (lastSlot != -1) {
            mc.thePlayer.inventory.currentItem = lastSlot;
        }
        ticks = lastSlot = -1;
        setSpeed = stopModules = sentPlace = false;
        initTicks = 0;
    }

    public void onEnable() {
        if (getFireball() == -1) {
            ChatUtil.display("Could not find Fireball");
            this.toggle();
            return;
        }
        stopModules = true;
        initTicks = 0;
    }

    private void setSpeed() {
        MoveUtil.strafe(1.5f);

    }

    private int getFireball() {
        int a = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == Items.fire_charge) {
                a = i;
                break;
            }
        }
        return a;
    }
}

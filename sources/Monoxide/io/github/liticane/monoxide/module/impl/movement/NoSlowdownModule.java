package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.listener.event.minecraft.game.RunTickEvent;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.NoSlowEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@ModuleData(name = "NoSlowdown", description = "Removes the blocking & eating slowdown", category = ModuleCategory.MOVEMENT)
public class NoSlowdownModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this, new String[] {"Vanilla", "Switch", "Grim", "Old Intave", "Old NCP", "Old Matrix", "Matrix", "Old Hypixel", "MineMenClub", "Polar"});

    private final MultiBooleanValue items = new MultiBooleanValue("Items", this, new String[] {"Sword"}, new String[] {"Sword", "Food", "Bow"});
    private final NumberValue<Float> swordForward = new NumberValue<Float>("Sword Forward", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Sword")}),
            swordStrafe = new NumberValue<Float>("Sword Strafe", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Sword")}),
            foodForward = new NumberValue<Float>("Food Forward", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Food")}),
            foodStrafe = new NumberValue<Float>("Food Strafe", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Food")}),
            bowForward = new NumberValue<Float>("Bow Forward", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Bow")}),
            bowStrafe = new NumberValue<Float>("Bow Strafe", this, 1f, 0f, 1f, 2, new Supplier[]{() -> items.get("Bow")});

    private final TimeHelper intaveTimer = new TimeHelper();

    private final ConcurrentLinkedQueue<Packet<?>> polar_c0Fs = new ConcurrentLinkedQueue<>();
    private int polar_c03s = 0;

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    @Listen
    public void onNoSlowEvent(NoSlowEvent event) {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null || !mc.thePlayer.isUsingItem() || !isMoving()) {
            return;
        }

        if (items.get("Sword") && currentItem.getItem() instanceof ItemSword) {
            event.setSprint(!mode.is("MineMenClub"));
            event.setForward(swordForward.getValue());
            event.setStrafe(swordStrafe.getValue());
        }

        if (items.get("Food") && currentItem.getItem() instanceof ItemFood) {
            event.setSprint(!mode.is("MineMenClub"));
            event.setForward(foodForward.getValue());
            event.setStrafe(foodStrafe.getValue());
        }

        if (items.get("Bow") && currentItem.getItem() instanceof ItemBow) {
            event.setSprint(!mode.is("MineMenClub"));
            event.setForward(bowForward.getValue());
            event.setStrafe(bowStrafe.getValue());
        }
    }

    @Listen
    public void onMotionEvent(UpdateMotionEvent event) {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null || !mc.thePlayer.isUsingItem() || !isMoving()) {
            return;
        }

        switch (mode.getValue()) {
            case "Old Hypixel":
                if (event.getType() == UpdateMotionEvent.Type.POST) {
                    if(mc.thePlayer.isUsingItem() && currentItem.getItem() instanceof ItemSword) {
                        sendPacket(new C08PacketPlayerBlockPlacement(currentItem));
                    }
                }
                break;
            case "Old Matrix":
                if (event.getType() == UpdateMotionEvent.Type.MID) {
                    if(mc.thePlayer.isUsingItem() && currentItem.getItem() instanceof ItemSword) {
                        sendPacketUnlogged(new C0BPacketEntityAction());
                        mc.thePlayer.onGround = false;
                    }
                }
                break;
            case "Old NCP":
                if(mc.thePlayer.isUsingItem() && currentItem.getItem() instanceof ItemSword) {
                    if (event.getType() == UpdateMotionEvent.Type.MID) {
                        sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }

                    if (event.getType() == UpdateMotionEvent.Type.POST) {
                        sendPacket(new C08PacketPlayerBlockPlacement(currentItem));
                    }
                }
                break;
            case "Old Intave":
                if(mc.thePlayer.isUsingItem() && currentItem.getItem() instanceof ItemSword && intaveTimer.hasReached(150L)) {
                    if(event.getType() == UpdateMotionEvent.Type.MID) {
                        sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }

                    if(event.getType() == UpdateMotionEvent.Type.POST) {
                        sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        intaveTimer.reset();
                    }
                }
                break;
            case "Grim":
            case "Switch":
            case "Matrix":
                if(event.getType() == UpdateMotionEvent.Type.MID) {
                    sendPacket(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                break;
        }
    }

    @Listen
    public void onPacketEvent(PacketEvent event) {
        Packet <?> packet = event.getPacket();
    }

    @Listen
    public void onTickEvent(RunTickEvent event) {
        if (Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;

        switch (mode.getValue()) {
            case "Grim":
                if (mc.thePlayer.isBlocking()) {
                    sendPacket(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                } else if (mc.thePlayer.isUsingItem()) {
                    sendPacket(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                break;
        }
    }

}

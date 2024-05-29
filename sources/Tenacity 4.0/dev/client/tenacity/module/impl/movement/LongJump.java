package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.player.MovementUtils;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.player.MoveEvent;
import dev.settings.impl.ModeSetting;
import dev.utils.misc.MathUtils;
import dev.utils.network.PacketUtils;
import dev.utils.time.TimerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class LongJump extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "NCP", "AGC");
    private int movementTicks = 0;
    private double speed;
    private float pitch;
    private int prevSlot, ticks = 0;
    private boolean damagedBow;
    private final TimerUtil jumpTimer = new TimerUtil();

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "Vanilla":
                if (MovementUtils.isMoving() && mc.thePlayer.onGround) {
                    MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 2);
                    mc.thePlayer.jump();
                }
                break;
            case "NCP":
                if (MovementUtils.isMoving()) {
                    if (MovementUtils.isOnGround(0.00023)) {
                        mc.thePlayer.motionY = 0.41;
                    }

                    switch (movementTicks) {
                        case 1:
                            speed = MovementUtils.getBaseMoveSpeed();
                            break;
                        case 2:
                            speed = MovementUtils.getBaseMoveSpeed() + (0.132535 * Math.random());
                            break;
                        case 3:
                            speed = MovementUtils.getBaseMoveSpeed() / 2;
                            break;
                    }
                    MovementUtils.setSpeed(Math.max(speed, MovementUtils.getBaseMoveSpeed()));
                    movementTicks++;
                }
                break;
            case "AGC":
                int bow = getBowSlot();

                if (damagedBow) {
                    if (mc.thePlayer.onGround && jumpTimer.hasTimeElapsed(1000)) {
                        toggle();
                    }
                    if(mc.thePlayer.onGround && mc.thePlayer.motionY > 0.003){
                        mc.thePlayer.motionY = 0.875f;
                    }else{
                        MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 3);
                        mc.thePlayer.motionY += 0.0309654364;
                    }
                }

                if (!damagedBow) {
                    switch (ticks) {
                        case 0:
                            if (prevSlot != bow) {
                                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(bow));
                            }
                            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bow)));
                            break;
                        case 3:
                            event.setPitch(-89.93F);
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), pitch, event.isOnGround()));
                            PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                            if (prevSlot != bow) {
                                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(prevSlot));
                            }
                            break;
                    }
                    if (mc.thePlayer.hurtTime != 0) {
                        damagedBow = true;
                    }
                }
        }
        ticks++;
    };

    private final EventListener<MoveEvent> moveEventEventListener = event -> {
        if (!damagedBow && mode.is("AGC")) {
            event.setX(0);
            event.setZ(0);
        }
    };


    public int getBowSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && is.getItem() == Items.bow) {
                return i;
            }
        }
        return -1;
    }

    public int getItemCount(Item item) {
        int count = 0;
        for (int i = 9; i < 45; i++) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == item) {
                count += stack.stackSize;
            }
        }
        return count;
    }

    @Override
    public void onEnable() {
        if ((mode.is("AGC"))) {
            prevSlot = mc.thePlayer.inventory.currentItem;
            pitch = MathUtils.getRandomFloat(-89.2F, -89.99F);
            if (getBowSlot() == -1) {
                this.toggleSilent();
                return;
            } else if (getItemCount(Items.arrow) == 0) {
                this.toggleSilent();
                return;
            }
        }
        ticks = 0;
        damagedBow = false;
        jumpTimer.reset();
        super.onEnable();
    }

    public LongJump() {
        super("LongJump", Category.MOVEMENT, "jump further");
        this.addSettings(mode);
    }

}

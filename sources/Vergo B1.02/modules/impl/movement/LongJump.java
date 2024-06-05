package xyz.vergoclient.modules.impl.movement;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.util.main.*;
import xyz.vergoclient.util.movement.Movement2;

import java.util.Arrays;
import java.util.Random;

public class LongJump extends Module implements OnEventInterface {

    Timer timer;


    public LongJump() {
        super("LongJump", Category.MOVEMENT);
        this.timer = new Timer();
    }

    public ModeSetting mode = new ModeSetting("Mode", "HypixelDmg", "HypixelDmg"/*, "HypixelBow"*/);

    public static transient TimerUtil hypixelTimer = new TimerUtil();

    @Override
    public void loadSettings() {
        mode.modes.clear();
        mode.modes.addAll(Arrays.asList(/*"HypixelBow",*/ "HypixelDmg"));

        addSettings(mode);
    }

    public int i;
    public int slotId;
    public int ticks;
    public boolean hasHurt = false;

    public ItemStack itemStack = null;

    public int count;

    public TimerUtil toggleTimer = new TimerUtil();

    @Override
    public void onEnable() {

        count = 0;

        mc.gameSettings.keyBindSprint.pressed = false;

        hasHurt = false;

        toggleTimer.reset();

        mc.gameSettings.keyBindSprint.pressed = false;
        mc.gameSettings.keyBindForward.pressed = false;

        this.timer.reset();

        if (MovementUtils.isMoving()) {
            mc.thePlayer.movementInput.moveForward = 0.0f;
            mc.thePlayer.movementInput.moveStrafe = 0.0f;
            MovementUtils.setMotion(0.0f);
        }

        for (i = 0; i < 9; i++) {
            itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBow)
                break;
        }
        if (i == 9) {
            ChatUtils.addChatMessage("Did not find a bow in your hotbar.");
            toggle();
            return;
        } else {
            slotId = mc.thePlayer.inventory.currentItem;
            if (i != slotId) {
                //ChatUtils.addChatMessage("Switching slot from " + slotId + " to " + i);
                mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(i));
            }
            ticks = mc.thePlayer.ticksExisted;
            mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(itemStack));
        }
    }

    @Override
    public void onDisable() {

        mc.thePlayer.speedInAir = 0.02f;

        mc.timer.timerSpeed = 1.0f;

        jumpCount = 0;

        count = 0;

        hasHurt = false;

        mc.gameSettings.keyBindForward .pressed = false;
        mc.gameSettings.keyBindSprint.pressed = false;

        if(mode.is("HypixelDmg")) {
            mc.gameSettings.keyBindJump.pressed = false;
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindSprint.pressed = false;
        }

    }

    public boolean veloWasOn = false;

    public int jumpCount = 0;

    Random r = new Random();
    float random = -88.500000f + r.nextFloat() * (-90.000000f - -89.000000f);

    @Override
    public void onEvent(Event e) {

        // Calls hypixelBow()
        if(e instanceof EventUpdate) {
            EventUpdate event = (EventUpdate) e;
            if (mode.is("Hypixel Bow")) {
                hypixelBow(event);
            }

            if (mode.is("HypixelDmg")) {
                doLongJump(event);
            }
        }

    }


    private void hypixelBow(EventUpdate e) {
        if(e.isPre()) {
            if (mode.is("HypixelBow")) {

                setInfo("HypixelBow");

                if (!hasHurt) {

                    if (mc.thePlayer.ticksExisted - ticks == 3) {
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, random, true));
                        mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));

                        if (i != slotId) {
                            mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slotId));
                        }

                        this.timer.reset();

                    }
                }

                if (mc.thePlayer.hurtTime == 9) {
                    hasHurt = true;
                }

                if (this.timer.delay(1000L)) {
                    mc.timer.timerSpeed = 0.8f;
                }

                if (this.timer.delay(1200L)) {
                    mc.timer.timerSpeed = 1.0f;
                }

                if (this.timer.delay(1500L)) {
                    toggle();
                    mc.gameSettings.keyBindForward.pressed = false;
                }

                if (hasHurt) {

                    mc.gameSettings.keyBindForward.pressed = true;
                    mc.gameSettings.keyBindSprint.pressed = true;

                    if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindJump.isPressed()) {

                    }
                    if (jumpCount == 0) {
                        mc.thePlayer.jump();
                        jumpCount++;
                    } else {

                    }

                    MovementUtils.setMotion(0.6);

                    if (this.timer.delay(1000L)) {
                        hasHurt = false;
                        jumpCount = 0;
                    }

                }
            }
        }

    }

    public double oldYaw;

    public void doLongJump(EventUpdate e) {

        if(e.isPre()) {

            if (!hasHurt) {

                if (mc.thePlayer.ticksExisted - ticks == 3) {

                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, -89.6f, true));
                    mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));

                    // Switch back to original slot
                    if (i != slotId) {
                        mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slotId));
                    }

                }
            }

            if (mc.thePlayer.hurtTime == 9) {
                hasHurt = true;
            }

            double yaw = Movement2.getDirection();

            if (hasHurt) {
                switch (count) {
                    case 0:
                        mc.thePlayer.jump();
                        mc.thePlayer.setSprinting(true);
                        mc.gameSettings.keyBindForward.pressed = true;
                        mc.thePlayer.speedInAir = 0.0355f;
                        mc.thePlayer.motionY += 0.4529654364;
                        break;
                    case 20:
                        mc.thePlayer.setSprinting(true);
                        mc.gameSettings.keyBindForward.pressed = true;
                        mc.thePlayer.speedInAir = 0.02f;
                        break;
                    case 21:
                        mc.thePlayer.setSprinting(false);
                        mc.gameSettings.keyBindForward.pressed = false;
                        toggle();
                        break;
                }
                count++;
                ChatUtils.addDevMessage(count);
            }

        }

    }

}

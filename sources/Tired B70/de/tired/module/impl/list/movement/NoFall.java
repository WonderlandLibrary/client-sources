package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

@ModuleAnnotation(name = "NoFall", category = ModuleCategory.MOVEMENT, clickG = "prevents fallDamage")
public class NoFall extends Module {

    public ModeSetting mode = new ModeSetting("NofallMode", this, new String[]{"Verus", "Matrix", "AAC4", "Vanilla", "Custom"});
    public NumberSetting fallDistance = new NumberSetting("fallDistance", this, 3.5, 0.5, 10, .5, () -> mode.getValue().equalsIgnoreCase("Matrix"));
    public NumberSetting fallDistanceCustom = new NumberSetting("fallDistanceC", this, 3.5, 0.5, 10, .5, () -> mode.getValue().equalsIgnoreCase("Custom"));
    public ModeSetting groundType = new ModeSetting("groundType", this, new String[]{"Packet", "PacketSilent", "MotionY", "FallDistance"}, () -> mode.getValue().equalsIgnoreCase("Custom"));
    public NumberSetting timer = new NumberSetting("timer", this, 1, .1, 2, .1, () -> mode.getValue().equalsIgnoreCase("Custom"));
    public BooleanSetting modifyPitch = new BooleanSetting("modifyPitch", this, true, () -> mode.getValue().equalsIgnoreCase("Custom"));
    public NumberSetting pitch = new NumberSetting("pitch", this, 90, -90, 90, 1, () -> mode.getValue().equalsIgnoreCase("Custom") && modifyPitch.getValue());



    @EventTarget
    public void onPacket(PacketEvent e) {

        if (mode.getValue().equalsIgnoreCase("Matrix")) {
            if (e.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer o = (C03PacketPlayer) e.getPacket();
                if (MC.thePlayer.fallDistance > fallDistance.getValue()) {
                    o.onGround = true;
                }

            }

        }

        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            if (mode.getValue().equalsIgnoreCase("AAC4")) {
                for (int i = 0; i <  50; i++) {
                    MC.thePlayer.motionY -= 0.01F;
                    MC.timer.updateTimer();
                }
                MC.thePlayer.motionY -= 0.01F;
                e.setCancelled(true);
            }
        }
    }

    public int groundDistance() {
        int blocks = (int) MC.thePlayer.posY;

        while (MC.thePlayer.fallDistance > 0 && MC.theWorld.getBlock(MC.thePlayer.posX, MC.thePlayer.posY - blocks, MC.thePlayer.posZ).getMaterial() == Material.air) {
            blocks--;
        }
        return blocks;
    }

    int fallTicks = 0;

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        switch (mode.getValue()) {
            case "Verus": {
                if (MC.thePlayer.fallDistance > 3.5) {
                    MC.timer.timerSpeed = .4F;
                    sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                    sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(getWorld(), new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
                    sendPacketUnlogged(new C03PacketPlayer(true));
                    MC.thePlayer.fallDistance = 0;
                } else {
                    MC.timer.timerSpeed = 1F;
                }
            }
            break;

            case "AAC4": {

                if (MC.thePlayer.fallDistance > 1.2 && !MC.thePlayer.onGround) {
                    if (MC.thePlayer.ticksExisted % 9 == 0) {
                        MC.thePlayer.fallDistance = 1;
                        sendPacket(new C03PacketPlayer(true));
                        MC.thePlayer.motionY = 0;
                        fallTicks++;
                    } else {
                        MC.timer.timerSpeed = .2F;
                        MC.timer.updateTimer();
                    }
                } else {
                    MC.timer.timerSpeed = 1F;
                }
            }
            break;

            case "Matrix": {
                if (MC.thePlayer.fallDistance > fallDistance.getValue()) {
                    MC.thePlayer.moveForward = 0;
                    MC.thePlayer.movementInput.moveStrafe = 0;
                    MC.thePlayer.fallDistance = 0;
                    MC.timer.timerSpeed = .4F;
                }else {
                    MC.timer.timerSpeed = 1F;
                }
            }
            break;
            case "Vanilla": {
                if (MC.thePlayer.fallDistance > 3.5) {
                    sendPacketUnlogged(new C03PacketPlayer(true));
                }
            }

            break;

            case "Custom": {
                if (MC.thePlayer.fallDistance > fallDistanceCustom.getValue() && !MC.thePlayer.onGround) {

                    switch (groundType.getValue()) {
                        case "Packet":
                            sendPacket(new C03PacketPlayer(true));
                            break;
                        case "PacketSilent":
                            sendPacketUnlogged(new C03PacketPlayer(true));
                            break;
                        case "MotionY":
                            MC.thePlayer.motionY = 0;
                            break;
                        case "FallDistance":
                            MC.thePlayer.fallDistance = 0;
                            break;
                    }

                    MC.timer.timerSpeed = timer.getValueFloat();

                    if (modifyPitch.getValue()) {
                        MC.thePlayer.rotationPitch = pitch.getValueInt();
                    }

                } else {
                    MC.timer.timerSpeed = 1F;
                }
            }

        }
    }

    public boolean shouldExecuteNoFall() {
        return MC.thePlayer.fallDistance > 2F;
    }

    @Override
    public void onState() {
        MC.timer.timerSpeed = 1F;
    }

    @Override
    public void onUndo() {
        MC.timer.timerSpeed = 1F;
    }
}

package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.util.math.TimerUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

@ModuleAnnotation(name = "LongJump", category = ModuleCategory.MOVEMENT, clickG = "Jump longer and faster")
public class LongJump extends Module {

    public ModeSetting LONGJUMP_MODE = new ModeSetting("Mode", this, new String[]{"MatrixWeb", "MatrixWater", "AAC4", "VerusDamage"});

    private int matrixFlags = 0;

    private final TimerUtil timerUtil = new TimerUtil();

    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            matrixFlags++;
            if (LONGJUMP_MODE.getValue().equalsIgnoreCase("AAC4")) {
                    final S08PacketPlayerPosLook posLook = (S08PacketPlayerPosLook) e.getPacket();
                    e.setCancelled(true);

                }

        }

    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        if (!this.state) return;

        switch (LONGJUMP_MODE.getValue()) {
            case "MatrixWeb": {

                boolean web = blockUnder(MC.thePlayer, 0.01f) != Blocks.web && MC.thePlayer.isInWeb;


                MC.thePlayer.isInWeb = false;

                if (matrixFlags < 2 && web) {
                    {

                        MC.thePlayer.motionY = 8.4F;

                        sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 1, MC.thePlayer.posZ, MC.thePlayer.onGround));

                    }
                }

                if (MC.gameSettings.keyBindSprint.pressed) {
                    MC.timer.timerSpeed = .1F;
                }

                if (matrixFlags == 2) {
                    MC.timer.timerSpeed = .9F;
                    MC.thePlayer.motionY = 13;

                    matrixFlags = 0;
                }
            }
            break;

            case "AAC4": {

                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                } else {
                    MC.timer.timerSpeed = 1.34F;
                    if (MC.thePlayer.ticksExisted % 12 == 0) {
                        MC.thePlayer.fallDistance = 1;

                        MC.thePlayer.motionY = .32F;
                    }

                }

            }
            break;
            case "MatrixWater": {
                if (MC.thePlayer.isInWater() && MC.thePlayer.onGround) {
                    MC.thePlayer.motionY = 4F;
                }
                if (MC.gameSettings.keyBindSprint.pressed) {
                    MC.timer.timerSpeed = .1F;
                }

            }
            break;

            case "VerusDamage": {
                if (MC.thePlayer.motionY > .5) {
                    MC.thePlayer.motionY = -.4F;
                    executeMod();
                    MC.timer.timerSpeed =1F;
                }
                if (MC.thePlayer.hurtTime != 0) {
                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();
                    } else {
                        sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                        sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(getWorld(), new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
                        MC.thePlayer.motionY = .6F;
                        ClientHelper.INSTANCE.doSpeedup(6);
                    }
                }
            }

        }


    }

    @Override
    public void onState() {

        if (LONGJUMP_MODE.getValue().equalsIgnoreCase("VerusDamage")) {
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(getWorld(), new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
            double x = MC.thePlayer.posX;
            double y = MC.thePlayer.posY;
            double z = MC.thePlayer.posZ;
            MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 3.35, z, false));
            MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
            MC.thePlayer.motionX = 0;
            MC.thePlayer.motionY = 0;
            MC.thePlayer.motionZ = 0;
            MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY + 0.42, MC.thePlayer.posZ);
            MC.thePlayer.posY += .6;
        }
        MC.timer.timerSpeed = 1F;

        matrixFlags = 0;

        timerUtil.doReset();
    }

    @Override
    public void onUndo() {
        matrixFlags = 0;
        timerUtil.doReset();
        MC.timer.timerSpeed = 1F;

    }
}

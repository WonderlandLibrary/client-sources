package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.util.math.TimerUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.EventBlockBB;
import de.tired.event.events.EventClip;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;

@ModuleAnnotation(name = "Fly", category = ModuleCategory.MOVEMENT, clickG = "Fly Around the world")
public class Flight extends Module {
    private final TimerUtil timer2 = new TimerUtil();
    public ModeSetting FLY_MODE = new ModeSetting("FLY_MODE", this, new String[]{"Minemora", "Hywixxel", "Rededark", "Redesky", "BlocksMC2", "Verus2"});
    public TimerUtil timerUtil = new TimerUtil();

    private boolean verusB3733SpoofGround = false;


    private int counter;

    public boolean firstLagback = false;

    public ArrayList<C03PacketPlayer> savedC03Packets = new ArrayList<>();

    @EventTarget
    public void onClip(EventClip e) {
        if (FLY_MODE.getValue().equalsIgnoreCase("Rededark")) {
            e.setShouldDoNoClip(true);
        }
    }

    @EventTarget
    public void onMove(UpdateEvent e) {
        switch (FLY_MODE.getValue()) {
            case "Minemora":


                if (MC.thePlayer.ticksExisted % 6 == 0) {
                    MC.thePlayer.motionY = 0.0000;
                }

                break;

            case "Verus2": {

                MC.thePlayer.onGround = true;
                MC.thePlayer.motionY = 0.00000001F;

            }
            break;

            case "Hywixxel": {
                double motionY = 0;


                    if (MC.thePlayer.ticksExisted % 20 == 0) {

                        motionY = 2;
                    }
                    else {
                        motionY = -0.1;
                    }


                MC.thePlayer.motionY = motionY;
                ClientHelper.INSTANCE.doSpeedup(1);
                MC.thePlayer.motionY = 0.00000000000001F;
                if (MC.gameSettings.keyBindSneak.isKeyDown()) {
                    MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY + 2.5, MC.thePlayer.posZ);
                }
                if (MC.gameSettings.keyBindSprint.isKeyDown()) {
                    MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY -2.5, MC.thePlayer.posZ);
                }
            }
            break;

            case "Redesky": {

                ClientHelper.INSTANCE.doSpeedup(.84);
                MC.thePlayer.motionY = 0.0001F;
            }
            break;

            case "BlocksMC2": {
                if (MC.thePlayer.hurtTime == 0) {
                    MC.thePlayer.motionY = 0.0001F;

                    ClientHelper.INSTANCE.doSpeedup(.24);
                    sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                    sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(getWorld(), new BlockPos(-1, -1, -1))), 0, 0.94f, 0));

                    MC.thePlayer.capabilities.isFlying = true;

                }
                break;
            }
        }
        setDesc(FLY_MODE.getValue());
    }

    @EventTarget
    public void onBlock(EventBlockBB eventBlockBB) {
        }

    @EventTarget
    public void onPacket(PacketEvent e) {

        switch (FLY_MODE.getValue()) {
            case "Verus2":

                if (e.getPacket() instanceof C03PacketPlayer) {
                    final C03PacketPlayer packetPlayer = (C03PacketPlayer) e.getPacket();
                    packetPlayer.onGround = true;
                }

                break;
            case "Redesky":
                final Packet<?> packet = e.getPacket();
                if (packet instanceof C00PacketKeepAlive || packet instanceof C0FPacketConfirmTransaction || packet instanceof C13PacketPlayerAbilities || packet instanceof C17PacketCustomPayload || packet instanceof C18PacketSpectate) {
                    e.setCancelled(true);
                }

                if (e.getPacket() instanceof net.minecraft.network.play.server.S12PacketEntityVelocity)
                    e.setCancelled(true);
                if (e.getPacket() instanceof net.minecraft.network.play.server.S27PacketExplosion)
                    e.setCancelled(true);

                if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook) e.getPacket();
                    if (this.counter < 30)
                        this.counter++;
                    if (!this.firstLagback)
                        this.counter = 15;
                }
                this.firstLagback = true;
        }
    }

    @Override
    public void onState() {

        if (FLY_MODE.getValue().equalsIgnoreCase("Verus2")) {
            MC.thePlayer.jump();
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

    }

    @Override
    public void onUndo() {
        if (FLY_MODE.getValue().equalsIgnoreCase("redesky")) {
            ClientHelper.INSTANCE.doSpeedup(.02);

        }
        ClientHelper.INSTANCE.doSpeedup(.02);
        MC.thePlayer.isCollided = false;
        MC.thePlayer.hurtTime = 0;
        MC.thePlayer.onGround = false;
        MC.timer.timerSpeed = 1;

        MC.thePlayer.capabilities.isFlying = false;
    }
}

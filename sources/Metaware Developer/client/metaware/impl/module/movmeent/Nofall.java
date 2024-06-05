package client.metaware.impl.module.movmeent;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.client.Logger;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.List;

@ModuleInfo(name = "Nofall", renderName = "Nofall", category = Category.MOVEMENT, keybind = Keyboard.KEY_NONE)
public class Nofall extends Module {

    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Vanilla);
    private double lastX, lastY, lastZ;
    private double lastFallDist = 0;
    public static boolean smooth = false;
    private boolean canNegate = true;

    public enum Mode{
        Vanilla, Hypixel, Verus, Packet, BedwarsPractice, AAC, Hypixel_S08
    }

    @Override
    public void onEnable() {
        super.onEnable();
        lastFallDist = 0;
        lastX = 0;
        lastY = 0;
        lastZ = 0;
        canNegate = false;
    }

    private boolean isOverGlass(double x, double y, double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.glass && mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.stained_glass;
    }

    private boolean isInCage() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        for (int i = 0; i < 1; ++i) {
            if (isOverGlass(x - 1, y + i, z) || isOverGlass(x + 1, y + i, z) || isOverGlass(x, y + i, z - 1) || isOverGlass(x, y + i, z + 1)) {
                return false;
            }
        }
        return true;
    }

    @EventHandler
    private final Listener<PacketEvent> packetEventListener = event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;
        if(mode.getValue() == Mode.Hypixel_S08){
            if (canNegate) {
                if (smooth && event.getPacket() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook) event.getPacket();
                   // Logger.print("Cancelled NoFall");
                    event.setCancelled(true);
                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(s08.getX(), s08.getY(), s08.getZ(), s08.getYaw(), s08.getPitch(), false));
                    s08.setX(lastX);
                    s08.setY(lastY + 0.01);
                    s08.setZ(lastZ);
                    smooth = false;
                    mc.thePlayer.setSprinting(false);
                }

                if (mc.thePlayer.fallDistance > 2.5 && !MovementUtils.isOverVoid() && !mc.thePlayer.isPotionActive(Potion.jump)) {
                    if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook) {
                        C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook C06 = (C03PacketPlayer.C04PacketPlayerPosition.C06PacketPlayerPosLook) event.getPacket();
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(C06.getPositionX(), C06.getPositionY(), C06.getPositionZ(), false));
                        event.setCancelled(true);
                    }
                    if (mc.thePlayer.isMoving() && event.getPacket() instanceof C0BPacketEntityAction) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    };

    @EventHandler
    private Listener<UpdatePlayerEvent> eventListener = event -> {
        setSuffix(mode.getValue().toString());
        if(mc.thePlayer == null || mc.theWorld == null) return;
        switch (mode.getValue()) {
            case Vanilla:{
                if (mc.thePlayer.fallDistance > 3) {
                    event.setOnGround(true);
                }
                break;
            }
            case Hypixel:{
                if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3 && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Flight.class).isToggled()) {
                    event.setOnGround(true);
                    mc.thePlayer.fallDistance = 0;
                }
                break;
            }
            case Hypixel_S08:{
                if (canNegate) {
                    if (mc.thePlayer.onGround && lastFallDist > 3 && !mc.thePlayer.isPotionActive(Potion.jump)) {
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(event.getPosX(), event.getPosY() - 0.075, event.getPosZ(), false));
                        mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getPosX(), event.getPosY() - 0.08, event.getPosZ(), event.getYaw(), event.getPitch(), false));
                        mc.thePlayer.setSprinting(true);
                        smooth = true;
                        lastX = event.getPosX();
                        lastY = event.getPosY();
                        lastZ = event.getPosZ();
                    }
                    lastFallDist = mc.thePlayer.fallDistance;
                }
                if (isInCage()) {
                    canNegate = false;
                } else if (!isInCage() && !canNegate && mc.thePlayer.onGround) {
                    canNegate = true;
                }
                break;
            }
            case Packet:{
                if (mc.thePlayer.fallDistance > 3) {
                    PacketUtil.packet(new C03PacketPlayer(true));
                }
                break;
            }
            case Verus:{
                if (mc.thePlayer.fallDistance - mc.thePlayer.motionY >= 3 && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class).isToggled()) {
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.fallDistance = 0;
                    event.setOnGround(true);
                }
                break;
            }
            case BedwarsPractice:{
                if(mc.thePlayer.fallDistance >= 3){
                    event.setOnGround(true);
                    mc.thePlayer.motionY += 0.1;
                    mc.thePlayer.fallDistance = 0.1F;
                }
                break;
            }
            case AAC:{
                if(mc.thePlayer.fallDistance >= 3){
                    event.setOnGround(true);
                    mc.thePlayer.motionY += 0.1;
                    PacketUtil.packet(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.fallDistance, mc.thePlayer.posZ,true));
                    mc.thePlayer.fallDistance = 0.1F;
                }
                break;
            }
        }
    };

}

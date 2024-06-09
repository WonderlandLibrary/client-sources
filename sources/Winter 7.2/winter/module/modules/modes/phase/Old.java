/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.events.BBEvent;
import winter.event.events.TickEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;

public class Old
extends Mode {
    public Phase parent;

    public Old(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
    }

    @Override
    public void onBB(BBEvent event) {
        if (Phase.isInsideBlock() && !Old.mc.gameSettings.keyBindSprint.getIsKeyPressed() && (double)event.getPos().getY() > Old.mc.thePlayer.getEntityBoundingBox().minY - 0.4 && (double)event.getPos().getY() < Old.mc.thePlayer.getEntityBoundingBox().maxY + 1.0) {
            event.setBounds(null);
        }
        if (Phase.isInsideBlock() && Old.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (Old.mc.thePlayer.isCollidedHorizontally && !Phase.isInsideBlock()) {
            if (Old.getDirection().equalsIgnoreCase("EAST")) {
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX + 0.5, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
                Old.mc.thePlayer.setPosition(Old.mc.thePlayer.posX + 1.0, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ);
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Double.POSITIVE_INFINITY, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
            } else if (Old.getDirection().equalsIgnoreCase("WEST")) {
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX - 0.5, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
                Old.mc.thePlayer.setPosition(Old.mc.thePlayer.posX - 1.0, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ);
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Double.POSITIVE_INFINITY, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
            } else if (Old.getDirection().equalsIgnoreCase("NORTH")) {
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ - 0.5, Old.mc.thePlayer.onGround));
                Old.mc.thePlayer.setPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ - 1.0);
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Double.POSITIVE_INFINITY, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
            } else if (Old.getDirection().equalsIgnoreCase("SOUTH")) {
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ + 0.5, Old.mc.thePlayer.onGround));
                Old.mc.thePlayer.setPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ + 1.0);
                Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Double.POSITIVE_INFINITY, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, Old.mc.thePlayer.onGround));
            }
        }
    }

    @Override
    public void onTick(TickEvent event) {
        if (Old.mc.thePlayer.isCollidedHorizontally && Old.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
            Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY - 0.05, Old.mc.thePlayer.posZ, true));
            Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY, Old.mc.thePlayer.posZ, true));
            Old.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Old.mc.thePlayer.posX, Old.mc.thePlayer.posY - 0.05, Old.mc.thePlayer.posZ, true));
        }
    }

    public static String getDirection() {
        return Minecraft.getMinecraft().func_175606_aa().func_174811_aO().name();
    }
}


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

public class Vanilla
extends Mode {
    public Phase parent;

    public Vanilla(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
    }

    @Override
    public void onBB(BBEvent event) {
        if (Phase.isInsideBlock() && !Vanilla.mc.gameSettings.keyBindSprint.getIsKeyPressed() && (double)event.getPos().getY() > Vanilla.mc.thePlayer.getEntityBoundingBox().minY - 0.4 && (double)event.getPos().getY() < Vanilla.mc.thePlayer.getEntityBoundingBox().maxY + 1.0 || Phase.isInsideBlock() && Vanilla.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (Vanilla.mc.thePlayer.isCollidedHorizontally && !Phase.isInsideBlock()) {
            if (Vanilla.getDirection().equalsIgnoreCase("EAST")) {
                Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX + 0.5, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ, Vanilla.mc.thePlayer.onGround));
                Vanilla.mc.thePlayer.setPosition(Vanilla.mc.thePlayer.posX + 1.0, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ);
            } else if (Vanilla.getDirection().equalsIgnoreCase("WEST")) {
                Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX - 0.5, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ, Vanilla.mc.thePlayer.onGround));
                Vanilla.mc.thePlayer.setPosition(Vanilla.mc.thePlayer.posX - 1.0, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ);
            } else if (Vanilla.getDirection().equalsIgnoreCase("NORTH")) {
                Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ - 0.5, Vanilla.mc.thePlayer.onGround));
                Vanilla.mc.thePlayer.setPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ - 1.0);
            } else if (Vanilla.getDirection().equalsIgnoreCase("SOUTH")) {
                Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ + 0.5, Vanilla.mc.thePlayer.onGround));
                Vanilla.mc.thePlayer.setPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ + 1.0);
            }
        }
    }

    @Override
    public void onTick(TickEvent event) {
        if (Vanilla.mc.thePlayer.isCollidedHorizontally && Vanilla.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
            Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY - 0.05, Vanilla.mc.thePlayer.posZ, true));
            Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY, Vanilla.mc.thePlayer.posZ, true));
            Vanilla.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Vanilla.mc.thePlayer.posX, Vanilla.mc.thePlayer.posY - 0.05, Vanilla.mc.thePlayer.posZ, true));
        }
    }

    public static String getDirection() {
        return Minecraft.getMinecraft().func_175606_aa().func_174811_aO().name();
    }
}


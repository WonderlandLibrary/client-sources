package wwe.modules.movement;

import wwe.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import java.util.*;

public class Step extends qMIe
{
    public ModeValue mode;
    
    public Step() {
        super("Step", "Better auto jump", 0, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("New");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.QXzK("Step " + TextFormatting.DARK_GRAY + "Normal");
        }
        if (this.mode.OnGi().equalsIgnoreCase("New")) {
            this.QXzK("Step " + TextFormatting.DARK_GRAY + "New");
        }
        final EntityPlayerSP player = Wrapper.mc.player;
        player.stepHeight = 0.5f;
        if (!player.collidedHorizontally) {
            return;
        }
        if (!player.onGround || player.isOnLadder() || player.isInWater() || player.isInLava()) {
            return;
        }
        if (player.movementInput.moveForward == 0.0f && player.movementInput.moveStrafe == 0.0f) {
            return;
        }
        if (player.movementInput.jump) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            final AxisAlignedBB grow = player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
            if (!Wrapper.mc.world.getCollisionBoxes((Entity)player, grow.offset(0.0, 1.0, 0.0)).isEmpty()) {
                return;
            }
            double maxY = -1.0;
            for (final AxisAlignedBB axisAlignedBB : Wrapper.mc.world.getCollisionBoxes((Entity)player, grow)) {
                if (axisAlignedBB.maxY > maxY) {
                    maxY = axisAlignedBB.maxY;
                }
                if (Step.\u200a\u200d\u2002\u200a\u200f\u2004) {
                    throw null;
                }
            }
            final double n = maxY - player.posY;
            if (n < 0.0 || n > 1.0) {
                return;
            }
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.42 * n, player.posZ, player.onGround));
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.753 * n, player.posZ, player.onGround));
            player.setPosition(player.posX, player.posY + 1.0 * n, player.posZ);
        }
        if (this.mode.OnGi().equalsIgnoreCase("New")) {
            double maxY2 = -1.0;
            final AxisAlignedBB grow2 = player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
            if (!Wrapper.mc.world.getCollisionBoxes((Entity)player, grow2.offset(0.0, 2.0, 0.0)).isEmpty()) {
                return;
            }
            for (final AxisAlignedBB axisAlignedBB2 : Wrapper.mc.world.getCollisionBoxes((Entity)player, grow2)) {
                if (axisAlignedBB2.maxY > maxY2) {
                    maxY2 = axisAlignedBB2.maxY;
                }
                if (Step.\u200a\u200d\u2002\u200a\u200f\u2004) {
                    throw null;
                }
            }
            final double n2 = maxY2 - player.posY;
            if (n2 < 0.0 || n2 > 2.0) {
                return;
            }
            if (n2 == 2.0) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.42, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.78, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.63, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.51, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.9, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.21, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.45, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.43, player.posZ, player.onGround));
                player.setPosition(player.posX, player.posY + 2.0, player.posZ);
            }
            if (n2 == 1.5) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.41999998688698, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.7531999805212, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.00133597911214, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.16610926093821, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.24918707874468, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 1.1707870772188, player.posZ, player.onGround));
                player.setPosition(player.posX, player.posY + 1.0, player.posZ);
            }
            if (n2 == 1.0) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.41999998688698, player.posZ, player.onGround));
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(player.posX, player.posY + 0.7531999805212, player.posZ, player.onGround));
                player.setPosition(player.posX, player.posY + 1.0, player.posZ);
            }
        }
    }
}

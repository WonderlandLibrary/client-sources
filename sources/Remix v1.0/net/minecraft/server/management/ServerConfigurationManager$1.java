package net.minecraft.server.management;

import net.minecraft.world.border.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

class ServerConfigurationManager$1 implements IBorderListener {
    @Override
    public void onSizeChanged(final WorldBorder border, final double newSize) {
        ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
    }
    
    @Override
    public void func_177692_a(final WorldBorder border, final double p_177692_2_, final double p_177692_4_, final long p_177692_6_) {
        ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
    }
    
    @Override
    public void onCenterChanged(final WorldBorder border, final double x, final double z) {
        ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
    }
    
    @Override
    public void onWarningTimeChanged(final WorldBorder border, final int p_177691_2_) {
        ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
    }
    
    @Override
    public void onWarningDistanceChanged(final WorldBorder border, final int p_177690_2_) {
        ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
    }
    
    @Override
    public void func_177696_b(final WorldBorder border, final double p_177696_2_) {
    }
    
    @Override
    public void func_177695_c(final WorldBorder border, final double p_177695_2_) {
    }
}
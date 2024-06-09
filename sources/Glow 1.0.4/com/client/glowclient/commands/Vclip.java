package com.client.glowclient.commands;

import net.minecraft.client.entity.*;
import com.client.glowclient.utils.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import com.client.glowclient.*;
import javax.annotation.*;

public class Vclip extends Command
{
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("vclip").toString();
    }
    
    private void M(final double n, final double n2, final double n3) {
        final Entity m;
        final Entity entity = m = M();
        entity.setPositionAndUpdate(n, n2, n3);
        if (entity instanceof EntityPlayerSP) {
            Ob.M().sendPacket((Packet)new CPacketPlayer$Position(m.posX, m.posY, m.posZ, Wrapper.mc.player.onGround));
            return;
        }
        Ob.M().sendPacket((Packet)new CPacketVehicleMove(m));
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 2) {
            qd.D("§cNot enough data given");
        }
        try {
            this.M(Double.parseDouble(array[1]));
            qd.D(new StringBuilder().insert(0, "§bVClipped ").append(array[2]).append(" blocks").toString());
        }
        catch (Exception ex) {}
    }
    
    @Nullable
    public static Entity M() {
        if (Ob.M() != null) {
            return Ob.M();
        }
        return (Entity)Wrapper.mc.player;
    }
    
    public Vclip() {
        super("vclip");
    }
    
    private void M(final double n) {
        final Entity m = M();
        this.M(m.posX, m.posY + n, m.posZ);
    }
}

package wwe;

import wwe.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class leIC extends HAxG
{
    public leIC() {
        super("clip");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        if (array.length < 2) {
            HAxG.TuzD(this.ZlRY());
            return;
        }
        if (array[0].equalsIgnoreCase("v")) {
            final Double value = Double.parseDouble(array[1]);
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY + value, Wrapper.mc.player.posZ, false));
            Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY + value, Wrapper.mc.player.posZ);
            HAxG.TuzD("Clipped " + value + ", blocks vertically");
            if (leIC.\u2009\u200d) {
                throw null;
            }
        }
        else {
            if (!array[0].equalsIgnoreCase("h")) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            final Double value2 = Double.parseDouble(array[1]);
            Wrapper.mc.player.getLookVec();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.mc.player.posX + Math.cos(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f)) * value2, Wrapper.mc.player.posY, Wrapper.mc.player.posZ + Math.sin(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f)) * value2, false));
            Wrapper.mc.player.setPosition(Wrapper.mc.player.posX + Math.cos(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f)) * value2, Wrapper.mc.player.posY, Wrapper.mc.player.posZ + Math.sin(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f)) * value2);
            HAxG.TuzD("Clipped " + value2 + ", blocks horizontally");
            if (leIC.\u2009\u200d) {
                throw null;
            }
        }
    }
    
    @Override
    public String UiIp() {
        return "Teleport blocks";
    }
    
    @Override
    public String ZlRY() {
        return "clip v/h amount";
    }
}

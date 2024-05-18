package wwe.modules.combat;

import java.util.*;
import net.minecraft.network.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import wwe.*;

public class Criticals extends qMIe
{
    public static ArrayList<Packet> Oyee;
    
    public Criticals() {
        super("Criticals", "Automatically does critical attacks", 0, Category.COMBAT);
    }
    
    @oqrr
    public void fNaG(final MUMg muMg) {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.player.isInWater() || Wrapper.mc.player.isInLava()) {
            return;
        }
        if (muMg.SiIq() instanceof EntityEnderCrystal) {
            Wrapper.mc.effectRenderer.emitParticleAtEntity(muMg.SiIq(), EnumParticleTypes.CRIT);
            Wrapper.mc.effectRenderer.emitParticleAtEntity(muMg.SiIq(), EnumParticleTypes.CRIT);
            Wrapper.mc.effectRenderer.emitParticleAtEntity(muMg.SiIq(), EnumParticleTypes.CRIT);
            Wrapper.mc.effectRenderer.emitParticleAtEntity(muMg.SiIq(), EnumParticleTypes.CRIT);
            return;
        }
        if (Wrapper.mc.player.onGround) {
            Criticals.Oyee.clear();
            final CPacketPlayer.Position position = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 0.1625, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position);
            Wrapper.mc.player.connection.sendPacket((Packet)position);
            final CPacketPlayer.Position position2 = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position2);
            Wrapper.mc.player.connection.sendPacket((Packet)position2);
            final CPacketPlayer.Position position3 = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 4.0E-6, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position3);
            Wrapper.mc.player.connection.sendPacket((Packet)position3);
            final CPacketPlayer.Position position4 = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position4);
            Wrapper.mc.player.connection.sendPacket((Packet)position4);
            final CPacketPlayer.Position position5 = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 1.0E-6, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position5);
            Wrapper.mc.player.connection.sendPacket((Packet)position5);
            final CPacketPlayer.Position position6 = new CPacketPlayer.Position(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ, false);
            Criticals.Oyee.add((Packet)position6);
            Wrapper.mc.player.connection.sendPacket((Packet)position6);
            final CPacketPlayer cPacketPlayer = new CPacketPlayer();
            Criticals.Oyee.add((Packet)cPacketPlayer);
            Wrapper.mc.player.connection.sendPacket((Packet)cPacketPlayer);
            Wrapper.mc.player.onCriticalHit(muMg.SiIq());
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    static {
        Criticals.Oyee = new ArrayList<Packet>();
    }
}

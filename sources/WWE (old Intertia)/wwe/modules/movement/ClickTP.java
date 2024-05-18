package wwe.modules.movement;

import net.minecraft.util.math.*;
import wwe.modules.*;
import wwe.utils.*;
import java.awt.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import wwe.*;

public class ClickTP extends qMIe
{
    public BlockPos QHCu;
    
    public ClickTP() {
        super("Click TP", "Allows you to teleport where your crosshair is", 0, Category.MOVEMENT);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.QHCu = Wrapper.mc.player.rayTrace(70.0, yYGD.XgOL().renderPartialTicks).getBlockPos();
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        BIIV.TGTO(this.QHCu, Color.red, 1.0, 1.0);
    }
    
    @oqrr
    public void vvOZ(final KhbB khbB) {
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.QHCu.getX() + 0.5, (double)(this.QHCu.getY() + 1), this.QHCu.getZ() + 0.5, true));
        Wrapper.mc.player.setPosition(this.QHCu.getX() + 0.5, (double)(this.QHCu.getY() + 1), this.QHCu.getZ() + 0.5);
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}

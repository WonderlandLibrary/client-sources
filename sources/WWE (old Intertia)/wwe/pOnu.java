package wwe;

import net.minecraft.entity.*;
import wwe.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class pOnu extends HAxG
{
    public Entity FDBx;
    public boolean zqQw;
    
    public pOnu() {
        super("vanish");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        if (array.length < 1) {
            HAxG.TuzD(this.ZlRY());
            return;
        }
        if (!array[0].equalsIgnoreCase("dismount") && !array[0].equalsIgnoreCase("remount")) {
            HAxG.TuzD(this.ZlRY());
            return;
        }
        if (array[0].equalsIgnoreCase("dismount")) {
            this.zqQw = true;
            if (pOnu.\u200a\u200b\u2003\u200a\u200a) {
                throw null;
            }
        }
        else {
            if (!array[0].equalsIgnoreCase("remount")) {
                HAxG.TuzD("dismount or remount, not " + array[0]);
                return;
            }
            this.zqQw = false;
            if (pOnu.\u200a\u200b\u2003\u200a\u200a) {
                throw null;
            }
        }
        if (Wrapper.mc.player.getRidingEntity() != null && this.zqQw) {
            Wrapper.mc.renderGlobal.loadRenderers();
            this.FDBx = Wrapper.mc.player.getRidingEntity();
            Wrapper.mc.player.dismountRidingEntity();
            Wrapper.mc.world.removeEntity(this.FDBx);
            hSNE.lyvR(this);
            if (pOnu.\u200a\u200b\u2003\u200a\u200a) {
                throw null;
            }
        }
        else if (!this.zqQw) {
            this.FDBx.isDead = false;
            Wrapper.mc.world.loadedEntityList.add(this.FDBx);
            Wrapper.mc.player.startRiding(this.FDBx, true);
            this.FDBx = null;
            hSNE.kduK(this);
        }
    }
    
    @Override
    public String UiIp() {
        return "Do the god mode exploit from Potentia client";
    }
    
    @Override
    public String ZlRY() {
        return "vanish dismount/remount";
    }
    
    @oqrr
    public void eoeu(final omqH omqH) {
        try {
            if (Wrapper.mc.player.getRidingEntity() != null) {
                this.FDBx = null;
            }
            if (this.FDBx == null && Wrapper.mc.player.getRidingEntity() != null) {
                this.FDBx = Wrapper.mc.player.getRidingEntity();
                Wrapper.mc.player.dismountRidingEntity();
                Wrapper.mc.world.removeEntity(this.FDBx);
            }
            if (this.FDBx != null) {
                this.FDBx.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ);
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.FDBx));
            }
            if (pOnu.\u200a\u200b\u2003\u200a\u200a) {
                throw null;
            }
        }
        catch (Exception ex) {
            HAxG.TuzD("This really shouldent be happening):");
        }
    }
}

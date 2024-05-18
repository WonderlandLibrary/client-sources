package wwe.modules.movement;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import wwe.*;

public class Sneak extends qMIe
{
    public ModeValue mode;
    
    public Sneak() {
        super("Sneak", "Makes you sneak", 0, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Packet");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
    }
    
    @oqrr
    public void ulkK(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Sneak" + TextFormatting.DARK_GRAY + " Normal");
            yYGD.rvRL(Wrapper.mc.gameSettings.keyBindSneak, true);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.owgW("Sneak" + TextFormatting.DARK_GRAY + " Packet");
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @oqrr
    public void phcl(final ILBH ilbh) {
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        yYGD.rvRL(Wrapper.mc.gameSettings.keyBindSneak, false);
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }
}

package com.client.glowclient.modules.server;

import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import net.minecraft.util.text.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;

public class Dupe extends ModuleContainer
{
    public static nB mode;
    private int B;
    
    @Override
    public void D() {
        if (Dupe.mode.e().equals("Donkey") && Wrapper.mc.player != null) {
            Ob.M().sendPacket((Packet)new CPacketPlayer$PositionRotation(Wrapper.mc.player.posX, 1000.0 + Wrapper.mc.player.posY, Wrapper.mc.player.posZ, Wrapper.mc.player.rotationYaw, Wrapper.mc.player.rotationPitch, true));
        }
        if (Dupe.mode.e().equals("DonkeyRelog")) {
            this.B = 0;
        }
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        if ((Dupe.mode.e().equals("Donkey") || Dupe.mode.e().equals("DonkeyRelog")) && cd.getPacket() instanceof CPacketConfirmTeleport) {
            cd.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        if (Dupe.mode.e().equals("DonkeyRelog")) {
            final float n = 1.0f;
            qd.D(Integer.toString(this.B));
            Ta.M(n);
            ++this.B;
            if (this.B >= 225) {
                Ob.M().sendPacket((Packet)new CPacketConfirmTeleport());
                Ob.M().closeChannel((ITextComponent)new TextComponentString("Reconnecting..."));
                this.B = 0;
            }
        }
    }
    
    static {
        Dupe.mode = ValueFactory.M("Dupe", "Mode", "Mode of Dupe", "Donkey", "Donkey", "DonkeyRelog", "BedGod");
    }
    
    public Dupe() {
        final int b = 0;
        super(Category.SERVER, "Dupe", false, -1, "Assistive module for duplication bugs");
        this.B = b;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Dupe.mode.e().equals("BedGod")) {
            Wrapper.mc.player.sleeping = false;
            Wrapper.mc.player.sleepTimer = 0;
        }
    }
    
    @Override
    public String M() {
        return Dupe.mode.e();
    }
    
    @SubscribeEvent
    public void M(final GuiOpenEvent guiOpenEvent) {
        if (Dupe.mode.e().equals("BedGod") && guiOpenEvent.getGui() instanceof GuiSleepMP) {
            guiOpenEvent.setCanceled(true);
        }
    }
}

package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public final class God extends ModBase
{
    private long stored;
    
    public God() {
        super("God", "G", true, ".t god");
        this.stored = -1L;
        this.setDescription("Ti rende immortale.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && (this.stored + 1000L < System.currentTimeMillis() || this.stored == -1L)) {
            MorbidWrapper.mcObj();
            final NetClientHandler sendQueue = Minecraft.thePlayer.sendQueue;
            MorbidWrapper.mcObj();
            final double posX = Minecraft.thePlayer.posX;
            MorbidWrapper.mcObj();
            final double par3 = Minecraft.thePlayer.boundingBox.minY - 0.2;
            MorbidWrapper.mcObj();
            final double par4 = Minecraft.thePlayer.posY - 1.5;
            MorbidWrapper.mcObj();
            final double posZ = Minecraft.thePlayer.posZ;
            MorbidWrapper.mcObj();
            sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, par3, par4, posZ, Minecraft.thePlayer.onGround));
            this.stored = System.currentTimeMillis();
        }
    }
}

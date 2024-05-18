package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.util.*;

public class NoSlowdown extends ModBase
{
    public NoSlowdown() {
        super("NoSlowdown", "0", true, ".t noslow");
    }
    
    @Override
    public void preUpdate() {
        if (KillAura.curTarget != null && !MorbidWrapper.mcObj().ingameGUI.persistantChatGUI.getChatOpen()) {
            final boolean var11 = true;
        }
        else {
            final boolean var11 = false;
        }
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.isBlocking()) {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, -255));
        }
    }
    
    @Override
    public void postMotionUpdate() {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.isBlocking()) {
            final Packet15Place var1 = new Packet15Place(-1, -1, -1, 255, MorbidWrapper.getPlayer().inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f);
            MorbidHelper.sendPacket(var1);
        }
    }
}

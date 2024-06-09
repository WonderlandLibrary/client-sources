package com.client.glowclient.modules.combat;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.item.*;
import com.client.glowclient.utils.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class Criticals extends ModuleContainer
{
    public boolean b;
    
    public Criticals() {
        final boolean b = true;
        super(Category.COMBAT, "Criticals", false, -1, "Causes a critical hit every time");
        this.b = b;
    }
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        if (eventClientPacket.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)eventClientPacket.getPacket()).getAction() == CPacketUseEntity$Action.ATTACK && !(((CPacketUseEntity)eventClientPacket.getPacket()).getEntityFromWorld((World)Wrapper.mc.world) instanceof EntityEnderCrystal)) {
            this.d();
        }
    }
    
    public void d() {
        final EntityPlayerSP player;
        if (!((EntityPlayer)(player = Wrapper.mc.player)).onGround) {
            return;
        }
        if (((EntityPlayer)player).isInWater() || ((EntityPlayer)player).isInLava()) {
            return;
        }
        final boolean b = this.b;
        final EntityPlayer entityPlayer = (EntityPlayer)player;
        if (b) {
            final double posX = entityPlayer.posX;
            final EntityPlayer entityPlayer2 = (EntityPlayer)player;
            final double posY = entityPlayer2.posY;
            final double posZ = entityPlayer2.posZ;
            final NetworkManager networkManager2;
            final NetworkManager networkManager = networkManager2 = Wrapper.mc.getConnection().getNetworkManager();
            networkManager.sendPacket((Packet)new CPacketPlayer$Position(posX, posY + 0.0625, posZ, true));
            networkManager2.sendPacket((Packet)new CPacketPlayer$Position(posX, posY, posZ, false));
            networkManager.sendPacket((Packet)new CPacketPlayer$Position(posX, posY + 1.1E-5, posZ, false));
            networkManager.sendPacket((Packet)new CPacketPlayer$Position(posX, posY, posZ, false));
            return;
        }
        entityPlayer.motionY = 0.10000000149011612;
        final boolean onGround = false;
        final EntityPlayerSP entityPlayerSP = player;
        ((EntityPlayer)entityPlayerSP).fallDistance = 0.1f;
        ((EntityPlayer)entityPlayerSP).onGround = onGround;
    }
}

package com.client.glowclient.modules.jewishtricks;

import net.minecraft.client.entity.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class SpawnPlayer extends ModuleContainer
{
    private EntityOtherPlayerMP b;
    
    public SpawnPlayer() {
        super(Category.JEWISH TRICKS, "SpawnPlayer", false, -1, "Spawns a player entity with your rotation");
    }
    
    @Override
    public void E() {
        try {
            Wrapper.mc.world.removeEntityFromWorld(-109);
            this.b = null;
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (this.b != null) {
            this.b.rotationYawHead = Wrapper.mc.player.rotationYawHead;
            this.b.rotationYaw = Wrapper.mc.player.rotationYaw;
            this.b.rotationPitch = Wrapper.mc.player.rotationPitch;
            if (Wrapper.mc.player.getHeldItemMainhand() != null) {
                this.b.setHeldItem(EnumHand.MAIN_HAND, Wrapper.mc.player.getHeldItemMainhand());
            }
            if (Wrapper.mc.player.getHeldItemOffhand() != null) {
                this.b.setHeldItem(EnumHand.OFF_HAND, Wrapper.mc.player.getHeldItemOffhand());
            }
        }
    }
    
    @Override
    public void D() {
        try {
            (this.b = new EntityOtherPlayerMP((World)Wrapper.mc.world, Wrapper.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Wrapper.mc.player);
            this.b.rotationYawHead = Wrapper.mc.player.rotationYawHead;
            Wrapper.mc.world.addEntityToWorld(-109, (Entity)this.b);
        }
        catch (Exception ex) {}
    }
}

package com.client.glowclient.modules.player;

import net.minecraftforge.fml.common.gameevent.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.player.*;

public class AutoFish extends ModuleContainer
{
    public final int f = 20;
    public final int M = 0;
    public final double G = 2.0;
    private boolean d;
    private int L;
    private int B;
    
    private void a() {
        final boolean d = false;
        final int n = 0;
        this.B = n;
        this.L = n;
        this.d = d;
    }
    
    @SubscribeEvent
    public void M(final InputEvent$MouseInputEvent inputEvent$MouseInputEvent) {
        if (Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown() && this.L > 0) {
            this.B = 20;
        }
    }
    
    @Override
    public void D() {
        this.a();
    }
    
    private void d() {
        try {
            if (this.B <= 0) {
                Wrapper.mc.rightClickMouse();
                this.B = 20;
            }
        }
        catch (Exception ex) {}
    }
    
    public AutoFish() {
        final int m = 0;
        final double g = 2.0;
        final int f = 20;
        final boolean b = false;
        final int b2 = 0;
        super(Category.PLAYER, "AutoFish", false, -1, "Automatically fishes");
        this.B = b2;
        this.L = (b ? 1 : 0);
        this.d = b;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final EntityPlayerSP player;
        final ItemStack heldItemMainhand = ((EntityPlayer)(player = Wrapper.mc.player)).getHeldItemMainhand();
        ItemStack itemStack;
        if (this.B > 20) {
            itemStack = heldItemMainhand;
            this.B = 20;
        }
        else {
            if (this.B > 0) {
                --this.B;
            }
            itemStack = heldItemMainhand;
        }
        if (itemStack == null || !(heldItemMainhand.getItem() instanceof ItemFishingRod)) {
            this.a();
            return;
        }
        if (!this.d) {
            final boolean d = true;
            this.B = 20;
            this.d = d;
            return;
        }
        if (((EntityPlayer)player).fishEntity == null) {
            this.d();
            return;
        }
        ++this.L;
    }
    
    private boolean M(final SPacketSoundEffect sPacketSoundEffect) {
        final EntityPlayerSP player = Wrapper.mc.player;
        return sPacketSoundEffect.getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH) && player != null && player.fishEntity != null && player.fishEntity.getPositionVector().distanceTo(new Vec3d(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ())) <= 2.0;
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketSoundEffect && this.M((SPacketSoundEffect)eventServerPacket.getPacket())) {
            this.d();
        }
    }
}

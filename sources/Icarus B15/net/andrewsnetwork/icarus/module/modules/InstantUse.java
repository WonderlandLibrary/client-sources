// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class InstantUse extends Module
{
    public final Value<Float> ticks;
    public final Value<Float> bowTicks;
    
    public InstantUse() {
        super("InstantUse", -5985391, Category.EXPLOITS);
        this.ticks = (Value<Float>)new ConstrainedValue("instantuse_Ticks", "ticks", 9.0f, 1.0f, 20.0f, this);
        this.bowTicks = (Value<Float>)new ConstrainedValue("instantuse_Bow Ticks", "bowticks", 15.0f, 1.0f, 20.0f, this);
        this.setTag("Instant Use");
    }
    
    public boolean isUsable(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (InstantUse.mc.thePlayer.isUsingItem()) {
            if (stack.getItem() instanceof ItemFood) {
                return true;
            }
            if (stack.getItem() instanceof ItemPotion) {
                return true;
            }
            if (stack.getItem() instanceof ItemBucketMilk) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isBow(final ItemStack stack) {
        return stack != null && (InstantUse.mc.thePlayer.isUsingItem() && stack.getItem() instanceof ItemBow);
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (event instanceof PostMotion) {
            if (this.isUsable(InstantUse.mc.thePlayer.getCurrentEquippedItem()) && InstantUse.mc.thePlayer.getItemInUseDuration() > this.ticks.getValue()) {
                for (int x = 0; x < 32; ++x) {
                    InstantUse.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(InstantUse.mc.thePlayer.onGround));
                }
                InstantUse.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                InstantUse.mc.thePlayer.stopUsingItem();
            }
            else if (this.isBow(InstantUse.mc.thePlayer.getCurrentEquippedItem()) && InstantUse.mc.thePlayer.getItemInUseDuration() > this.bowTicks.getValue()) {
                for (int x = 0; x < 24; ++x) {
                    InstantUse.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(InstantUse.mc.thePlayer.onGround));
                }
                InstantUse.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                InstantUse.mc.thePlayer.stopUsingItem();
            }
        }
        else if (event instanceof PreMotion) {
            if (this.isUsable(InstantUse.mc.thePlayer.getCurrentEquippedItem()) && InstantUse.mc.thePlayer.isUsingItem()) {
                this.setColor(-17664);
            }
            else {
                this.setColor(-5985391);
            }
        }
    }
}

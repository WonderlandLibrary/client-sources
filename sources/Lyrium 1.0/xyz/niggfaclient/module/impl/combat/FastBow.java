// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.item.ItemBow;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "FastBow", description = "Allows you use bow faster", cat = Category.COMBAT)
public class FastBow extends Module
{
    private final DoubleProperty delay;
    private final TimerUtil timer;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public FastBow() {
        this.delay = new DoubleProperty("Delay", 100.0, 1.0, 750.0, 1.0, Representation.MILLISECONDS);
        this.timer = new TimerUtil();
        int i;
        this.updateEventListener = (e -> {
            if (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && this.mc.thePlayer.isUsingItem()) {
                if (this.timer.hasElapsed(this.delay.getValue().longValue())) {
                    for (i = 0; i < 10; ++i) {
                        this.mc.rightClickDelayTimer = 0;
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
                    }
                    this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
                    this.timer.reset();
                }
            }
            else {
                this.mc.rightClickDelayTimer = 0;
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.rightClickDelayTimer = 6;
    }
}

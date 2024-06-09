// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import xyz.niggfaclient.utils.player.PlayerUtil;
import net.minecraft.block.BlockLiquid;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.CollideEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Jesus", description = "Allows you to walk on water", cat = Category.PLAYER)
public class Jesus extends Module
{
    private final EnumProperty<Mode> mode;
    @EventLink
    private final Listener<CollideEvent> collideEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    private final Listener<PacketEvent> packetEventListener;
    
    public Jesus() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Vanilla);
        this.collideEventListener = (e -> {
            switch (this.mode.getValue()) {
                case Vanilla: {
                    if (e.getBlock() instanceof BlockLiquid && !PlayerUtil.isInLiquid() && !this.mc.thePlayer.isSneaking()) {
                        e.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).contract(0.0, 0.0, 0.0).offset(e.getX(), e.getY(), e.getZ()));
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case NCP: {
                    if (e.getBlock() instanceof BlockLiquid && !PlayerUtil.isInLiquid() && !this.mc.thePlayer.isSneaking()) {
                        e.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).contract(0.0, 2.000111E-12, 0.0).offset(e.getX(), e.getY(), e.getZ()));
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            switch (this.mode.getValue()) {
                case Vanilla: {
                    if (PlayerUtil.isInLiquid() && !this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.thePlayer.fallDistance < 3.0f) {
                        e.setOnGround(false);
                        this.mc.thePlayer.motionY = 0.1;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case NCP: {
                    if (PlayerUtil.isInLiquid() && !this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.thePlayer.fallDistance < 3.0f) {
                        this.mc.thePlayer.motionY = 0.1;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        C03PacketPlayer c03;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND) {
                switch (this.mode.getValue()) {
                    case NCP: {
                        if (e.getPacket() instanceof C03PacketPlayer && PlayerUtil.isOnLiquid() && !this.mc.thePlayer.isSneaking()) {
                            c03 = (C03PacketPlayer)e.getPacket();
                            c03.setY(c03.getY() + ((this.mc.thePlayer.ticksExisted % 2 == 0) ? 2.000111E-12 : 0.0));
                            c03.setOnGround(this.mc.thePlayer.ticksExisted % 2 != 0);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
        });
    }
    
    public enum Mode
    {
        Vanilla, 
        NCP;
    }
}
